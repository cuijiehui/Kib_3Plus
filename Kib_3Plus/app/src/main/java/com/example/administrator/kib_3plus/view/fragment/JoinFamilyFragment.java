package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import static com.example.administrator.kib_3plus.view.fragment.CreateFamilyFragment.ADD_FAMILY_FAIL;
import static com.example.administrator.kib_3plus.view.fragment.CreateFamilyFragment.ADD_FAMILY_OK;

/**
 * Created by cui on 2017/6/26.
 */

public class JoinFamilyFragment extends BaseFragment {
    private TextView join_family_name_tv;
    private Button join_refresh_bt,join_cancel_bt;
    AddFamilyMode getFamilyMode;
    public static final int GET_FAMILY_OK=210;
    public static final int GET_FAMILY_FAIL=110;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ADD_FAMILY_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();

                    join_family_name_tv.setText("+"+getFamilyMode.getData().getName()+" Fragment");
                    break;
                case ADD_FAMILY_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;
            }
        }
    };;

private static JoinFamilyFragment mJoinFamilyFragment;
    public static JoinFamilyFragment getInstance(){
        if(mJoinFamilyFragment==null){
            mJoinFamilyFragment=new JoinFamilyFragment();
        }
        return mJoinFamilyFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.join_family_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        join_family_name_tv= findViewById(R.id.join_family_name_tv);
        join_refresh_bt= findViewById(R.id.join_refresh_bt);
        join_cancel_bt= findViewById(R.id.join_cancel_bt);

    }

    @Override
    public void onResume() {
        super.onResume();
        getFamily();
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
            getFamilyMode= GsonUtils.stringToClss(sj,AddFamilyMode.class);

            if(getFamilyMode.getResult().equals("0")){
                mHandler.sendEmptyMessage(GET_FAMILY_OK);

            }else{
                mHandler.sendEmptyMessage(GET_FAMILY_FAIL);
//
            }

        }
    };

    @Override
    public void initListener() {
        super.initListener();
        join_refresh_bt.setOnClickListener(this);
        join_cancel_bt.setOnClickListener(this);
        join_family_name_tv.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_refresh_bt:
                LogUtils.i("join_refresh_bt");
                getFamily();
                break;
            case R.id.join_cancel_bt:
                LogUtils.i("join_cancel_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CREATE_NEW_ACCOUNT_FRAGMENT);
                break;
            case R.id.join_family_name_tv:
                LogUtils.i("join_family_name_tv");
                Bundle bundle=new Bundle();
                bundle.putString(FamilyCreatedFrafment.FAMILY_NAME,getFamilyMode.getData().getName());
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, true);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_CREATED_FRAFMENT);

                break;

        }

    }

    public void getFamily() {
        HashMap map=new HashMap();
        String email=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,SPManager.DATA_STRING);
        map.put("personMail",email);
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.GET_FAMILY,callback,map,"getFamily");
    }
}
