package com.example.administrator.kib_3plus.http;

import com.example.administrator.kib_3plus.Utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.R.attr.tag;

/**
 * Created by cui on 2017/6/29.
 */

public class OkHttpUtils {
    public static final String HOST="http://test3plus.fashioncomm.com/";
    public static final String REG="sport/api/reg_for_france";
    public static final String LOGIN="sport/api/login";
    public static final String FORGOT_PASSWD="sport/api/forgot_passwd";
    public static final String ADD_FAMILY="family/family/add";
    public static final String GET_FAMILY="family/family/get";
    public static final String ADD_CHILD="/family/child/add";
    public static final String UPLOAD_SPORT="/sport/childSport/upload";
    public static final String UPLOAD_ICON="/family/child/uploadImg";

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public  OkHttpClient mOkHttpClient;
    private static OkHttpUtils okHttpUtils;
    public static OkHttpUtils getInstance(){
        if(okHttpUtils==null){
            okHttpUtils=new OkHttpUtils();
        }
      return okHttpUtils;
    }

    private OkHttpUtils() {
        init();
    }
    private void init(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
         mOkHttpClient=builder.build();
    }
    public  void postAsynHttp(String url, Callback callback, HashMap<String, String> dataMap,String tag) {
        RequestBody formBody ;
        FormBody.Builder builder= new FormBody.Builder();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            LogUtils.i("key= " + entry.getKey() + " and value= " + entry.getValue());
            builder.add(entry.getKey(),entry.getValue());
        }
        formBody=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .addHeader("access_token","")
                .addHeader("accept","application/json")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


    public  void postJsonAsynHttp(String url, Callback callback, String json,String tag) {
        LogUtils.i("postJsonAsynHttp="+json);
        RequestBody requestBody= RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .addHeader("access_token","")
                .addHeader("accept","application/json")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }


}
