package edu.uit.gireshoppingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private ImageView userImg;
    private TextView username;
    private TextView balance;
    private TextView address;
    private EditText edit_address;
    private Button edit_address_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        User user = MainActivity.getCurrentUser();

        userImg = view.findViewById(R.id.userImg);
        balance = view.findViewById(R.id.balance);
        username = view.findViewById(R.id.username);
        address = view.findViewById(R.id.address);
        edit_address = view.findViewById(R.id.edit_address);
        edit_address_button = view.findViewById(R.id.edit_address_button);

        Glide.with(view.getContext())
                .asBitmap()
                .load(user.getImgURL())
                .into(userImg);
        balance.setText("Balance: " + user.getBalance());
        username.setText("Email: " + user.getName());
        address.setText("Address: " + user.getAddress());

        edit_address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_address.getText().toString().length() == 0)
                {
                    MainActivity.setCurrentAddress("None");
                    address.setText("Address: " + MainActivity.getCurrentUser().getAddress());
                }
                else
                {
                    MainActivity.setCurrentAddress(edit_address.getText().toString());
                    edit_address.setText("");
                    address.setText("Address: " + MainActivity.getCurrentUser().getAddress());
                }
            }
        });

        return view;
    }
}