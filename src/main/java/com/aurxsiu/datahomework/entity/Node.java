package com.aurxsiu.datahomework.entity;

public class Node {
    public Node(){}
    private double x;
    private double y;

    private String name;
    private Integer id;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node(double x, double y, String name, Integer id, String type) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.id = id;
        this.type = type;
    }

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
}
