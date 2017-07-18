package cn.appscomm.bluetoothscan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;

import java.util.HashSet;
import java.util.Set;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.util.LogUtil;

/**
 * Created by Administrator on 2017/2/14.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
enum BluetoothScanEx {
    INSTANCE;

    private static final String TAG = BluetoothScan.class.getSimpleName();
    private Set<IBluetoothScanResultCallBack> iBluetoothScanResultCallBackSet = new HashSet<>();
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    ScanSettings.Builder builder = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
    private boolean mScanning = false;

    private boolean init() {
        if (mBluetoothManager == null) {
            mBluetoothManager = ((BluetoothManager) BluetoothScanAppContext.INSTANCE.getContext().getSystemService(Context.BLUETOOTH_SERVICE));
            if (mBluetoothManager == null) {
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        LogUtil.i(TAG, "mBluetoothAdapter : " + (mBluetoothAdapter != null) + "getBluetoothLeScanner : " + (mBluetoothLeScanner != null) + " scanCallback : " + (scanCallback != null));
        return !(mBluetoothAdapter == null || mBluetoothLeScanner == null || scanCallback == null);
    }

    /**
     * 增加蓝牙扫描结果回调
     *
     * @param iBluetoothScanResultCallBack 要新增的蓝牙扫描结果回调
     */
    public void addBluetoothScanResultCallBack(IBluetoothScanResultCallBack iBluetoothScanResultCallBack) {
        iBluetoothScanResultCallBackSet.add(iBluetoothScanResultCallBack);
    }

    /**
     * 开始扫描
     *
     * @param bluetoothScanResultCallBack 扫描回调
     * @return 是否开始扫描成功
     */
    public boolean startScan(IBluetoothScanResultCallBack bluetoothScanResultCallBack) {
        LogUtil.i(TAG, "1、蓝牙新接口准备开始扫描");
        if (!init()) return false;
        LogUtil.i(TAG, "2、蓝牙新接口开始扫描");
        iBluetoothScanResultCallBackSet.add(bluetoothScanResultCallBack);
        return scanLeDevice(true);
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        if (!init()) return;
        if (iBluetoothScanResultCallBackSet != null && iBluetoothScanResultCallBackSet.size() > 0) {
            for (IBluetoothScanResultCallBack iBluetoothScanResultCallBack : iBluetoothScanResultCallBackSet) {
                iBluetoothScanResultCallBack.onStopScan();
            }
            iBluetoothScanResultCallBackSet.clear();
        }
        scanLeDevice(false);
    }

    // 进行开启扫描或停止扫描
    private boolean scanLeDevice(boolean enable) {
        LogUtil.i(TAG, "BluetoothScanEx " + (enable ? "开启扫描" : "关闭扫描") + " mScanning : " + mScanning);
        if (enable) {
            if (mScanning)
                return true;
            mBluetoothLeScanner.startScan(null, builder.build(), scanCallback);                     // 推荐使用该配置,扫描速度比较快
            mScanning = true;
        } else {
            if (!mScanning) return true;
            mBluetoothLeScanner.stopScan(scanCallback);
            mScanning = false;
        }
        return mScanning;
    }

    // 扫描回调
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (iBluetoothScanResultCallBackSet != null && iBluetoothScanResultCallBackSet.size() > 0) {
                for (IBluetoothScanResultCallBack iBluetoothScanResultCallBack : iBluetoothScanResultCallBackSet) {
                    iBluetoothScanResultCallBack.onLeScan(result.getDevice(), result.getRssi());
                }
            }
        }
    };
}
