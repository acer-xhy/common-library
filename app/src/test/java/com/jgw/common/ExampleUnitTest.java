package com.jgw.common;

import org.junit.Test;

import static org.junit.Assert.*;

import android.text.TextUtils;

import com.jgw.common_library.base.ui.BaseActivity;
import com.jgw.common_library.http.HttpClient;
import com.jgw.common_library.http.okhttp.CommonInterceptor;
import com.jgw.common_library.http.rxjava.CustomObserver;
import com.jgw.common_library.utils.InputFormatUtils;
import com.jgw.common_library.utils.LogUtils;
import com.jgw.common_library.utils.click_utils.ClickUtils;
import com.jgw.common_library.utils.json.JsonObject;
import com.jgw.common_library.utils.json.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testNumber() {
        String url = "https://app1730.eapps.dingtalkcloud.com/mobile/dist/login?company_id=ding415673947849eb55&gotoUrl=trainingDetail&uuid=08F32103-78AA-4DBA-8E48-5FF10F7D6538&expireTime=1659950855&source=training";
        Pattern p = Pattern.compile("(expireTime=)(\\d{10})(&)");
        Matcher m = p.matcher(url);
        if (m.find()) {
            for (int i = 0; i < m.groupCount() + 1; i++) {
                String group = m.group(i);
                System.out.println("group" + i + "=" + group);
            }
        }
    }

    @Test
    public void testPhoneNumber() {
        Pattern p = Pattern.compile("(0[0-9]{2,3}-)?([0-9]{7,8})");//固话;
//        Pattern p = Pattern.compile("1[0-9]{10}");
        String input = "0371-12345678";
        Matcher m = p.matcher(input);
        if (m.find() && m.group().length() == input.length()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(m.group(i));
            }
        }
    }
    @Test
    public void testInputNumber() {
        String input = ".1";
        String s = InputFormatUtils.formatText(input, 6, 2);
        System.out.println(s);
    }

    @Test
    public void testInputText() {
        String input = "";
        boolean aaa = matcherNumber(input);
        System.out.println("testInputText="+aaa);
    }
    public static boolean matcherNumber(String str){

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    @Test
    public void testFastJson(){
        String str="{\"XXX\":\"XXXXXXXXXXXX\"}";
        JsonObject jb = JsonUtils.parseObject(str);
//        LogUtils.xswShowLog(jb.toString());
    }

}