package com.example.jxw679.mogul.model.requests;

/**
 * Created by jxw679 on 7/6/16.
 */
public class TaskRequest {
    public String deadline;
    public String description;
    public String owner;
    public String taskname;
    public int reward;
    public String assignto;
    public String type;

    public TaskRequest() {

    }


    public TaskRequest(String deadline, String description, String owner, String taskname, boolean completed, int reward, String assignto) {
        this.deadline = deadline;
        this.description = description;
        this.owner = owner;
        this.taskname = taskname;
        this.reward = reward;
        this.assignto = assignto;
        this.type = "addTask";

    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAssignto(String assignto) {
        this.assignto = assignto;
    }

    public String getAssignto() {

        return assignto;
    }

    public int getReward() {
        return reward;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getOwner() {
        return owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public String getTaskname() {
        return taskname;
    }

    public String toString() {
        return taskname + ": " + description + ", " + owner + ", " + deadline + ", " + assignto + ", " + reward;
    }
}
