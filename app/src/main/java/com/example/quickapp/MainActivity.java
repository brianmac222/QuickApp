package com.example.quickapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> shoppingCart = new ArrayList<>();
    private static final int REQUEST_DESSERT_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ImageButtons by their IDs
        ImageButton btnFood = findViewById(R.id.btnFood);
        ImageButton btnCafe = findViewById(R.id.btnCafe);
        ImageButton btnDessert = findViewById(R.id.btnDessert);

        // Set click listeners for the ImageButtons to navigate to the respective activities
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for the Food button
                // You can start FoodActivity similarly to how DessertActivity is started
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        btnCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for the Cafe button
                // You can start CafeActivity similarly to how DessertActivity is started
                Intent intent = new Intent(MainActivity.this, CafeActivity.class);
                startActivity(intent);
            }
        });

        btnDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for the Dessert button
                // Start DessertActivity using startActivityForResult
                Intent intent = new Intent(MainActivity.this, DessertActivity.class);
                startActivityForResult(intent, REQUEST_DESSERT_ACTIVITY);
            }
        });
    }

    // Handle the result from DessertActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DESSERT_ACTIVITY && resultCode == RESULT_OK && data != null) {
            // Get the shopping cart data from the result
            ArrayList<String> updatedCart = data.getStringArrayListExtra("shoppingCart");

            // Update your shopping cart or UI with the new data
            shoppingCart.clear();
            shoppingCart.addAll(updatedCart);
        }
    }
}
