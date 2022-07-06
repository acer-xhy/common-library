package com.jgw.common_library.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * 对象序列化为JSON字符串
     * 仅支持字段为基本数据类型或引用对象中字段也全部为基本数据类型的对象
     * 不能包含公共的非基本数据类型的get开头的方法(如Drawable(崩溃)(替换为_get),ToOne(非target类型)等)
     * @return jsonString
     */
    public static String toJsonString(Object object) {
        //noinspection IfStatementWithIdenticalBranches
        if (object instanceof List) {
            return JSONArray.toJSONString(object);
        } else {
            return JSON.toJSONString(object);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)||TextUtils.equals("{}",json)){
            return new ArrayList<>();
        }
        return JSON.parseArray(json, clazz);
    }

}
