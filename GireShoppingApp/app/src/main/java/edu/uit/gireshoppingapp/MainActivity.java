package edu.uit.gireshoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private Toolbar tb;
    private NavigationView nv;
    private AppBarConfiguration appBarConfiguration;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "MainActivity";

    private static User user;
    public static User getCurrentUser()
    {
        return user;
    }
    public static void setCurrentBalance(int balance) {
        user.setBalance(Integer.toString(balance));
        DatabaseReference ref = database.getReference("users/" + mAuth.getUid());
        ref.setValue(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.main);
        tb = findViewById(R.id.toolbar);
        nv = findViewById(R.id.nav_view);

        // Ger user's data
        DatabaseReference ref = database.getReference("users/" + mAuth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                // Log.i(TAG, user.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // Setting up toolbar and fragments
        setSupportActionBar(tb);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_dashboard, R.id.nav_cart).setDrawerLayout(dl).build();
        NavController nc = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, nc, appBarConfiguration);
        NavigationUI.setupWithNavController(nv, nc);

        nv.getMenu().findItem(R.id.signout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                mAuth.signOut();
                finish();
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Setup toolbar
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}