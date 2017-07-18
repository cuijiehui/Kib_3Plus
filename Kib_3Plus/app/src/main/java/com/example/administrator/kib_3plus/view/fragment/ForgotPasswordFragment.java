package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kib_3plus.Utils.KeyboardUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.CallBackBaseMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by cui on 2017/6/26.
 */

public class ForgotPasswordFragment extends BaseFragment  {

    private Button forgot_submit_bt;
    private EditText forgot_eamil_et;
    public static final int FORGOT_PASSWORD_OK=201;
    public static final int FORGOT_PASSWORD_FAIL=101;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case FORGOT_PASSWORD_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"成功");

                    break;
                case FORGOT_PASSWORD_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;
            }
        }
    };;

    private static ForgotPasswordFragment mForgotPasswordFragment;
    public static ForgotPasswordFragment getInstance(){
        if(mForgotPasswordFragment==null){
            mForgotPasswordFragment=new ForgotPasswordFragment();
        }
        return mForgotPasswordFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.forgot_pas_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        forgot_submit_bt= findViewById(R.id.forgot_submit_bt);
        forgot_eamil_et= findViewById(R.id.forgot_eamil_et);
    }

    @Override
    public void initListener() {
        super.initListener();
        forgot_submit_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        KeyboardUtils.hideKeyBoard(getContext(),getView());

        ContentViewManage.getInstance().setFragmentType(ContentViewManage.WELCOME_FRAGMENT);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgot_submit_bt:
                LogUtils.i("forgot_submit_bt");
                forgotPassword();
                break;
        }
    }

    private void forgotPassword() {
        if(TextUtils.isEmpty(forgot_eamil_et.getText().toString().trim())){
            showToast("邮件不能为空");
            return;
        }
//        String params = "email=" + reset_email.getText().toString().trim();

        HashMap map = new HashMap();
        map.put("email",forgot_eamil_et.getText().toString().trim());
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.FORGOT_PASSWD,callback,map,"forgotPasswd");
    }
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mHandler.sendEmptyMessage(FORGOT_PASSWORD_FAIL);

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String sj= (String) response.body().string();//需要添加stirng强转
            call.request().tag();
            LogUtils.i("SJ="+sj);
            CallBackBaseMode callBackBaseMode=   GsonUtils.stringToClss(sj,CallBackBaseMode.class);

            if(callBackBaseMode.getResult().equals("0")){
                mHandler.sendEmptyMessage(FORGOT_PASSWORD_OK);
            }else{
                mHandler.sendEmptyMessage(FORGOT_PASSWORD_FAIL);
            }

        }
    };
}
