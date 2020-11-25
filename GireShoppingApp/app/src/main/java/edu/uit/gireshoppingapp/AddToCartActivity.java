package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AddToCartActivity extends AppCompatActivity {

    private static Item addToCartItem = new Item();

    private ImageView tempItemImage;
    private TextView tempItemId;
    private TextView tempItemName;
    private TextView tempItemBrand;
    private TextView tempItemPrice;
    private TextView tempItemDesc;
    private TextView tempItemNumber;
    private Button minusTempItem;
    private Button plusTempItem;
    private EditText noOfTempItemsToAdd;
    private Button addTempItemToCart;
    private Button cancelAdd;

    public static void addTempItem(Item item)
    {
        addToCartItem = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        tempItemImage = findViewById(R.id.temp_item_image);
        tempItemId = findViewById(R.id.temp_item_id);
        tempItemName = findViewById(R.id.temp_item_name);
        tempItemBrand = findViewById(R.id.temp_item_brand);
        tempItemPrice = findViewById(R.id.temp_item_price);
        tempItemDesc = findViewById(R.id.temp_item_desc);
        tempItemNumber = findViewById(R.id.temp_item_number);
        noOfTempItemsToAdd = findViewById(R.id.number_of_temp_items_to_add);
        addTempItemToCart = findViewById(R.id.add_temp_item_to_cart);
        cancelAdd = findViewById(R.id.cancel_add);

        Glide.with(this)
                .asBitmap()
                .load(addToCartItem.getImgURL())
                .into(tempItemImage);
        tempItemId.setText("ID: " + addToCartItem.getId());
        tempItemName.setText("NAME: " + addToCartItem.getName());
        tempItemBrand.setText("BRAND: " + addToCartItem.getBrand());
        tempItemPrice.setText("PRICE: " + addToCartItem.getPrice());
        tempItemDesc.setText("DESCRIPTION: " + addToCartItem.getDesc());
        tempItemNumber.setText("IN STOCK: " + addToCartItem.getNumber());

        addTempItemToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(noOfTempItemsToAdd.getText().toString());
                if(number > 0)
                {
                    CartFragment.addItem(new Item(addToCartItem.getDesc(), addToCartItem.getId(), addToCartItem.getImgURL(), addToCartItem.getName(), addToCartItem.getPrice(), addToCartItem.getBrand(), Integer.toString(number)));
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("items/item" + addToCartItem.getId());
                    int left = Integer.parseInt(addToCartItem.getNumber()) - number;
                    ref.setValue(new Item(addToCartItem.getDesc(), addToCartItem.getId(), addToCartItem.getImgURL(), addToCartItem.getName(), addToCartItem.getPrice(), addToCartItem.getBrand(), Integer.toString(left)));
                }
            }
        });

        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}