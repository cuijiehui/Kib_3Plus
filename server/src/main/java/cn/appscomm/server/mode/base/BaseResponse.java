package cn.appscomm.server.mode.base;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/3/1.
 */

public class BaseResponse {
    public String seq;
//    public int code;
//    public String msg;
    public ResMap resMap;

    public int result;
    public String message;
    public String servertime;
    public String data;
//{"result":"9999","message":"program exception","servertime":"1498628082","data":{}}
    //(返回值):{"result":"0","message":"The success of the operation","data":{"id":"35238","userId":"34993"}}

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resMap=" + resMap +
                ", result=" + result +
                ", message='" + message + '\'' +
                ", servertime='" + servertime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
