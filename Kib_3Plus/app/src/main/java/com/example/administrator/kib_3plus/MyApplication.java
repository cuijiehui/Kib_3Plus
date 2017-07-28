package com.example.administrator.kib_3plus;

import android.app.Application;

import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.Utils.ToastUtil;

import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

/**
 * Created by cui on 2017/6/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        PresenterAppContext.INSTANCE.init(getApplicationContext(), true, false);
        PBluetooth.INSTANCE.startService();
        ToastUtil.INSTANCE.init(getApplicationContext());
        PublicData.InitDragListData(getApplicationContext());
        DialogUtil.INSTANCE.init(getApplicationContext());
        NumberUtils.INSTANCE.init(getApplicationContext());
        super.onCreate();
    }
}
