package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PDB;

/**
 * Created by cui on 2017/7/13.
 */

public class GiveCoinsFragment extends BaseFragment {

    private ChildInfoDB childInfoDB;
    private TextView give_coins_1_tv,give_coins_2_tv,give_coins_3_tv;
    private Button give_coins_confirm_bt,give_coins_custom_bt;
    private View cacheView=null;//用于保存上一次操作的view

    private static GiveCoinsFragment instance;
    public static GiveCoinsFragment getInstance(){
        if(instance==null){
            instance=new GiveCoinsFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.give_coins_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        give_coins_1_tv= findViewById(R.id.give_coins_1_tv);
        give_coins_2_tv=  findViewById(R.id.give_coins_2_tv);
        give_coins_3_tv= findViewById(R.id.give_coins_3_tv);
        give_coins_confirm_bt= findViewById(R.id.give_coins_confirm_bt);
        give_coins_custom_bt= findViewById(R.id.give_coins_custom_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        give_coins_1_tv.setOnClickListener(this);
        give_coins_2_tv.setOnClickListener(this);
        give_coins_3_tv.setOnClickListener(this);
        give_coins_custom_bt.setOnClickListener(this);
        give_coins_confirm_bt.setOnClickListener(this);
    }

    private void showView(View v){
        give_coins_1_tv.setBackground(null);
        give_coins_2_tv.setBackground(null);
        give_coins_3_tv.setBackground(null);
        if(v!=null){
            v.setBackgroundResource(R.drawable.btn_green_bg_shape);
            give_coins_confirm_bt.setBackgroundResource(R.drawable.btn_green_bg_shape);
        }else{
            give_coins_confirm_bt.setBackgroundResource(R.drawable.btn_gray_bg_shape);
        }
        cacheView=v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.give_coins_1_tv:
                LogUtils.i("give_coins_1_tv");
                if(cacheView!=give_coins_1_tv){
                    showView(give_coins_1_tv);
                }
                break;
            case R.id.give_coins_2_tv:
                LogUtils.i("give_coins_2_tv");
                if(cacheView!=give_coins_2_tv){
                    showView(give_coins_2_tv);
                }

                break;
            case R.id.give_coins_3_tv:
                LogUtils.i("give_coins_3_tv");
                if(cacheView!=give_coins_3_tv){
                    showView(give_coins_3_tv);
                }

                break;
            case R.id.give_coins_confirm_bt:
                LogUtils.i("give_coins_confirm_bt");
                if(cacheView!=null){
                    addCoins();
                }
                break;
            case R.id.give_coins_custom_bt:
                LogUtils.i("give_coins_custom_bt");
                showView(null);

                break;
        }
    }

    private void addCoins() {
        int count=childInfoDB.getGoldCount();
        ContentValues contentValues =new ContentValues();
        switch (cacheView.getId()){
            case R.id.give_coins_1_tv:
                LogUtils.i("加一");
                contentValues.put("goldCount",count+1);
                PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                break;
            case R.id.give_coins_2_tv:
                LogUtils.i("加二");
                contentValues.put("goldCount",count+2);
                PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                break;
            case R.id.give_coins_3_tv:
                LogUtils.i("加三");
                contentValues.put("goldCount",count+3);
                PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                break;
        }
        DialogUtil.INSTANCE.commonDialog(getContext(),"Give Coins","ok");
        showView(null);
        childInfoDB= PDB.INSTANCE.getChildInfo(childInfoDB.getuId());

    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MEMBER_PARTICULAR_FRAGMENT,bundle);
        return true;
    }
}
