package edu.uit.gireshoppingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView bestSellingRecView;
    private RecyclerView allRecView;
    private ItemAdapter adapter1;
    private DatabaseReference mbase1;
    private ItemAdapter adapter2;
    private DatabaseReference mbase2;
    private ItemAdapter adapter3;
    private DatabaseReference mbase3;

    private ImageView woman_cat;
    private ImageView man_cat;
    private ImageView kid_cat;
    private TextView featured_text;
    private TextView best_selling_text;
    private TextView all_text;
    private SearchView home_search_bar;

    private Button price_filter_button;
    private EditText from_price;
    private EditText to_price;
    private Button price_filter;
    private LinearLayout price_filter_bar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.featured_recview);
        mbase1 = FirebaseDatabase.getInstance().getReference("featured_items");

        FirebaseRecyclerOptions<Item> items
                = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(mbase1, Item.class)
                .build();

        adapter1 = new ItemAdapter(items, view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter1);


        bestSellingRecView = view.findViewById(R.id.best_selling_recview);
        mbase2 = FirebaseDatabase.getInstance().getReference("best_selling_items");

        FirebaseRecyclerOptions<Item> best_selling
                = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(mbase2, Item.class)
                .build();

        adapter2 = new ItemAdapter(best_selling, view.getContext());
        bestSellingRecView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false));
        bestSellingRecView.setAdapter(adapter2);

        woman_cat = view.findViewById(R.id.woman_cat);
        man_cat = view.findViewById(R.id.man_cat);
        kid_cat = view.findViewById(R.id.kid_cat);

        allRecView = view.findViewById(R.id.all_recview);
        mbase3 = FirebaseDatabase.getInstance().getReference("all_items");

        FirebaseRecyclerOptions<Item> all
                = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(mbase3, Item.class)
                .build();

        adapter3 = new ItemAdapter(all, view.getContext());
        allRecView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false));
        allRecView.setAdapter(adapter3);

        woman_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WomanCatActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        man_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManCatActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        kid_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KidCatActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        featured_text = view.findViewById(R.id.featured_text);
        featured_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeaturedActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        best_selling_text = view.findViewById(R.id.best_selling_text);
        best_selling_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BestSellingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        all_text = view.findViewById(R.id.all_text);
        all_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        home_search_bar = view.findViewById(R.id.home_search_bar);
        home_search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivity.setQuery(query);
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                ((Activity)view.getContext()).startActivity(intent);
                ((Activity)view.getContext()).finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        price_filter_button = view.findViewById(R.id.price_filter_button);
        from_price = view.findViewById(R.id.from_price);
        to_price = view.findViewById(R.id.to_price);
        price_filter = view.findViewById(R.id.price_filter);
        price_filter_bar = view.findViewById(R.id.price_filter_bar);

        price_filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price_filter_bar.getVisibility() == price_filter_bar.VISIBLE)
                {
                    price_filter_bar.setVisibility(price_filter_bar.GONE);
                }
                else
                {
                    price_filter_bar.setVisibility(price_filter_bar.VISIBLE);
                }
            }
        });

        price_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(from_price.getText()) && !TextUtils.isEmpty(to_price.getText()))
                {
                    SearchActivity.setQuery("{search_with_price}" + from_price.getText().toString() + "-" + to_price.getText().toString());
                    Intent intent = new Intent(view.getContext(), SearchActivity.class);
                    ((Activity)view.getContext()).startActivity(intent);
                    ((Activity)view.getContext()).finish();
                }
            }
        });

        return view;
    }

    @Override public void onStart()
    {
        super.onStart();
        adapter1.startListening();
        adapter2.startListening();
        adapter3.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override public void onStop()
    {
        super.onStop();
        adapter1.stopListening();
        adapter2.stopListening();
        adapter3.stopListening();
    }
}