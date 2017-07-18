package cn.appscomm.presenter.implement;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import cn.appscomm.bluetoothscan.interfaces.IBluetoothScanResultCallBack;
import cn.appscomm.ota.implement.MOta;
import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.ota.interfaces.PMOtaCall;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.interfaces.PVBluetoothScanCall;
import cn.appscomm.presenter.interfaces.PVOtaCall;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/5/3
 * 说明：
 */

public enum POta implements PVOtaCall {
    INSTANCE {

    };

    private static final String TAG = POta.class.getSimpleName();
    private PVBluetoothScanCall pvBluetoothScanCall = PBluetoothScan.INSTANCE;
    private PVBluetoothCall pvBluetoothCall = PBluetooth.INSTANCE;
    private PMOtaCall pmOtaCall = MOta.INSTANCE;
    private PVSPCall pvspCall = PSP.INSTANCE;

    @Override
    public void update(final String dfuName, final String touchPanelPath, final String heartRatePath, final String freescalePath, final String picturePath, final String languagePath, final String nordicPath, final IUpdateProgressCallBack iUpdateProgressCallBack, String... macs) {
        if (macs == null || macs.length == 0) {
            String mac = pvspCall.getMAC();
            if (TextUtils.isEmpty(mac)) return;
            macs = new String[]{mac};
        }
        if ((TextUtils.isEmpty(dfuName)) || (TextUtils.isEmpty(touchPanelPath) && TextUtils.isEmpty(heartRatePath) && TextUtils.isEmpty(freescalePath) && TextUtils.isEmpty(picturePath) && TextUtils.isEmpty(languagePath) && TextUtils.isEmpty(nordicPath))) {
            if (iUpdateProgressCallBack != null)
                iUpdateProgressCallBack.updateResult(false);
            LogUtil.i(TAG, "dfuName为空 或 各文件路径都为空");
            return;
        }
        pvBluetoothCall.enterUpdateMode(touchPanelPath, heartRatePath, freescalePath, null, macs);
        pvBluetoothScanCall.startScan(new IBluetoothScanResultCallBack() {
            @Override
            public void onLeScan(BluetoothDevice device, int signalStrength) {
                String deviceName = device.getName();
                LogUtil.i(TAG, "空中升级 扫描回调中:deviceName(" + deviceName + ") mac(" + device.getAddress() + ") 需要升级的dfuName(" + dfuName + ")");
                if (!TextUtils.isEmpty(deviceName) && deviceName.equals(dfuName)) {
                    LogUtil.i(TAG, "找到需要升级的设备:" + deviceName + " mac : " + device.getAddress());
                    pvBluetoothScanCall.stopScan();
                    pmOtaCall.update(device.getAddress(), touchPanelPath, heartRatePath, freescalePath, picturePath, languagePath, nordicPath, iUpdateProgressCallBack);
                }
            }

            @Override
            public void onStopScan() {
            }
        });
    }

    @Override
    public void updateApollo(final String dfuName, final String touchPanelPath, final String heartRatePath, final String picturePath, final String languagePath, final String apolloPath, final IUpdateProgressCallBack iUpdateProgressCallBack, String... macs) {
        if (macs == null || macs.length == 0) {
            String mac = pvspCall.getMAC();
            if (TextUtils.isEmpty(mac)) return;
            macs = new String[]{mac};
        }
        if ((TextUtils.isEmpty(dfuName)) || (TextUtils.isEmpty(touchPanelPath) && TextUtils.isEmpty(heartRatePath) && TextUtils.isEmpty(picturePath) && TextUtils.isEmpty(languagePath) && TextUtils.isEmpty(apolloPath))) {
            if (iUpdateProgressCallBack != null)
                iUpdateProgressCallBack.updateResult(false);
            LogUtil.i(TAG, "dfuName为空 或 各文件路径都为空");
            return;
        }
        pvBluetoothCall.enterUpdateMode(null, macs);
        pvBluetoothScanCall.startScan(new IBluetoothScanResultCallBack() {
            @Override
            public void onLeScan(BluetoothDevice device, int signalStrength) {
                String deviceName = device.getName();
                LogUtil.i(TAG, "空中升级 扫描回调中:deviceName(" + deviceName + ") mac(" + device.getAddress() + ") 需要升级的dfuName(" + dfuName + ")");
                if (!TextUtils.isEmpty(deviceName) && deviceName.equals(dfuName)) {
                    LogUtil.i(TAG, "找到需要升级的设备:" + deviceName + " mac : " + device.getAddress());
                    pvBluetoothScanCall.stopScan();
                    pmOtaCall.updateApollo(device.getAddress(), touchPanelPath, heartRatePath, picturePath, languagePath, apolloPath, iUpdateProgressCallBack);
                }
            }

            @Override
            public void onStopScan() {
            }
        });
    }

    @Override
    public void update(String dfuName, int updateType, IUpdateProgressCallBack iUpdateProgressCallBack) {

    }
}
