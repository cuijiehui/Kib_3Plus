package com.example.administrator.kib_3plus.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kib_3plus.MyApplication;
import com.example.administrator.kib_3plus.R;

/**
 * Created by cui on 2017/6/29.
 */

public enum ToastUtil {
    INSTANCE;
    private View view;
    private TextView tv;
    private Toast toast;

    /**
     * 初始化Toast
     * @param context
     */
    public void init(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        tv = (TextView) view.findViewById(R.id.toast_text_tv);
        toast = new Toast(context);
        toast.setView(view);
    }
    /**
     * 显示Toast
     * @param content
     * @param duration Toast持续时间
     */
    public void display(CharSequence content , int duration){
        if (content.length()!=0) {
            tv.setText(content);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 显示toast
     * @param content  内容
     * @param duration  持续时间
     */
    public  void toast(String content , int duration){
        if (content == null) {
            return;
        }else {
            display(content, duration);
        }
    }
    /**
     * 显示默认持续时间为short的Toast
     * @param content  内容
     */
    public  void toast(String content){
        if (content == null) {
            return;
        }else {
            display(content, Toast.LENGTH_SHORT);
        }
    }
}
