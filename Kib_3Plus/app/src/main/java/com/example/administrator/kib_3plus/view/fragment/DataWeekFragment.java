package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.MySportView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;

import cn.appscomm.db.mode.SpoetL28TDB;

/**
 * Created by cui on 2017/7/28.
 */

public class DataWeekFragment extends BaseFragment {


   ImageView data_week_left_iv,data_week_right_iv;
    TextView data_week_date_tv,data_week_name_tv,data_week_goal_tv;
   MySportView data_week_data_msv;
    SeekBar data_week_seekbar_sb;
    private static String mDate;
    private static SpoetL28TDB mSpoetL28TDB;
    private static int mUId;
    private static int mType;
    private static int mViewKindType;

    private static DataWeekFragment instance;
    public static DataWeekFragment getInstance(String date, SpoetL28TDB spoetL28TDB , int uId,int type,int viewKindType){
        instance=new DataWeekFragment();
        mDate=date;
        mSpoetL28TDB=spoetL28TDB;
        mUId=uId;
        mType=type;
        mViewKindType=viewKindType;
        return instance;
    }


    @Override
    public int getCreateViewLayoutId() {
        return R.layout.data_week_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        data_week_left_iv= findViewById(R.id.data_week_left_iv);
        data_week_right_iv= findViewById(R.id.data_week_right_iv);
        data_week_date_tv= findViewById(R.id.data_week_date_tv);
        data_week_name_tv= findViewById(R.id.data_week_name_tv);
        data_week_goal_tv= findViewById(R.id.data_week_goal_tv);
        data_week_data_msv= findViewById(R.id.data_week_data_msv);
        data_week_seekbar_sb= findViewById(R.id.data_week_seekbar_sb);

    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        LogUtils.i("datas");
        updateData();
    }
    public void updateData(){
        float[] datas=new float[]{0,0,0,0,0,0,0};
        data_week_data_msv.setDatas(mViewKindType,mType,datas,datas,500,5,500);
        data_week_seekbar_sb.setMax(500);
        data_week_seekbar_sb.setProgress(10);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_day_tv:
                LogUtils.i("data_day_tv");
                break;

        }
    }


}
