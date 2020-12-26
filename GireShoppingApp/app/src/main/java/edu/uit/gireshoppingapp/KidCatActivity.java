package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KidCatActivity extends AppCompatActivity {

    private Button back_button;
    private RecyclerView kid_cat_recview;
    private DatabaseReference ref;
    private ItemAdapter adapter;
    private DatabaseReference temp_ref;
    private ItemAdapter temp_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_cat);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KidCatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        kid_cat_recview = findViewById(R.id.kid_cat_recview);
        ref = FirebaseDatabase.getInstance().getReference("kid_items");
        FirebaseRecyclerOptions<Item> kid_items
                = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(ref, Item.class)
                .build();

        adapter = new ItemAdapter(kid_items, this);
        kid_cat_recview.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        kid_cat_recview.setAdapter(adapter);
    }

    @Override public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}