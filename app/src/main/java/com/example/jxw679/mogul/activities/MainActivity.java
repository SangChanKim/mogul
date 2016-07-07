package com.example.jxw679.mogul.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.*;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.util.Map;

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
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etUsername = (EditText) findViewById(R.id.username);
        etUsername.setText("kristapsberzinch@gmail.com");
        etPassword = (EditText) findViewById(R.id.password);
        etPassword.setText("kristapsberzinch");
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
                                        System.out.println("UID: " + uid);
                                        mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                                                new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        // Get user value
                                                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                                        String type = (String) map.get("type");
                                                        System.out.println(type);
                                                        if (type.equals("parent")) {
                                                            Intent intent = new Intent(getApplicationContext(), ParentView.class);
                                                            MainActivity.this.startActivity(intent);
                                                        } else {
                                                            Intent intent = new Intent(getApplicationContext(), ChildView.class);
                                                            MainActivity.this.startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                                    }
                                                });

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
