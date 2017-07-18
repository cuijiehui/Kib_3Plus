package cn.appscomm.bluetooth.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 作者：hsh
 * 日期：2017/3/21
 * 说明：日志打印类(所有的日志都用该类来管理)
 */

public class LogUtil {
    private static boolean isPrintfLog = true;
    private static boolean isWriteLog = false;

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM月dd HH:mm:ss");

    private static FileOutputStream outputStream;
    private static final int TYPE_INFO = 0;
    private static final int TYPE_WRITE = 1;
    private static final int TYPE_ERROR = 2;
    private static final int TYPE_VERBOSE = 3;

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && isWriteLog) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            String path = externalStorageDirectory.getAbsolutePath() + "/AppscommLog/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(new File(path), "bluetooth.txt");
            try {
                outputStream = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
            }
        }
    }

    /**
     * 打印info类消息
     *
     * @param TAG 类名
     * @param msg 消息
     */
    public static void i(String TAG, String msg) {
        printf(TAG, msg, TYPE_INFO);
    }

    /**
     * 打印info类消息(不需要传类名，并包含行号打印)
     *
     * @param msg 消息
     */
    public static void i(String msg) {
        printfClassNameAndLineNumber(new Throwable().getStackTrace(), msg, "i");
    }

    /**
     * 打印warn类消息
     *
     * @param TAG 类名
     * @param msg 消息
     */
    public static void w(String TAG, String msg) {
        printf(TAG, msg, TYPE_WRITE);
    }

    /**
     * 打印warn类消息(不需要传类名，并包含行号打印)
     *
     * @param msg 消息
     */
    public static void w(String msg) {
        printfClassNameAndLineNumber(new Throwable().getStackTrace(), msg, "w");
    }

    /**
     * 打印error类消息
     *
     * @param TAG 类名
     * @param msg 消息
     */
    public static void e(String TAG, String msg) {
        printf(TAG, msg, TYPE_ERROR);
    }

    /**
     * 打印error类消息(不需要传类名，并包含行号打印)
     *
     * @param msg 消息
     */
    public static void e(String msg) {
        printfClassNameAndLineNumber(new Throwable().getStackTrace(), msg, "e");
    }

    /**
     * 打印verbose类消息
     *
     * @param TAG 类名
     * @param msg 消息
     */
    public static void v(String TAG, String msg) {
        printf(TAG, msg, TYPE_VERBOSE);
    }

    /**
     * 打印verbose类消息(不需要传类名，并包含行号打印)
     *
     * @param msg 消息
     */
    public static void v(String msg) {
        printfClassNameAndLineNumber(new Throwable().getStackTrace(), msg, "v");
    }

    // 打印类名和行号综合处理
    private static void printfClassNameAndLineNumber(StackTraceElement[] sElements, String msg, String type) {
        String className = sElements[1].getFileName();
        int lineNumber = sElements[1].getLineNumber();
        if (!TextUtils.isEmpty(className)) {
            msg = "[第" + lineNumber + "行] " + msg;
            switch (type) {
                case "w":
                    w(className, msg);
                    break;
                case "e":
                    e(className, msg);
                    break;
                case "v":
                    v(className, msg);
                    break;
                default:
                    i(className, msg);
                    break;
            }
        }
    }

    private static void printf(String tag, String msg, int type) {
        if (isPrintfLog) {
            switch (type) {
                case TYPE_WRITE:
                    Log.w(tag, msg);
                    break;
                case TYPE_ERROR:
                    Log.e(tag, msg);
                    break;
                case TYPE_VERBOSE:
                    Log.v(tag, msg);
                    break;
                case TYPE_INFO:
                default:
                    Log.i(tag, msg);
                    break;
            }
        }
        if (isWriteLog) {
            String time = sdf.format(System.currentTimeMillis());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                if (outputStream != null) {
                    String className = tag;
                    String content = "(" + time + ") : " + msg + "\r\n";
//                        writeAndFlush(("*******" + className + "(" + time + ")*******\r\n").getBytes());
                    writeAndFlush(content.getBytes());
                }
            }
        }
    }

    private static void writeAndFlush(byte[] buf) {
        try {
            outputStream.write(buf);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
