package com.example.jxw679.mogul.activities;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.example.jxw679.mogul.model.Task;

import java.util.ArrayList;
import java.util.List;
import com.example.jxw679.mogul.model.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChildView extends AppCompatActivity {

    private ArrayList<Task> data = new ArrayList<Task>();
    private DatabaseReference mDatabase;
    private static final String TAG = "CHILDVIEW";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            System.out.println("UID: " + uid);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Child child = dataSnapshot.getValue(Child.class);
                            child.setUsername(child.getEmail());

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                child.setUid(user.getUid());
                            }
                            TextView childName = (TextView) findViewById(R.id.child_name);
                            childName.setText(child.getFirstname() +  " " + child.getLastname());

                            TextView childBalance = (TextView) findViewById(R.id.child_balance);
                            childBalance.setText("$" + String.valueOf(child.getBalance()));

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        }
                    });

        } else {
            System.out.println("Not logged in!");
        }

//        ListView lv = (ListView) findViewById(R.id.child_list);
//        generateListContent();
//        lv.setAdapter(new MyListAdapter(this, R.layout.child_list_item, data));


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
