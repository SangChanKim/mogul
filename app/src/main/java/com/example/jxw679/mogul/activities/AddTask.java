package com.example.jxw679.mogul.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jxw679.mogul.R;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText taskName = (EditText) findViewById(R.id.task_name_edit);
        EditText description = (EditText) findViewById(R.id.description_edit);
        EditText price = (EditText) findViewById(R.id.price_edit);
        EditText deadline = (EditText) findViewById(R.id.deadline_edit);
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
    }

    private void sendRequestForTask() {

    }
}

