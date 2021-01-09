package edu.uit.gireshoppingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    public static String getListOfItems;
    private static ArrayList<Item> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton buyButton;
    private RelativeLayout emptyCartScreen;
    private RelativeLayout cartScreen;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static void buyFunction()
    {
        int temp = Integer.parseInt(MainActivity.getCurrentUser().getBalance()) - CartFragment.getTotalPrice();
        MainActivity.setCurrentBalance(temp);
        String ordered = "";
        for(int i = 0; i < items.size(); i++)
        {
            ordered = ordered + "(" + items.get(i).getId() + " " + items.get(i).getNumber() + ")";
        }
        Order order = new Order(ordered);
        DatabaseReference ref = database.getReference("orders/" + mAuth.getUid());
        ref.setValue(order);
        items = new ArrayList<>();
    }
    public static void removeAllFunction()
    {
        items = new ArrayList<>();
    }

    public static String getListOfItems()
    {
        String temp = "";
        for(int i = 0; i < items.size(); i++)
        {
            temp = temp + items.get(i).getName() + " (" + items.get(i).getNumber() + ")" + ", ";
        }
        return temp.substring(0, temp.length()-2);
    }

    public static int getTotalPrice()
    {
        int sum = 0;
        for(int i = 0; i < items.size(); i++)
        {
            sum += Integer.parseInt(items.get(i).getPrice())*Integer.parseInt(items.get(i).getNumber());
        }
        return sum;
    }

    public static int getItemIndex(Item item)
    {
        if(items.size() > 0)
        {
            for(int i = 0; i < items.size(); i++)
            {
                if(item.getId().equals(items.get(i).getId()))
                    return i;
            }
            return -1;
        }
        else
        {
            return -1;
        }
    }

    public static void addItem(Item item)
    {
        items.add(item);
    }

    public static void changeNoOfItem(int number, int index)
    {
        items.get(index).setNumber(Integer.toString(number));
    }

    public static void removeItem(int index)
    {
        items.remove(index);
    }

    public static Item getItem(int index)
    {
        return items.get(index);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        buyButton = view.findViewById(R.id.buyButton);
        emptyCartScreen = view.findViewById(R.id.empty_cart_screen);
        cartScreen = view.findViewById(R.id.cart_screen);

        if (items.size() > 0)
        {
            recyclerView = view.findViewById(R.id.cart_recview);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            adapter = new RecyclerViewAdapter(view.getContext(), items);
            recyclerView.setAdapter(adapter);

            emptyCartScreen.setVisibility(View.GONE);
            cartScreen.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyCartScreen.setVisibility(View.VISIBLE);
            cartScreen.setVisibility(View.GONE);
        }
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckOutActivity.class);
                ((Activity)v.getContext()).startActivity(intent);
                ((Activity)v.getContext()).finish();
            }
        });

        return view;
    }
}