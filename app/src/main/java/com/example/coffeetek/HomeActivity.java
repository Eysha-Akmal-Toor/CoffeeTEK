package com.example.coffeetek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageView icon1, icon2, icon3, icon4;
    private View cursor;
    private int iconWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Link the layout file
        // Initialize bottom navigation icons and cursor
        icon1 = findViewById(R.id.menu_icon1);
        icon2 = findViewById(R.id.menu_icon2);
        icon3 = findViewById(R.id.menu_icon3);
        icon4 = findViewById(R.id.menu_icon4);
        cursor = findViewById(R.id.cursor);

        // Measure icon width for cursor movement
        icon1.post(() -> iconWidth = icon1.getWidth());

        // Set click listeners for each icon
        // Set the home icon to "dark" and move cursor to its position initially
        icon1.setImageResource(R.drawable.home_dark); // Highlight home icon
        icon1.setOnClickListener(v -> {
            resetIcons();
            icon1.setImageResource(R.drawable.home_dark);
            moveCursorTo(0);
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish(); // Ends the current activity
        });

        icon2.setOnClickListener(v -> {
            resetIcons();
            icon2.setImageResource(R.drawable.coffee_dark);
            moveCursorTo(1);
            startActivity(new Intent(HomeActivity.this, CustomListMenu.class));
        });

        icon3.setOnClickListener(v -> {
            resetIcons();
            icon3.setImageResource(R.drawable.location_dark);
            moveCursorTo(2);
            startActivity(new Intent(HomeActivity.this, MapActivity.class)); // Replace with actual target activity
        });

        icon4.setOnClickListener(v -> {
            resetIcons();
            icon4.setImageResource(R.drawable.user_dark);
            moveCursorTo(3);
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });
    }

    // Reset all icons to their default state
    private void resetIcons() {
        icon1.setImageResource(R.drawable.home_light);
        icon2.setImageResource(R.drawable.coffee_light);
        icon3.setImageResource(R.drawable.location_light);
        icon4.setImageResource(R.drawable.user_light);
    }

    // Move cursor to the specified position (0 for icon1, 1 for icon2, etc.)
    private void moveCursorTo(int position) {
        int targetX = position * iconWidth;

        cursor.animate()
                .x(targetX)
                .setDuration(300) // Duration in milliseconds
                .start();
    }

}
