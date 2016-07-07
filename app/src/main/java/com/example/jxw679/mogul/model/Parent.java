package com.example.jxw679.mogul.model;

import android.os.Build;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jxw679 on 7/6/16.
 */
public class Parent implements Serializable{
    private List<String> children;
    private String customerid;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String accountid;
    private int balance;
    private String type;
    private String uid;

    public Parent() {
        super();
    }

    public Parent(String username, String email, String firstname, String lastname, String accountid, int balance, String type, String uid, List<String> children, String customerid) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountid = accountid;
        this.balance = balance;
        this.type = type;
        this.uid = uid;
        this.children = children;
        this.customerid = customerid;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAccountid() {
        return accountid;
    }

    public int getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public List<String> getChildren() {
        return children;
    }

    public String getCustomerid() {
        return customerid;
    }

    @Override
    public String toString() {
        String childrenString = "[";
        for (String child: children) {
            childrenString += child + ", ";
        }
        return username + "\n" + email + "\n" + firstname + " \n" + lastname + "\n" + accountid + "\n" + balance + "\n" + type + "\n" + uid + "\n" + childrenString + "]\n" + customerid;
    }
}
