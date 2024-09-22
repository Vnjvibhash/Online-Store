package in.innovateria.onlinestore.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.SignInButton;
import com.hbb20.CountryCodePicker;

import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.Constant;

public class LogInActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText editTextPhoneNumber;
    private AppCompatButton buttonLogin;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        countryCodePicker = findViewById(R.id.ccp);
        editTextPhoneNumber = findViewById(R.id.et_mobile);
        buttonLogin = findViewById(R.id.loginButton);

        buttonLogin.setText("Send OTP");

        buttonLogin.setOnClickListener(v -> {
            String countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNumber)) {
                editTextPhoneNumber.setError("Required Field");
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            } else {
                String fullPhoneNumber = countryCode + phoneNumber;
                Toast.makeText(this, "Phone Number: " + fullPhoneNumber, Toast.LENGTH_LONG).show();
                buttonLogin.setText("Verify OTP");
            }
        });
    }
}