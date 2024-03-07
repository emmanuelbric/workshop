package com.example.workshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    Button fetchButton;
    EditText newContactField;
    Button updateButton;
    Button deleteButton;

    Button logoutButton;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String emailId = auth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fetchButton = findViewById(R.id.btn_fetch_data);
        newContactField = findViewById(R.id.et_update_contact);
        updateButton = findViewById(R.id.btn_update_data);
        deleteButton = findViewById(R.id.btn_delete_data);
        logoutButton = findViewById(R.id.btn_logout);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(HomeActivity.this,"FETCHING DATA"+emailId,Toast.LENGTH_SHORT).show();
                firestore.collection("USERS").document(emailId).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                UserData fetchedData = documentSnapshot.toObject(UserData.class);
                                Log.d("Data fetched successfully",fetchedData.username+" | "+fetchedData.dob+" | "+fetchedData.contact);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Oops some error occured :)", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST","Button Clicked");
                Intent i = new Intent(HomeActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });
       deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                firestore.collection("USERS").document(emailId).update("contact", FieldValue.delete())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"Data updated successfully :)",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

           }
       });
       updateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String newContact = newContactField.getText().toString().trim();
               firestore.collection("USERS").document(emailId).update("contact",newContact)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Toast.makeText(getApplicationContext(),"Data deleted successfully :)",Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getApplicationContext(),"Data deleted unsuccessfully :)",Toast.LENGTH_SHORT).show();
                           }
                       });
           }
       });


    }
}