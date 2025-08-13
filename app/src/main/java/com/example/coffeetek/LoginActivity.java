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
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtSignInEmail, edtSignInPassword;
    private TextInputLayout emailLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        TextView txtSignUp = findViewById(R.id.txtSignUp);
        ImageView backArrow = findViewById(R.id.back_arrow);
        edtSignInEmail = findViewById(R.id.edtSignInEmail);
        edtSignInPassword = findViewById(R.id.edtSignInPassword);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        // Handle Sign Up navigation
        txtSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle Back Arrow navigation
        backArrow.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle Login button click
        findViewById(R.id.signupButton).setOnClickListener(view -> validateInputs());
    }

    // Close the keyboard when clicking outside the fields
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

    // Validate input fields and show error messages if invalid
    private void validateInputs() {
        String email = edtSignInEmail.getText() != null ? edtSignInEmail.getText().toString().trim() : "";
        String password = edtSignInPassword.getText() != null ? edtSignInPassword.getText().toString().trim() : "";

        boolean isValid = true;

        // Check if email is empty
        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Please enter your email.");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Please enter a valid email address.");
            isValid = false;
        } else {
            emailLayout.setError(null); // Clear error
        }

        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Please enter your password.");
            isValid = false;
        } else {
            passwordLayout.setError(null); // Clear error
        }

        // If both email and password are valid, check against stored values
        if (isValid) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String storedEmail = sharedPreferences.getString("email", "");
            String storedPassword = sharedPreferences.getString("password", "");

            if (email.equals(storedEmail) && password.equals(storedPassword)) {
                // Navigate to the Profile Activity if credentials match
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Show error if credentials don't match
                emailLayout.setError("Invalid email or password.");
                passwordLayout.setError("Invalid email or password.");
            }
        }
    }
}
