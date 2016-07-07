package com.example.jxw679.mogul.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Spinner;

import com.example.jxw679.mogul.R;

public class ParentTask extends AppCompatActivity {

    String[] statuses = {"Requested", "In Progress", "In Review", "Completed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_task);

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button);
        TextView task_name = (TextView) findViewById(R.id.task_name_edit);
        TextView description_text = (TextView) findViewById(R.id.description_text);
        Spinner status_spinner = (Spinner) findViewById(R.id.status_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, statuses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        status_spinner.setAdapter(arrayAdapter);

        for (String status : statuses) {

        }
    }
}
