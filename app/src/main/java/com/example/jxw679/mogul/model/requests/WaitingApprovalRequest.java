package com.example.jxw679.mogul.model.requests;

/**
 * Created by jxw679 on 7/7/16.
 */
public class WaitingApprovalRequest {

    private String needApprovalBy;
    private String requester;
    private String type = "approvalRequest";
    private String taskUID;

    public WaitingApprovalRequest() {

    }

    public WaitingApprovalRequest(String needApprovalBy, String requester, String type, String taskUID) {

    }

    public String getNeedApprovalBy() {
        return needApprovalBy;
    }

    public String getType() {
        return type;
    }

    public String getRequester() {
        return requester;
    }

    public String getTaskUID() {
        return taskUID;
    }

    public void setNeedApprovalBy(String needApprovalBy) {
        this.needApprovalBy = needApprovalBy;
    }

    public void setTaskUID(String taskUID) {
        this.taskUID = taskUID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}
