package com.example.administrator.kib_3plus.Utils.EventBusUtils;

/**
 * Created by cui on 2017/7/1.
 */

public class TitleMessageEvent {

     private String message;

    public TitleMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
