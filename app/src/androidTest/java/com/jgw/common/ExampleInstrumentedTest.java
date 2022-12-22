package com.jgw.common;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.jgw.common.bean.CodeBean;
import com.jgw.common.bean.CodeV2BillCodeResultBean;
import com.jgw.common.bean.ExchangeGoodsConfigBean;
import com.jgw.common.bean.ExchangeGoodsUploadBean;
import com.jgw.common.bean.V2ErrorListBean;
import com.jgw.common.network.HttpUtils;
import com.jgw.common.network.api.ApiService;
import com.jgw.common.utils.ConstantUtil;
import com.jgw.common_library.base.CustomApplication;
import com.jgw.common_library.http.CustomHttpApiException;
import com.jgw.common_library.http.HttpClient;
import com.jgw.common_library.http.okhttp.CommonInterceptor;
import com.jgw.common_library.http.okhttp.ProgressListener;
import com.jgw.common_library.http.rxjava.CustomObserver;
import com.jgw.common_library.utils.LogUtils;
import com.jgw.common_library.utils.MMKVUtils;
import com.jgw.common_library.utils.MathUtils;
import com.jgw.common_library.utils.json.JsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testDownload() {
        MMKVUtils.save(ConstantUtil.USER_TOKEN, "1e589df0a30749e194ee6b7796d31491");
        Observable<ExchangeGoodsUploadBean> observable;
        ExchangeGoodsUploadBean input = new ExchangeGoodsUploadBean();
        ExchangeGoodsConfigBean configBean = new ExchangeGoodsConfigBean();
        configBean.setCustomerInCode("KXS215");
        configBean.setCustomerInId("3ab660251ca64b03a9ccf1fa1643bd73");
        configBean.setCustomerInName("东莞百润生物科技有限公司");
        configBean.setCustomerOutCode("KXS074");
        configBean.setCustomerOutId("235045367bf14c20bbaf283be343608b");
        configBean.setCustomerOutName("东莞百润生物科技有限公司（广西海之秀）");
        input.setConfigBean(configBean);

        ArrayList<CodeBean> allList = new ArrayList<>();
        allList.add(new CodeBean("1234567890123456"));
        CodeBean e1 = new CodeBean("12345678901234567");
        e1.codeVersion = CodeBean.VERSION_CODE_V2;
        allList.add(e1);
        input.setAllList(allList);
        if (!input.getV2List().isEmpty()) {
            observable = uploadV2CodeList(input)
                    .flatMap(this::uploadCode);
        } else {
            observable = uploadCode(input);
        }
        observable.subscribe(new CustomObserver<ExchangeGoodsUploadBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
            }

            @Override
            public void onNext(ExchangeGoodsUploadBean exchangeGoodsUploadBean) {
                LogUtils.xswShowLog("");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public Observable<ExchangeGoodsUploadBean> uploadV2CodeList(ExchangeGoodsUploadBean input) {
//        String token = "123";
//        String requestString = "Token=" + token;
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestString);
//        return HttpUtils.getV2JsonApi(ApiService.class)
//                .getBillCodeV2("GetBillCode", requestBody)
//                .compose(HttpUtils.applyMainSchedulersV2())
//                .flatMap((Function<CodeV2BillCodeResultBean, ObservableSource<ExchangeGoodsUploadBean>>) codeV2BillCodeResultBean -> {
//                    HashMap<String, Object> map = new HashMap<>();
//                    List<String> codes = new ArrayList<>();
//                    for (CodeBean codeBean : input.getV2List()) {
//                        codes.add(codeBean.outerCodeId);
//                    }
//                    map.put("StockOutOrgCode", input.getConfigBean().getCustomerOutCode());
//                    map.put("StockInOrgCode", input.getConfigBean().getCustomerInCode());
//                    map.put("SpecID", "");
//                    map.put("version", "2.0");
//                    map.put("CodeList", JsonUtils.toJsonString(codes));
//                    map.put("Token", "123");
//                    map.put("BillCode", codeV2BillCodeResultBean.billCode);
//                    String requestString1 = map2String(map);
//                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestString1);
//                    return HttpUtils.getV2JsonApi(ApiService.class)
//                            .postExchangeGoodsCodeV2List("BatchStockTransfer", requestBody1)
//                            .compose(HttpUtils.applyMainSchedulersV2())
//                            .map(v2ErrorListBean -> {
//                                input.setErrorList(v2ErrorListBean.ErrorList);
//                                return input;
//                            });
//                });
        ArrayList<V2ErrorListBean.ErrorListBean> errorList = new ArrayList<>();
        V2ErrorListBean.ErrorListBean e = new V2ErrorListBean.ErrorListBean();
        e.Code = "123456789012345678";
        e.Msg = "ceshi";
        errorList.add(e);
        input.setErrorList(errorList);
        return Observable.just(input);
    }

    private String map2String(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue().toString());
            builder.append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /**
     * 上传codes
     */
    public Observable<ExchangeGoodsUploadBean> uploadCode(ExchangeGoodsUploadBean input) {
        if (input.getV3List().isEmpty() && input.getErrorList().isEmpty()) {
            return Observable.just(input);
        }
        List<String> codes = new ArrayList<>();
        for (CodeBean codeBean : input.getV3List()) {
            codes.add(codeBean.outerCodeId);
        }
        ExchangeGoodsConfigBean configBean = input.getConfigBean();
        HashMap<String, Object> map = new HashMap<>();
//        map.put("houseList", "");
        map.put("wareGoodsInCode", configBean.getCustomerInCode());
        map.put("wareGoodsInId", configBean.getCustomerInId());
        map.put("wareGoodsInName", configBean.getCustomerInName());
        map.put("wareGoodsOutCode", configBean.getCustomerOutCode());
        map.put("wareGoodsOutId", configBean.getCustomerOutId());
        map.put("wareGoodsOutName", configBean.getCustomerOutName());
        map.put("operationType", "030003");
        map.put("outerCodeIdList", codes);
        if (!input.getErrorList().isEmpty()) {
            map.put("secondCodeFailures", input.getErrorList());
        }
        return HttpUtils.getGatewayApi(ApiService.class)
                .uploadExchangeGoods(map)
                .compose(HttpUtils.applyMainSchedulers())
                .map(s -> input);
    }

}