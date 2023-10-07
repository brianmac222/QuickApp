package com.example.quickapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private List<String> shoppingCart;
    private static final String CHANNEL_ID = "cart_notification_channel";
    private static final int NOTIFICATION_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        shoppingCart = new ArrayList<>();

        createNotificationChannel();

        Button btnBack = findViewById(R.id.btnBack);
        ImageButton btnCart = findViewById(R.id.btnCart);

        Button btnAddCheesePizza = findViewById(R.id.btnAddCheesePizza);
        Button btnAddPizza = findViewById(R.id.btnAddPizza);
        Button btnAddHotdogs = findViewById(R.id.btnAddHotdogs);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to navigate back to the previous activity (MainActivity)
                Intent intent = new Intent(FoodActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent it from stacking on the back stack
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the shopping cart items to the CheckoutActivity
                Intent cartIntent = new Intent(FoodActivity.this, cartActivity.class);
                cartIntent.putStringArrayListExtra("shoppingCart", (ArrayList<String>) shoppingCart);
                startActivity(cartIntent);
            }
        });

        btnAddCheesePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart("Large Cheese Pizza", 11.99);
            }
        });

        btnAddPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart("Large Pepperoni Pizza", 12.99);
            }
        });

        btnAddHotdogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart("Large Hotdog", 5.99);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Pass the shoppingCart items back to the previous activity
        Intent intent = new Intent();
        intent.putStringArrayListExtra("shoppingCart", new ArrayList<>(shoppingCart));
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
    private void addToCart(String itemName, double itemPrice) {
        shoppingCart.add(itemName);
        // You can also update the UI to show the added item in the cart
        updateNotification(shoppingCart.size());
        Toast.makeText(this, itemName + " added to cart", Toast.LENGTH_SHORT).show();
    }

    private void showShoppingCart() {
        // Implement logic to display the shopping cart contents in a new activity or fragment
        // For now, let's show a Toast message with cart contents
        if (shoppingCart.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder cartContents = new StringBuilder("Shopping Cart:\n");
            for (String item : shoppingCart) {
                cartContents.append("- ").append(item).append("\n");
            }
            Toast.makeText(this, cartContents.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Cart Notification Channel";
            String description = "Channel for cart notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotification(int cartSize) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.cart)
                .setContentTitle("Shopping Cart")
                .setContentText("Items in cart: " + cartSize)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}