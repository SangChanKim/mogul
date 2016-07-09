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

public class ParentViewActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageButton mAddTaskButton;
    private ListView mListOfChildren;
    private MyListAdapter mListAdapter;
    private FirebaseUser mCurrentUser;

    private Parent currentUser;
    private ArrayList<Child> mChildObjects;

    public  static final String TAG = "PARENT_VIEW_ACTIVITY";
    public static final String PARENT_OBJECT_EXTRA_TAG = "PARENT";
    public static final String CHILD_LIST_OBJECT_EXTRA_TAG = "CHILDLIST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_view);
        mChildObjects = new ArrayList<Child>();



        mAddTaskButton = (ImageButton) findViewById(R.id.add_task);
        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra(PARENT_OBJECT_EXTRA_TAG, mChildObjects);
                intent.putExtra(CHILD_LIST_OBJECT_EXTRA_TAG, currentUser);
                startActivityForResult(intent, 123);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_orange, R.color.refresh_green, R.color.refresh_blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("onRefresh()");
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mCurrentUser != null) {
                    updateParentObject(mCurrentUser.getUid(), true);
                    if (mListAdapter != null) {
                        mListAdapter.notifyDataSetChanged();
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mCurrentUser != null) {
            updateParentObject(mCurrentUser.getUid(), true);
        }
    }

    private void updateParentObject(String uid, final boolean updateChildListContent) {

        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currentUser = dataSnapshot.getValue(Parent.class);
                        currentUser.setUsername(currentUser.getEmail());
                        currentUser.setUid(dataSnapshot.getKey());
                        TextView parentName = (TextView) findViewById(R.id.parent_name);
                        parentName.setText(currentUser.getFirstname() +  " " + currentUser.getLastname());
                        //System.out.println(parent.toString());
                        if (updateChildListContent) {
                            updateChildListContent();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    private void updateChildListContent() {
        mChildObjects.clear();
        for (String childUID: currentUser.getChildren()) {
            //System.out.println("childuid: " + childuid);
            mDatabase.child("users").child(childUID).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Child child = dataSnapshot.getValue(Child.class);
                            child.setUsername(child.getEmail());
                            child.setUid(dataSnapshot.getKey());
                            //System.out.println("FOUND CHILD DATA FOR " + child.getUid());
                            mChildObjects.add(child);
                            setUpListAdapter();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        }
                    });
        }

    }

    private void setUpListAdapter() {
        if (mChildObjects.size() == currentUser.getChildren().size()) {
            mListOfChildren = (ListView) findViewById(R.id.child_list);
            mListAdapter = new MyListAdapter(getApplicationContext(), R.layout.child_list_item, mChildObjects);
            mListOfChildren.setAdapter(mListAdapter);
        }
    }

    private class MyListAdapter extends ArrayAdapter {
        private int layout;

        private MyListAdapter(Context context, int resource, List<Child> objects) {
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
                        Intent intent = new Intent(getApplicationContext(), ChildViewActivity.class);
                        startActivity(intent);
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

    private class ViewHolder {
        ImageView profile_pic;
        TextView name;
        TextView balance;
        ImageButton button;
    }
}
