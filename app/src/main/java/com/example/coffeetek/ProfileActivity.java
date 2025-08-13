package com.example.coffeetek;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtProfileInitial, txtProfileName, txtProfileEmail, txtProfilePhone, txtProfileCity, txtProfilePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI elements
        txtProfileName = findViewById(R.id.edtSignInName);
        txtProfileEmail = findViewById(R.id.edtSignInEmail);
        txtProfilePhone = findViewById(R.id.edtSignInPhoneno);
        txtProfileCity = findViewById(R.id.edtSignInCity);
        txtProfilePassword = findViewById(R.id.edtSignInPassword);
        txtProfileInitial = findViewById(R.id.profileInitial);
        ImageView backArrow = findViewById(R.id.back_arrow);

        // Handle Back Arrow navigation (to Home)
        backArrow.setOnClickListener(view -> navigateToHome());

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "N/A");
        String fullName = sharedPreferences.getString("fullName", "N/A");
        String phone = sharedPreferences.getString("phone", "N/A");
        String city = sharedPreferences.getString("city", "N/A");
        String password = sharedPreferences.getString("password", "N/A");

        // Set the data to UI elements
        txtProfileName.setText(fullName);
        txtProfileEmail.setText(email);
        txtProfilePhone.setText(phone);
        txtProfileCity.setText(city);
        txtProfilePassword.setText(password);

        // Set the initial letter to profileInitial TextView
        if (fullName != null && !fullName.isEmpty()) {
            String initial = fullName.substring(0, 1).toUpperCase(); // Extract and capitalize the first letter
            txtProfileInitial.setText(initial); // Set the initial letter
        }
    }

    // Navigate to HomeActivity
    private void navigateToHome() {
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
