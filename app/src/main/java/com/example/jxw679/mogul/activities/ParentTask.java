package com.example.jxw679.mogul.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jxw679.mogul.R;

public class ParentTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_task);

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button);
        TextView task_name = (TextView) findViewById(R.id.task_name_edit);
        TextView description_text = (TextView) findViewById(R.id.description_text);
    }
}
