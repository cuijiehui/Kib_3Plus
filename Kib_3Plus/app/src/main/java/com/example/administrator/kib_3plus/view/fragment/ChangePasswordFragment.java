package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kib_3plus.Utils.KeyboardUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

/**
 * Created by cui on 2017/6/26.
 */

public class ChangePasswordFragment extends BaseFragment {
    private EditText change_current_psw_et,change_new_psw_et,change_confirm_new_psw_et;
    private Button change_change_bt;
    private static ChangePasswordFragment mChangePasswordFragment;
    public static ChangePasswordFragment getInstance(){
        if(mChangePasswordFragment==null){
            mChangePasswordFragment=new ChangePasswordFragment();
        }
        return mChangePasswordFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.change_password_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        change_current_psw_et=findViewById(R.id.change_current_psw_et);
        change_new_psw_et=  findViewById(R.id.change_new_psw_et);
        change_confirm_new_psw_et=   findViewById(R.id.change_confirm_new_psw_et);
        change_change_bt=   findViewById(R.id.change_change_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        change_change_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        KeyboardUtils.hideKeyBoard(getContext(),getView());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_change_bt:
                LogUtils.i("change_change_bt");
                changePassword();
                break;
        }
    }

    private void changePassword() {
        if(TextUtils.isEmpty(change_current_psw_et.getText().toString())){
            showToast("邮件不能空");
            return;
        }
        if(TextUtils.isEmpty(change_new_psw_et.getText().toString())){
            showToast("密码不能空");
            return;

        }
        if(TextUtils.isEmpty(change_confirm_new_psw_et.getText().toString())){
            showToast("密码不能空");
            return;

        }
    }
}
