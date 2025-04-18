package com.aurxsiu.datahomework.request;

public class GetMapRequest {
    private String name;

    public GetMapRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
