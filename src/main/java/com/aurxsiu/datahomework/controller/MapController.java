package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.request.*;
import com.aurxsiu.datahomework.response.GetLeastConnectionsResponse;
import com.aurxsiu.datahomework.response.GetMapResponse;
import com.aurxsiu.datahomework.response.SearchResponse;
import com.aurxsiu.datahomework.service.MapService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map")
@CrossOrigin
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("/getMap")
    public GetMapResponse getMap(@RequestBody GetMapRequest request){
        return new GetMapResponse(mapService.getMap(request.getType(),request.getName()));
    }
    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest searchRequest){
        String input = searchRequest.getInput();
        return new SearchResponse(mapService.searchMap(input));
    }
    @PostMapping("getLeastConnections")
    public GetLeastConnectionsResponse getLeastConnections(@RequestBody GetLeastConnectionsRequest request){
        return mapService.getLeastConnections(request);
    }

    @PostMapping("getRate")
    public Double GetRate(@RequestBody GetRateRequest request){
        return mapService.getRate(request.mapName);
    }

    @PostMapping("addRate")
    public Double AddRate(@RequestBody AddRateRequest request){
        return mapService.addRate(request.getMapName(),request.getUserId(),request.getRate());
    }
}
