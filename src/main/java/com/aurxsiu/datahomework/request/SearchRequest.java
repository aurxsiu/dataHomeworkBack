package com.aurxsiu.datahomework.request;

public class SearchRequest {
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public SearchRequest(String input) {
        this.input = input;
    }
}
