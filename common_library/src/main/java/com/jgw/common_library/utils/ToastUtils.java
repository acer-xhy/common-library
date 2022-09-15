package com.jgw.common_library.utils;

import android.text.TextUtils;
import android.view.Gravity;

import com.hjq.toast.config.IToastStyle;
import com.hjq.toast.style.BlackToastStyle;
import com.jgw.common_library.base.ui.BaseActivity;


/**
 * Created by xsw on 2016/10/25.
 */
public class ToastUtils {
    public static void showToast(final String msg) {
        if (!TextUtils.isEmpty(msg)) {
            com.hjq.toast.ToastUtils.setGravity(Gravity.BOTTOM, 0, (int) (60 * BaseActivity.getXMultiple()));
            com.hjq.toast.ToastUtils.show(msg);
        }
    }

    public static IToastStyle<?> getNormalStyle() {
        return new BlackToastStyle() {
            @Override
            public int getGravity() {
                return Gravity.BOTTOM;
            }
        };
    }
}
