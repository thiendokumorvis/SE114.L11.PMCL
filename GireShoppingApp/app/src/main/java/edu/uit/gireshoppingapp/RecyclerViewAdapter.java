package edu.uit.gireshoppingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    ArrayList<Item> items = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.itemName.setText("NAME: " + items.get(position).getName());
        holder.cartNumber.setText("IN CART: " + items.get(position).getNumber());
        int totalPrice = Integer.parseInt(items.get(position).getNumber()) * Integer.parseInt(items.get(position).getPrice());
        holder.cartItemPrice.setText("TOTAL PRICE FOR THIS ITEM: " + Integer.toString(totalPrice));
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getImgURL())
                .into(holder.itemImg);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCartActivity.setItem(items.get(position).getId());
                Intent intent = new Intent(v.getContext(), AddToCartActivity.class);
                ((Activity)v.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView itemImg;
        private TextView itemName;
        private TextView itemPrice;
        private TextView cartNumber;
        private TextView cartItemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.cart_item_image);
            parent = itemView.findViewById(R.id.cart_item_view);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            cartNumber = itemView.findViewById(R.id.cart_number);
            cartItemPrice = itemView.findViewById(R.id.cart_item_price);
        }
    }
}
