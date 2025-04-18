package com.aurxsiu.datahomework.service;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.util.FileHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MapService {
    //todo 排序
    public ArrayList<JourneyMap> searchMap(String s){
        return new ArrayList<>(filterByWord(s));
    }
    private HashSet<JourneyMap> filterByWord(String s){
        return FileHelper.MapFileHelper.getAllMap().stream().filter(v -> {
            return v.getName().contains(s);
        }).collect(Collectors.toCollection(HashSet::new));
    }

    public JourneyMap getMap(String name){
        //todo
        return null;
    }
}
