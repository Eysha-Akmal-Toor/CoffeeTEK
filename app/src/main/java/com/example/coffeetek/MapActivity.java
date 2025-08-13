package com.example.coffeetek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView icon1, icon2, icon3, icon4;
    private View cursor;
    private int iconWidth;
    private GoogleMap mMap; // Reference to the Google Map object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); // Link the layout file

        // ImageView to send an email
        ImageView emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(v -> {
            sendEmail();
        });

        // Initialize bottom navigation icons and cursor
        icon1 = findViewById(R.id.menu_icon1);
        icon2 = findViewById(R.id.menu_icon2);
        icon3 = findViewById(R.id.menu_icon3);
        icon4 = findViewById(R.id.menu_icon4);
        cursor = findViewById(R.id.cursor);

        // Measure icon width for cursor movement
        icon1.post(() -> iconWidth = icon1.getWidth());

        // Set click listeners for each icon
        icon1.setOnClickListener(v -> {
            resetIcons();
            icon1.setImageResource(R.drawable.home_dark);
            moveCursorTo(0);
            startActivity(new Intent(MapActivity.this, HomeActivity.class));
            finish(); // Ends the current activity
        });

        icon2.setOnClickListener(v -> {
            resetIcons();
            icon2.setImageResource(R.drawable.coffee_dark);
            moveCursorTo(1);
            startActivity(new Intent(MapActivity.this, CustomListMenu.class));
        });

        // Set the location icon to "dark" and move cursor to its position initially
        icon3.setImageResource(R.drawable.location_dark); // Highlight location icon
        icon3.setOnClickListener(v -> {
            resetIcons();
            icon3.setImageResource(R.drawable.location_dark);
            moveCursorTo(2);
            startActivity(new Intent(MapActivity.this, MapActivity.class));
        });

        icon4.setOnClickListener(v -> {
            resetIcons();
            icon4.setImageResource(R.drawable.user_dark);
            moveCursorTo(3);
            startActivity(new Intent(MapActivity.this, ProfileActivity.class));
        });

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void sendEmail() {
        // Define recipient email
        String[] recipients = {"querycoffeetek@gmail.com"};

        // Create an Intent with ACTION_SEND
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        // Set the MIME type to email
        emailIntent.setType("message/rfc822");

        // Add recipients, subject, and text to the intent
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);     // Recipient email address
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Query Subject"); // Subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi, my query is about [insert your query]. Could you please help?");   // Message body

        // Verify if there are email apps installed to handle this intent
        try {
            // Launch email app to handle the intent
            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle error case when no email apps are installed
            System.out.println("No email app found.");
        }
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

    // Callback when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set a default location (example: Islamabad, Pakistan)
        LatLng defaultLocation = new LatLng(33.6844, 73.0479);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Islamabad"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f));
    }
}
