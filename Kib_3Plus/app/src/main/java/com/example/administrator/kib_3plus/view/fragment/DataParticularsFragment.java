package com.example.administrator.kib_3plus.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.Utils.TimeUtils;
import com.example.administrator.kib_3plus.ui.DataViewChart;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.SpoetL28TDB;
import cn.appscomm.presenter.implement.PDB;


/**
 * Created by cui on 2017/7/11.
 */

public class DataParticularsFragment extends BaseFragment {
    private ChildInfoDB childInfoDB;
    private SpoetL28TDB spoetL28TDB;
    private RoundImageView data_day_icon_riv;
    private TextView data_day_tv,data_week_tv,data_month_tv;
    private RelativeLayout data_content_view_rl;
    LinearLayout data_content_botton_ll;
    private TextView data_goal_tv,data_best_tv;
    private  String mDate;
    private int type=0;
    private View cacheView=null;
    private static DataParticularsFragment instance;
    public static DataParticularsFragment getInstance(){
            instance=new DataParticularsFragment();
        return instance;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.data_particulars_layout;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        type= bundle.getInt("type");

        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
         mDate= TimeUtils.getInstance().getTimeType("yyyy-MM-dd");
        spoetL28TDB= PDB.INSTANCE.getSportL28T(mDate,childInfoDB.getuId());


    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        data_day_icon_riv=findViewById(R.id.data_day_icon_riv);
        data_day_tv=findViewById(R.id.data_day_tv);
        data_week_tv=findViewById(R.id.data_week_tv);
        data_month_tv=findViewById(R.id.data_month_tv);
        data_content_view_rl=findViewById(R.id.data_content_view_rl);
        data_goal_tv=findViewById(R.id.data_goal_tv);
        data_best_tv=findViewById(R.id.data_best_tv);
        data_content_botton_ll=findViewById(R.id.data_content_botton_ll);
    }

    @Override
    public void initListener() {
        super.initListener();
        data_day_tv.setOnClickListener(this);
        data_week_tv.setOnClickListener(this);
        data_month_tv.setOnClickListener(this);

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
////        360
        data_day_icon_riv.setBackgroundPaint(NumberUtils.INSTANCE.getFavorite(childInfoDB.getFavorite()));
//
        if(childInfoDB.isIcon()){
            data_day_icon_riv.setImageResource(childInfoDB.getIcon());
        }else{
            LogUtils.i(childInfoDB.getIconUrl());
            String poth=CameraUtils.INSTANCE.getIcon(childInfoDB.getIconUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(childInfoDB.getIconUrl());
                data_day_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }
        data_goal_tv.setText(getString(R.string.particular_goal_tv)+"500");
//        type=0;
//        showView(type);
//        LogUtils.i("datas");
//        float[] datas=new float[]{0,0,0,0,0,0,0};
//        data_week_data_dvc.setDatas(3,1,datas,datas,500,5,500);
//        data_week_seekbar_sb.setMax(500);
//        data_week_seekbar_sb.setProgress(10);
        setDataView(type,DataViewChart.DATEVIEW_DAY);

    }

    public void setDataView(int type,int date){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        LogUtils.i("setDataView+"+date);
        LogUtils.i("type+"+type);
        switch (date){
            case DataViewChart.DATEVIEW_DAY:
                LogUtils.i("setDataView+"+date);

                data_content_botton_ll.setVisibility(View.VISIBLE);
                DataDayFragment dataDayFragment= DataDayFragment.getInstance(mDate,spoetL28TDB,childInfoDB.getuId(),type);
                transaction.replace(R.id.data_content_view_rl,dataDayFragment , DataDayFragment.class.getSimpleName());
                break;
            case DataViewChart.DATEVIEW_MONTH:
                LogUtils.i("setDataView+"+date);

                data_content_botton_ll.setVisibility(View.GONE);
                DataWeekFragment dataWeeksFragment= DataWeekFragment.getInstance(mDate,spoetL28TDB,childInfoDB.getuId(),DataViewChart.DATEVIEW_MONTH,type);
                transaction.replace(R.id.data_content_view_rl,dataWeeksFragment , DataWeekFragment.class.getSimpleName());
                break;
            case DataViewChart.DATEVIEW_WEEK:
                LogUtils.i("setDataView+"+date);

                data_content_botton_ll.setVisibility(View.GONE);
                DataWeekFragment dataWeekFragment= DataWeekFragment.getInstance(mDate,spoetL28TDB,childInfoDB.getuId(),DataViewChart.DATEVIEW_WEEK,type);
                transaction.replace(R.id.data_content_view_rl,dataWeekFragment , DataWeekFragment.class.getSimpleName());
                break;
        }
        transaction.commit();
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_day_tv:
                LogUtils.i("data_day_tv");
                setDataView(type,DataViewChart.DATEVIEW_DAY);
                data_day_tv.setBackgroundResource(R.color.colorViolet);
                data_week_tv.setBackgroundResource(R.color.main_bg_color);
                data_month_tv.setBackgroundResource(R.color.main_bg_color);

                break;
            case R.id.data_week_tv:
                LogUtils.i("data_week_tv");
                setDataView(type,DataViewChart.DATEVIEW_WEEK);
                data_week_tv.setBackgroundResource(R.color.colorViolet);
                data_day_tv.setBackgroundResource(R.color.main_bg_color);
                data_month_tv.setBackgroundResource(R.color.main_bg_color);
                break;
            case R.id.data_month_tv:
                LogUtils.i("data_month_tv");
                setDataView(type,DataViewChart.DATEVIEW_MONTH);
                data_month_tv.setBackgroundResource(R.color.colorViolet);
                data_day_tv.setBackgroundResource(R.color.main_bg_color);
                data_week_tv.setBackgroundResource(R.color.main_bg_color);

                break;
        }
    }

    @Override
    public boolean onBackPressed() {
//        childInfoDB
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MEMBER_PARTICULAR_FRAGMENT,bundle);
        return true;
    }
}
