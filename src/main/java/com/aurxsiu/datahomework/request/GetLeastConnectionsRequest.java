package com.aurxsiu.datahomework.request;

public class GetLeastConnectionsRequest {
    private int type;
    private int start;
    private int end;
    public GetLeastConnectionsRequest(){}
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public GetLeastConnectionsRequest(int type, int start, int end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }
}
