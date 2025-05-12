package com.aurxsiu.datahomework.struct;

import java.util.HashMap;

public class UserRate {
    public HashMap<String,HashMap<Integer,Double>> userRates;

    public UserRate(HashMap<String, HashMap<Integer, Double>> userRates) {
        this.userRates = userRates;
    }

    public UserRate(){}
}
