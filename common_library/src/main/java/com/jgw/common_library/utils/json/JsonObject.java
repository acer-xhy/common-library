package com.jgw.common_library.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonObject {
    private final JSONObject jb;

    public JsonObject() {
        jb = new JSONObject();
    }

    protected JsonObject(String json) {
        jb = JSON.parseObject(json);
    }

    protected JsonObject(JSONObject jsonObject) {
        jb = jsonObject;
    }

    public void put(String key, Object value) {
        jb.put(key, value);
    }

    public void remove(String key) {
        jb.remove(key);
    }

    public Map<String, Object> covertMap() {
        return jb;
    }

    public String getString(String key) {
        return jb.getString(key);
    }

    public byte getByte(String key) {
        return jb.getByteValue(key);
    }

    public short getShort(String key) {
        return jb.getShortValue(key);
    }

    public int getInt(String key) {
        return jb.getIntValue(key);
    }

    public long getLong(String key) {
        return jb.getLongValue(key);
    }

    public float getFloat(String key) {
        return jb.getFloatValue(key);
    }

    public double getDouble(String key) {
        return jb.getDoubleValue(key);
    }

    public boolean getBoolean(String key) {
        return jb.getBooleanValue(key);
    }

    public JsonObject getJsonObject(String key) {
        JSONObject jsonObject = jb.getJSONObject(key);
        return new JsonObject(jsonObject);
    }

    public JsonArray getJsonArray(String key) {
        JSONArray jsonArray = jb.getJSONArray(key);
        return new JsonArray(jsonArray);
    }

    public <T> T toJavaObject(Class<T> clazz) {
        return jb.toJavaObject(clazz);
    }
}
