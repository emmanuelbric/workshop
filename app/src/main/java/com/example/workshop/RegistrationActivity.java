package com.example.workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    EditText usernameField, emailField, passwordField, confirmPasswordField, dobField, contactField;
    Button registerButton;

    ProgressBar progressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameField = findViewById(R.id.et_username);
        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_password);
        confirmPasswordField = findViewById(R.id.et_confirm_password);
        dobField = findViewById(R.id.et_dob);
        contactField = findViewById(R.id.et_contact);
        registerButton = findViewById(R.id.register_button);
        progressBarView = findViewById(R.id.progressBar2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBarView.setVisibility(View.VISIBLE);
                registerButton.setEnabled(false);



                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                String confirmPassword = confirmPasswordField.getText().toString().trim();
                String dob = dobField.getText().toString().trim();
                String contact = contactField.getText().toString().trim();

                validatefields( username,  email,  password, confirmPassword, dob, contact);       }
        });
    }

    void validatefields(String username, String email, String password,String confirmPassword,String dob,String contact)
    {
        if(username.isEmpty())
        {
            usernameField.setError("This field is compulsory");
            return;
        }
        if(email.isEmpty())
        {
            emailField.setError("This field is compulsory");
            return;
        }
        if(password.isEmpty())
        {
            passwordField.setError("This field is compulsory");
            return;
        }
        if(confirmPassword.isEmpty())
        {
            confirmPasswordField.setError("This field compulsory");
            return;
        }
        if(!password.equals(confirmPassword))
        {
            passwordField.setError("Password dont match");
            return;
        }
        if(dob.isEmpty())
        {
            dobField.setError("This field is compulsory");
            return;
        }
        if(contact.isEmpty())
        {
            contactField.setError("This field is compulsory");
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registerUser(email, password,username, dob, contact);
            }
        },2000);
    }

    void registerUser(String email,String password,String username, String dob, String contact)
    {
        Log.d("TEST","button clicked !!!");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getApplicationContext(),"User registered successfully :)", Toast.LENGTH_SHORT).show();
                    UserData obj = new UserData(username, dob, contact);
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        firestore.collection("USERS").document(email).set(obj)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"Data registered successfully :)", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Data not registered successfully :("+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "User not registered successfully :("+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        progressBarView.setVisibility(View.INVISIBLE);
        registerButton.setEnabled(true);

    }

}
