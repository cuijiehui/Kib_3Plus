package com.example.administrator.kib_3plus.view.fragment;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.ScanDeviceModel;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.http.mode.AddMenberMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.Timer;
import java.util.TimerTask;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

import static com.example.administrator.kib_3plus.view.manage.ContentViewManage.SELECT_DEVICE_FRAGMENT;

/**
 * Created by cui on 2017/6/27.
 */

public class SetUpNewDeviceFragment extends BaseFragment {

    public static final String MEMBER_DATA="Member";
    public static final String SCAN_DEVICE_MODEL="ScanDeviceModel";
    public static final String NO_BIND="NoBind";

    private ImageView set_new_device_type_iv;
    private TextView set_new_device_hint;
    private Button set_new_device_bt;
    Bundle bundle;
    ScanDeviceModel scanDeviceModel;
    AddMenberMode addMenberMode;
    public static final int BIND_OK=200;
    public static final int BIND_FAIL=100;
    public static final int BIND_TIME_OUT=999;
    private boolean isOk=false;
    private final Timer timer = new Timer();
    private TimerTask task;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case BIND_OK:
                    //保存数据
                    timer.cancel();
                    DialogUtil.INSTANCE.closeProgressDialog();
                    //绑定结束，做保存操作
//                    ChildInfoDB childInfoDB=new ChildInfoDB(
//                            addMenberMode.getData().getId()
//                            ,addMenberMode.getData().getFamilyId()
//                            ,addMenberMode.getData().getName()
//                            ,addMenberMode.getData().getGender()
//                            ,addMenberMode.getData().getAge()
//                            ,addMenberMode.getData().getHeight()
//                            ,addMenberMode.getData().getWeight()
//                            ,addMenberMode.getData().getBrithday()
//                            ,addMenberMode.getData().getFavorite()
//                            ,addMenberMode.getData().getUrl()
//                            ,R.mipmap.bear
//                            ,true
//                            ,scanDeviceModel.deviceID
//                    ,0);
//                    LogUtils.i(PDB.INSTANCE.getChildInfo(addMenberMode.getData().getId()).toString());
                    LogUtils.i("addMenberMode.getData().getId()"+addMenberMode.getData().getId());
//                    LogUtils.i("childInfoDB.toString()"+childInfoDB.toString());
                    ContentValues contentValues =new ContentValues();
                    contentValues.put("mac",scanDeviceModel.deviceID);
                    PDB.INSTANCE.updateAllChildInfo(contentValues,addMenberMode.getData().getId());
//                    PDB.INSTANCE.addChildInfo(childInfoDB);
//                    LogUtils.i(PDB.INSTANCE.getChildInfo(addMenberMode.getData().getId()).toString());
                    isOk=true;
                    changeView(isOk);

                    break;
                case BIND_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");
                    timer.cancel();
                    isOk=false;
                    PBluetooth.INSTANCE.disConnect(scanDeviceModel.deviceID);

                    changeView(isOk);

                    break;
                case BIND_TIME_OUT:
                    timer.cancel();
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");
                    PBluetooth.INSTANCE.disConnect(scanDeviceModel.deviceID);
                    isOk=false;
                    changeView(isOk);

