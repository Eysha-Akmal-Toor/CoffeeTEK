package com.example.coffeetek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the button
        Button showCustomListButton1 = findViewById(R.id.signupbtnn);

        // Set the click listener
        showCustomListButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        // Initialize the button
        Button showCustomListButton2 = findViewById(R.id.loginbtnn);

        // Set the click listener
        showCustomListButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
