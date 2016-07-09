package com.example.jxw679.mogul.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Spinner;

import com.example.jxw679.mogul.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentTaskActivity extends AppCompatActivity {

    private String[] statuses = {"Requested", "In Progress", "In Review", "Completed"};
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_task);

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChildViewActivity.class);
                startActivity(intent);
            }
        });
        TextView task_name = (TextView) findViewById(R.id.task_name_edit);
        TextView description_text = (TextView) findViewById(R.id.description_text);
        Spinner status_spinner = (Spinner) findViewById(R.id.status_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, statuses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        status_spinner.setAdapter(arrayAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else {
            System.out.println("Not logged in!");
        }

    }
}
