package com.aurxsiu.datahomework.request;

public class GetMapRequest {
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GetMapRequest(int type) {
        this.type = type;
    }
}
