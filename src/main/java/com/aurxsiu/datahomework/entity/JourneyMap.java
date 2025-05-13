package com.aurxsiu.datahomework.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class JourneyMap {
    private HashSet<Node> nodes;
    private HashSet<ArrayList<Integer>> connections;
    private String name;
    private Integer type;//0表示景区,1表示校园
    private Double judge;
    private Integer popular;
    public JourneyMap(){}


    public JourneyMap(String name, Double judge, int popular,int type) {
        this.name = name;
        this.judge = judge;
        this.popular = popular;
        this.type = type;
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashSet<Node> nodes) {
        this.nodes = nodes;
    }


    public HashSet<ArrayList<Integer>> getConnections() {
        return connections;
    }

    public void setConnections(HashSet<ArrayList<Integer>> connections) {
        this.connections = connections;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getJudge() {
        return judge;
    }

    public void setJudge(Double judge) {
        this.judge = judge;
    }

    public Integer getPopular() {
        return popular;
    }

    public void setPopular(Integer popular) {
        this.popular = popular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyMap that = (JourneyMap) o;
        return type==that.type && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
