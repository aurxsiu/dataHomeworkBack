package com.aurxsiu.datahomework.response;

import java.util.ArrayList;

public class SearchResponse {
    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    public SearchResponse(ArrayList<String> result) {
        this.result = result;
    }

    private ArrayList<String> result;
}
