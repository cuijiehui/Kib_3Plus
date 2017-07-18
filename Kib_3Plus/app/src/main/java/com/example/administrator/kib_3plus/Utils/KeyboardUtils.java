package com.example.administrator.kib_3plus.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by cui on 2017/7/5.
 */

public class KeyboardUtils {
    /**隐藏系统键盘*/
    public static void hideKeyBoard(Context ctx, View view){
        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
