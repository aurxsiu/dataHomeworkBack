package com.aurxsiu.datahomework.service;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.entity.Node;
import com.aurxsiu.datahomework.request.GetLeastConnectionsRequest;
import com.aurxsiu.datahomework.response.GetLeastConnectionsResponse;
import com.aurxsiu.datahomework.util.FileHelper;
import com.aurxsiu.datahomework.util.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MapService {
    //todo 排序
    public ArrayList<JourneyMap> searchMap(String s) {
        return new ArrayList<>(filterByWord(s));
    }

    private HashSet<JourneyMap> filterByWord(String s) {
        return FileHelper.MapFileHelper.getAllMap().stream().filter(v -> {
            return v.getName().contains(s);
        }).collect(Collectors.toCollection(HashSet::new));
    }

    public JourneyMap getMap(int type) {
        try {
            JourneyMap R = new JourneyMap();
            if (type == 0) {
                R.setNodes(JsonHelper.encode(new TypeReference<HashSet<Node>>() {
                }, FileHelper.readString(FileHelper.scenicMapNodeFile)));
                R.setConnections(JsonHelper.encode(new TypeReference<HashSet<ArrayList<Integer>>>() {
                }, FileHelper.readString(FileHelper.scenicMapConnectionsFile)));
            } else {
                R.setNodes(JsonHelper.encode(new TypeReference<HashSet<Node>>() {
                }, FileHelper.readString(FileHelper.schoolMapNodeFile)));
                R.setConnections(JsonHelper.encode(new TypeReference<HashSet<ArrayList<Integer>>>() {
                }, FileHelper.readString(FileHelper.schoolMapConnectionsFile)));
            }
            return R;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class Connection {
        int node1;
        int node2;
        int length;

        public Connection(int node1, int node2, int length) {
            this.node1 = node1;
            this.node2 = node2;
            this.length = length;
        }
    }

    private List<Connection> dijkstra(int start, int end, List<Connection> connections) {
        // Step 1: Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (Connection conn : connections) {
            graph.putIfAbsent(conn.node1, new ArrayList<>());
            graph.putIfAbsent(conn.node2, new ArrayList<>());
            graph.get(conn.node1).add(new int[]{conn.node2, conn.length});
            graph.get(conn.node2).add(new int[]{conn.node1, conn.length}); // For undirected graph
        }

        // Step 2: Initialize Dijkstra's data structures
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // (node, distance)

        dist.put(start, 0);
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int currentDist = current[1];

            // If we reach the target node, no need to continue
            if (node == end) break;

            // Process neighbors
            for (int[] neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                int neighborNode = neighbor[0];
                int edgeWeight = neighbor[1];
                int newDist = currentDist + edgeWeight;

                if (newDist < dist.getOrDefault(neighborNode, Integer.MAX_VALUE)) {
                    dist.put(neighborNode, newDist);
                    prev.put(neighborNode, node);
                    pq.offer(new int[]{neighborNode, newDist});
                }
            }
        }

        // Step 3: Reconstruct the path
        List<Connection> pathConnections = new ArrayList<>();
        Integer node = end;
        while (prev.containsKey(node)) {
            int prevNode = prev.get(node);
            for (Connection conn : connections) {
                if ((conn.node1 == node && conn.node2 == prevNode) || (conn.node1 == prevNode && conn.node2 == node)) {
                    pathConnections.add(conn);
                    break;
                }
            }
            node = prevNode;
        }

        Collections.reverse(pathConnections);
        return pathConnections;
    }

    public static Random random = new Random(2023211080);

    public GetLeastConnectionsResponse getLeastConnections(GetLeastConnectionsRequest request) {
        try {
            ArrayList<ArrayList<Integer>> cons = null;
            ArrayList<Node> nodes = null;
            switch (request.getType()) {
                case 0 -> {
                    cons = JsonHelper.encode(new TypeReference<ArrayList<ArrayList<Integer>>>() {
                    }, FileHelper.readString(FileHelper.scenicMapConnectionsFile));
                    nodes = JsonHelper.encode(new TypeReference<ArrayList<Node>>() {
                    }, FileHelper.readString(FileHelper.scenicMapNodeFile));
                }
                case 1 -> {
                    cons = JsonHelper.encode(new TypeReference<ArrayList<ArrayList<Integer>>>() {
                    }, FileHelper.readString(FileHelper.schoolMapConnectionsFile));
                    nodes = JsonHelper.encode(new TypeReference<ArrayList<Node>>() {
                    }, FileHelper.readString(FileHelper.scenicMapNodeFile));
                }
                default -> {
                    throw new RuntimeException("CASE 错误");
                }
            }
            ArrayList<Connection> connectionArrayList = new ArrayList<>();
            ArrayList<Connection> connectionArrayList2 = new ArrayList<>();
            for (ArrayList<Integer> con : cons) {
                Node node1 = nodes.stream().filter(v -> v.getId().equals(con.getFirst())).findFirst().get();
                Node node2 = nodes.stream().filter(v -> v.getId().equals(con.getLast())).findFirst().get();
                double xGap=node1.getX() - node2.getX(),yGap=node1.getY() - node2.getY();
                int length=(int)Math.ceil(Math.sqrt(xGap*xGap+yGap*yGap));
                if (length < 0) {
                    throw new RuntimeException("超出范围");
                }
                if (length == 0 || length == 1) {
                    System.out.println("error");
                }
                connectionArrayList.add(new Connection(node1.getId(), node2.getId(), length));
                connectionArrayList2.add(new Connection(node1.getId(), node2.getId(), (int) Math.ceil(random.nextDouble() * 5 * length)));
            }

            List<Connection> dijkstra = dijkstra(request.getStart(), request.getEnd(), connectionArrayList);
            List<Connection> dijkstra1 = dijkstra(request.getStart(), request.getEnd(), connectionArrayList2);
            ArrayList<ArrayList<Integer>> collect = dijkstra.stream().map(v -> new ArrayList<Integer>(List.of(v.node1, v.node2, v.length))).collect(Collectors.toCollection(ArrayList::new));
            ArrayList<ArrayList<Integer>> collect1 = dijkstra1.stream().map(v -> new ArrayList<Integer>(List.of(v.node1, v.node2, v.length))).collect(Collectors.toCollection(ArrayList::new));
            return new GetLeastConnectionsResponse(collect, collect1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
