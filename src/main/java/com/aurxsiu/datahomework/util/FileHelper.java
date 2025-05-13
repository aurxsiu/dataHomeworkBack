package com.aurxsiu.datahomework.util;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.entity.Node;
import com.aurxsiu.datahomework.entity.User;
import com.aurxsiu.datahomework.struct.UserRate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FileHelper {
    //todo 锁加了一半就跑了...

    public static final File scenicFile = new File(FileHelper.getDataPath() + "/scenic.mysql");
    public static final File schoolFile = new File(FileHelper.getDataPath() + "/school.mysql");
    private static final File scenicMapFile = new File(FileHelper.getResourcePath() + "/scenic.json");
    private static final File schoolMapFile = new File(FileHelper.getResourcePath() + "/schoolMap.json");
    public static final File scenicMapNodeFile = new File(FileHelper.getResourcePath() + "/scenicMapNodes.mysql");
    public static final File scenicMapConnectionsFile = new File(FileHelper.getResourcePath() + "/scenicMapConnections.mysql");
    public static final File schoolMapNodeFile = new File(FileHelper.getResourcePath() + "/schoolMapNodes.mysql");
    public static final File schoolMapConnectionsFile = new File(FileHelper.getResourcePath() + "/schoolMapConnects.mysql");
    private static final File userFile = new File(FileHelper.getDataPath() + "/user.mysql");

    private static final File user_rateFile=new File(FileHelper.getDataPath()+"/UserRate.json");

    /**
     * 内部方法
     */
    private static void changeToData() throws Exception {
        File file = new File(FileHelper.getResourcePath() + "/scenicName.aur");
        String s = readString(file);
        String[] split = s.split("\r?\n|\r");
        HashSet<JourneyMap> set = new HashSet<>();
        for (String string : split) {
            String[] split1 = string.split(" ");
            if (split1.length != 3) {
                throw new RuntimeException("格式错误");
            }
            String name = split1[0];
            int judge = Integer.parseInt(split1[1]);
            int popular = Integer.parseInt(split1[2]);
            set.add(new JourneyMap(name, (double) judge, popular, 0));
        }
        String to = JsonHelper.decode(set);
        scenicFile.createNewFile();
        writeString(scenicFile, to);


        file = new File(FileHelper.getResourcePath() + "/schoolName.aur");
        s = readString(file);
        split = s.split("\r?\n|\r");
        set = new HashSet<>();
        for (String string : split) {
            String[] split1 = string.split(" ");
            if (split1.length != 3) {
                throw new RuntimeException("格式错误");
            }
            String name = split1[0];
            int judge = Integer.parseInt(split1[1]);
            int popular = Integer.parseInt(split1[2]);
            set.add(new JourneyMap(name, (double)judge, popular, 1));
        }
        to = JsonHelper.decode(set);
        schoolFile.createNewFile();
        writeString(schoolFile, to);
    }

    public static String getResourcePath() {
        return Objects.requireNonNull(FileHelper.class.getClassLoader().getResource("")).getPath() + "/static";
    }
    public static String getDataPath(){
        return System.getProperty("user.dir").replace("\\", "/");
    }
    //todo 改private
    public static String readString(File file){
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    //todo 改private
    /**
     * 不保证成功,也不校验
     */
    public static void writeString(File file, String result){
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(result.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        getNodeMapSchool();
        getNodeMapScenic();
    }

    public static class MapFileHelper {
        private static boolean addPopularIfExist(String name, File schoolFile) {
            HashSet<JourneyMap> encode = JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {
            }, readString(schoolFile));
            for (JourneyMap journeyMap : encode) {
                if(journeyMap.getName().equals(name)){
                    journeyMap.setPopular(journeyMap.getPopular()+1);
                    writeString(schoolFile,JsonHelper.decode(encode));
                    return true;
                }
            }
            return false;
        }
        public static class SchoolMapFileHelper {
            public static HashSet<JourneyMap> getSchools() {
                synchronized (SchoolMapFileHelper.class) {
                    try {
                        return JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {
                        }, readString(schoolFile));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            public static boolean addPopularIfExist(String name){
                synchronized (SchoolMapFileHelper.class) {
                    return MapFileHelper.addPopularIfExist(name, schoolFile);
                }
            }


        }

        public static class ScenicMapFileHelper {
            public static HashSet<JourneyMap> getScenic() {
                synchronized (ScenicMapFileHelper.class) {
                    try {
                        return JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {
                        }, readString(scenicFile));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            public static boolean addPopularIfExist(String name){
                synchronized (ScenicMapFileHelper.class) {
                    return MapFileHelper.addPopularIfExist(name, scenicFile);
                }
            }
        }

        public static HashSet<JourneyMap> getAllMap() {
            HashSet<JourneyMap> result = ScenicMapFileHelper.getScenic();
            result.addAll(SchoolMapFileHelper.getSchools());
            return result;
        }
    }

    public static class NodeMap {
        public static class Node {
            private int id;
            private double x;
            private double y;

            public Node() {
            }

            public Node(int id, double x, double y) {
                this.id = id;
                this.x = x;
                this.y = y;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

        private ArrayList<Node> nodes;
        private ArrayList<ArrayList<Integer>> connections;

        public NodeMap(ArrayList<Node> nodes, ArrayList<ArrayList<Integer>> connections) {
            this.nodes = nodes;
            this.connections = connections;
        }

        public NodeMap() {
        }

        public ArrayList<Node> getNodes() {
            return nodes;
        }

        public void setNodes(ArrayList<Node> nodes) {
            this.nodes = nodes;
        }

        public ArrayList<ArrayList<Integer>> getConnections() {
            return connections;
        }

        public void setConnections(ArrayList<ArrayList<Integer>> connections) {
            this.connections = connections;
        }
    }

    private static int index;

    /**
     * 内部方法
     */
    private static void getNodeMapScenic() throws Exception {
        NodeMap encode = JsonHelper.encode(new TypeReference<NodeMap>() {
        }, FileHelper.readString(scenicMapFile));

        int nodeNum = encode.getNodes().size();
        assert nodeNum == 100;

        int buildingsNum = 40;
        int restNum = 20;
        int storeNum = 20;
        int wcNum = 20;

        String[] buildings = FileHelper.readString(new File(FileHelper.getResourcePath() + "/sc.aur")).split("\r?\n|\r");
        String[] rests = FileHelper.readString(new File(FileHelper.getResourcePath() + "/rest.aur")).split("\r?\n|\r");
        String[] stores = FileHelper.readString(new File(FileHelper.getResourcePath() + "/store.aur")).split("\r?\n|\r");
        JourneyMap map = new JourneyMap();
        index = -1;
        ArrayList<NodeMap.Node> nodes = encode.getNodes();
        Collections.shuffle(nodes);
        ArrayList<Node> nodeList = nodes.stream().map(v -> {
            index++;
            if (index < buildingsNum) {
                return new Node(v.x, v.y, buildings[index], v.id, "buildings");
            } else if (index < buildingsNum + restNum) {
                return new Node(v.x, v.y, rests[index - buildingsNum], v.id, "restaurant");
            } else if (index < buildingsNum + restNum + storeNum) {
                return new Node(v.x, v.y, stores[index - buildingsNum - restNum], v.id, "store");
            } else {
                return new Node(v.x, v.y, "WC" + (char) ('A' + index - buildingsNum - restNum - storeNum), v.id, "wc");
            }
        }).sorted(Comparator.comparing(Node::getId)).collect(Collectors.toCollection(ArrayList::new));

        File newFile = new File(FileHelper.getResourcePath() + "/scenicMapNodes.mysql");
        newFile.createNewFile();
        FileHelper.writeString(newFile, JsonHelper.decode(nodeList));

        newFile = new File(FileHelper.getResourcePath() + "/scenicMapConnections.mysql");
        newFile.createNewFile();
        FileHelper.writeString(newFile, JsonHelper.decode(encode.getConnections()));
    }

    private static void getNodeMapSchool() throws Exception {
        NodeMap encode = JsonHelper.encode(new TypeReference<NodeMap>() {
        }, FileHelper.readString(schoolMapFile));

        ArrayList<NodeMap.Node> nodes = encode.getNodes();
        Collections.shuffle(nodes);

        index = -1;
        int jiaoxuelouNum = 20;
        int bangonglouNum = 20;
        int sushelouNum = 20;
        int restNum = 20;
        int wcNum = 20;

        ArrayList<Node> collect = nodes.stream().map(v -> {
            index++;
            if (index < jiaoxuelouNum) {
                return new Node(v.x, v.y, "教学楼" + (jiaoxuelouNum - index), v.id, "教学楼");
            } else if (index < jiaoxuelouNum + bangonglouNum) {
                return new Node(v.x, v.y, "办公楼" + (bangonglouNum + jiaoxuelouNum - index), v.id, "办公楼");
            } else if (index < jiaoxuelouNum + bangonglouNum + sushelouNum) {
                return new Node(v.x, v.y, "宿舍" + (sushelouNum + jiaoxuelouNum - bangonglouNum - index), v.id, "宿舍");
            } else if (index < jiaoxuelouNum + bangonglouNum + sushelouNum + wcNum) {
                return new Node(v.x, v.y, "WC" + (wcNum + jiaoxuelouNum + bangonglouNum + sushelouNum - index), v.id, "wc");
            } else {
                return new Node(v.x, v.y, "食堂" + (jiaoxuelouNum + bangonglouNum + sushelouNum + wcNum + restNum - index), v.id, "restaurant");
            }
        }).sorted(Comparator.comparing(Node::getId)).collect(Collectors.toCollection(ArrayList::new));

        File newFile = new File(FileHelper.getResourcePath() + "/schoolMapNodes.mysql");
        newFile.createNewFile();
        FileHelper.writeString(newFile, JsonHelper.decode(collect));
        newFile = new File(FileHelper.getResourcePath() + "/schoolMapConnects.mysql");
        newFile.createNewFile();
        FileHelper.writeString(newFile, JsonHelper.decode(encode.getConnections()));
    }

    //todo 改为setUser(),不能直接setFile
    public static void setUserFile(String content){
        synchronized (userFile){
            try{
                if(!userFile.isFile()){
                    boolean newFile = userFile.createNewFile();
                    writeString(userFile,"[]");
                }
                writeString(userFile,content);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    public static HashSet<User> getUser(){
        synchronized (userFile){
            try{
                if(!userFile.isFile()){
                    boolean newFile = userFile.createNewFile();
                    writeString(userFile,"[]");
                    return new HashSet<>();
                }
                return JsonHelper.encode(new TypeReference<HashSet<User>>() {
                },readString(userFile));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void addPopular(String name){
        if (!MapFileHelper.ScenicMapFileHelper.addPopularIfExist(name)) {
            if (!MapFileHelper.SchoolMapFileHelper.addPopularIfExist(name)) {
                throw new RuntimeException("name 不存在!");
            }
        }
    }



    public static class UserRateFileHelper{
        private static boolean createUserRateFileIfNotExist(){
            if(!user_rateFile.isFile()){
                try {
                    user_rateFile.createNewFile();
                    //todo 调试
                    HashMap<String, HashMap<Integer, Double>> stringHashMapHashMap = new HashMap<>();
                    writeString(user_rateFile,"{}");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            return false;
        }
        /**
         * 自己挖的坑,返回空对象,懒得填了
         * */
        public static UserRate getUserRates(){
            if (createUserRateFileIfNotExist()) {
                return new UserRate();
            }

            return JsonHelper.encode(new TypeReference<UserRate>() {
            },readString(user_rateFile));
        }
        public static UserRate addRate(int userId,String mapName,double rate){
            UserRate userRates = getUserRates();
            if(userRates.userRates==null){
                userRates.userRates=new HashMap<String,HashMap<Integer,Double>>();
            }

            if (userRates.userRates.containsKey(mapName)) {
                userRates.userRates.get(mapName).put(userId,rate);
            }else{
                HashMap<Integer, Double> integerDoubleHashMap = new HashMap<>();
                integerDoubleHashMap.put(userId,rate);
                userRates.userRates.put(mapName,integerDoubleHashMap);
            }

            writeString(user_rateFile,JsonHelper.decode(userRates));

            return userRates;
        }
    }


}
