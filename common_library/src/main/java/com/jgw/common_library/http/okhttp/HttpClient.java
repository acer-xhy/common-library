package com.jgw.common_library.http.okhttp;


import com.jgw.common_library.BuildConfig;
import com.jgw.common_library.http.HttpResult;
import com.jgw.common_library.http.HttpResultFunc;
import com.jgw.common_library.http.HttpResultNullableFunc;
import com.jgw.common_library.utils.MMKVUtils;

import java.util.HashMap;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;


/**
 * Created by admin on 2019/2/15.
 */

@SuppressWarnings({"rawtypes", "unchecked"})
public class HttpClient {

    // 文件的url
    private static final String FILE_URL_RELEASE = "https://file.jgwcjm.com/";
    private static final String FILE_URL_TEST = "http://filetest.jgwcjm.com/";

    // 网关正式域名  预发布使用正式域名 通过拦截器添加Cookie链接预发布服务
    private static final String BASE_GATEWAY_URL_RELEASE = "https://api-gateway.jgwcjm.com/";
    // 网关测试域名
    private static final String BASE_GATEWAY_URL_TEST = "https://api-gateway.cjm3.kf315.net/";
    //开发环境域名(不稳定,和后台联调处理后再使用)
    private static final String BASE_GATEWAY_URL_DEBUG = "https://dev-api-gateway.cjm3.kf315.net/";

    private static final String CJM_BASE_GATEWAY_URL_RELEASE = "https://api-gateway.jgwcjm.com/";
    private static final String CJM_BASE_GATEWAY_URL_TEST = "https://api-gateway.cjm3.kf315.net/";
    private static final String CJM_BASE_GATEWAY_URL_DEBUG = "https://dev-api-gateway.cjm3.kf315.net/";
    public static String buildType = BuildConfig.BUILD_TYPE;
    public static boolean testRelease = false;

    private HttpClient() {
    }

    @SuppressWarnings({"DuplicateBranchesInSwitch"})
    public static String getGatewayUrl() {
        if (testRelease) {
            return BASE_GATEWAY_URL_RELEASE;
        }
        switch (buildType) {
            case "debug":
                return BASE_GATEWAY_URL_DEBUG;
            case "customtest":
                int current = MMKVUtils.getInt(MMKVUtils.HTTP_TYPE);
                return current == MMKVUtils.TYPE_PRERELEASE ? BASE_GATEWAY_URL_RELEASE : BASE_GATEWAY_URL_TEST;
            case "prerelease":
                return BASE_GATEWAY_URL_RELEASE;
            case "release":
                return BASE_GATEWAY_URL_RELEASE;
            default:
                return BASE_GATEWAY_URL_RELEASE;
        }
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    public static String getCJMGatewayUrl() {
        if (testRelease) {
            return CJM_BASE_GATEWAY_URL_RELEASE;
        }
        switch (buildType) {
            case "debug":
                return CJM_BASE_GATEWAY_URL_DEBUG;
            case "customtest":
                int current = MMKVUtils.getInt(MMKVUtils.HTTP_TYPE);
                return current == MMKVUtils.TYPE_PRERELEASE ? CJM_BASE_GATEWAY_URL_RELEASE : CJM_BASE_GATEWAY_URL_TEST;
            case "prerelease":
                return CJM_BASE_GATEWAY_URL_RELEASE;
            case "release":
                return CJM_BASE_GATEWAY_URL_RELEASE;
            default:
                return CJM_BASE_GATEWAY_URL_RELEASE;
        }
    }

    @SuppressWarnings({"DuplicateBranchesInSwitch"})
    public static String getFileUrl() {
        if (testRelease) {
            return FILE_URL_RELEASE;
        }
        switch (buildType) {
            case "debug":
            case "customtest":
                int current = MMKVUtils.getInt(MMKVUtils.HTTP_TYPE);
                return current == MMKVUtils.TYPE_PRERELEASE ? FILE_URL_RELEASE : FILE_URL_TEST;
            case "prerelease":
                return FILE_URL_RELEASE;
            case "release":
                return FILE_URL_RELEASE;
            default:
                return FILE_URL_RELEASE;
        }
    }

    private static final HashMap<ApiConfigInfoBean, Object> map = new HashMap<>();

    public static synchronized <T> T getGatewayApi(Class<T> clz) {
        ApiConfigInfoBean<T> infoBean = new ApiConfigInfoBean<>(clz, getGatewayUrl());
        Object o = map.get(infoBean);
        if (o == null) {
            o = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient())
                    .baseUrl(getGatewayUrl())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(clz);
            map.put(infoBean, o);
        }
        return (T) o;
    }

    public static synchronized <T> T getGatewayApi(Class<T> clz, int time) {
        ApiConfigInfoBean<T> infoBean = new ApiConfigInfoBean<>(clz, getGatewayUrl());
        Object o = map.get(infoBean);
        if (o == null) {
            o = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient(time))
                    .baseUrl(getGatewayUrl())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(clz);
            map.put(infoBean, o);
        }
        return (T) o;
    }

    public static synchronized <T> T getCJMGatewayApi(Class<T> clz) {
        ApiConfigInfoBean<T> infoBean = new ApiConfigInfoBean<>(clz, getCJMGatewayUrl());
        Object o = map.get(infoBean);
        if (o == null) {
            o = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient())
                    .baseUrl(getCJMGatewayUrl())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(clz);
            map.put(infoBean, o);
        }
        return (T) o;
    }

    public static synchronized <T> T getCJMGatewayApi(Class<T> clz, int time) {
        ApiConfigInfoBean<T> infoBean = new ApiConfigInfoBean<>(clz, getCJMGatewayUrl());
        Object o = map.get(infoBean);
        if (o == null) {
            o = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient(time))
                    .baseUrl(getCJMGatewayUrl())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(clz);
            map.put(infoBean, o);
        }
        return (T) o;
    }

    public static <K> ObservableTransformer<HttpResult<K>, K> applyMainSchedulers() {
        return (ObservableTransformer<HttpResult<K>, K>) schedulersTransformer;
    }

    private static final ObservableTransformer schedulersTransformer = upstream -> {
//        List<String> objects = Collections.<String>emptyList();
        return upstream.map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    };

    public static <K> ObservableTransformer<HttpResult<K>, K> applyResultNullableMainSchedulers() {
        return (ObservableTransformer<HttpResult<K>, K>) schedulersResultNullableTransformer;
    }

    private static final ObservableTransformer schedulersResultNullableTransformer = upstream -> {
//        List<String> objects = Collections.<String>emptyList();
        return upstream.map(new HttpResultNullableFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    };

    @SuppressWarnings("unchecked")
    public static <K> ObservableTransformer<HttpResult<K>, K> applyIOSchedulers() {
        return (ObservableTransformer<HttpResult<K>, K>) schedulersIOTransformer;
    }

    @SuppressWarnings("unchecked")
    private static final ObservableTransformer schedulersIOTransformer = upstream -> {
//        List<String> objects = Collections.<String>emptyList();
        return upstream.map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    };
}
