package com.example.administrator.kib_3plus.view.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.ui.DialogFragment.OneWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.DialogFragment.WeightWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.PickerView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

/**
 * Created by cui on 2017/7/12.
 */

public class InactivityAlertFragment extends BaseFragment {
    private CheckBox inactivity_alert_cb;
    private TextView inactivity_alert_interval_tv,inactivity_alert_start_tv,inactivity_alert_end_tv,inactivity_alert_steps_tv;
    private ToggleButton inactivity_alert_su_tb,inactivity_alert_m_tb,inactivity_alert_tu_tb,inactivity_alert_w_tb,inactivity_alert_th_tb,inactivity_alert_f_tb
            ,inactivity_alert_sa_tb;
    private String mac="";
    private BandSettingDB mBandSettingDB;
    private boolean isInactivitySwON;
    private OneWheelDialogFragment mOneWheelDialogFragment;
    private WeightWheelDialogFragment weightWheelDialogFragment;
    private boolean mInactivity;
    private int mInterval,mIntervalCache;
    private int mStart,mStartCacheH,mStartCacheM;
    private int mEnd,mEndCacheH,mEndCacheM;
    private int mSteps,mStepsCache;
    private String mFrequency;
    private boolean isInactivity=false,isStart=false;
    private static InactivityAlertFragment instance;
    public static InactivityAlertFragment getInstance(){
        if(instance==null){
            instance=new InactivityAlertFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.inactivity_alert_layout;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        if(bundle!=null){
            mac=bundle.getString("mac");
        }
        mBandSettingDB= PDB.INSTANCE.getBandSettingDB(mac);
        mInactivity= mBandSettingDB.isInactivity();
        mInterval=mBandSettingDB.getInterval();
        mStart=mBandSettingDB.getStart();
        mEnd=mBandSettingDB.getEnd();
        mSteps=mBandSettingDB.getSteps();
        mFrequency=mBandSettingDB.getFrequency();
        mIntervalCache=mInterval;
        mStartCacheH=mStart/60;
        mStartCacheM=mStart%60;
        mEndCacheH=mEnd/60;
        mEndCacheM=mEnd%60;
        mStepsCache=mSteps;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        inactivity_alert_cb=findViewById(R.id.inactivity_alert_cb);
        inactivity_alert_interval_tv=findViewById(R.id.inactivity_alert_interval_tv);
        inactivity_alert_start_tv=findViewById(R.id.inactivity_alert_start_tv);
        inactivity_alert_end_tv=findViewById(R.id.inactivity_alert_end_tv);
        inactivity_alert_steps_tv=findViewById(R.id.inactivity_alert_steps_tv);

        inactivity_alert_su_tb=findViewById(R.id.inactivity_alert_su_tb);
        inactivity_alert_m_tb=findViewById(R.id.inactivity_alert_m_tb);
        inactivity_alert_tu_tb=findViewById(R.id.inactivity_alert_tu_tb);
        inactivity_alert_w_tb=findViewById(R.id.inactivity_alert_w_tb);
        inactivity_alert_th_tb=findViewById(R.id.inactivity_alert_th_tb);
        inactivity_alert_f_tb=findViewById(R.id.inactivity_alert_f_tb);
        inactivity_alert_sa_tb=findViewById(R.id.inactivity_alert_sa_tb);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Save":
                LogUtils.i("save");
                showSynDialog();
                break;
        }
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        showView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void initListener() {
        super.initListener();
        inactivity_alert_interval_tv.setOnClickListener(this);
        inactivity_alert_start_tv.setOnClickListener(this);
        inactivity_alert_end_tv.setOnClickListener(this);
        inactivity_alert_steps_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inactivity_alert_interval_tv:
                LogUtils.i("inactivity_alert_interval_tv");
                isInactivity=true;
                int inIndex=0;
                inIndex= PublicData.arrAlertInterval.indexOf(timeIntToS(mInterval));
                mOneWheelDialogFragment= OneWheelDialogFragment.newInstance(PublicData.arrAlertInterval, mOnSelectListener,this);
                mOneWheelDialogFragment.setSelected(inIndex);
                mOneWheelDialogFragment.show(getChildFragmentManager(),"inactivity_alert_interval_tv");
                break;
            case R.id.inactivity_alert_start_tv:
                LogUtils.i("inactivity_alert_start_tv");
                isStart=true;
                int startLIndex=0,startRIndex=0;
                if(mStartCacheH<10){
                    startLIndex= PublicData.preset_sleep_time_h.indexOf("0"+mStartCacheH+"");

                }else{
                    startLIndex= PublicData.preset_sleep_time_h.indexOf(mStartCacheH+"");

                }
                if(mStartCacheM<10){
                    startRIndex= PublicData.preset_sleep_time_m.indexOf("0"+mStartCacheM+"");
                }else{
                    startRIndex= PublicData.preset_sleep_time_m.indexOf(mStartCacheM+"");
                }
                weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.preset_sleep_time_h, PublicData.preset_sleep_time_m
                        ,LeftSelectLin ,RightSelectLin,this);
                weightWheelDialogFragment.setSelected(startLIndex,startRIndex);
                weightWheelDialogFragment.show(getChildFragmentManager(),"ri");
                break;
            case R.id.inactivity_alert_end_tv:
                LogUtils.i("inactivity_alert_end_tv");
                isStart=false;
                int endLIndex=0,endRIndex=0;
                if(mEndCacheH<10){
                    endLIndex= PublicData.preset_sleep_time_h.indexOf("0"+mEndCacheH+"");
                }else{
                    endLIndex= PublicData.preset_sleep_time_h.indexOf(mEndCacheH+"");
                }
                if(mEndCacheM<10){
                    endRIndex= PublicData.preset_sleep_time_m.indexOf("0"+mEndCacheM+"");

                }else{
                    endRIndex= PublicData.preset_sleep_time_m.indexOf(mEndCacheM+"");

                }
                weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.preset_sleep_time_h, PublicData.preset_sleep_time_m
                        ,LeftSelectLin ,RightSelectLin,this);
                weightWheelDialogFragment.setSelected(endLIndex,endRIndex);
                weightWheelDialogFragment.show(getChildFragmentManager(),"ri");

