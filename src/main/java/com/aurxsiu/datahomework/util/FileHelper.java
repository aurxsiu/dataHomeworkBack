package com.aurxsiu.datahomework.util;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;

public class FileHelper {
    public static final File scenicFile = new File(FileHelper.getResourcePath()+"/scenic.mysql");
    public static final File schoolFile = new File(FileHelper.getResourcePath()+"/school.mysql");
    /**
     * 内部方法
     * */
    private static void changeToData() throws Exception{
        File file = new File(FileHelper.getResourcePath()+"/scenicName.aur");
        String s = readString(file);
        String[] split = s.split("\r?\n|\r");
        HashSet<JourneyMap> set = new HashSet<>();
        for (String string : split) {
            String[] split1 = string.split(" ");
            if(split1.length!=3){
                throw new RuntimeException("格式错误");
            }
            String name = split1[0];
            int judge = Integer.parseInt(split1[1]);
            int popular = Integer.parseInt(split1[2]);
            set.add(new JourneyMap(name,judge,popular));
        }
        String to = JsonHelper.decode(set);
        scenicFile.createNewFile();
        writeString(scenicFile,to);


        file = new File(FileHelper.getResourcePath()+"/schoolName.aur");
        s = readString(file);
        split = s.split("\r?\n|\r");
        set = new HashSet<>();
        for (String string : split) {
            String[] split1 = string.split(" ");
            if(split1.length!=3){
                throw new RuntimeException("格式错误");
            }
            String name = split1[0];
            int judge = Integer.parseInt(split1[1]);
            int popular = Integer.parseInt(split1[2]);
            set.add(new JourneyMap(name,judge,popular));
        }
        to = JsonHelper.decode(set);
        schoolFile.createNewFile();
        writeString(schoolFile,to);
    }
    public static String getResourcePath(){
        return Objects.requireNonNull(FileHelper.class.getClassLoader().getResource("")).getPath()+"/static";
    }
    public static String readString(File file)  throws Exception{
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    /**
     * 不保证成功,也不校验
     * */
    public static void writeString(File file,String result) throws Exception{
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(result.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void main(String[] args) throws Exception {
        changeToData();
    }
    public static class MapFileHelper{
        public static class SchoolMapFileHelper{
            public static HashSet<JourneyMap> getSchools(){
                synchronized (SchoolMapFileHelper.class){
                    try {
                        return JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {}, readString(schoolFile));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        public static class ScenicMapFileHelper{
            public static HashSet<JourneyMap> getScenic(){
                synchronized (ScenicMapFileHelper.class){
                    try {
                        return JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {}, readString(scenicFile));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public static HashSet<JourneyMap> getAllMap() {
            HashSet<JourneyMap> result = ScenicMapFileHelper.getScenic();
            result.addAll(SchoolMapFileHelper.getSchools());
            return result;
        }
    }

}
