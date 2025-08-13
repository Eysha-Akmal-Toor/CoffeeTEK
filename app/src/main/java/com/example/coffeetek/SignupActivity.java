package com.example.coffeetek;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText edtSignInName, edtSignInEmail, edtSignInPhoneno, edtSignInCity, edtSignInPassword;
    private TextInputLayout nameLayout, emailLayout, phonenoLayout, cityLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        edtSignInName = findViewById(R.id.edtSignInName);
        edtSignInEmail = findViewById(R.id.edtSignInEmail);
        edtSignInPhoneno = findViewById(R.id.edtSignInPhoneno);
        edtSignInCity = findViewById(R.id.edtSignInCity);
        edtSignInPassword = findViewById(R.id.edtSignInPassword);
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        phonenoLayout = findViewById(R.id.PhonenoLayout);
        cityLayout = findViewById(R.id.CityLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        TextView txtSignUp = findViewById(R.id.txtSignUp);
        ImageView backArrow = findViewById(R.id.back_arrow);

        // Handle Sign Up navigation (to Login)
        txtSignUp.setOnClickListener(view -> navigateToLogin());

        // Handle Back Arrow navigation (to Login)
        backArrow.setOnClickListener(view -> navigateToRegister());

        // Handle Signup Button click event
        Button Signup = findViewById(R.id.signupButton);
        Signup.setOnClickListener(v -> {
            validateInputs();
            // Show a Toast message when the user is registered
            Toast.makeText(SignupActivity.this, "Registered Successfully! ", Toast.LENGTH_SHORT).show();
        });
    }

    private void navigateToRegister() {
        Intent intent = new Intent(SignupActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    private void navigateToLogin() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void validateInputs() {
        String fullName = edtSignInName.getText() != null ? edtSignInName.getText().toString().trim() : "";
        String email = edtSignInEmail.getText() != null ? edtSignInEmail.getText().toString().trim() : "";
        String phone = edtSignInPhoneno.getText() != null ? edtSignInPhoneno.getText().toString().trim() : "";
        String city = edtSignInCity.getText() != null ? edtSignInCity.getText().toString().trim() : "";
        String password = edtSignInPassword.getText() != null ? edtSignInPassword.getText().toString().trim() : "";

        boolean isValid = true;

        // Validation checks
        if (TextUtils.isEmpty(fullName)) {
            nameLayout.setError("Please enter your full name.");
            isValid = false;
        } else {
            nameLayout.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Please enter your email.");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Please enter a valid email address.");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            phonenoLayout.setError("Please enter your phone number.");
            isValid = false;
        } else if (phone.length() != 11) {
            phonenoLayout.setError("Phone number must be exactly 11 digits.");
            isValid = false;
        } else {
            phonenoLayout.setError(null);
        }

        if (TextUtils.isEmpty(city)) {
            cityLayout.setError("Please enter your city.");
            isValid = false;
        } else {
            cityLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Please enter your password.");
            isValid = false;
        } else if (password.length() < 8) {
            passwordLayout.setError("Password must be at least 8 characters long.");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        // If all inputs are valid
        if (isValid) {
            // Store data in SharedPreferences for LoginActivity and ProfileActivity
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("fullName", fullName);
            editor.putString("email", email);
            editor.putString("phone", phone);
            editor.putString("city", city);
            editor.putString("password", password);
            editor.apply();

            // Navigate to LoginActivity
            Intent loginIntent = new Intent(SignupActivity.this, RegisterActivity.class);
            loginIntent.putExtra("EMAIL", email);
            loginIntent.putExtra("PASSWORD", password); // Optional, pass data if needed
            startActivity(loginIntent);

            finish();
        }
    }

}
