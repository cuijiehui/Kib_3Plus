package com.example.administrator.kib_3plus.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kib_3plus.Utils.KeyboardUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.AddFamilyMode;
import com.example.administrator.kib_3plus.http.mode.LoginMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.io.IOException;
import java.util.HashMap;

import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.administrator.kib_3plus.Utils.KeyboardUtils.hideKeyBoard;
import static com.example.administrator.kib_3plus.http.OkHttpUtils.GET_FAMILY;

/**
 * Created by cui on 2017/6/26.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private EditText login_eamil_et,login_password_et;
    private Button login_login_bt,login_forgot_psw_bt;
    private String mEmail="",mPassword="";
    public static final int LOGIN_OK=200;
    public static final int LOGIN_FAIL=100;
    public static final int GET_FAMILY_OK=220;
    public static final int GET_FAMILY_FAIL=120;
    public static final String BUNDLE_EMAIL="Email";
    public static final String BUNDLE_PASSWORD="Password";
    LoginMode loginMode;
    AddFamilyMode getFamilyMode;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case LOGIN_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();


                    HashMap map = new HashMap();
                    map.put("personMail",login_eamil_et.getText().toString().trim());
                    OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+ GET_FAMILY,callback,map,"getFamily");

//                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);
                    break;
                case LOGIN_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;

                case GET_FAMILY_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();

                    //保存数据
                    SPManager.INSTANCE.setSPValue(SPKey.SP_USER_EMAIL_L28T,loginMode.getData().getMail());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_USER_NAME_L28T,loginMode.getData().getUserName());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_USER_ID_L28T,loginMode.getData().getUserId());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_USER_NICK_NAME_L28T,loginMode.getData().getNickName());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_USER_PASSWORD_l28t,login_password_et.getText().toString());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_IS_SIGN_L28t, true);
                    if(getFamilyMode.getData()!=null){
                        SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, true);
                        SPManager.INSTANCE.setSPValue(SPKey.SP_FAMILY_NAME_L28t,getFamilyMode.getData().getName());
                        SPManager.INSTANCE.setSPValue(SPKey.SP_FAMILY_ID_L28t,getFamilyMode.getData().getId());
                        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);

                    }else{
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);
                    }

                    break;
                case GET_FAMILY_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;
            }
        }
    };;




    private static LoginFragment mLoginFragment;
    public static LoginFragment getInstance(){
        if(mLoginFragment==null){
            mLoginFragment=new LoginFragment();
        }
        return mLoginFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        if(bundle!=null){
            mEmail=bundle.getString(BUNDLE_EMAIL);
            mPassword=bundle.getString(BUNDLE_PASSWORD);
        }
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        login_eamil_et=findViewById(R.id.login_eamil_et);
        login_password_et=findViewById(R.id.login_password_et);
        login_login_bt=findViewById(R.id.login_login_bt);
        login_forgot_psw_bt=findViewById(R.id.login_forgot_psw_bt);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        if(!TextUtils.isEmpty(mEmail)||!TextUtils.isEmpty(mPassword)){
            login_eamil_et.setText(mEmail);
            login_password_et.setText(mPassword);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        login_login_bt.setOnClickListener(this);
        login_forgot_psw_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login_bt:
                LogUtils.i("login_login_bt");
                login();
                break;
            case R.id.login_forgot_psw_bt:
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FORGOT_PASSWORD_FRAGMENT);
                LogUtils.i("login_forgot_psw_bt");
                break;

        }
    }

    public void login(){
        if(TextUtils.isEmpty(login_eamil_et.getText().toString().trim())){
            showToast("邮件不能为空");
            return;
        }
        if(TextUtils.isEmpty(login_password_et.getText().toString().trim())){
            showToast("密码不能为空");
            return;

        }
        // String params = "account=" + login_email.getText().toString().trim() + "&password=" + pwd + "&encryptMode=1";
        HashMap map = new HashMap();
        map.put("account",login_eamil_et.getText().toString().trim());
        map.put("password", NumberUtils.INSTANCE.MD5(login_password_et.getText().toString().trim()));
        map.put("encryptMode", "1");
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.LOGIN,callback,map,"login");
         DialogUtil.INSTANCE.logining(getContext()).show();


    }
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mHandler.sendEmptyMessage(LOGIN_FAIL);

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String sj= (String) response.body().string();//需要添加stirng强转
            LogUtils.i("call.request().url()"+call.request().url().toString());
            if(call.request().url().toString().equals(OkHttpUtils.HOST+OkHttpUtils.LOGIN)){
                LogUtils.i("SJ="+sj);
                loginMode= GsonUtils.stringToClss(sj,LoginMode.class);

                if(loginMode.getResult().equals("0")){
                    mHandler.sendEmptyMessage(LOGIN_OK);

                }else{
                    mHandler.sendEmptyMessage(LOGIN_FAIL);
//
                }
            }else if(call.request().url().toString().equals(OkHttpUtils.HOST+OkHttpUtils.GET_FAMILY)){
                getFamilyMode= GsonUtils.stringToClss(sj,AddFamilyMode.class);
                if(getFamilyMode.getResult().equals("0")){
                    mHandler.sendEmptyMessage(GET_FAMILY_OK);

                }else{
                    mHandler.sendEmptyMessage(GET_FAMILY_FAIL);
//
                }
            }


        }
    };

    @Override
    public boolean onBackPressed() {
        LogUtils.i("onBackPressed");
//        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        LogUtils.i("imm.isActive()="+imm.isActive());
//        if(imm.isActive()){//若返回true，则表示输入法打开
////        ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//        }
        KeyboardUtils.hideKeyBoard(getContext(),getView());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.WELCOME_FRAGMENT);
        return true;
    }
}
