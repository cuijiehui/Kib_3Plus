//package com.example.administrator.kib_3plus.Utils;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.ParseException;
//import android.util.Log;
//
//import com.example.administrator.kib_3plus.LogUtils;
//import com.example.administrator.kib_3plus.PublicData;
//import com.example.administrator.kib_3plus.R;
//
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.params.HttpConnectionParams;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import okhttp3.internal.http.StatusLine;
//import retrofit2.http.HTTP;
//
///**
// * Created by cui on 2017/6/28.
// */
//
//public class HttpUtil {
//
//    public String httpResponseResult = "";
//    public String resultCode = "";
//    public String message = "";
//
//    public final String TAG = this.getClass().getName();
//
//    //0：返回字符串     1：其它
//    public String flag = "0";
//
//    public InputStream httpResponseIO = null;
//
//    private Context context;
//
//    //
//    private String encode = "utf-8";
//    private static final String APPLICATION_JSON = "application/json";
//
//    public HttpUtil() {
//
//    }
//
//    public HttpUtil(Context context) {
//        this.context = context;
//    }
//
//    /**
//     * 是否连接网络
//     *
//     * @return
//     */
//    public boolean isNetworkConnected() {
//
//        if (context == null) {
//            LogUtils.i(TAG, "context is null..........");
//            return false;
//        }
//        // 获取系统的连接服务
//        ConnectivityManager connectivityManager = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (null == connectivityManager)
//            return false;
//
//        // 获取网络的连接情况
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (null == networkInfo)
//            return false;
//
//        if (networkInfo.isAvailable() && networkInfo.isConnected())
//            return true;
//
//        return false;
//    }
//
//    /**
//     * 登陆
//     * 0：成功	大于0：失败
//     * 1：用户名或密码错误
//     * 2：ClientProtocolException
//     * 3：IOException
//     *
//     * @param pwd
//     * @return
//     */
////    public boolean login(String email, String pwd) {
////        PropertiesUtil pu = new PropertiesUtil();
////        pu.initResRawPropFile(context, R.raw.server);
////        String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
////                PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
////        Properties props = pu.getPropsObj();
////        String url = props.getProperty("server.login.address", "http://app.appscomm.cn/sport/api/login");
////        RequestParams params = new RequestParams(url);
////        params.addBodyParameter("account", email);
////        params.addBodyParameter("password", pwd);
////        params.addBodyParameter("encryptMode", "1");
////
////        params.addHeader("access_token", access_token);
////        params.addHeader("accept", "application/json");
////        x.http().post(params, new Callback.CommonCallback<String>() {
////            @Override
////            public void onSuccess(String result) {
////                //加载成功回调，返回获取到的数据
////                Log.i(TAG, "onSuccess: " + result);
////            }
////
////            @Override
////            public void onFinished() {
////            }
////
////            @Override
////            public void onCancelled(CancelledException cex) {
////
////            }
////
////            @Override
////            public void onError(Throwable ex, boolean isOnCallback) {
////
////            }
////        });
////        return true;
////    }
//
//    /**
//     * xutil的post请求 04/21
//     *
//     */
////    public void xutilPost(String url, Map<String, String> param,Callback.CommonCallback callback) {
////        String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
////                PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
////        LogUtils.i("","xutils-url="+url);
////        RequestParams params = new RequestParams(url);
////        for (Map.Entry<String, String> entry : param.entrySet()) {
////            LogUtils.i("","key= " + entry.getKey() + " and value= " + entry.getValue());
////            params.addBodyParameter(entry.getKey(),entry.getValue());
////        }
////        params.addHeader("access_token", access_token);
////        params.addHeader("accept", "application/json");
////        x.http().post(params, callback);
////    }
//    /**
//     * xutil的post请求 带参数json 没测试json带中文
//     *
//     */
////    public void xutilPostJson(String url,String json,Callback.CommonCallback callback) {
////        String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
////                PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
////        LogUtils.i("","xutilPostJson-url="+url);
////        RequestParams params = new RequestParams(url);
////        params.setAsJsonContent(true);
////        params.setBodyContent(json);
////        params.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
////        params.addHeader("access_token", access_token);
////        params.addHeader("accept", "application/json");
////        x.http().post(params, callback);
////    }
//
//    /**
//     * 登陆
//     * 0：成功	大于0：失败
//     * 1：用户名或密码错误
//     * 2：ClientProtocolException
//     * 3：IOException
//     *
//     * @param user
//     * @param pwd
//     * @return
//     */
//    public int reg(String user, String pwd, String email, String gender,
//                   String birthDay, String height, String weight) {
//
//        int LOGIN_STATE = 1;
//        httpResponseResult = "";
//        //http://域名/sport/api/reg_for_france
//        //String url = "http://app.appscomm.cn/sport/api/reg_for_france";
//        PropertiesUtil pu = new PropertiesUtil();
//        pu.initResRawPropFile(context, R.raw.server);
//
//        Properties props = pu.getPropsObj();
//        String url = props.getProperty("server.reg.address", "http://app.appscomm.cn/sport/api/reg_for_france");
//
//        LogUtils.i(TAG, "注册请求地址：" + url);
//
//        try {
//            String method = "post";
//            String params = "userName=" + user + "&email=" + email + "&password=" + pwd + "&gender=" + gender
//                    + "&birthDay=" + birthDay + "&height=" + height + "&weight=" + weight + "&validCode=";
//
//            LOGIN_STATE = httpReq(method, url, params);
//
//            String responseBody = httpResponseResult;
//            LogUtils.i(TAG, ">>>>>responseBody:" + responseBody);
//
//            if (LOGIN_STATE != 0) {
//
//                return LOGIN_STATE;
//            } else {//有响应
//
//                //正确的响应信息
//                if (responseBody.indexOf("\"result\"") != -1 && responseBody.indexOf("\"message\"") != -1
//                        && responseBody.indexOf("\"data\"") != -1) {
//
//                    JSONObject jsonObj = new JSONObject(responseBody);
//                    String result = jsonObj.getString("result");
//                    resultCode = result;
//                    message = jsonObj.getString("message");
//                    LogUtils.i(TAG, ">>>>>message:" + message);
//                    if ("0".equals(result)) {
//                        LogUtils.i(TAG, ">>>>>resultCode:" + resultCode);
//
//                        LOGIN_STATE = 0;
//                    } else {
//                        LogUtils.i(TAG, ">>>>>resultCode:" + resultCode);
//
//                        LOGIN_STATE = 1;
//                    }
//
//                } else {
//                    LOGIN_STATE = -5;//注册失败
//
//                    LogUtils.i(TAG, ">>>state2:" + LOGIN_STATE);
//
//                    return LOGIN_STATE;
//
//                }
//
//            }
//
//        } catch (JSONException e) {
//            LogUtils.i(TAG, "---------jasonException----");
//            LOGIN_STATE = 3;
//        } finally {
//
//        }
//        return LOGIN_STATE;
//    }
//
//    public void test(String path, String params) {
//        try {
//            URL url = new URL("http://test_3plus.fashioncomm.com/common/api/check_version?deviceName=L38I0000&deviceType=L38I0000&version=N1.4R0.2H0.1");
//            Log.i("test", "url : " + url.toString() + " params : " + params);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            String data = params;
////conn.setRequestProperty("Content-Length", data.length()+"");
////            conn.setRequestProperty("accept", "application/json");
////
////conn.setDoOutput(true);
////OutputStream os = conn.getOutputStream();
////os.write(data.getBytes("utf-8"));
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(2000);
//            Log.i("test", "9099999999999999999");
//            int code = conn.getResponseCode();
//            Log.i("test", "code : " + code);
//            if (code == 200) {
//
//            }
//        } catch (Exception e) {
//            Log.i("test", "89888888888888888888");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param method >>http请求方式：post/get
//     * @param url    >>请求的url地址
//     * @param params >>请求的所有参数值
//     * @return 返回的请求状态  0：有响应  -1：未响应  -2：ClientProtocolException -3：ParseException -4：IOException
//     */
//    public int httpReq(String method, String url, String params) {
//        int state = -1;
//        LogUtils.i(TAG, "------------url:" + url);
//        LogUtils.i(TAG, "---------params:" + params);
//        httpResponseResult = "";
//        try {
//            //1.创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象
//            HttpPost httpPost = new HttpPost(url);
//
//            // 设置HTTP POST请求参数必须用NameValuePair对象
//            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
//            if (!"".equals(params)) {
//                String tmp[] = params.split("&");
//                LogUtils.i(TAG, "--------tmp.length:" + tmp.length);
//
//                for (int i = 0; i < tmp.length; i++) {
//                    if (tmp[i].contains("=")) {
//                        String parmVals[] = tmp[i].split("=");
//                        String name = parmVals[0];
//                        String val = "";
//                        if (parmVals.length == 2) {
//                            val = parmVals[1];
//                        }
//
//                        paramsList.add(new BasicNameValuePair(name, val));
//                    }
//                }
//            }
//            LogUtils.i(TAG, "encode=" + encode);
//            // 设置HTTP POST请求参数
//            LogUtils.i("", "paramsList=" + paramsList.toString());
//            HttpEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");
////            httpPost.addHeader("Content-Type", "text/html");
////            httpPost.addHeader("charset", "GBK");
//            LogUtils.i(TAG, "entity=" + entity.toString());
//
//            //增加权限限制
//            String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
//                    PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
//            httpPost.setHeader("access_token", access_token);
//            httpPost.setHeader("accept", "application/json");
//            httpPost.setEntity(entity);
//
//            LogUtils.i(TAG, "httpPost=" + httpPost.toString());
//            //2.创建DefaultHttpClient对象
//
//            //c超时
//            BasicHttpParams httpParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParams, 7000);
//            HttpConnectionParams.setSoTimeout(httpParams, 7000);
//
//            HttpClient httpClient = new DefaultHttpClient(httpParams);
//
////            // 请求超时
////            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
////            // 读取超时
////            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
//            //getHttpConnectionManager().getParams().setConnectionTimeout(1000 * 60);
//            //httpClient.getParams().setParameter(name, value)
//
//            // 通过DefaultHttpClient对象的execute方法发送httpGet/httpPost请求,并返回HttpResponse对象。
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//
//            StatusLine statusLine = httpResponse.getStatusLine();
//            int code = statusLine.getStatusCode();
//            LogUtils.i(TAG, ">>>>HTTP请求状态码:" + code);
//            if (code == 200) {
//                state = 0;
//                LogUtils.i(TAG, "--this.flag:" + this.flag);
//                if ("0".equals(this.flag)) {
//                    httpResponseResult = EntityUtils.toString(httpResponse.getEntity());
//                }
//
//            } else {
//                state = -1;
//            }
//            if ("0".equals(this.flag)) {
//                LogUtils.i(TAG, ">>httpResponseResult(返回值):" + httpResponseResult);
//            }
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//            state = -2;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            state = -3;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        } catch (IOException e) {
//            e.printStackTrace();
//            state = -4;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        }
////        catch (Exception e){
////            e.printStackTrace();
////            state = -5;
////            LogUtils.i(TAG, "state=" + state);
////
////            return state;
////        }
//
//        return state;
//    }
//
//    public int httpGetReq(String url) {
//        int state = -1;
//        LogUtils.i(TAG, "==>httpGetReq-------url:" + url);
//        httpResponseResult = "";
//        try {
//            //1.创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象
//            HttpGet httpGet = new HttpGet(url);
//
////c超时
//            BasicHttpParams httpParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParams, 7000);
//            HttpConnectionParams.setSoTimeout(httpParams, 7000);
////        	//2.创建DefaultHttpClient对象
//            HttpClient httpClient = new DefaultHttpClient(httpParams);
//////			// 请求超时
////			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//////            // 读取超时
////			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
//            //
//            //httpClient.getParams().setParameter(name, value)
//
//            // 通过DefaultHttpClient对象的execute方法发送httpGet/httpPost请求,并返回HttpResponse对象。
//
//            //增加权限限制
//            String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
//                    PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
//            httpGet.setHeader("access_token", access_token);
//            httpGet.setHeader("accept", "application/json");
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//
//            StatusLine statusLine = httpResponse.getStatusLine();
//            int code = statusLine.getStatusCode();
//            LogUtils.i(TAG, ">>>>HTTP请求状态码:" + code);
//            if (code == 200) {
//                state = 0;
//                LogUtils.i(TAG, "--this.flag:" + this.flag);
//                if ("0".equals(this.flag)) {
//                    httpResponseResult = EntityUtils.toString(httpResponse.getEntity());
//                }
//
//            } else {
//                state = -1;
//            }
//            if ("0".equals(this.flag)) {
//                LogUtils.i(TAG, ">>httpResponseResult(返回值):" + httpResponseResult);
//            }
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//            state = -2;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            state = -3;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        } catch (IOException e) {
//            e.printStackTrace();
//            state = -4;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        }
//
//        return state;
//    }
//
//    public int httpPostWithJSON(String url, String json) {
//        int state = -1;
//        httpResponseResult = "";
//        LogUtils.i(TAG, "------------url:" + url);
//        LogUtils.i(TAG, "---------json:" + json);
//
//        try {
//            //1.创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象
//            HttpPost httpPost = new HttpPost(url);
//
//            //增加权限限制
//            String access_token = (String) ConfigHelper.getSharePref(context, PublicData.SHARED_PRE_SAVE_FILE_NAME,
//                    PublicData.ACCESS_TOKEN_KEY, ConfigHelper.DATA_STRING);
//            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
//            httpPost.setHeader("access_token", access_token);
//            httpPost.setHeader("accept", "application/json");
//
//            // 将JSON进行UTF-8编码,以便传输中文
//            String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
//            StringEntity se = new StringEntity(json, "utf-8");
////    		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
//            httpPost.setEntity(se);
//
//            //2.创建DefaultHttpClient对象
//            HttpClient httpClient = new DefaultHttpClient();
//            // 请求超时
//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//            // 读取超时
//            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
//            //
//            //httpClient.getParams().setParameter(name, value)
//
//            // 通过DefaultHttpClient对象的execute方法发送httpGet/httpPost请求,并返回HttpResponse对象。
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//
//            StatusLine statusLine = httpResponse.getStatusLine();
//            int code = statusLine.getStatusCode();
//            LogUtils.i(TAG, ">>>>HTTP请求状态码:" + code);
//            if (code == 200) {
//                state = 0;
//                LogUtils.i(TAG, "--this.flag:" + this.flag);
//                if ("0".equals(this.flag)) {
//                    httpResponseResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//                }
//
//            } else {
//                state = -1;
//            }
//            if ("0".equals(this.flag)) {
//                LogUtils.i(TAG, ">>httpResponseResult(返回值):" + httpResponseResult);
//            }
//
//        }  catch (ParseException e) {
//            e.printStackTrace();
//            state = -3;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        } catch (IOException e) {
//            e.printStackTrace();
//            state = -4;
//            LogUtils.i(TAG, "state=" + state);
//
//            return state;
//        }
//
//        return state;
//    }
//
//
//    public String getFlag() {
//        return flag;
//    }
//
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }
//
//    public String getEncode() {
//        return encode;
//    }
//
//    public void setEncode(String encode) {
//        this.encode = encode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getResultCode() {
//        return resultCode;
//    }
//
//    public void setResultCode(String resultCode) {
//        this.resultCode = resultCode;
//    }
//
//}
