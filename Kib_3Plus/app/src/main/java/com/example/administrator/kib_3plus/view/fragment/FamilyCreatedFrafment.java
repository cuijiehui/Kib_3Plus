package com.example.administrator.kib_3plus.view.fragment;


import android.os.Bundle;
import android.text.TextUtils;
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

public class FamilyCreatedFrafment extends BaseFragment{
    private TextView family_family_name_tv;
    private Button family_add_new_member_bt;

    public static final String FAMILY_NAME="family_name";
    public String familyName="";
    private static FamilyCreatedFrafment mFamilyCreatedFrafment;
    public static FamilyCreatedFrafment getInstance(){
        if(mFamilyCreatedFrafment==null){
            mFamilyCreatedFrafment=new FamilyCreatedFrafment();
        }
        return mFamilyCreatedFrafment;
    }

    @Override
    public void initData() {
        super.initData();
       Bundle bundle= getArguments();
        familyName= bundle.getString(FAMILY_NAME);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.family_created_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        family_family_name_tv=findViewById(R.id.family_family_name_tv);
        family_add_new_member_bt=findViewById(R.id.family_add_new_member_bt);

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        if(!TextUtils.isEmpty(familyName)){
            family_family_name_tv.setText(familyName);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        family_add_new_member_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.family_add_new_member_bt:
                LogUtils.i("family_add_new_member_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.ADD_MEMBER_FRAGMENT);

                break;

        }
    }
}
