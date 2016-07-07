package com.example.jxw679.mogul.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
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
import com.example.jxw679.mogul.model.Parent;
import com.example.jxw679.mogul.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ChildView extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private static final String TAG = "ChildView";
    public static Child child;

    public ArrayList<Task> data = new ArrayList<Task>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            System.out.println("UID: " + uid);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ChildView.child = dataSnapshot.getValue(Child.class);

                            child.setUsername(child.getEmail());
                            child.setUid(dataSnapshot.getKey());

                            TextView childName = (TextView) findViewById(R.id.child_name);
                            System.out.println(child.getFirstname());
                            childName.setText(child.getFirstname() +  " " + child.getLastname());

                            TextView balance = (TextView) findViewById(R.id.child_balance);
                            System.out.println(child.getBalance());
                            balance.setText(String.valueOf(child.getBalance()));

                            generateListContent();

                            ListView lv = (ListView) findViewById(R.id.assigned_list);
                            lv.setAdapter(new MyListAdapter(getApplicationContext(), R.layout.task_list_item, data));


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

    private void generateListContent() {
        for (Task task: child.getTasks().values()) {
            data.add(task);
        }

    }

    private class MyListAdapter extends ArrayAdapter {
        private int layout;

        private MyListAdapter(Context context, int resource, List<Task> objects) {
            super(context, resource, objects);
            System.out.println("Size of objects: " + objects.size());
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();

                Task currentTask = (Task) this.getItem(position);
                viewHolder.name = (TextView) convertView.findViewById(R.id.task_text);
                viewHolder.name.setText(currentTask.taskname);
                viewHolder.balance = (TextView) convertView.findViewById(R.id.task_amount);

                Double currencyAmount = new Double(currentTask.getReward());
                Currency currentCurrency = Currency.getInstance(Locale.US);
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

                viewHolder.balance.setText(currencyFormatter.format(currencyAmount));
                //viewHolder.button = (ImageButton) convertView.findViewById(R.id.button);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                convertView.setTag(viewHolder);
            } else {
                //mainViewHolder = (ViewHolder) convertView.getTag();
                //mainViewHolder.name.setText((Integer) getItem(position));
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
