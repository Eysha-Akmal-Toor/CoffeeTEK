package com.example.coffeetek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context; // Ensure context is an Activity for launching intents
    private final String[] coffeeNames;
    private final int[] coffeeImages;
    private final double[] coffeePrices;  // Add price array

    public CustomListAdapter(Activity context, String[] coffeeNames, int[] coffeeImages, double[] coffeePrices) {
        super(context, R.layout.menu_item, coffeeNames);
        this.context = context;
        this.coffeeNames = coffeeNames;
        this.coffeeImages = coffeeImages;
        this.coffeePrices = coffeePrices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.menu_item, parent, false);

        TextView titleText = rowView.findViewById(R.id.text); // Verify R.id.text exists in menu_item.xml
        ImageView imageView = rowView.findViewById(R.id.icon); // Verify R.id.icon exists in menu_item.xml

        titleText.setText(coffeeNames[position]);
        imageView.setImageResource(coffeeImages[position]);

        // Set OnClickListener to open CoffeeDetailActivity
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CoffeeDetailActivity.class);
                intent.putExtra("coffeeName", coffeeNames[position]);
                intent.putExtra("coffeeImage", coffeeImages[position]);
                intent.putExtra("coffeePrice", coffeePrices[position]);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}

