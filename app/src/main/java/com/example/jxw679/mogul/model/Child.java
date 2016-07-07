package com.example.jxw679.mogul.model;

import java.util.List;
/**
 * Created by jxw679 on 7/6/16.
 */
public class Child {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String accountid;
    private int balance;
    private List<String> parents;
    private List<Task> tasks;
    private String type;
    private String uid;

    public Child() {

    }

    public Child(String username, String email, String firstname, String lastname, String accountid, int balance, String type, String uid, List<String> parents, List<Task> tasks) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountid = accountid;
        this.balance = balance;
        this.type = type;
        this.uid = uid;
        this.parents = parents;
        this.tasks = tasks;
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

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<String> getParents() {
        return parents;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String toString() {
        String parentsString = "";
        for (String child: parents) {
            parentsString += child + "\n";
        }
        String tasksString = "";
        for (Task task: tasks) {
            tasksString += task.toString() + "\n";
        }
        return username + "\n" + email + "\n" + firstname + " \n" + lastname + "\n" + accountid + "\n" + balance + "\n" + type + "\n" + uid + "\n" + parentsString + tasksString;
    }
}
