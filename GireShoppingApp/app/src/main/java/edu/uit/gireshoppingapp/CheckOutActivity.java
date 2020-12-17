package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class CheckOutActivity extends AppCompatActivity {

    private TextView totalPrice;
    private TextView listOfItems;
//    private TextView address;
//    private EditText addressInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        totalPrice = findViewById(R.id.total_price_text);
        listOfItems = findViewById(R.id.item_list_text);
//        address = findViewById(R.id.address_text);
//        addressInput = findViewById(R.id.address_edit_text);

        totalPrice.setText("TOTAL PRICE: " + CartFragment.getTotalPrice());
        listOfItems.setText("ITEM(s): " + CartFragment.getListOfItems());
    }
}