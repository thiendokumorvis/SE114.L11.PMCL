package edu.uit.gireshoppingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView bestSellingRecView;
    private ItemAdapter adapter1;
    private DatabaseReference mbase1;
    private ItemAdapter adapter2;
    private DatabaseReference mbase2;

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
        mbase1 = FirebaseDatabase.getInstance().getReference("items");

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

        return view;
    }

    @Override public void onStart()
    {
        super.onStart();
        adapter1.startListening();
        adapter2.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override public void onStop()
    {
        super.onStop();
        adapter1.stopListening();
        adapter2.stopListening();
    }
}