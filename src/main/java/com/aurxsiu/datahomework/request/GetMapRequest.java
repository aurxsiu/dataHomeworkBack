package com.aurxsiu.datahomework.request;

public class GetMapRequest {
    private int type;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GetMapRequest(int type,String name) {
        this.type = type;this.name=name;
    }

    public GetMapRequest() {
    }
}
