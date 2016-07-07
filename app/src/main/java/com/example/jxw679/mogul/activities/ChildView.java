package com.example.jxw679.mogul.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.text.style.AbsoluteSizeSpan;
import android.text.TextUtils;

import com.example.jxw679.mogul.R;
import com.example.jxw679.mogul.model.Child;

import java.util.ArrayList;
import java.util.List;

public class ChildView extends AppCompatActivity {

    private ArrayList<Task> data = new ArrayList<Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_view);
        ListView lv = (ListView) findViewById(R.id.child_list);
        generateListContent();
        //lv.setAdapter(new MyListAdapter(this, R.layout.child_list_item, data));

        RelativeLayout task_button = (RelativeLayout) findViewById(R.id.task_button_layout);
        task_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               // go to task view page
                                           }
                                       });

        TextView task_text = (TextView) findViewById(R.id.task_text);
        TextView task_amount = (TextView) findViewById(R.id.task_amount);


        // We need to find all the tasks for the given child and parse them here
    }

    private void generateListContent() {


    }

    private class MyListAdapter extends ArrayAdapter {
        private int layout;

        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name = (TextView) convertView.findViewById(R.id.child_name);
                viewHolder.balance = (TextView) convertView.findViewById(R.id.child_balance);
                viewHolder.button = (ImageButton) convertView.findViewById(R.id.button);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Send child ID or something
                    }
                });
                convertView.setTag(viewHolder);
            } else {
                mainViewHolder = (ViewHolder) convertView.getTag();
                mainViewHolder.name.setText((Integer) getItem(position));
            }

            return convertView;
        }
    }

    public class ViewHolder {
        ImageView profile_pic;
        TextView name;
        TextView balance;
        ImageButton button;
    }
}
