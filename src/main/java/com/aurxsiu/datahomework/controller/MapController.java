package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.request.GetMapRequest;
import com.aurxsiu.datahomework.request.SearchRequest;
import com.aurxsiu.datahomework.request.GetLeastConnectionsRequest;
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
    public GetMapResponse getMap(@RequestBody GetMapRequest requset){
        return new GetMapResponse(mapService.getMap(requset.getType()));
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
}
