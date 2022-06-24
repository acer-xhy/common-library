package com.jgw.common_library.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.List;

public class JsonUtils {
    public static String toJsonString(Object object) {
        return toJsonString(object, true, true, true, true);
    }

    public static String toJsonString(List<?> object) {
        return JSONArray.toJSONString(object);
    }

    public static String toJsonString(Object object, boolean fieldOnly, boolean jsonTypeSupport, boolean jsonFieldSupport, boolean fieldGenericSupport) {
        SerializeConfig config = new SerializeConfig();
        config.registerIfNotExists(object.getClass(), object.getClass().getModifiers(), fieldOnly, jsonTypeSupport, jsonFieldSupport, fieldGenericSupport);
        return JSON.toJSONString(object, config);
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
