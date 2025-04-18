package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.request.GetMapRequest;
import com.aurxsiu.datahomework.request.SearchRequest;
import com.aurxsiu.datahomework.response.GetMapResponse;
import com.aurxsiu.datahomework.response.SearchResponse;
import com.aurxsiu.datahomework.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/map")
@CrossOrigin
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("/getMap")
    public GetMapResponse getMap(@RequestBody GetMapRequest requset){
        return null;
    }
    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest searchRequest){
        String input = searchRequest.getInput();
        return new SearchResponse(mapService.searchMap(input).stream().map(JourneyMap::getName).collect(Collectors.toCollection(ArrayList::new)));
    }
}
