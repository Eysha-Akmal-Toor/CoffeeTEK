package com.example.coffeetek;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the splash screen layout
        setContentView(R.layout.activity_splash_screen);

        // Delay of 3 seconds before switching to the next activity
        new Handler().postDelayed(() -> {
            // Navigate to MainActivity
            Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
            startActivity(intent);

            // Add transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Finish the SplashScreenActivity
            finish();
        }, 3000);
    }
}