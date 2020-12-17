package edu.uit.gireshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private Button goToRegisterButton;
    // private ProgressBar loginProgressBar;
    private RelativeLayout login_screen;
    private RelativeLayout loading_login_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        goToRegisterButton = findViewById(R.id.goToRegisterButton);

        login_screen = findViewById(R.id.login_screen);
        loading_login_screen = findViewById(R.id.loading_login_screen);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loginProgressBar.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                loading_login_screen.setVisibility(View.VISIBLE);
                login_screen.setVisibility(View.GONE);

                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if(email.length() <= 8 || password.length() <= 8)
                {
                    Toast.makeText(LoginActivity.this, "Email and password must be more than 8 characters.",
                            Toast.LENGTH_SHORT).show();

                    loading_login_screen.setVisibility(View.GONE);
                    login_screen.setVisibility(View.VISIBLE);
                }
                else
                    loginButtonOnPress(email, password);
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginButtonOnPress(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            loading_login_screen.setVisibility(View.GONE);
                            login_screen.setVisibility(View.VISIBLE);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            loading_login_screen.setVisibility(View.GONE);
                            login_screen.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}