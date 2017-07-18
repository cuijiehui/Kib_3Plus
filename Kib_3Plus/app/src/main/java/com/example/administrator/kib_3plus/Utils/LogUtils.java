package com.example.administrator.kib_3plus.Utils;

import android.util.Log;

import com.example.administrator.kib_3plus.PublicData;

/**
 * Created by cui on 2017/6/10.
 */

public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
    public static void i( String msg) {

        if (!PublicData.weatherPrint)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className+"3PLUS_Kid", createLog(msg));
    }
    public static void i(String TAG, String msg) {

        if (!PublicData.weatherPrint)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className+"3PLUS_Kid"+TAG, createLog(msg));
    }
    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }
    public static void e( String msg) {

        if (!PublicData.weatherPrint)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(className+"3PLUS_Kid", createLog(msg));
    }
    public static void w( String msg) {

        if (!PublicData.weatherPrint)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className+"3PLUS_Kid", createLog(msg));
    }
}
