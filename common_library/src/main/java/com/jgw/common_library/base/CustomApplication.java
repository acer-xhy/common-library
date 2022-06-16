package com.jgw.common_library.base;

import android.app.Application;
import android.content.Context;
import android.util.TypedValue;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.jgw.common_library.BuildConfig;
import com.jgw.common_library.utils.MMKVUtils;

/**
 * Created by XiongShaoWu
 * on 2019/9/10
 */
public class CustomApplication extends Application {
    public static CustomApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //do something
        initARouter();
        MMKVUtils.init(this);
        ToastUtils.init(this, com.jgw.common_library.utils.ToastUtils.getNormalStyle());
    }

    /**
     * dp转px
     */
    public int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    public void initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public static Context getCustomApplicationContext() {
        return context.getApplicationContext();
    }
}
