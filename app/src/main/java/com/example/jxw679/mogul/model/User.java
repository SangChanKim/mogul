package com.example.jxw679.mogul.model;

/**
 * Created by jxw679 on 7/6/16.
 */
public class User {

    public String username;
    public String email;
    public String firstname;
    public String lastname;
    public String accountid;
    public int balance;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
