package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
 * Created by cui on 2017/7/6.
 */

public class SettingTimeFragment extends BaseFragment {
    private String mac;
    private BandSettingDB mBandSettingDB;
    private String timeFormat="12";

    private RelativeLayout time_12_rl,time_24_rl;
    private ImageView time_12_select_iv,time_24_select_iv;
    private static SettingTimeFragment instance;
    public static SettingTimeFragment getInstance(){
        if(instance==null){
            instance=new SettingTimeFragment();
        }
        return instance;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.tiem_format_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        time_12_rl=findViewById(R.id.time_12_rl);
        time_24_rl=findViewById(R.id.time_24_rl);
        time_12_select_iv=findViewById(R.id.time_12_select_iv);
        time_24_select_iv=findViewById(R.id.time_24_select_iv);

    }

    @Override
    public void initListener() {
        super.initListener();
        time_12_rl.setOnClickListener(this);
        time_24_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_12_rl:
                LogUtils.i("time_12_rl");
                if(mBandSettingDB.getTiem().equals("24")){
                    timeFormat="12";
                    setView("12",true);
                }
                break;
            case R.id.time_24_rl:
                LogUtils.i("time_24_rl");
                if(mBandSettingDB.getTiem().equals("12")){
                    timeFormat="24";
                    setView("24",true);
                }
                break;
        }
    }
    private void setView(String timeFormat,boolean isSend){
        byte index=0x03;
        if(timeFormat.equals("12")){
            time_12_select_iv.setVisibility(View.VISIBLE);
            time_24_select_iv.setVisibility(View.GONE);
            index=0x03;

        }else if(timeFormat.equals("24")){
            time_12_select_iv.setVisibility(View.GONE);
            time_24_select_iv.setVisibility(View.VISIBLE);
            index=0x02;
        }
        if(isSend){
            if(!PBluetooth.INSTANCE.isConnect(mac)){
                PBluetooth.INSTANCE.connect(mac,false,false);
            }
            BluetoothUtils.INSTANCE.setTimeFormat(mPvBluetoothCallback,mac,index);
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
                case L28T_TIME_FORMAT_DATA:
                    LogUtils.i("设置时间格式"+timeFormat);
                    mBandSettingDB.setTiem(timeFormat);
                    ContentValues values=new ContentValues();
                    values.put("tiem",timeFormat);
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            LogUtils.i("设置设置时间格式失败");
            timeFormat=mBandSettingDB.getTiem();
            setView(timeFormat,false);
            showToast("失败");
        }
    };

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        timeFormat=mBandSettingDB.getTiem();
        setView(timeFormat,false);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        mac= bundle.getString("mac");
        mBandSettingDB= PDB.INSTANCE.getBandSettingDB(mac);
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putString("mac",mac);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_SETTING_FRAGMENT,bundle);
        return true;
    }
}
