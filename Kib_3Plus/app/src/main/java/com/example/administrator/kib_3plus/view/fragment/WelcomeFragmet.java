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

/**
 * Created by cui on 2017/6/26.
 */

public class WelcomeFragmet extends BaseFragment implements View.OnClickListener {
    private Button intro_sign_into_bt,intro_create_new_account_bt;
    private TextView intro_fogot_psw_tv;

    private static WelcomeFragmet mWelcomeFragmet;

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.intro_screen_layout;
    }

    public static BaseFragment getInstance() {
        if(mWelcomeFragmet==null){
            mWelcomeFragmet=new WelcomeFragmet();
        }
        return mWelcomeFragmet;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        intro_sign_into_bt=  findViewById(R.id.intro_sign_into_bt);
        intro_create_new_account_bt= findViewById(R.id.intro_create_new_account_bt);
        intro_fogot_psw_tv= findViewById(R.id.intro_fogot_psw_tv);
        TitleManage.getInstance().setWindowStatusBarColor(getActivity(),R.color.colorWhite);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
        intro_sign_into_bt.setOnClickListener(this);
        intro_create_new_account_bt.setOnClickListener(this);
        intro_fogot_psw_tv.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.intro_sign_into_bt:
                LogUtils.i("welcome_create_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.LOGIN_FRAGMENT);
            break;
            case R.id.intro_create_new_account_bt:
                LogUtils.i("welcome_join_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_ACCOUNT_FRAGMENT);
                break;
            case R.id.intro_fogot_psw_tv:
                LogUtils.i("welcome_change_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FORGOT_PASSWORD_FRAGMENT);
                break;
        }
    }
}
