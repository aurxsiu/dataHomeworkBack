package com.aurxsiu.datahomework.response;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.entity.Node;

import java.util.HashSet;

public class GetMapResponse {
    private JourneyMap map;

    public GetMapResponse(JourneyMap map) {
        this.map = map;
    }

    public JourneyMap getMap() {
        return map;
    }

    public void setMap(JourneyMap map) {
        this.map = map;
    }
}
