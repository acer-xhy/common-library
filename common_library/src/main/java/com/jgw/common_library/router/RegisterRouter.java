package com.jgw.common_library.router;

import android.app.Activity;

public class RegisterRouter {

    public static boolean register(Class<? extends Activity> clazz) {
        String className = clazz.getName();
        CustomRouter.realRegister(className, clazz);
        return true;
    }

}
