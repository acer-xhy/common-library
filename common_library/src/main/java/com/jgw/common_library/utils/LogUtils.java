package com.jgw.common_library.utils;

import android.text.TextUtils;
import android.util.Log;

import com.jgw.common_library.BuildConfig;


/**
 * Created by xsw on 2016/10/27.
 */
public class LogUtils {
    public static boolean debugShowLog = false;

    public static void xswShowLog(String msg) {
        if (getShowLogEnable() && !TextUtils.isEmpty(msg)) {
            Log.e("xsw", msg);
        }
    }

    public static void showLog(String msg) {
        if (getShowLogEnable() && !TextUtils.isEmpty(msg)) {
            Log.i("jgw", msg);
        }
    }

    public static void showLog(String tag, String msg) {
        if (getShowLogEnable() && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static boolean getShowLogEnable() {
        return BuildConfig.DEBUG || debugShowLog;
    }

    public static void showChannelLog(String s) {
        Log.i("jgw_channel", s);
    }
}
