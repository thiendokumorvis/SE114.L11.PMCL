package edu.uit.gireshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText registerEmail;
    private EditText confirmEmail;
    private EditText registerPassword;
    private EditText confirmPassword;
    private Button registerButton;
    private TextView goToLoginButton;
    private FirebaseDatabase database;
    private RelativeLayout loading_reg_screen;
    private CardView reg_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.registerEmail);
        confirmEmail = findViewById(R.id.confirmEmail);
        registerPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        goToLoginButton = findViewById(R.id.goToLoginButton);

        reg_screen = findViewById(R.id.reg_screen);
        loading_reg_screen = findViewById(R.id.loading_reg_screen);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                loading_reg_screen.setVisibility(View.VISIBLE);
                reg_screen.setVisibility(View.GONE);

                String re = registerEmail.getText().toString();
                String ce = confirmEmail.getText().toString();
                String rp = registerPassword.getText().toString();
                String cp = confirmPassword.getText().toString();

                if(!re.equals(ce) || !rp.equals(cp) || (re.length() <= 8) || (rp.length() <= 8))
                {
                    Toast.makeText(RegisterActivity.this, "Email or password is invalid.",
                            Toast.LENGTH_SHORT).show();

                    loading_reg_screen.setVisibility(View.GONE);
                    reg_screen.setVisibility(View.VISIBLE);
                }
                else
                {
                    registerButtonOnPress(re, rp);
                }
            }
        });

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerButtonOnPress(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("users/" + user.getUid());

                            User new_user = new User(email, "https://i.stack.imgur.com/JlSEL.png", "1000000");

                            ref.setValue(new_user);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(RegisterActivity.this, "Registration succeeded.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();

                            loading_reg_screen.setVisibility(View.GONE);
                            reg_screen.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}