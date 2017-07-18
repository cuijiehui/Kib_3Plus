package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.BandSettingsMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PDB;

/**
 * Created by cui on 2017/7/6.
 */

public class BandSettingsSettingFragment extends BaseFragment {
    private BandSettingDB mBandSettingDB;
    private String mac="";
    private ToggleButton setting_sync_tbt;
    private TextView setting_inactivity_tv,setting_sleep_tv,setting_time_tv,setting_untils_tv,setting_vibration_tv,setting_reset_tv;

    private static BandSettingsSettingFragment instance;
    public static BandSettingsSettingFragment getInstance(){
        if(instance==null){
            instance=new BandSettingsSettingFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.band_settings_setting_layout;
    }


    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        setting_sync_tbt= findViewById(R.id.setting_sync_tbt);
        setting_inactivity_tv= findViewById(R.id.setting_inactivity_tv);
        setting_sleep_tv= findViewById(R.id.setting_sleep_tv);
        setting_time_tv= findViewById(R.id.setting_time_tv);
        setting_untils_tv= findViewById(R.id.setting_untils_tv);
        setting_vibration_tv= findViewById(R.id.setting_vibration_tv);
        setting_reset_tv= findViewById(R.id.setting_reset_tv);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        if(bundle!=null){
            mac=bundle.getString("mac");
        }
        mBandSettingDB= PDB.INSTANCE.getBandSettingDB(mac);
        if(mBandSettingDB==null){
            mBandSettingDB=new BandSettingDB(
                    mac
                    ,false
                    ,"1"
                    ,"standard"
                    ,"12"
                    ,false
                    ,645
                    ,360
                    ,false
                    ,90
                    ,510
                    ,465
                    ,100
                    ,"0000000"
            );
            PDB.INSTANCE.addBandSettingDB(mBandSettingDB);
        }
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        setting_sync_tbt.setChecked(mBandSettingDB.isSync());
    }

    @Override
    public void initListener() {
        super.initListener();
        setting_sync_tbt.setOnClickListener(this);
        setting_inactivity_tv.setOnClickListener(this);
        setting_sleep_tv.setOnClickListener(this);
        setting_time_tv.setOnClickListener(this);
        setting_untils_tv.setOnClickListener(this);
        setting_vibration_tv.setOnClickListener(this);
        setting_reset_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putString("mac",mac);
        switch (v.getId()){
            case R.id.setting_sync_tbt:
                LogUtils.i("setting_sync_tbt"+setting_sync_tbt.isChecked());
                mBandSettingDB.setSync(setting_sync_tbt.isChecked());
                ContentValues values =new ContentValues();
                values.put("sync",mBandSettingDB.isSync());
                PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                break;
            case R.id.setting_inactivity_tv:
                LogUtils.i("setting_inactivity_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.INACTIVITY_ALERT_FRAGMENT,bundle);

                break;
            case R.id.setting_sleep_tv:
                LogUtils.i("setting_sleep_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.PRESET_SLEEP_FRAGMENT,bundle);

                break;
            case R.id.setting_time_tv:
                LogUtils.i("setting_time_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SETTING_TIME_FRAGMENT,bundle);

                break;
            case R.id.setting_untils_tv:
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SETTING_UNITS_FRAGMENT,bundle);
                LogUtils.i("setting_untils_tv");
                break;
            case R.id.setting_vibration_tv:
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SETTING_VIBRATION_FRAGMENT,bundle);
                LogUtils.i("setting_vibration_tv");
                break;
            case R.id.setting_reset_tv:
                LogUtils.i("setting_reset_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SEEING_RESET_FRAGMENT,bundle);

                break;

        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_FRAGMENT);
        return true;
    }

}
