package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PDB;

/**
 * Created by cui on 2017/7/6.
 */

public class SettingUnitsFragment extends BaseFragment {
    private String mac;
    private BandSettingDB mBandSettingDB;
    private  RelativeLayout units_pam_rl,units_kak_rl;
    private ImageView units_pam_select_iv,units_kak_select_iv;
    private static SettingUnitsFragment instance;
    public static SettingUnitsFragment getInstance(){
        if(instance==null){
            instance=new SettingUnitsFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.setting_units_layout;
    }
    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        units_pam_rl= findViewById(R.id.units_pam_rl);
        units_kak_rl= findViewById(R.id.units_kak_rl);
        units_pam_select_iv= findViewById(R.id.units_pam_select_iv);
        units_kak_select_iv= findViewById(R.id.units_kak_select_iv);

    }

    @Override
    public void initListener() {
        super.initListener();
        units_pam_rl.setOnClickListener(this);
        units_kak_rl.setOnClickListener(this);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
       String units= mBandSettingDB.getUnits();
        if(units.equals("1")){
            setUnitSelect(false);

        }else{
            setUnitSelect(true);

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.units_pam_rl:
                LogUtils.i("units_pam_rl");
                setUnitSelect(false);
                if(mBandSettingDB.getUnits().equals("0")){
                    mBandSettingDB.setUnits("1");
                    ContentValues values=new ContentValues();
                    values.put("units","1");
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                }
                break;
            case R.id.units_kak_rl:
                LogUtils.i("units_kak_rl");
                setUnitSelect(true);
                if(mBandSettingDB.getUnits().equals("1")){
                    mBandSettingDB.setUnits("0");
                    ContentValues values=new ContentValues();
                    values.put("units","0");
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                }

                break;
        }
    }
    private void setUnitSelect(boolean isK){
        if(isK){
            units_pam_select_iv.setVisibility(View.GONE);
            units_kak_select_iv.setVisibility(View.VISIBLE);
        }else{
            units_pam_select_iv.setVisibility(View.VISIBLE);
            units_kak_select_iv.setVisibility(View.GONE);
        }
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
