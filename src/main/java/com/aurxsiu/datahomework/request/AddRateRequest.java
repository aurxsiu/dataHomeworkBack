package com.aurxsiu.datahomework.request;

public class AddRateRequest {
    private Integer userId;
    private String mapName;
    private Double rate;

    public AddRateRequest(Integer userId, String mapName, Double rate) {
        this.userId = userId;
        this.mapName = mapName;
        this.rate = rate;
    }

    public AddRateRequest(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
