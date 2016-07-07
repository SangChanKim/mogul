package com.example.jxw679.mogul.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.example.jxw679.mogul.model.Child;
import com.example.jxw679.mogul.model.Parent;
import com.example.jxw679.mogul.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentView extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "PARENTVIEW";
    public static Parent parent;

    public ArrayList<Child> data = new ArrayList<Child>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            System.out.println("UID: " + uid);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ParentView.parent = dataSnapshot.getValue(Parent.class);
                            parent.setUsername(dataSnapshot.getKey());
                            TextView parentName = (TextView) findViewById(R.id.parent_name);
                            parentName.setText(parent.getFirstname() +  " " + parent.getLastname());
                            System.out.println(parent.toString());

                            generateListContent();

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
        for (String childuid: parent.getChildren()) {
            System.out.println("childuid: " + childuid);
            mDatabase.child("users").child(childuid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Child child = dataSnapshot.getValue(Child.class);
                            child.setUsername(child.getEmail());
                            child.setUid(dataSnapshot.getKey());
                            System.out.println("FOUND CHILD DATA FOR " + child.getUid());
                            data.add(child);
                            if (data.size() == parent.getChildren().size()) {
                                ListView lv = (ListView) findViewById(R.id.child_list);
                                lv.setAdapter(new MyListAdapter(getApplicationContext(), R.layout.child_list_item, data));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        }
                    });
        }

    }

    private class MyListAdapter extends ArrayAdapter {
        private int layout;

        private MyListAdapter(Context context, int resource, List<Child> objects) {
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

                Child currentChild = (Child) this.getItem(position);
                viewHolder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name = (TextView) convertView.findViewById(R.id.child_name);
                viewHolder.name.setText(currentChild.getFirstname());
                viewHolder.balance = (TextView) convertView.findViewById(R.id.child_balance);

                Double currencyAmount = new Double(currentChild.getBalance());
                Currency currentCurrency = Currency.getInstance(Locale.US);
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

                viewHolder.balance.setText(currencyFormatter.format(currencyAmount));
                viewHolder.button = (ImageButton) convertView.findViewById(R.id.button);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ChildView.class);
                        ParentView.this.startActivity(intent);
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
