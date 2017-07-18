package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

/**
 * Created by cui on 2017/6/26.
 */

public class CreateNewAccountFragment extends BaseFragment {
    private TextView welcome_change_tv;
    private Button welcome_create_bt,welcome_join_bt;

    private static CreateNewAccountFragment mCreateNewAccountFragment;
    public static CreateNewAccountFragment getInstance(){
        if(mCreateNewAccountFragment==null){
            mCreateNewAccountFragment=new CreateNewAccountFragment();
        }
        return mCreateNewAccountFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.create_new_accout_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        welcome_change_tv= findViewById(R.id.welcome_change_tv);
        welcome_create_bt= findViewById(R.id.welcome_create_bt);
        welcome_join_bt= findViewById(R.id.welcome_join_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        welcome_change_tv.setOnClickListener(this);
        welcome_create_bt.setOnClickListener(this);
        welcome_join_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.welcome_change_tv:
               String email=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,SPManager.DATA_STRING);
                String password=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_PASSWORD_l28t,SPManager.DATA_STRING);
                Bundle bundle=new Bundle();
                bundle.putString(LoginFragment.BUNDLE_EMAIL,email);
                bundle.putString(LoginFragment.BUNDLE_PASSWORD,password);
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, false);
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_SIGN_L28t, false);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.LOGIN_FRAGMENT,bundle);
                LogUtils.i("welcome_change_tv");
                break;
            case R.id.welcome_create_bt:
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_FAMILY_FRAGMENT);

                LogUtils.i("welcome_change_tv");
                break;
            case R.id.welcome_join_bt:
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.JOIN_FAMILY_FRAGMENT);

                LogUtils.i("welcome_change_tv");
                break;
        }
    }
}
