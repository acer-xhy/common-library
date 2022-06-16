package com.jgw.common_library.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;

import java.util.List;

/**
 * Created by XiongShaoWu
 * on 2019/9/26
 */
public class MMKVUtils {

    public static final String USER_REMEMBER_ME = "user_remember_me";
    public static final String USER_MOBILE = "user_mobile";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TOKEN = "user_token";
    public static final String ORGANIZATION_ID = "organization_id";
    public static final String ORGANIZATION_NAME = "organization_name";
    public static final String ORGANIZATION_ICON = "organization_icon";
    public static final String SYSTEM_ID = "system_id";
    public static final String SYSTEM_NAME = "system_name";

    public static final String FIRST_IN = "first_in";
    @Deprecated
    public static final String PERMISSION_MENU = "permission_menu";//权限码

    public static final String HOME_MENU = "home_menu";//首页菜单带层级
    public static final String LOCAL_MENU = "local_menu";//本地菜单平铺功能
    public static final String CURRENT_CUSTOMER_ID = "current_customer_id";
    public static final String CURRENT_CUSTOMER_TYPE = "current_customer_type";// 0客户 1企业总部
    public static final String LAST_CHECK_VERSION_CODE = "last_check_version_code";//上次检测版本的版本号
    public static final String LAST_CHECK_VERSION_TIME = "last_check_version_time";//上次检测版本的时间
    public static final String NUMBER_OF_REMINDERS = "number_of_reminders";//提醒次数
    public static final String TEMP_DATA = "temp_data";//临时存储文件
    public static final String SYSTEM_EXPIRE_TIME = "system_expire_time";//系统到期时间

    public static final String HTTP_TYPE = "http_type"; //环境类型 用来切换测试和预发布
    public static final int TYPE_DEBUG = 1; //测试环境
    public static final int TYPE_TEST = 2;//预发布环境
    public static final int TYPE_PRERELEASE = 3;//预发布环境

    /**
     * 数据库相关信息
     */
    public static final String USER_ENTITY_ID = "user_entity_id";

    //当前版本
    public static final String VERSION_TYPE = "version_type";

    private static MMKV mmkv;

    private MMKVUtils() {
    }

    private static class SingleInstance {
        private static MMKV INSTANCE = MMKV.defaultMMKV();
    }


    public static MMKV getInstance() {
        return SingleInstance.INSTANCE;
    }

    public static void init(Context context) {
        MMKV.initialize(context);
        mmkv = getInstance();
    }

    public static void save(String key, String value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, long value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, int value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, double value) {
        mmkv.encode(key, value);
    }

    public static void save(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public static String getString(@NonNull String key) {
        String value = mmkv.decodeString(key);
        return TextUtils.isEmpty(value) ? "" : value;
    }

    public static int getInt(@NonNull String key) {
        return mmkv.decodeInt(key, -1);
    }

    public static long getLong(@NonNull String key) {
        return mmkv.decodeLong(key, -1);
    }

    public static double getDouble(@NonNull String key) {
        return mmkv.decodeDouble(key, 0d);
    }

    public static boolean getBoolean(@NonNull String key) {
        return mmkv.decodeBool(key, false);
    }

    public static boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }
    public static void saveTempData(Object object) {
        String json = JSONObject.toJSONString(object);
        MMKVUtils.save(MMKVUtils.TEMP_DATA, json);
    }
    public static <T>T getTempData(Class<T> clazz) {
        String json = MMKVUtils.getString(MMKVUtils.TEMP_DATA);
        //noinspection UnnecessaryLocalVariable
        T object = JSONArray.parseObject(json, clazz);
        return object;
    }
    public static <T>List<T> getTempDataList(Class<T> clazz) {
        String json = MMKVUtils.getString(MMKVUtils.TEMP_DATA);
//        MMKVUtils.save(MMKVUtils.TEMP_DATA, "");
        //noinspection UnnecessaryLocalVariable
        List<T> list = JSONArray.parseArray(json, clazz);
        return list;
    }
}
