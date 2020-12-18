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
import android.widget.Toast;

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
    private static FirebaseDatabase database;
    private static FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private long backPressedTime;
    private Toast backToast;

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

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Get user's data
        DatabaseReference ref = database.getReference("users/" + mAuth.getUid().toString());
        // Toast.makeText(MainActivity.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "The read failed: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        // Setting up toolbar and fragments
        setSupportActionBar(tb);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_dashboard, R.id.nav_cart, R.id.nav_voucher).setDrawerLayout(dl).build();
        NavController nc = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, nc, appBarConfiguration);
        NavigationUI.setupWithNavController(nv, nc);

        nv.getMenu().findItem(R.id.signout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                mAuth.signOut();
                user = null;
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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}