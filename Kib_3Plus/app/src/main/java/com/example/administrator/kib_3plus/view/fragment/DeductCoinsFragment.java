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

import static com.example.administrator.kib_3plus.R.id.give_coins_1_tv;
import static com.example.administrator.kib_3plus.R.id.give_coins_2_tv;
import static com.example.administrator.kib_3plus.R.id.give_coins_3_tv;
import static com.example.administrator.kib_3plus.R.id.give_coins_confirm_bt;
import static com.example.administrator.kib_3plus.R.id.give_coins_custom_bt;

/**
 * Created by cui on 2017/7/13.
 */

public class DeductCoinsFragment extends BaseFragment {

    private ChildInfoDB childInfoDB;
    private TextView deduct_coins_1_tv,deduct_coins_2_tv,deduct_coins_3_tv;
    private Button deduct_coins_confirm_bt,deduct_coins_custom_bt;
    private View cacheView=null;//用于保存上一次操作的view

    private static DeductCoinsFragment instance;
    public static DeductCoinsFragment getInstance(){
        if(instance==null){
            instance=new DeductCoinsFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.deduct_coins_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        deduct_coins_1_tv= findViewById(R.id.deduct_coins_1_tv);
        deduct_coins_2_tv=  findViewById(R.id.deduct_coins_2_tv);
        deduct_coins_3_tv= findViewById(R.id.deduct_coins_3_tv);
        deduct_coins_confirm_bt= findViewById(R.id.deduct_coins_confirm_bt);
        deduct_coins_custom_bt= findViewById(R.id.deduct_coins_custom_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        deduct_coins_1_tv.setOnClickListener(this);
        deduct_coins_2_tv.setOnClickListener(this);
        deduct_coins_3_tv.setOnClickListener(this);
        deduct_coins_confirm_bt.setOnClickListener(this);
        deduct_coins_custom_bt.setOnClickListener(this);
    }

    private void showView(View v){
        deduct_coins_1_tv.setBackground(null);
        deduct_coins_2_tv.setBackground(null);
        deduct_coins_3_tv.setBackground(null);
        if(v!=null){
            v.setBackgroundResource(R.drawable.btn_red_bg_shape);
            deduct_coins_confirm_bt.setBackgroundResource(R.drawable.btn_green_bg_shape);
        }else{
            deduct_coins_confirm_bt.setBackgroundResource(R.drawable.btn_gray_bg_shape);
        }
        cacheView=v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deduct_coins_1_tv:
                LogUtils.i("deduct_coins_1_tv");
                if(cacheView!=deduct_coins_1_tv){
                    showView(deduct_coins_1_tv);
                }
                break;
            case R.id.deduct_coins_2_tv:
                LogUtils.i("deduct_coins_2_tv");
                if(cacheView!=deduct_coins_2_tv){
                    showView(deduct_coins_2_tv);
                }

                break;
            case R.id.deduct_coins_3_tv:
                LogUtils.i("deduct_coins_3_tv");
                if(cacheView!=deduct_coins_3_tv){
                    showView(deduct_coins_3_tv);
                }

                break;
            case R.id.deduct_coins_confirm_bt:
                LogUtils.i("deduct_coins_confirm_bt");
                if(cacheView!=null){
                    deductCoins();
                }
                break;
            case R.id.deduct_coins_custom_bt:
                LogUtils.i("deduct_coins_custom_bt");
                showView(null);

                break;
        }
    }

    private void deductCoins() {
        int count=childInfoDB.getGoldCount();
        ContentValues contentValues =new ContentValues();
        boolean isOk=true;
        switch (cacheView.getId()){
            case R.id.deduct_coins_1_tv:
                LogUtils.i("减一");
                if(count-1>0){
                    contentValues.put("goldCount",count-1);
                    PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                }else{
                    isOk=false;
                }
                break;
            case R.id.deduct_coins_2_tv:
                if(count-2>0) {
                    LogUtils.i("减二");
                    contentValues.put("goldCount",count-2);
                    PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                }else{
                    isOk=false;

                }

                break;
            case R.id.deduct_coins_3_tv:
                if(count-3>0) {
                    LogUtils.i("减三");
                    contentValues.put("goldCount",count-3);
                    PDB.INSTANCE.updateAllChildInfo(contentValues,childInfoDB.getuId());
                }else{
                    isOk=false;

                }

                break;
        }
        if(isOk){
            DialogUtil.INSTANCE.commonDialog(getContext(),"deduct Coins","ok");
            showView(null);
        }else{
            DialogUtil.INSTANCE.commonDialog(getContext(),"deduct Coins","Not enough COINS");

        }
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
