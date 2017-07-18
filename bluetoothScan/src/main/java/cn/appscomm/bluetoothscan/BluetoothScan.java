package cn.appscomm.bluetoothscan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.util.LogUtil;

/**
 * Created by Administrator on 2017/2/14.
 */

public enum BluetoothScan {
    INSTANCE;

    private static final String TAG = BluetoothScan.class.getSimpleName();
    private Set<IBluetoothScanResultCallBack> iBluetoothScanResultCallBackSet = new HashSet<>();
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;

    private boolean init() {
        if (mBluetoothManager == null) {
            mBluetoothManager = ((BluetoothManager) BluetoothScanAppContext.INSTANCE.getContext().getSystemService(Context.BLUETOOTH_SERVICE));
            if (mBluetoothManager == null) {
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        return this.mBluetoothAdapter != null;
    }

    /**
     * 增加蓝牙扫描结果回调
     *
     * @param iBluetoothScanResultCallBack 要新增的蓝牙扫描结果回调
     */
    public void addBluetoothScanResultCallBack(IBluetoothScanResultCallBack iBluetoothScanResultCallBack) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            BluetoothScanEx.INSTANCE.addBluetoothScanResultCallBack(iBluetoothScanResultCallBack);
        } else {
            iBluetoothScanResultCallBackSet.add(iBluetoothScanResultCallBack);
        }
    }

    public boolean startScan(IBluetoothScanResultCallBack bluetoothScanResultCallBack) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            return BluetoothScanEx.INSTANCE.startScan(bluetoothScanResultCallBack);
        } else {
            if (!init()) return false;
            iBluetoothScanResultCallBackSet.add(bluetoothScanResultCallBack);
            return scanLeDevice(true);
        }
    }

    public void stopScan() {
        if (!init()) return;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            BluetoothScanEx.INSTANCE.stopScan();
        } else {
            if (iBluetoothScanResultCallBackSet != null && iBluetoothScanResultCallBackSet.size() > 0) {
                for (IBluetoothScanResultCallBack iBluetoothScanResultCallBack : iBluetoothScanResultCallBackSet) {
                    iBluetoothScanResultCallBack.onStopScan();
                }
                iBluetoothScanResultCallBackSet.clear();
            }
            scanLeDevice(false);
        }
    }

    private boolean scanLeDevice(boolean enable) {
        LogUtil.i(TAG, "BluetoothScan 处理扫描");
        if (enable) {
            if (mScanning)
                return true;
            boolean flag = false;
            for (int i = 0; i < 30; i++) {
                flag = mBluetoothAdapter.startLeScan(mLeScanCallback);
                LogUtil.e(TAG, "当前为第" + i + "次开启扫描...flag : " + flag);
                if (flag) {
                    break;
                }
            }
            mScanning = flag;
        } else {
            if (!mScanning)
                return true;
            if (mBluetoothAdapter != null && mLeScanCallback != null)
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
        return mScanning;
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (iBluetoothScanResultCallBackSet != null && iBluetoothScanResultCallBackSet.size() > 0) {
                for (IBluetoothScanResultCallBack iBluetoothScanResultCallBack : iBluetoothScanResultCallBackSet) {
                    iBluetoothScanResultCallBack.onLeScan(device, rssi);
                }
            }
        }
    };

}
