package me.mechanic.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JSONData {
    public static Gson gson = (new GsonBuilder()).enableComplexMapKeySerialization().setPrettyPrinting().create();

    private static final Type TT_mapStringBP = (new TypeToken<Map<String, Boolean>>() {

    }).getType();

    private static final Type TT_mapStringIB = (new TypeToken<Map<Integer, Boolean>>() {

    }).getType();

    private static final Type TT_mapStringSS = (new TypeToken<Map<String, String>>() {

    }).getType();

    public static Map<String, Boolean> jsonToMap(String json) {
        Map<String, Boolean> ret = new HashMap<String, Boolean>();
        if (json == null || json.isEmpty())
            return ret;
        return (Map<String, Boolean>)gson.fromJson(json, TT_mapStringBP);
    }

    public static String mapToJson(Map<String, Boolean> map) {
        if (map == null)
            map = new HashMap<String, Boolean>();
        return gson.toJson(map);
    }

    public static Map<Integer, Boolean> jsonToMapIB(String json) {
        Map<Integer, Boolean> ret = new HashMap<Integer, Boolean>();
        if (json == null || json.isEmpty())
            return ret;
        return (Map<Integer, Boolean>)gson.fromJson(json, TT_mapStringIB);
    }

    public static String mapToJsonIB(Map<Integer, Boolean> map) {
        if (map == null)
            map = new HashMap<Integer, Boolean>();
        return gson.toJson(map);
    }

    public static Map<String, String> jsonToMapSS(String json) {
        Map<String, String> ret = new HashMap<String, String>();
        if (json == null || json.isEmpty())
            return ret;
        return (Map<String, String>)gson.fromJson(json, TT_mapStringSS);
    }

    public static String mapToJsonSS(Map<String, String> map) {
        if (map == null)
            map = new HashMap<String, String>();
        return gson.toJson(map);
    }
}
