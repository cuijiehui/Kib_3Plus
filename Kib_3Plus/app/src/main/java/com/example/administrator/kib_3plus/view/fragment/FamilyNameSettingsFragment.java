package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.DialogFragment.MainDeleteDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

import static com.example.administrator.kib_3plus.R.id.head_account_emil_tv;
import static com.example.administrator.kib_3plus.R.id.head_family_name_et;
import static com.example.administrator.kib_3plus.R.id.head_family_sign_bt;

/**
 * Created by cui on 2017/7/25.
 */

public class FamilyNameSettingsFragment extends BaseFragment {

    EditText family_name_name_et;
    Button family_name_leave_bt;
    String familyName="";
    MainDeleteDialogFragment mMainDeleteDialogFragment;

    private static FamilyNameSettingsFragment instance;

    public static FamilyNameSettingsFragment getInstance(){
        if(instance==null){
            instance=new FamilyNameSettingsFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        family_name_name_et= findViewById(R.id.family_name_name_et);
        family_name_leave_bt= findViewById(R.id.family_name_leave_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        family_name_leave_bt.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        familyName= (String) PSP.INSTANCE.getSPValue(SPKey.SP_FAMILY_NAME_L28t,PSP.DATA_STRING);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        family_name_name_et.setHint(familyName);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.family_name_setting_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.family_name_leave_bt:
                LogUtils.i("family_name_leave_bt");
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_SETTINGS_FRAGMENT);
        return true;
    }
}
