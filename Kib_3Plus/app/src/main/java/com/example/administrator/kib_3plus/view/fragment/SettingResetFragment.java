package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

/**
 * Created by cui on 2017/7/10.
 */

public class SettingResetFragment extends BaseFragment {
    private String mac;
    private BandSettingDB mBandSettingDB;
    private Button reset_confirm_btn;

    private static SettingResetFragment instance;
    public static SettingResetFragment getInstance(){
        if(instance==null){
            instance=new SettingResetFragment();
        }
        return instance;
    }
    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        mac= bundle.getString("mac");
        mBandSettingDB= PDB.INSTANCE.getBandSettingDB(mac);
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        reset_confirm_btn= findViewById(R.id.reset_confirm_btn);
    }

    @Override
    public void initListener() {
        super.initListener();
        reset_confirm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_confirm_btn:
                BluetoothUtils.INSTANCE.setReset(mPvBluetoothCallback,mac);
                break;
        }
    }
    PVBluetoothCallback mPvBluetoothCallback=new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("len="+len);
            LogUtils.i("dataType="+dataType);
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            if(objects!=null){
                LogUtils.i("objects="+objects[0].toString());
            }
            switch (bluetoothCommandType){
                case L28T_SET_RESET:
                    LogUtils.i("设置reset成功");
                    BluetoothUtils.INSTANCE.unBind(mac);
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            LogUtils.i("设置reset失败");
//            timeFormat=mBandSettingDB.getTiem();
//            setView(timeFormat,false);
            showToast("失败");
        }
    };
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.setting_reset_layout;
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putString("mac",mac);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_SETTING_FRAGMENT,bundle);
        return true;
    }
}
