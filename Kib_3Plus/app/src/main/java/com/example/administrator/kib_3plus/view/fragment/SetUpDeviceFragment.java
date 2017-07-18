package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

/**
 * Created by cui on 2017/6/27.
 */

public class SetUpDeviceFragment extends BaseFragment {
    private Button set_device_confirm_bt;
    private Bundle mBundle;
    private static SetUpDeviceFragment mSetUpDeviceFragment;
    public static SetUpDeviceFragment getInstance(){
        if(mSetUpDeviceFragment==null){
            mSetUpDeviceFragment=new SetUpDeviceFragment();
        }
        return mSetUpDeviceFragment;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.set_up_device_layout;
    }

    @Override
    public void initData() {
        super.initData();
        mBundle=getArguments();
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        set_device_confirm_bt=findViewById(R.id.set_device_confirm_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        set_device_confirm_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_device_confirm_bt:
                LogUtils.i("set_device_confirm_bt");
                Bundle bundle=mBundle;
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_DEVICE_FRAGMENT,bundle);
                break;
        }
    }



}
