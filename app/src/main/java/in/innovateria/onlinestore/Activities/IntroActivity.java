package in.innovateria.onlinestore.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import in.innovateria.onlinestore.R;


public class IntroActivity extends AppCompatActivity {
    AppCompatButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
        });

    }
}