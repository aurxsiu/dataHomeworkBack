package com.aurxsiu.datahomework.response;

import com.aurxsiu.datahomework.entity.JourneyMap;

import java.util.ArrayList;

public class SearchResponse {
    private ArrayList<JourneyMap> result;

    public SearchResponse(ArrayList<JourneyMap> result) {
        this.result = result;
    }

    public ArrayList<JourneyMap> getResult() {
        return result;
    }

    public void setResult(ArrayList<JourneyMap> result) {
        this.result = result;
    }
}
