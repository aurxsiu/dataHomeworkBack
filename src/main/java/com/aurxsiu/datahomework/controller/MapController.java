package com.aurxsiu.datahomework.controller;

import com.aurxsiu.datahomework.entity.MdMark;
import com.aurxsiu.datahomework.request.*;
import com.aurxsiu.datahomework.response.GetLeastConnectionsResponse;
import com.aurxsiu.datahomework.response.GetMapResponse;
import com.aurxsiu.datahomework.response.SearchResponse;
import com.aurxsiu.datahomework.service.MapService;
import com.aurxsiu.datahomework.util.FileHelper;
import com.aurxsiu.datahomework.util.SnowflakeIdWorker;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;

@RestController
@RequestMapping("/map")
@CrossOrigin
public class MapController {
    private final MapService mapService;
    private final SnowflakeIdWorker snowflakeIdWorker;

    public MapController(MapService mapService,SnowflakeIdWorker snowflakeIdWorker) {
        this.mapService = mapService;
        this.snowflakeIdWorker=snowflakeIdWorker;
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

    @PostMapping("uploadFile")
    public long UploadFile(@RequestParam("file") MultipartFile file,@RequestParam("userId") Integer userId,@RequestParam("mapName") String mapName){
        long fileId= snowflakeIdWorker.nextId();
        File target = FileHelper.MarkFileHelper.createFile(fileId,userId,mapName);
        try (FileOutputStream out = new FileOutputStream(target)){
            out.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileId;
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable String id) throws IOException {
        File[] matches = FileHelper.MarkFileHelper.getFolder().listFiles((dir, name) -> name.endsWith("_"+id));
        if (matches == null || matches.length == 0) {
            return ResponseEntity.notFound().build();
        }

        File file = matches[0];
        String contentType = Files.probeContentType(file.toPath());

        return ResponseEntity.ok().body(new FileSystemResource(file));
    }

    @PostMapping("/saveMark")
    public void SaveMark(@RequestBody MdMark mark){
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(mark.markContent);

        HashSet<Long> contentFile = new HashSet<>();
        // 遍历 AST 节点
        document.getDescendants().forEach(node -> {
            if (node instanceof Link link) {
                String url = link.getUrl().toString();
                mark.filesUploaded.remove(Long.valueOf(url.substring(url.lastIndexOf("/")+1)));
            } else if (node instanceof Image image) {
                String url = image.getUrl().toString();
                mark.filesUploaded.remove(Long.valueOf(url.substring(url.lastIndexOf("/")+1)));
            }
        });

        FileHelper.MarkFileHelper.deleteFile(mark.filesUploaded);


    }

}
