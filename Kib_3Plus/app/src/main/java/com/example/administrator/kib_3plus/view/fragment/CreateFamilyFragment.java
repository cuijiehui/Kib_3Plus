package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.AddFamilyMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import java.io.IOException;
import java.util.HashMap;

import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by cui on 2017/6/26.
 */

public class CreateFamilyFragment extends BaseFragment  {

    private Button create_family_confirm_bt,create_family_cancel_bt;
    private EditText create_family_et;
    public static final int ADD_FAMILY_OK=200;
    public static final int ADD_FAMILY_FAIL=100;
    private AddFamilyMode addFamilyMode;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ADD_FAMILY_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    SPManager.INSTANCE.setSPValue(SPKey.SP_FAMILY_NAME_L28t,addFamilyMode.getData().getName());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_FAMILY_ID_L28t,addFamilyMode.getData().getId());

                    //保存数据
                    Bundle bundle=new Bundle();
                    bundle.putString(FamilyCreatedFrafment.FAMILY_NAME,addFamilyMode.getData().getName());
                    SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, true);

                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_CREATED_FRAFMENT,bundle);

                    break;
                case ADD_FAMILY_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;
            }
        }
    };;

    private static CreateFamilyFragment mCreateFaminlyFragment;
    public static CreateFamilyFragment getInstance(){
        if(mCreateFaminlyFragment==null){
            mCreateFaminlyFragment=new CreateFamilyFragment();
        }
        return mCreateFaminlyFragment;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.create_family_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        create_family_confirm_bt=findViewById(R.id.create_family_confirm_bt);
        create_family_cancel_bt=findViewById(R.id.create_family_cancel_bt);
        create_family_et=findViewById(R.id.create_family_et);

    }

    @Override
    public void initListener() {
        super.initListener();
        create_family_confirm_bt.setOnClickListener(this);
        create_family_cancel_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_family_confirm_bt:
                LogUtils.i("create_family_confirm_bt=");
                addFramily();
                break;
            case R.id.create_family_cancel_bt:
                LogUtils.i("create_family_cancel_bt=");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);
                break;
        }
    }

    private void addFramily() {
        if(TextUtils.isEmpty(create_family_et.getText().toString().trim())){
            showToast("请出入家庭名字");
                return;
        }
        HashMap map=new HashMap();
        map.put("name",create_family_et.getText().toString());
        String email=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,SPManager.DATA_STRING);
        map.put("personMail",email);
        map.put("customer","3PlusKid");//?
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.ADD_FAMILY,callback,map,"addFramily");
        DialogUtil.INSTANCE.logining(getContext()).show();

    }
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mHandler.sendEmptyMessage(ADD_FAMILY_FAIL);

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String sj= (String) response.body().string();//需要添加stirng强转
            LogUtils.i("SJ="+sj);
            addFamilyMode= GsonUtils.stringToClss(sj,AddFamilyMode.class);

            if(addFamilyMode.getResult().equals("0")){
                mHandler.sendEmptyMessage(ADD_FAMILY_OK);

            }else{
                mHandler.sendEmptyMessage(ADD_FAMILY_FAIL);
//
            }

        }
    };

}