                break;
            case R.id.inactivity_alert_steps_tv:
                LogUtils.i("inactivity_alert_steps_tv");
                isInactivity=false;
                int stepsIndex=0;
                stepsIndex=PublicData.arrStep.indexOf(mSteps+"");
                mOneWheelDialogFragment= OneWheelDialogFragment.newInstance(PublicData.arrStep, mOnSelectListener,this);
                mOneWheelDialogFragment.setSelected(stepsIndex);
                mOneWheelDialogFragment.show(getChildFragmentManager(),"inactivity_alert_interval_tv");
                break;
            case R.id.one_wheel_done_tv:
                LogUtils.i("one_wheel_done_tv");
                if(mOneWheelDialogFragment!=null){
                    mOneWheelDialogFragment.dismiss();
                }
                if(isInactivity){
                    mInterval= mIntervalCache;
                    inactivity_alert_interval_tv.setText(timeIntToS(mInterval));
                }else{
                    mSteps=mStepsCache;
                    inactivity_alert_steps_tv.setText(mSteps+" "+getString(R.string.inactivity_alert_steps_tv));

                }

                break;
            case R.id.one_wheel_cancel_tv:
                LogUtils.i("one_wheel_cancel_tv");
                if(mOneWheelDialogFragment!=null){
                    mOneWheelDialogFragment.dismiss();
                }
                if(isInactivity){
                    mIntervalCache= mInterval;
                }else{
                    mStepsCache=mSteps;

                }
                break;
            case R.id.wheel_done_tv:
                LogUtils.i("wheel_done_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                }
                if(isStart){
                    mStart= mStartCacheH*60+mStartCacheM;
                    setStartView();
                }else{
                    mEnd= mEndCacheH*60+mEndCacheM;
                    setEndTime();

                }
                break;

            case R.id.wheel_cancel_tv:
                LogUtils.i("wheel_cancel_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                }
                if(isStart){
                    mStartCacheH=mStart/60;
                    mStartCacheM=mStart%60;
                }else{
                    mEndCacheH=mEnd/60;
                    mEndCacheM=mEnd%60;
                }
                break;


        }
    }
    PickerView.onSelectListener mOnSelectListener = new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的是="+text);
            if(isInactivity){
                mIntervalCache=timeSToInt(text);
            }else{
                mStepsCache=new Integer(text);
            }

        }
    };
    public PickerView.onSelectListener LeftSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text);
            if(isStart){
                mStartCacheH=new Integer(text);
            }else{
                mEndCacheH=new Integer(text);
            }
        }
    };
    public PickerView.onSelectListener RightSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text);
            if(isStart){
                mStartCacheM=new Integer(text);
            }else{
                mEndCacheM=new Integer(text);
            }
        }
    };

    /**
     * 初始化界面
     */
    private void showView(){
        inactivity_alert_cb.setChecked(mInactivity);
        inactivity_alert_interval_tv.setText(timeIntToS(mInterval));
        setStartView();
        setEndTime();
        inactivity_alert_steps_tv.setText(mSteps+" "+getString(R.string.inactivity_alert_steps_tv));
        setWeek();

    }

    /**
     * 设置开始时间界面
     */
    private void setStartView(){
        String AM_PM=getString(R.string.am_tv);
        int h=mStartCacheH;
        if(mStartCacheH>=12){
            AM_PM=getString(R.string.pm_tv);
            if(mStartCacheH!=12){
                h=mStartCacheH-12;
            }
        }

        if(h<10){
            if(mStartCacheM<10){
                inactivity_alert_start_tv.setText("0"+h+":0"+mStartCacheM+" "+AM_PM);

            }else{
                inactivity_alert_start_tv.setText("0"+h+":"+mStartCacheM+" "+AM_PM);
            }
        }else{
            if(mStartCacheM<10){
                inactivity_alert_start_tv.setText(h+":0"+mStartCacheM+" "+AM_PM);

            }else{
                inactivity_alert_start_tv.setText(h+":"+mStartCacheM+" "+AM_PM);

            }
        }
    }
    /**
     * 设置结束时间界面
     */
    private void setEndTime(){
        String AM_PM=getString(R.string.am_tv);
        int h=mEndCacheH;
        if(mEndCacheH>=12){
            AM_PM=getString(R.string.pm_tv);
            if(mEndCacheH!=12){
                h=mEndCacheH-12;
            }
        }

        if(h<10){
            if(mEndCacheM<10){
                inactivity_alert_end_tv.setText("0"+h+":0"+mEndCacheM+" "+AM_PM);

            }else{
                inactivity_alert_end_tv.setText("0"+h+":"+mEndCacheM+" "+AM_PM);
            }
        }else{
            if(mEndCacheM<10){
                inactivity_alert_end_tv.setText(h+":0"+mEndCacheM+" "+AM_PM);

            }else{
                inactivity_alert_end_tv.setText(h+":"+mEndCacheM+" "+AM_PM);

            }
        }
    }

    /**
     * 设置周期界面
     */
    private void setWeek() {
        if (mFrequency.equals(null) || mFrequency.equals("")) {
            return;
        }
        String weeks1 = mFrequency.trim().substring(0,1);
        String weeks2 = mFrequency.trim().substring(1,2);
        String weeks3 = mFrequency.trim().substring(2,3);
        String weeks4 = mFrequency.trim().substring(3,4);
        String weeks5 = mFrequency.trim().substring(4,5);
        String weeks6 = mFrequency.trim().substring(5,6);
        String weeks7 = mFrequency.trim().substring(6,7);
        LogUtils.i("","weeks1="+weeks1);
        LogUtils.i("","weeks2="+weeks2);
        LogUtils.i("","weeks3="+weeks3);
        LogUtils.i("","weeks4="+weeks4);
        LogUtils.i("","weeks5="+weeks5);
        LogUtils.i("","weeks6="+weeks6);
        LogUtils.i("","weeks7="+weeks7);
        if (weeks7.equals("1")) {
            inactivity_alert_m_tb.setChecked(true);
        } else {
            inactivity_alert_m_tb.setChecked(false);

        }
        if (weeks6.equals("1")) {
            inactivity_alert_tu_tb.setChecked(true);
        } else {
            inactivity_alert_tu_tb.setChecked(false);

        }
        if (weeks5.equals("1")) {
            inactivity_alert_w_tb.setChecked(true);
        } else {
            inactivity_alert_w_tb.setChecked(false);

        }
        if (weeks4.equals("1")) {
            inactivity_alert_th_tb.setChecked(true);
        } else {
            inactivity_alert_th_tb.setChecked(false);

        }
        if (weeks3.equals("1")) {
            inactivity_alert_f_tb.setChecked(true);
        } else {
            inactivity_alert_f_tb.setChecked(false);

        }
        if (weeks2 .equals("1")) {
            inactivity_alert_sa_tb.setChecked(true);
        } else {
            inactivity_alert_sa_tb.setChecked(false);

        }
        if (weeks1 .equals("1")) {
            inactivity_alert_su_tb.setChecked(true);
        } else {
            inactivity_alert_su_tb.setChecked(false);

        }

    }


    /**
     * int的时间分钟变成1h30mins形式的string
     * @param mins
     * @return
     */
    private String timeIntToS(int mins){
        StringBuilder timeS = new StringBuilder();

        if(mins/60>0){
            timeS.append(mins/60+"h");
        }
        if(mins%60>0){
            timeS.append(mins%60+"mins");
        }
        return timeS.toString();
    }

    /**
     * string的时间变成int的时间分钟
     * @param timeS
     * @return
     */
    private int timeSToInt(String timeS){
        int mins=0;
        int intervalH=0;
        int intervalM=0;
        int indexH=0,indexM=0;
        if(timeS.indexOf("h")>0){
            indexH=timeS.indexOf("h");
            intervalH = new Integer(timeS.substring(0, indexH));
            if(timeS.indexOf("m")>0){
                indexM=timeS.indexOf("m");
                intervalM = new Integer(timeS.substring(indexH + 1,indexM ));
            }
        }else if(timeS.indexOf("m")>0){
            indexM=timeS.indexOf("m");
            intervalM = new Integer(timeS.substring(0, indexM));
        }
        mins=intervalH*60+intervalM;
        return mins;
    }

    /**
     * 弹同步到设备的框
     */
    private void showSynDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setPositiveButton(getString(android.R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //yes 去同步
                        synData();
                    }
                });
        builder.setNegativeButton(getString(android.R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //no 不用处理

                    }
                });

        builder.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        builder.setMessage(getString(R.string.syn_to_device)).setTitle(
                getString(R.string.app_name));
        AlertDialog b1 = builder.create();
        b1.show();
    }

    /**
     * 同步数据到设备
     */
    private void synData(){
        byte[] bytes=new byte[]{0x6e,0x01,0x43,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,(byte)0x8f};
        byte byte4=getPeriodic();
        LogUtils.i("byte4="+byte4);
        bytes[4]=byte4;//周期和开关
        byte[] byte5= NumberUtils.INSTANCE.intToByteArray(mInterval,1);
        bytes[5]=byte5[0];//频率

        bytes[6]=(byte)mStartCacheH;//开始 时
        bytes[7]=(byte)mStartCacheM;//开始 分
        bytes[8]=(byte)mEndCacheH;//结束 时
        bytes[9]=(byte)mEndCacheM;//结束 分
        byte[] byte10= NumberUtils.INSTANCE.intToByteArray(mSteps,2);
        bytes[10]=byte10[0];//步数
        bytes[11]=byte10[1];//步数
        LogUtils.i("synData="+NumberUtils.INSTANCE.binaryToHexString(bytes));
        BluetoothUtils.INSTANCE.setAlert(mPvBluetoothCallback,mac,bytes);
    }
    PVBluetoothCallback mPvBluetoothCallback=new PVBluetoothCallback() {
        @Override
        public void onSuccess(Object[] objects, int len, int dataType, String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("len="+len);
            LogUtils.i("dataType="+dataType);
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            if(objects!=null){
                LogUtils.i("objects="+objects[0].toString());
            }
            switch (bluetoothCommandType){
                case L28T_SET_ALERT:
                    LogUtils.i("设置久坐成功");
//                    mBandSettingDB
                    ContentValues values =new ContentValues();
                    values.put("inactivity",mInactivity);
                    values.put("interval",mInterval);
                    values.put("start",mStart);
                    values.put("end",mEnd);
                    values.put("steps",mSteps);
                    values.put("frequency",mFrequency);
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            LogUtils.i("设置久坐失败");
//            timeFormat=mBandSettingDB.getTiem();
//            setView(timeFormat,false);
            showToast("失败");
        }
    };
    /**
     * 获取周期
     * @return
     */
    private byte getPeriodic(){
        int v1 = 0;
        StringBuilder sb = new StringBuilder();
        if (inactivity_alert_m_tb.isChecked()) {//inactivity_alert_m_tb
            v1 |= 1;
            sb.append('1');
        } else sb.append('0');

        if (inactivity_alert_tu_tb.isChecked()) {//inactivity_alert_tu_tb
            sb.append('1');
            v1 |= 1 << 1;
        } else sb.append('0');

        if (inactivity_alert_w_tb.isChecked()) {//inactivity_alert_w_tb
            sb.append('1');
            v1 |= 1 << 2;
        } else sb.append('0');

        if (inactivity_alert_th_tb.isChecked()) {//inactivity_alert_th_tb
            sb.append('1');
            v1 |= 1 << 3;
        } else sb.append('0');

        if (inactivity_alert_f_tb.isChecked()) {//inactivity_alert_f_tb
            sb.append('1');
            v1 |= 1 << 4;
        } else sb.append('0');

        if (inactivity_alert_sa_tb.isChecked()) {//inactivity_alert_sa_tb
            sb.append('1');
            v1 |= 1 << 5;
        } else sb.append('0');

        if (inactivity_alert_su_tb.isChecked()) {//inactivity_alert_su_tb
            sb.append('1');
            v1 |= 1 << 6;
        } else sb.append('0');
        isInactivitySwON = inactivity_alert_cb.isChecked();
        if (isInactivitySwON) v1 |= 1 << 7;
        mFrequency=sb.toString();
        return (byte)v1;
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putString("mac",mac);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_SETTING_FRAGMENT,bundle);
        return true;
    }
}
