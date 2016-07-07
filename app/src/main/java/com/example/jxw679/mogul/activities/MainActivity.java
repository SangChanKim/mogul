package com.example.jxw679.mogul.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.*;
import android.view.View;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.jxw679.mogul.model.User;
import com.example.jxw679.mogul.R;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etUsername;
    private EditText etPassword;
    private ImageButton btSignIn;
    private ImageButton btSignUp;


    private DatabaseReference mDatabase;

    private static final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btSignIn = (ImageButton) findViewById(R.id.signin);
        btSignUp = (ImageButton) findViewById(R.id.signup);
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());


                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {


                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        String uid = user.getUid();
                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                                                new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        // Get user value
                                                        Object s = dataSnapshot.getValue();
                                                        System.out.println(s.toString());

                                                        // ...
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                                    }
                                                });
                                        Intent intent = new Intent(getApplicationContext(), ChildView.class);
                                        MainActivity.this.startActivity(intent);
                                    } else {
                                        System.out.println("Not logged in!");
                                    }
                                }
                            }
                        });
            }
        });
    }
}
