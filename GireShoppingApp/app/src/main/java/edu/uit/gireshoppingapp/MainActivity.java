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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private DrawerLayout dl;
    private Toolbar tb;
    private NavigationView nv;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.main);
        tb = findViewById(R.id.toolbar);
        nv = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();

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