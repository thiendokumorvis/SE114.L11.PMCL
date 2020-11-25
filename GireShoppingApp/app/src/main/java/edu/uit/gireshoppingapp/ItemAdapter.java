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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class ItemAdapter extends FirebaseRecyclerAdapter<Item, ItemAdapter.ItemViewHolder> {

    private Context mContext;

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Item> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {

        holder.itemName.setText(model.getName());
        holder.itemPrice.setText("PRICE: " + model.getPrice());
        Glide.with(mContext)
                .asBitmap()
                .load(model.getImgURL())
                .into(holder.itemImg);
        holder.itemBrand.setText("BRAND: " + model.getBrand());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCartActivity.setItem(model.getId());
                Intent intent = new Intent(v.getContext(), AddToCartActivity.class);
                ((Activity)v.getContext()).finish();
                ((Activity)v.getContext()).startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemAdapter.ItemViewHolder(view);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView itemImg;
        private TextView itemName;
        private TextView itemPrice;
        private TextView itemBrand;
        private Button addButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.item_image);
            parent = itemView.findViewById(R.id.item_view);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            addButton = itemView.findViewById(R.id.addButton);
            itemBrand = itemView.findViewById(R.id.item_brand);
        }
    }
}
