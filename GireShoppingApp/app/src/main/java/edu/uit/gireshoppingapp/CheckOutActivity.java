package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutActivity extends AppCompatActivity {

    private TextView totalPrice;
    private TextView listOfItems;
    private Button cancelBuyButton;
    private Button buyButton;

//    private TextView address;
//    private EditText addressInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        totalPrice = findViewById(R.id.total_price_text);
        listOfItems = findViewById(R.id.item_list_text);

        totalPrice.setText("Total price: " + CartFragment.getTotalPrice());
        listOfItems.setText("Item(s): " + CartFragment.getListOfItems());

        cancelBuyButton = findViewById(R.id.cancel_buy_button);
        buyButton = findViewById(R.id.buy_button);

        cancelBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                CartFragment.buyFunction();
                Toast.makeText(CheckOutActivity.this, "Transaction completed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}