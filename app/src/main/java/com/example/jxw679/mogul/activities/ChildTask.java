package com.example.jxw679.mogul.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jxw679.mogul.R;

import org.w3c.dom.Text;

public class ChildTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_task);

        TextView description = (TextView) findViewById(R.id.description_text);
    }
}
