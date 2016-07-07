package com.example.jxw679.mogul.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import com.example.jxw679.mogul.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class ChildTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_task);


        Intent intent = getIntent();

        String taskname = intent.getStringExtra("taskname");
        String description = intent.getStringExtra("description");
        String reward = intent.getStringExtra("reward");
        String deadline = intent.getStringExtra("deadline");

        TextView task_name_edit = (TextView) findViewById(R.id.task_name_edit);
        TextView description_text = (TextView) findViewById(R.id.description_text);
        TextView reward_text = (TextView) findViewById(R.id.reward);
        TextView deadline_text = (TextView) findViewById(R.id.deadline);


        final Double currencyAmount = new Double(reward);
        Currency currentCurrency = Currency.getInstance(Locale.US);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        task_name_edit.setText(taskname);
        description_text.setText(description);
        reward_text.setText(currencyFormatter.format(currencyAmount));
        deadline_text.setText(deadline);

    }
}
