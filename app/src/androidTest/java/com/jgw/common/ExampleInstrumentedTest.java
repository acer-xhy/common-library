package com.jgw.common;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.jgw.common.network.ApiService;
import com.jgw.common_library.http.rxjava.CustomObserver;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.jgw.common", appContext.getPackageName());
    }
    @Test
    public void testNet(){
        HttpUtils.getGatewayApi(ApiService.class)
                .getCheckDevice("12345678")
                .compose(HttpUtils.applyMainSchedulers())
                .subscribe(new CustomObserver<String>() {
                    @Override
                    public void onNext(String s) {

                    }
                });
        try {
            Thread.sleep(60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}