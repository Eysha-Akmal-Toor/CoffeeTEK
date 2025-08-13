package com.example.coffeetek;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CoffeeDetailActivity extends AppCompatActivity {

    // UI elements for displaying count and total value
    private TextView countTextView;
    private TextView totalValueTextView;

    // Variables to track coffee count and price
    private int count = 1; // Default count is 1
    private double price; // Base price of the selected coffee
    private ImageView smallSizeIcon, mediumSizeIcon, largeSizeIcon; // ImageViews for size selection
    private boolean isSmallSelected = true, isMediumSelected = false, isLargeSelected = false; // Track which size is selected

    // Prices for different coffee sizes
    private double smallSizePrice = 10.0, mediumSizePrice = 12.0, largeSizePrice = 15.0; // Price for small, medium, large sizes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail); // Set the layout for the activity

        // Initialize UI elements
        ImageView coffeeImageView = findViewById(R.id.coffeeImageView);
        TextView coffeeNameTextView = findViewById(R.id.coffeeNameTextView);
        TextView coffeePriceTextView = findViewById(R.id.coffeePriceTextView);
        countTextView = findViewById(R.id.countTextView); // To show the current count of coffee
        totalValueTextView = findViewById(R.id.totalValueTextView); // To show the total price

        // Initialize size icons
        smallSizeIcon = findViewById(R.id.smallSizeIcon);
        mediumSizeIcon = findViewById(R.id.mediumSizeIcon);
        largeSizeIcon = findViewById(R.id.largeSizeIcon);

        // Set initial coffee count and display it
        countTextView.setText(String.valueOf(count));

        // Retrieve data from Intent passed from the previous activity
        String coffeeName = getIntent().getStringExtra("coffeeName");
        int coffeeImage = getIntent().getIntExtra("coffeeImage", 0);
        price = getIntent().getDoubleExtra("coffeePrice", 0.0); // Base price of the coffee

        // Set dynamic prices for sizes based on base price
        smallSizePrice = price;
        mediumSizePrice = price + 5.0;
        largeSizePrice = price + 10.0;

        // Set initial coffee price (small size price)
        coffeePriceTextView.setText(formatPrice(smallSizePrice));
        this.price = smallSizePrice; // Initially set price to small size price

        // Populate UI with the coffee details
        coffeeNameTextView.setText(coffeeName);
        coffeeImageView.setImageResource(coffeeImage);

        // Calculate and display the initial total value (count * price)
        updateTotal();

        // Set initial size selection (small size selected)
        updateSizeSelection();

        // Set up the "Place Order" button to handle order placement
        Button placeOrderButton = findViewById(R.id.placeOrderButton);
        placeOrderButton.setOnClickListener(v -> {
            // Show a Toast message when the order is placed
            Toast.makeText(CoffeeDetailActivity.this, "Order placed! Brewing soon.", Toast.LENGTH_SHORT).show();
            resetOrder(); // Reset the order to its initial state
        });
    }

    // Method to update the total value (count * price)
    private void updateTotal() {
        double total = count * price; // Calculate the total price
        totalValueTextView.setText(formatPrice(total)); // Display the formatted total price
    }

    // Method to format the price or total value (removes decimals if value is an integer)
    private String formatPrice(double value) {
        if (value == Math.floor(value)) {
            return String.format("%d EGP", (int) value); // Display without decimals if integer
        } else {
            return String.format("%.2f EGP", value); // Display with 2 decimal points
        }
    }

    // Method for the Minus button to decrease the count
    public void onMinusClick(View view) {
        if (count > 1) { // Prevent going below 1
            count--; // Decrease count
            countTextView.setText(String.valueOf(count)); // Update count display
            updateTotal(); // Update total price
        }
    }

    // Method for the Plus button to increase the count
    public void onPlusClick(View view) {
        count++; // Increase count
        countTextView.setText(String.valueOf(count)); // Update count display
        updateTotal(); // Update total price
    }

    // Method to handle size selection (small, medium, large)
    public void onSizeClick(View view) {
        if (view.getId() == R.id.smallSizeIcon) {
            // If small size icon is clicked, select small size
            isSmallSelected = true;
            isMediumSelected = false;
            isLargeSelected = false;
            price = smallSizePrice; // Set price to small size
        } else if (view.getId() == R.id.mediumSizeIcon) {
            // If medium size icon is clicked, select medium size
            isSmallSelected = false;
            isMediumSelected = true;
            isLargeSelected = false;
            price = mediumSizePrice; // Set price to medium size
        } else if (view.getId() == R.id.largeSizeIcon) {
            // If large size icon is clicked, select large size
            isSmallSelected = false;
            isMediumSelected = false;
            isLargeSelected = true;
            price = largeSizePrice; // Set price to large size
        }

        updateSizeSelection(); // Update the icon appearance based on the selection
        updateTotal(); // Update total price after size selection
    }

    // Method to update the size icons based on the selection
    private void updateSizeSelection() {
        if (isSmallSelected) {
            // Highlight the small size and dim the others
            smallSizeIcon.setImageResource(R.drawable.small_coffee);
            mediumSizeIcon.setImageResource(R.drawable.medium_light);
            largeSizeIcon.setImageResource(R.drawable.large_light);
        } else if (isMediumSelected) {
            // Highlight the medium size and dim the others
            smallSizeIcon.setImageResource(R.drawable.small_light);
            mediumSizeIcon.setImageResource(R.drawable.medium_coffee);
            largeSizeIcon.setImageResource(R.drawable.large_light);
        } else if (isLargeSelected) {
            // Highlight the large size and dim the others
            smallSizeIcon.setImageResource(R.drawable.small_light);
            mediumSizeIcon.setImageResource(R.drawable.medium_light);
            largeSizeIcon.setImageResource(R.drawable.large_coffee);
        }
    }

    // Method to handle the sugar option selection
    public void onSugarOptionClick(View view) {
        ImageView selectedSugar = (ImageView) view; // Get the selected sugar option
        ImageView noSugarIcon = findViewById(R.id.noSugarIcon);
        ImageView sugarIcon = findViewById(R.id.sugarIcon);

        // Reset the appearance of sugar icons
        noSugarIcon.setImageResource(R.drawable.nosugar_light);
        sugarIcon.setImageResource(R.drawable.sugar_light);

        // Highlight the selected sugar option
        if (selectedSugar == noSugarIcon) {
            noSugarIcon.setImageResource(R.drawable.nosugar_dark);
        } else {
            sugarIcon.setImageResource(R.drawable.sugar_dark);
        }
    }

    // Method to handle the addition selection (cream or vanilla)
    public void onAdditionOptionClick(View view) {
        ImageView selectedAddition = (ImageView) view; // Get the selected addition option
        ImageView creamIcon = findViewById(R.id.creamIcon);
        ImageView vanillaIcon = findViewById(R.id.vanillaIcon);

        // Reset the appearance of addition icons
        creamIcon.setImageResource(R.drawable.cream_light);
        vanillaIcon.setImageResource(R.drawable.vanilla_light);

        // Highlight the selected addition option
        if (selectedAddition == creamIcon) {
            creamIcon.setImageResource(R.drawable.cream_dark);
        } else {
            vanillaIcon.setImageResource(R.drawable.vanilla_dark);
        }
    }

    // Method to reset the order to its initial state
    private void resetOrder() {
        count = 1; // Reset count to 1
        countTextView.setText(String.valueOf(count));

        // Reset size selection to small
        isSmallSelected = true;
        isMediumSelected = false;
        isLargeSelected = false;
        price = smallSizePrice; // Set price to small size price
        updateSizeSelection(); // Update size icon selection

        // Reset sugar selection to no sugar
        ImageView noSugarIcon = findViewById(R.id.noSugarIcon);
        ImageView sugarIcon = findViewById(R.id.sugarIcon);
        noSugarIcon.setImageResource(R.drawable.nosugar_light);
        sugarIcon.setImageResource(R.drawable.sugar_light);

        // Reset addition selection to none
        ImageView creamIcon = findViewById(R.id.creamIcon);
        ImageView vanillaIcon = findViewById(R.id.vanillaIcon);
        creamIcon.setImageResource(R.drawable.cream_light);
        vanillaIcon.setImageResource(R.drawable.vanilla_light);

        updateTotal(); // Update total price
    }
}
