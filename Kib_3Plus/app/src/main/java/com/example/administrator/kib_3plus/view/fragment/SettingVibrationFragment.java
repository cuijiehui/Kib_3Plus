package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.LinkedList;

import cn.appscomm.bluetooth.mode.SportBTL28T;
import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

import static android.R.attr.onClick;
import static com.example.administrator.kib_3plus.R.id.main_family_sr;

/**
 * Created by cui on 2017/7/6.
 */

public class SettingVibrationFragment extends BaseFragment {

    private RelativeLayout vibration_off_rl,vibration_weak_rl,vibration_standard_rl,vibration_strong_rl;
    private ImageView vibration_off_iv,vibration_weak_iv,vibration_standard_iv,vibration_strong_iv;
    private String mac;
    private BandSettingDB mBandSettingDB;
    private static SettingVibrationFragment instance;
    private String vi="standard";

    public static SettingVibrationFragment getInstance(){
        if(instance==null){
            instance=new SettingVibrationFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.setting_vibration_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        vibration_off_rl=findViewById(R.id.vibration_off_rl);
        vibration_weak_rl=findViewById(R.id.vibration_weak_rl);
        vibration_standard_rl=findViewById(R.id.vibration_standard_rl);
        vibration_strong_rl=findViewById(R.id.vibration_strong_rl);

        vibration_off_iv=findViewById(R.id.vibration_off_iv);
        vibration_weak_iv=findViewById(R.id.vibration_weak_iv);
        vibration_standard_iv=findViewById(R.id.vibration_standard_iv);
        vibration_strong_iv=findViewById(R.id.vibration_strong_iv);
    }


    @Override
    public void initListener() {
        super.initListener();
        vibration_off_rl.setOnClickListener(this);
        vibration_weak_rl.setOnClickListener(this);
        vibration_standard_rl.setOnClickListener(this);
        vibration_strong_rl.setOnClickListener(this);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
         vi=mBandSettingDB.getVibration();
       switch (vi){
           case "standard":
               onClick(vibration_standard_rl);
               break;
           case "off":
               onClick(vibration_off_rl);
               break;
           case "weak":
               onClick(vibration_weak_rl);
               break;
           case "strong":
               onClick(vibration_strong_rl
               );
               break;
       }
    }

    @Override
    public void onClick(View v) {
        vibration_off_rl.setBackground(getResources().getDrawable(R.color.colorWhite));
        vibration_weak_rl.setBackground(getResources().getDrawable(R.color.colorWhite));
        vibration_standard_rl.setBackground(getResources().getDrawable(R.color.colorWhite));
        vibration_strong_rl.setBackground(getResources().getDrawable(R.color.colorWhite));
        vibration_off_iv.setVisibility(View.GONE);
        vibration_weak_iv.setVisibility(View.GONE);
        vibration_standard_iv.setVisibility(View.GONE);
        vibration_strong_iv.setVisibility(View.GONE);
        switch (v.getId()){
            case R.id.vibration_off_rl:
                LogUtils.i("vibration_off_rl");
                vibration_off_rl.setBackground(getResources().getDrawable(R.color.colorBgText));
                vibration_off_iv.setVisibility(View.VISIBLE);
                 vi="off";

                break;
            case R.id.vibration_weak_rl:
                LogUtils.i("vibration_weak_rl");
                vibration_weak_rl.setBackground(getResources().getDrawable(R.color.colorBgText));
                vibration_weak_iv.setVisibility(View.VISIBLE);
                vi="weak";

                break;
            case R.id.vibration_standard_rl:
                LogUtils.i("vibration_standard_rl");
                vibration_standard_rl.setBackground(getResources().getDrawable(R.color.colorBgText));
                vibration_standard_iv.setVisibility(View.VISIBLE);
                vi="standard";

                break;
            case R.id.vibration_strong_rl:
                LogUtils.i("vibration_strong_rl");
                vibration_strong_rl.setBackground(getResources().getDrawable(R.color.colorBgText));
                vibration_strong_iv.setVisibility(View.VISIBLE);
                vi="strong";

                break;
        }
        if(!mBandSettingDB.getVibration().equals(vi)){
            if(!PBluetooth.INSTANCE.isConnect(mac)){
                PBluetooth.INSTANCE.connect(mac,false,false);
            }
            switch (vi){
                case "standard":
                    BluetoothUtils.INSTANCE.setVibration(mPvBluetoothCallback,mac,(byte)0x02);
                    break;
                case "off":
                    BluetoothUtils.INSTANCE.setVibration(mPvBluetoothCallback,mac,(byte)0x00);
                    break;
                case "weak":
                    BluetoothUtils.INSTANCE.setVibration(mPvBluetoothCallback,mac,(byte)0x01);
                    break;
                case "strong":
                    BluetoothUtils.INSTANCE.setVibration(mPvBluetoothCallback,mac,(byte)0x03);
                    break;
            }
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
                case L28T_VIBRATION_DATA:
                    LogUtils.i("设置震动强度成功");
                    mBandSettingDB.setVibration(vi);
                    ContentValues values=new ContentValues();
                    values.put("vibration",vi);
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            LogUtils.i("设置震动强度失败");
            vi=mBandSettingDB.getVibration();
            switch (vi){
                case "standard":
                    onClick(vibration_standard_rl);
                    break;
                case "off":
                    onClick(vibration_off_rl);
                    break;
                case "weak":
                    onClick(vibration_weak_rl);
                    break;
                case "strong":
                    onClick(vibration_strong_rl
                    );
                    break;
            }
        }
    };

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
