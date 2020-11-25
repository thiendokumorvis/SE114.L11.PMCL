package edu.uit.gireshoppingapp;

import android.content.Context;
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
        holder.itemName.setText(items.get(position).getName());
        holder.cartNumber.setText(items.get(position).getNumber());
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getImgURL())
                .into(holder.itemImg);
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
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
        private Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.cart_item_image);
            parent = itemView.findViewById(R.id.cart_item_view);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            removeButton = itemView.findViewById(R.id.removeButton);
            cartNumber = itemView.findViewById(R.id.cart_number);
        }
    }
}
