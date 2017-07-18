package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.TimeUtils;
import com.example.administrator.kib_3plus.ui.RevolveCircleView;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.List;

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
    private TextView data_goal_tv,data_best_tv;
    private ImageView data_day_left_iv,data_day_right_iv;
    private TextView data_day_date_tv;
    private RevolveCircleView data_day_data_rcv;
    private LinearLayout data_day_cal_ll,data_day_activity_ll;
    private CheckBox data_day_activity_cb,data_day_cal_cb;
    private int best=0;
    private  String mDate;
    private int type=0;
    private View cacheView=null;
    private static DataParticularsFragment instance;
    public static DataParticularsFragment getInstance(){
        if(instance==null){
            instance=new DataParticularsFragment();
        }
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
        data_day_left_iv=findViewById(R.id.data_day_left_iv);
        data_day_right_iv=findViewById(R.id.data_day_right_iv);
        data_day_date_tv=findViewById(R.id.data_day_date_tv);
        data_day_data_rcv=findViewById(R.id.data_day_data_rcv);
        data_day_cal_ll=findViewById(R.id.data_day_cal_ll);
        data_day_activity_ll=findViewById(R.id.data_day_activity_ll);
        data_day_activity_cb=findViewById(R.id.data_day_activity_cb);
        data_day_cal_cb=findViewById(R.id.data_day_cal_cb);
    }

    @Override
    public void initListener() {
        super.initListener();
        data_day_tv.setOnClickListener(this);
        data_week_tv.setOnClickListener(this);
        data_month_tv.setOnClickListener(this);
        data_day_left_iv.setOnClickListener(this);
        data_day_right_iv.setOnClickListener(this);
        data_day_activity_cb.setOnClickListener(this);
        data_day_cal_cb.setOnClickListener(this);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
//        360

        data_goal_tv.setText(getString(R.string.particular_goal_tv)+"500");
        type=0;
        showView(type);
    }

    /**
     * 初始化界面
     * @param type 显示的是什么类型 0为运动 1为卡路里 2为睡眠
     */
    private void showView(int type){
        int percent= 0;
        int sweepAngle=0;
        int activity=0;
        int cal=0;
        LogUtils.i("type"+type);
        if(type==0){
            if(spoetL28TDB!=null){
                activity=spoetL28TDB.getActivity();
            }
            sweepAngle= activity*360/500;
            percent=activity*100/500;
            data_day_data_rcv.setData(type,activity+"",percent,sweepAngle);
            data_day_activity_cb.setVisibility(View.VISIBLE);
            data_day_data_rcv.setVisibility(View.VISIBLE);
            setRadioButton(data_day_activity_cb);
        }else if(type==1){
            if(spoetL28TDB!=null){
                cal=spoetL28TDB.getChores();
            }
            sweepAngle= cal*360/500/1000;
            percent= cal*100/500/1000;
            data_day_data_rcv.setData(type,cal/1000+"",percent,sweepAngle);
            data_day_activity_cb.setVisibility(View.VISIBLE);
            data_day_data_rcv.setVisibility(View.VISIBLE);
            setRadioButton(data_day_cal_cb);

        }else if(type==2){
            sweepAngle= spoetL28TDB.getSleep()*360/720;//分钟
            percent= spoetL28TDB.getSleep()*100/720;//分钟
            data_day_data_rcv.setData(type,spoetL28TDB.getSleep()+"",percent,sweepAngle);
            data_day_activity_cb.setVisibility(View.GONE);
            data_day_data_rcv.setVisibility(View.GONE);
        }

    }
    /**
     * 获取最高的数据
     * @param date 日期
     * @param type 类型 0是步数，1是卡路里 2.是睡眠
     */
    private void getBestData(String date,int type){
        List<ChildInfoDB> childDataList= PDB.INSTANCE.getAllChildInfo();
        LogUtils.i("date"+date);
        LogUtils.i("childDataList.size="+childDataList.size());
        for(ChildInfoDB childInfoDB : childDataList){
            LogUtils.i("childInfoDB="+childInfoDB.getMac());
            SpoetL28TDB spoetL28TDB= PDB.INSTANCE.getSportL28T(date,childInfoDB.getuId());
            if(spoetL28TDB!=null){
                switch (type){
                    case 0:
                        if(spoetL28TDB.getActivity()>best){
                            best=spoetL28TDB.getActivity();
                        }
                        break;
                     case 1:
                         if(spoetL28TDB.getChores()>best){
                             best=spoetL28TDB.getChores();
                         }
                        break;
                     case 2:
                         if(spoetL28TDB.getSleep()>best){
                             best=spoetL28TDB.getSleep();
                         }
                        break;

                }

            }
        }

    }
    /**
     * 设置按钮
     * @param v activity 按钮或者是cal按钮
     */
    private void setRadioButton(View v){
        best=0;
        switch (v.getId()){
            case R.id.data_day_activity_cb:
                LogUtils.i("data_day_activity_rb");
                data_day_activity_cb.setChecked(true);
                data_day_cal_cb.setChecked(false);
                getBestData(mDate,0);

                break;
            case R.id.data_day_cal_cb:
                LogUtils.i("data_day_cal_rb");
                data_day_activity_cb.setChecked(false);
                data_day_cal_cb.setChecked(true);
                getBestData(mDate,1);
                break;

        }
        data_best_tv.setText(getString(R.string.particular_best_tv)+best+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_day_tv:
                LogUtils.i("data_day_tv");
                break;
            case R.id.data_week_tv:
                LogUtils.i("data_week_tv");
                break;
            case R.id.data_month_tv:
                LogUtils.i("data_month_tv");
                break;
            case R.id.data_day_left_iv:
                LogUtils.i("data_day_left_iv");
                mDate= TimeUtils.getInstance().getSpecifiedDayBefore(mDate);
                spoetL28TDB= PDB.INSTANCE.getSportL28T(mDate,childInfoDB.getuId());
                LogUtils.i("data_day_left_iv"+mDate);
//                LogUtils.i("spoetL28TDB="+spoetL28TDB.toString());
                showView(type);
                break;
            case R.id.data_day_right_iv:
                if(!mDate.equals(TimeUtils.getInstance().getTimeType("yyyy-MM-dd"))){
                    LogUtils.i("data_day_right_iv");

                    mDate= TimeUtils.getInstance().getSpecifiedDayAfter(mDate);
                    spoetL28TDB= PDB.INSTANCE.getSportL28T(mDate,childInfoDB.getuId());
                    showView(type);
                }else{
                    LogUtils.i("data_day_right_iv");

                    showToast("没有数据");
                }
                LogUtils.i("data_day_right_iv");
                break;
            case R.id.data_day_cal_cb:
                type=1;
//                if(cacheView==null||cacheView.getId()!=R.id.data_day_cal_cb){
                    LogUtils.i("data_day_activity_ll");
//                    cacheView=v;
                    showView(type);
//                }

                LogUtils.i("data_day_cal_ll");
                break;
            case R.id.data_day_activity_cb:
                type=0;
//                if(cacheView==null||cacheView.getId()!=R.id.data_day_activity_cb){
                    LogUtils.i("data_day_activity_ll");
//                    cacheView=v;
                    showView(type);
//                }
                LogUtils.i("data_day_activity_ll");
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
