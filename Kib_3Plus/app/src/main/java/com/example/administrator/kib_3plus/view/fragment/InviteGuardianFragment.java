package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.DialogFragment.MainDeleteDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

/**
 * Created by cui on 2017/7/25.
 */

public class InviteGuardianFragment extends BaseFragment {

    EditText invite_name_et,invite_eamil_et;
    Button invite_submit_bt;
    String familyName="";

    private static InviteGuardianFragment instance;

    public static InviteGuardianFragment getInstance(){
        if(instance==null){
            instance=new InviteGuardianFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        invite_name_et= findViewById(R.id.invite_name_et);
        invite_eamil_et= findViewById(R.id.invite_eamil_et);
        invite_submit_bt= findViewById(R.id.invite_submit_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        invite_submit_bt.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.invite_guardian_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invite_submit_bt:
                LogUtils.i("invite_submit_bt");
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_SETTINGS_FRAGMENT);
        return true;
    }
}
