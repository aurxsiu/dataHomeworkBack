package Test;

import com.aurxsiu.datahomework.entity.JourneyMap;
import com.aurxsiu.datahomework.util.FileHelper;
import com.aurxsiu.datahomework.util.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        String s = FileHelper.readString(FileHelper.scenicFile);
        JsonHelper.encode(new TypeReference<HashSet<JourneyMap>>() {
        },s);
    }
}
