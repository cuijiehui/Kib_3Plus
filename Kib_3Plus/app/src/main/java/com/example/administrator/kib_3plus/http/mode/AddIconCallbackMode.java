package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/7/27.
 */

public class AddIconCallbackMode  extends CallBackBaseMode  {

    /**
     * ={"result":0,"message":"Upload image successful","servertime":1501137117,"data":{"imgUrl":"/2017-07-27/993b8e49-009f-4c70-b8e6-4e9d56467fc3.jpg"}}
     */

    CallBackData data;

    public CallBackData getData() {
        return data;
    }

    public void setData(CallBackData data) {
        this.data = data;
    }

    public static class CallBackData{
        String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