                    break;

            }
        }
    };;

    private static SetUpNewDeviceFragment mSetUpNewDeviceFragment;
    public static SetUpNewDeviceFragment getInstance(){
        if(mSetUpNewDeviceFragment==null){
            mSetUpNewDeviceFragment=new SetUpNewDeviceFragment();
        }
        return mSetUpNewDeviceFragment;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.set_up_new_device_layout;
    }

    @Override
    public void initData() {
        super.initData();
        bundle= getArguments();
        scanDeviceModel=(ScanDeviceModel) bundle.getSerializable(SCAN_DEVICE_MODEL);
        addMenberMode=(AddMenberMode)bundle.getSerializable(MEMBER_DATA);
        LogUtils.i("scanDeviceModel="+scanDeviceModel.toString());
        LogUtils.i("addMenberMode="+addMenberMode.toString());
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = BIND_TIME_OUT;
                mHandler.sendMessage(message);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void connect() {
        PBluetooth.INSTANCE.connect(scanDeviceModel.deviceID,false,false);
       BluetoothUtils.INSTANCE.getSoftwareVersion(mPvBluetoothCallback,scanDeviceModel.deviceID);
        DialogUtil.INSTANCE.logining(getContext()).show();
        timer.schedule(task, 120000);

    }

    public void changeView(boolean isOk){
        if(isOk){
            Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.device_bind_ok);
            set_new_device_type_iv.setImageBitmap(bitmap);
            set_new_device_hint.setText(getString(R.string.pairing_is_hint));

            set_new_device_bt.setText(getString(R.string.back_text_btn));
        }else{
            Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.device_bind_fail);
            set_new_device_type_iv.setImageBitmap(bitmap);
            set_new_device_hint.setText(getString(R.string.pairing_was_hint));
            set_new_device_bt.setText(getString(R.string.back_text_btn));
        }
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
                case L28T_SOFTWARE_VERSION:
                    LogUtils.i("获取L28T软件版本成功");
                    BluetoothUtils.INSTANCE.getWatvhID(mPvBluetoothCallback,scanDeviceModel.deviceID);

                    break;
                case L28T_WATCH_ID:
                    LogUtils.i("获取L28TwatchID成功");
                    BluetoothUtils.INSTANCE.setUID(mPvBluetoothCallback,scanDeviceModel.deviceID,addMenberMode.getData().getId());

                    break;
                case L28T_BIND_UID:
                    LogUtils.i("绑定Uid成功");
                    BluetoothUtils.INSTANCE.bindInfo(mPvBluetoothCallback,scanDeviceModel.deviceID, addMenberMode);

                    break;
                case L28T_BIND_TIME:
                    LogUtils.i("设置时间成功");
                    mHandler.sendEmptyMessage(BIND_OK);

                    break;
                case L28T_BIND_INFO:
                    LogUtils.i("设置个人信息成功");
                    BluetoothUtils.INSTANCE.setName(mPvBluetoothCallback,scanDeviceModel.deviceID,addMenberMode.getData().getName());
                    break;
                case L28T_SET_NAME_DATA:
                    LogUtils.i("设置名字");
                    BluetoothUtils.INSTANCE.setTime(mPvBluetoothCallback,scanDeviceModel.deviceID);

                    break;
            }
        }

        @Override
        public void onFail(String mac, BluetoothCommandType bluetoothCommandType) {
            LogUtils.i("mac="+mac);
            LogUtils.i("bluetoothCommandType="+bluetoothCommandType);
            mHandler.sendEmptyMessage(BIND_FAIL);
            PBluetooth.INSTANCE.clearSendCommand(mac);
            switch (bluetoothCommandType){
                case L28T_SOFTWARE_VERSION:
                    LogUtils.i("获取L28T软件版本失败");
                    break;
                case L28T_WATCH_ID:
                    LogUtils.i("获取L28TwatchID失败");
                    break;
                case L28T_BIND_UID:
                    LogUtils.i("绑定Uid失败");
                    break;
                case L28T_BIND_TIME:
                    LogUtils.i("设置时间失败");
                    break;
                case L28T_BIND_INFO:
                    LogUtils.i("设置个人信息失败");
                    break;
            }

        }
    };

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        set_new_device_type_iv=findViewById(R.id.set_new_device_type_iv);
        set_new_device_hint=findViewById(R.id.set_new_device_hint);
        set_new_device_bt=findViewById(R.id.set_new_device_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        set_new_device_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putSerializable(SetUpNewDeviceFragment.SCAN_DEVICE_MODEL,scanDeviceModel);
        bundle.putSerializable(SetUpNewDeviceFragment.MEMBER_DATA,addMenberMode);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_DEVICE_FRAGMENT,bundle);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_new_device_bt:
                LogUtils.i("set_new_device_bt");
                if(set_new_device_bt.getText().equals(getString(R.string.back_text_btn))){
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);
                }else{
                    connect();
                }
                break;
        }
    }
}
