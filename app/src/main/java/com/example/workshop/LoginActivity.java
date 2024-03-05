package com.example.workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView goToRegistration;

    Button loginButton;

    EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goToRegistration = findViewById(R.id.tv_go_to_register);
        emailField = findViewById(R.id.et_login_email);
        passwordField = findViewById(R.id.et_login_password);
        loginButton = findViewById(R.id.login_button);


        goToRegistration.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("TEST","button clicked !!!");
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(v,"Welcome :)", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                } else
                                {
                                    Snackbar snackbar = Snackbar
                                            .make(v,task.getException().getMessage()+":(", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            }
                        });
            }
        });
    }
}