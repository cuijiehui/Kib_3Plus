package com.example.administrator.kib_3plus.view.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.DialogFragment.WeightWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.PickerView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

/**
 * Created by cui on 2017/7/10.
 */

public class PresetSleepFragment extends BaseFragment {
    private String mac;
    private BandSettingDB mBandSettingDB;
    private boolean sleepAoto=false;
    private int awakeTime=645,bedTime=360;
    private ToggleButton preset_sleep_auto_tbt;
    private TextView preset_sleep_bed_tv,preset_sleep_awake_tv;
    private WeightWheelDialogFragment weightWheelDialogFragment;
    private String awakeTimeH,awakeTimeM,bedTimeH,bedTimeM;
    private boolean isAwake=false;
    private static PresetSleepFragment instance;
    public static PresetSleepFragment getInstance(){
        if(instance==null){
            instance=new PresetSleepFragment();
        }
        return instance;
    }
    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        mac= bundle.getString("mac");
        mBandSettingDB= PDB.INSTANCE.getBandSettingDB(mac);
        awakeTime=mBandSettingDB.getAwakeTime();
        bedTime=mBandSettingDB.getBedTime();
        sleepAoto=mBandSettingDB.isSleep();
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        preset_sleep_awake_tv= findViewById(R.id.preset_sleep_awake_tv);
        preset_sleep_bed_tv= findViewById(R.id.preset_sleep_bed_tv);
        preset_sleep_auto_tbt= findViewById(R.id.preset_sleep_auto_tbt);

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        preset_sleep_auto_tbt.setChecked(sleepAoto);
        preset_sleep_awake_tv.setText(getTimeS(awakeTime));
        preset_sleep_bed_tv.setText(getTimeS(bedTime));
    }
    private String getTimeS(int time){
        String timeS="";
        String AM_BM=getString(R.string.am_tv);
        String hTime="";
        String mTime="";
        if(time>=720){
            AM_BM=getString(R.string.pm_tv);
        }else{
            AM_BM=getString(R.string.am_tv);

        }
        hTime =time/60+"";
        mTime =time%60+"";
        if(time/60<10){
            hTime="0"+hTime;
        }else if(time/60>12){
            hTime=time/60-12+"";
        }
        if(time%60<10){
            mTime="0"+mTime;
        }
        timeS=hTime+":"+mTime+AM_BM;
        return timeS;
    }

    @Override
    public void initListener() {
        super.initListener();
        preset_sleep_auto_tbt.setOnClickListener(this);
        preset_sleep_awake_tv.setOnClickListener(this);
        preset_sleep_bed_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.preset_sleep_auto_tbt:
                LogUtils.i("preset_sleep_auto_tbt");
                break;
            case R.id.preset_sleep_awake_tv:
                LogUtils.i("preset_sleep_awake_tv");
                String awakeH="";
                String awakeM="";
                int rghtIntawake=0,leftIntawake=0;
                awakeH=awakeTime/60<10?"0"+awakeTime/60:""+awakeTime/60;
                awakeM=awakeTime%60<10?"0"+awakeTime%60:""+awakeTime%60;
                leftIntawake=PublicData.preset_sleep_time_h.indexOf(awakeH);
                rghtIntawake=PublicData.preset_sleep_time_m.indexOf(awakeM);
                awakeTimeH=awakeH;
                awakeTimeM=awakeM;
                isAwake=true;
                showDialogFragemnt(leftIntawake,rghtIntawake,PublicData.preset_sleep_time_h, PublicData.preset_sleep_time_m);
                break;
            case R.id.preset_sleep_bed_tv:
                LogUtils.i("preset_sleep_bed_tv");
                String bedH="";
                String bedM="";
                int rghtIntBed=0,leftIntBed=0;
                bedH=bedTime/60<10?"0"+bedTime/60:""+bedTime/60;
                bedM=bedTime%60<10?"0"+bedTime%60:""+bedTime%60;
                LogUtils.i("preset_sleep_bed_tvbedH="+bedH+"--bedM="+bedM);
                leftIntBed=PublicData.preset_sleep_time_h.indexOf(bedH);
                rghtIntBed=PublicData.preset_sleep_time_m.indexOf(bedM);
                LogUtils.i("rghtIntBed="+bedH+"--leftIntBed="+bedM);

                bedTimeH=bedH;
                bedTimeM=bedM;
                isAwake=false;
                showDialogFragemnt(leftIntBed,rghtIntBed,PublicData.preset_sleep_time_h, PublicData.preset_sleep_time_m);
                break;
            case R.id.wheel_cancel_tv:
                LogUtils.i("wheel_cancel_tv");

                dismissDialog();

                break;
            case R.id.wheel_done_tv:
                LogUtils.i("wheel_done_tv");
                dismissDialog();
                if(isAwake){
                    int aTH=new Integer(awakeTimeH);
                    int aTM=new Integer(awakeTimeM);
                    awakeTime=aTH*60+aTM;
                    preset_sleep_awake_tv.setText(getTimeS(awakeTime));
                }else{
                    int bTH=new Integer(bedTimeH);
                    int bTM=new Integer(bedTimeM);
                    bedTime=bTH*60+bTM;
                    preset_sleep_bed_tv.setText(getTimeS(bedTime));
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

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
    private void showSynDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setPositiveButton(getString(android.R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //yes
                        if(preset_sleep_auto_tbt.isChecked()){
                            sendBle(true);
                            sleepAoto=true;
                        }else{
                            sendBle(false);
                            sleepAoto=false;
                        }
                    }
                });
        builder.setNegativeButton(getString(android.R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //no

                    }
                });

        builder.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        builder.setMessage(getString(R.string.syn_to_device)).setTitle(
                getString(R.string.app_name));
        AlertDialog b1 = builder.create();
        b1.show();

    }

    private void showDialogFragemnt(int leftInt, int rghtInt, List<String> leftData,List<String> rghtData){
        weightWheelDialogFragment= WeightWheelDialogFragment.newInstance(leftData, rghtData
                ,LeftSelectLin ,RightSelectLin,this);
        if(leftInt>=0||rghtInt>=0){
            weightWheelDialogFragment.setSelected(leftInt,rghtInt);
        }
        weightWheelDialogFragment.show(getChildFragmentManager(),"PresetSleep");
    }
    private void dismissDialog(){
        if(weightWheelDialogFragment!=null){
            weightWheelDialogFragment.dismiss();
        }
    }
    public PickerView.onSelectListener LeftSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text);
            if(isAwake){
                awakeTimeH=text;
            }else{
                bedTimeH=text;

            }
        }
    };
    public PickerView.onSelectListener RightSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text);
            if(isAwake){
                awakeTimeM=text;
            }else{
                bedTimeM=text;

            }
        }
    };

    private void sendBle(boolean isAuto){
        byte[] bytes;
        int sleep_bedTimeH = 0,sleep_bedTimeM = 0,sleep_awakeTimeH = 0,sleep_awakeTimeM = 0;
        if(isAuto){
            sleep_bedTimeH=bedTime/60;
            sleep_bedTimeM=bedTime%60;
            sleep_awakeTimeH=awakeTime/60;
            sleep_awakeTimeM=awakeTime%60;
            bytes = new byte[]{0x6E, 0x01, (byte) 0x36, (byte) (sleep_bedTimeH), (byte) (sleep_bedTimeM)
                    , (byte) (sleep_awakeTimeH), (byte) (sleep_awakeTimeM), (byte) 0x8F};
        }else{
            bytes = new byte[]{0x6E, 0x01, (byte) 0x36, (byte) (0), (byte) (0), (byte) (0), (byte) (0), (byte) 0x8F};

        }
        BluetoothUtils.INSTANCE.setPresetSleep(mPvBluetoothCallback,mac,bytes);

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
                case L28T_SET_PRESET_SLEEP:
                    LogUtils.i("设置预设睡眠 "+sleepAoto);
                    LogUtils.i("设置预设睡眠 "+bedTime);
                    LogUtils.i("设置预设睡眠 "+awakeTime);
//                    mBandSettingDB.setTiem(timeFormat);
//                    ContentValues values=new ContentValues();
//                    values.put("tiem",timeFormat);
//                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                    ContentValues values=new ContentValues();
                    values.put("sleep",sleepAoto);
                    values.put("bedTime",bedTime);
                    values.put("awakeTime",awakeTime);
                    PDB.INSTANCE.updateBandSettingDB(values,mBandSettingDB.getMac());
                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            LogUtils.i("设置设置时间格式失败");
//            timeFormat=mBandSettingDB.getTiem();
//            setView(timeFormat,false);
            showToast("失败");
        }
    };

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.setting_preset_sleep_layout;
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putString("mac",mac);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_SETTING_FRAGMENT,bundle);
        return true;
    }
}
