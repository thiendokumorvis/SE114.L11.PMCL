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

public class SearchRecViewApdater extends RecyclerView.Adapter<SearchRecViewApdater.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    ArrayList<Item> items = new ArrayList<>();
    private Context mContext;
    Item temp_item = new Item();

    public SearchRecViewApdater(Context mContext) {
        this.mContext = mContext;
    }

    public SearchRecViewApdater(Context mContext, ArrayList<Item> items) {
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
        holder.itemName.setText(items.get(position).getName());
        holder.cartNumber.setVisibility(View.GONE);
        holder.cartItemPrice.setText("$" + items.get(position).getPrice());
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getImgURL())
                .into(holder.itemImg);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(items.get(position).getId(), new ItemAdapter.OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        AddToCartActivity.setItem(temp_item);
                        Intent intent = new Intent(v.getContext(), AddToCartActivity.class);
                        ((Activity)v.getContext()).startActivity(intent);
                        ((Activity)v.getContext()).finish();
                    }
                    @Override
                    public void onStart() {
                        //when starting
                        Log.d("ONSTART", "Started");
                    }

                    @Override
                    public void onFailure() {
                        Log.d("onFailure", "Failed");
                    }
                });
            }
        });
    }

    public interface OnGetDataListener {
        //this is for callbacks
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }
    public void readData(String id, final ItemAdapter.OnGetDataListener listener) {
        listener.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("all_items/item" + id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temp_item = dataSnapshot.getValue(Item.class);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // System.out.println("The read failed: " + databaseError.getCode());
                listener.onFailure();
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
