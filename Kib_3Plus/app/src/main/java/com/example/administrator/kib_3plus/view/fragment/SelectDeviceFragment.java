package com.example.administrator.kib_3plus.view.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.ScanBleDeviceAdapter;
import com.example.administrator.kib_3plus.ScanDeviceModel;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.http.mode.AddMenberMode;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.ArrayList;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.presenter.implement.PBluetoothScan;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;

import static android.media.CamcorderProfile.get;

/**
 * Created by cui on 2017/6/27.
 */

public class SelectDeviceFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView select_device_name_lv;
    private Button select_device_confirm_bt,select_device_refresh_bt;
    private ScanBleDeviceAdapter mScanBleDeviceAdapter;
    private ArrayList<ScanDeviceModel> mDeviceList = new ArrayList<>();
    private ArrayList<String> mScanDeviceList = new ArrayList<>();
    private Bundle mBundle;
    private static SelectDeviceFragment mSelectDeviceFragment;
    public static SelectDeviceFragment getInstance(){
        if(mSelectDeviceFragment==null){
            mSelectDeviceFragment=new SelectDeviceFragment();
        }
        return mSelectDeviceFragment;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.select_device_layout;
    }

    @Override
    public void initData() {
        super.initData();
        mBundle=getArguments();
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        select_device_refresh_bt=  findViewById(R.id.select_device_refresh_bt);
        select_device_confirm_bt=  findViewById(R.id.select_device_confirm_bt);
        select_device_name_lv=  findViewById(R.id.select_device_name_lv);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        mScanBleDeviceAdapter=new ScanBleDeviceAdapter(getContext(),mDeviceList);
        select_device_name_lv.setAdapter(mScanBleDeviceAdapter);
        select_device_name_lv.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
            startScan();

    }

    @Override
    public void onPause() {
        super.onPause();
        PBluetoothScan.INSTANCE.stopScan();
    }

    @Override
    public void initListener() {
        super.initListener();
        select_device_refresh_bt.setOnClickListener(this);
        select_device_confirm_bt.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {

        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SET_UP_DEVICE_FRAGMENT,mBundle);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_device_refresh_bt:
                PBluetoothScan.INSTANCE.stopScan();
                startScan();
                LogUtils.i("select_device_refresh_bt");
                break;
            case R.id.select_device_confirm_bt:
                LogUtils.i("select_device_confirm_bt");
                break;
            case R.id.select_device_name_lv:
                LogUtils.i("select_device_name_lv");
                break;
        }
    }
    /**
     * 开始扫描
     *
     */
    public void startScan(){
        mScanDeviceList.clear();
        mDeviceList.clear();
        if(BluetoothUtils.INSTANCE.isOpenBle()&&BluetoothUtils.INSTANCE.checkGPSPermission()&&BluetoothUtils.INSTANCE.checkGPSStatus(true)){
            PBluetoothScan.INSTANCE.startScan(scanCallBack);
        }
    }
    private IBluetoothScanResultCallBack scanCallBack=new IBluetoothScanResultCallBack() {
        @Override
        public void onLeScan(BluetoothDevice device, int signalStrength) {
            LogUtils.i("BluetoothDevicename="+device.getName()+"----?signalStrength="+signalStrength);
            //BASIC
            String deviceName=device.getName();
            String address=device.getAddress();
            if(TextUtils.isEmpty(deviceName))
                return;

            if(deviceName.contains("Lite jr ")){
                LogUtils.i("contains="+mScanDeviceList.contains(deviceName)+"--deviceName="+deviceName);
                if(!mScanDeviceList.contains(deviceName)){
                    mScanDeviceList.add(deviceName);
                    mDeviceList.add(new ScanDeviceModel(deviceName,address));
                    mScanBleDeviceAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onStopScan() {
            mScanDeviceList.clear();
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PBluetoothScan.INSTANCE.stopScan();
        ScanDeviceModel scanDeviceModel=  mDeviceList.get(position);
        scanDeviceModel=mDeviceList.get(position);
       AddMenberMode addMenberMode=(AddMenberMode) mBundle.getSerializable(SetUpNewDeviceFragment.MEMBER_DATA);
        Bundle bundle=new Bundle();
        bundle.putSerializable(SetUpNewDeviceFragment.SCAN_DEVICE_MODEL,scanDeviceModel);
        bundle.putSerializable(SetUpNewDeviceFragment.MEMBER_DATA,addMenberMode);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SET_UP_NEW_DEVICE_FRAGMENT,bundle);


    }

}
