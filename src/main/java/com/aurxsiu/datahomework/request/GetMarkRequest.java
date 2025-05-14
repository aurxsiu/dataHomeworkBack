package com.aurxsiu.datahomework.request;

public class GetMarkRequest {

    private String title;
    private int userId;

    public GetMarkRequest(String title, int userId) {
        this.title = title;
        this.userId = userId;
    }

    public GetMarkRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
