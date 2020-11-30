package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Button removeTempItemFromCart;
    private TextView numberText;
    private TextView cartNumberText;

    public static void setItem(String id)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("items/item" + id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addToCartItem = dataSnapshot.getValue(Item.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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
        removeTempItemFromCart = findViewById(R.id.remove_temp_item_from_cart);
        numberText = findViewById(R.id.number_text);
        cartNumberText = findViewById(R.id.cart_number_text);

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

        int tempIndex = CartFragment.getItemIndex(addToCartItem);

        if(tempIndex != -1)
        {
            noOfTempItemsToAdd.setText(CartFragment.getItem(tempIndex).getNumber());
            addTempItemToCart.setText("CHANGE");
            removeTempItemFromCart.setVisibility(View.VISIBLE);
            cartNumberText.setVisibility(View.VISIBLE);
            cartNumberText.setText("IN CART: " + CartFragment.getItem(tempIndex).getNumber());
            numberText.setText("NUMBER TO CHANGE:");
        }
        else
        {
            noOfTempItemsToAdd.setText("0");
            addTempItemToCart.setText("ADD TO CART");
            removeTempItemFromCart.setVisibility(View.GONE);
            cartNumberText.setVisibility(View.GONE);
        }

        addTempItemToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(noOfTempItemsToAdd.getText().toString());
                if(number <= 0)
                {
                    Toast.makeText(AddToCartActivity.this, "Number of items must be more than 0.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(number > Integer.parseInt(addToCartItem.getNumber()))
                {
                    Toast.makeText(AddToCartActivity.this, "Only " + addToCartItem.getNumber() + " item(s) remained", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(tempIndex != -1)
                {
                    CartFragment.changeNoOfItem(number, tempIndex);
                }
                else
                {
                    CartFragment.addItem(new Item(addToCartItem.getDesc(), addToCartItem.getId(), addToCartItem.getImgURL(), addToCartItem.getName(), addToCartItem.getPrice(), addToCartItem.getBrand(), Integer.toString(number)));
                }

                addToCartItem = new Item();
                Intent intent = new Intent(AddToCartActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        removeTempItemFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.removeItem(CartFragment.getItemIndex(addToCartItem));
                addToCartItem = new Item();
                Intent intent = new Intent(AddToCartActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartItem = new Item();
                Intent intent = new Intent(AddToCartActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}