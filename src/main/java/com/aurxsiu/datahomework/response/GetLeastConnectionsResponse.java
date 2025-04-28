package com.aurxsiu.datahomework.response;

import java.util.ArrayList;

public class GetLeastConnectionsResponse {
    private ArrayList<ArrayList<Integer>> leastLengthConnections;//0,1:node,2:length
    private ArrayList<ArrayList<Integer>> leastTimeConnections;

    public GetLeastConnectionsResponse(ArrayList<ArrayList<Integer>> leastLengthConnections, ArrayList<ArrayList<Integer>> leastTimeConnections) {
        this.leastLengthConnections = leastLengthConnections;
        this.leastTimeConnections = leastTimeConnections;
    }

    public GetLeastConnectionsResponse() {
    }

    public ArrayList<ArrayList<Integer>> getLeastTimeConnections() {
        return leastTimeConnections;
    }

    public void setLeastTimeConnections(ArrayList<ArrayList<Integer>> leastTimeConnections) {
        this.leastTimeConnections = leastTimeConnections;
    }

    public ArrayList<ArrayList<Integer>> getLeastLengthConnections() {
        return leastLengthConnections;
    }

    public void setLeastLengthConnections(ArrayList<ArrayList<Integer>> leastLengthConnections) {
        this.leastLengthConnections = leastLengthConnections;
    }
}
