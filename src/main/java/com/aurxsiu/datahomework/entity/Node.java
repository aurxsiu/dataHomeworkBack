package com.aurxsiu.datahomework.entity;

public class Node {
    public Node(){}
    private double x;
    private double y;

    private String name;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Node(double x, double y, String name, Integer id) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.id = id;
    }
}
