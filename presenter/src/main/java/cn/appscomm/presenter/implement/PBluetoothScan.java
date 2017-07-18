package cn.appscomm.presenter.implement;

import android.os.Handler;
import android.os.Looper;

import cn.appscomm.bluetoothscan.implement.MBluetoothScan;
import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.bluetoothscan.interfaces.PMBluetoothScanCall;
import cn.appscomm.presenter.interfaces.PVBluetoothScanCall;
import cn.appscomm.presenter.util.LogUtil;

/**
 * Created by Administrator on 2017/3/6.
 */
public enum PBluetoothScan implements PVBluetoothScanCall {
    INSTANCE;

    private String TAG = PBluetoothScan.class.getSimpleName();
    private Handler handler = new Handler(Looper.getMainLooper());
    public int MAX_SCAN_TIME = 30;                                                                  // 最大扫描时间为30秒，外部可以修改该值
    private int scanTime = 0;                                                                       // 扫描时间

    private PMBluetoothScanCall mCall = MBluetoothScan.INSTANCE;                                    // 用户调用M层的方法

    @Override
    public void startScan(IBluetoothScanResultCallBack callBack) {
        if (scanTime <= 0) {
            if (!mCall.startScan(callBack)) return;
            scanTime = MAX_SCAN_TIME;
            handler.postDelayed(scanTimeOutRunning, 1000);
        } else {
            LogUtil.i(TAG, "已经在扫描了...");
            scanTime = MAX_SCAN_TIME;
            mCall.addBluetoothScanResultCallBack(callBack);
        }
    }

    @Override
    public void stopScan() {
        handler.removeCallbacks(scanTimeOutRunning);
        scanTime = 0;
        mCall.stopScan();
    }

    // 扫描超时
    private Runnable scanTimeOutRunning = new Runnable() {
        @Override
        public void run() {
            if (--scanTime > 0) {
                handler.postDelayed(scanTimeOutRunning, 1000);
            } else {
                stopScan();
            }
        }
    };
}
