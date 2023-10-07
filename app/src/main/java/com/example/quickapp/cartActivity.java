package com.example.quickapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class cartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView totalPriceTextView;
    private Button proceedToPaymentButton;
    private List<String> shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        proceedToPaymentButton = findViewById(R.id.proceedToPaymentButton);

        // Retrieve the shopping cart items from the DessertActivity
        shoppingCart = getIntent().getStringArrayListExtra("shoppingCart");


        if (shoppingCart == null) {
            shoppingCart = new ArrayList<>();
        }

        // Calculate the total price (you may have a separate data structure for prices)
        double totalPrice = calculateTotalPrice(shoppingCart);

        // Update the ListView with cart items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCart);
        cartListView.setAdapter(adapter);

        // Update the total price TextView
        totalPriceTextView.setText("Total: $" + String.format("%.2f", totalPrice));

        // Handle the "Proceed to Payment" button click
        proceedToPaymentButton.setOnClickListener(view -> {
            // Start a new activity or integrate a payment gateway here
            // For simplicity, we'll show a Toast message
            Toast.makeText(this, "Proceeding to payment...", Toast.LENGTH_SHORT).show();
        });
    }


    private double calculateTotalPrice(List<String> items) {
        // Calculate the total price based on the items in the cart
        // You should have a data structure that maps item names to prices
        // For this example, we'll assume all items have the same price
        double itemPrice = 9.99; // Replace with your actual item price
        return items.size() * itemPrice;
    }
}
