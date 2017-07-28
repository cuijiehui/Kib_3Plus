package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

import static com.example.administrator.kib_3plus.view.manage.ContentViewManage.INVITE_GUARDIAN_FRAGMENT;
import static okhttp3.internal.Internal.instance;

/**
 * Created by cui on 2017/7/25.
 */

public class FamilySettingsFragment extends BaseFragment {

    TextView family_setting_family_name_tv,family_setting_head_name_tv,family_setting_invite_tv;
    String userName="";
    String familyName="";
    private static   FamilySettingsFragment instance;

    public static FamilySettingsFragment getInstance(){
        if(instance==null){
            instance=new FamilySettingsFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        family_setting_invite_tv= findViewById(R.id.family_setting_invite_tv);
        family_setting_head_name_tv= findViewById(R.id.family_setting_head_name_tv);
        family_setting_family_name_tv= findViewById(R.id.family_setting_family_name_tv);
    }

    @Override
    public void initListener() {
        super.initListener();
        family_setting_invite_tv.setOnClickListener(this);
        family_setting_head_name_tv.setOnClickListener(this);
        family_setting_family_name_tv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        userName= (String) PSP.INSTANCE.getSPValue(SPKey.SP_USER_NAME_L28T,PSP.DATA_STRING);
        familyName= (String) PSP.INSTANCE.getSPValue(SPKey.SP_FAMILY_NAME_L28t,PSP.DATA_STRING);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        family_setting_family_name_tv.setText(familyName);
        family_setting_head_name_tv.setText(userName);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.family_member_setting_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.family_setting_invite_tv:
                LogUtils.i("family_setting_invite_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.INVITE_GUARDIAN_FRAGMENT);

                break;
            case R.id.family_setting_head_name_tv:
                LogUtils.i("family_setting_head_name_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.HEAD_FAMILY_SETTINGS_FRAGMENT);

                break;
            case R.id.family_setting_family_name_tv:
                LogUtils.i("family_setting_family_name_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_NAME_SETTINGS_FRAGMENT);

                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_SETTINGS_FRAGMENT);
        return true;
    }
}
