package com.example.jxw679.mogul.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jxw679.mogul.R;
import com.example.jxw679.mogul.model.Child;
import com.example.jxw679.mogul.model.Task;
import com.example.jxw679.mogul.model.requests.TaskRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddTask extends AppCompatActivity {

    EditText taskName = null;
    EditText description = null;
    EditText price = null;
    EditText deadline = null;
    Intent prevIntent = null;
    ArrayList<Child> child_data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        prevIntent = getIntent();
        child_data = (ArrayList<Child>) prevIntent.getExtras().get("child_list");
        taskName = (EditText) findViewById(R.id.task_name_edit);
        description = (EditText) findViewById(R.id.description_edit);
        price = (EditText) findViewById(R.id.price_edit);
        deadline = (EditText) findViewById(R.id.deadline_edit);
        ImageButton assign = (ImageButton) findViewById(R.id.add_child_to_task);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParentView.class);
                startActivity(intent);
            }
        });
        ImageButton createTask = (ImageButton) findViewById(R.id.create_task_button);
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestForTask();
                Intent intent = new Intent(getApplicationContext(), ParentView.class);
                startActivity(intent);
            }
        });
    }

    private void sendRequestForTask() {
        TaskRequest req = new TaskRequest();
        req.setDescription(description.getText().toString());
        req.setDeadline(deadline.getText().toString());
        req.setReward(Integer.parseInt(price.getText().toString()));
        req.setTaskname(taskName.getText().toString());
        req.setAssignto();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("/requests").push().setValue(req);
    }
}

