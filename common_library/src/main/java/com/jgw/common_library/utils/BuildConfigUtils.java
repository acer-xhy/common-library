package com.jgw.common_library.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;

import com.jgw.common_library.base.CustomApplication;


/**
 * Created by xsw on 2016/10/25.
 */
public class BuildConfigUtils {
    @Nullable
    public static String getVersionName() {
        String versionName = null;
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
        }
        return versionName;
    }

    @Nullable
    public static int getVersionCode() {
        int versionCode = 0;
        PackageInfo packageInfo = getPackageInfo();
        if (packageInfo != null) {
            versionCode = packageInfo.versionCode;
        }
        return versionCode;
    }

    @Nullable
    public static PackageInfo getPackageInfo() {
        PackageInfo packageInfo = null;
        PackageManager packageManager = CustomApplication.context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(CustomApplication.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }
}
