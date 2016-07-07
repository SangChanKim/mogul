package com.example.jxw679.mogul.model;

/**
 * Created by jxw679 on 7/6/16.
 */
public class Task {
    public String deadline;
    public String description;
    public String owner;
    public String taskname;

    public Task() {

    }

    public String getDeadline() {
        return deadline;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public String getTaskname() {
        return taskname;
    }

    public Task(String deadline, String description, String owner, String taskname) {
        this.deadline = deadline;
        this.description = description;
        this.owner = owner;
        this.taskname = taskname;
    }

    public String toString() {
        return taskname + ": " + description + ", " + owner + ", " + deadline;
    }
}
