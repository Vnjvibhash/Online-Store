package in.innovateria.onlinestore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.AnimationUtils;
import in.innovateria.onlinestore.Utils.Constant;


public class IntroActivity extends AppCompatActivity {
    AppCompatButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        splashLogic();

        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
        });

    }

    private void splashLogic() {
        View splashScreenHolder = findViewById(R.id.splash_screen_holder);
        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(() -> {
            AnimationUtils.slideToEndWithFadeOut(splashScreenHolder, this);
            splashScreenHolder.setVisibility(View.GONE);
        }, 3000);
    }

}