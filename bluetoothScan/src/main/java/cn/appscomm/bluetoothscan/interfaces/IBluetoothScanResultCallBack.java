package cn.appscomm.bluetoothscan.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：蓝牙扫描回调接口
 */

public interface IBluetoothScanResultCallBack {

    /**
     * 扫描回调
     *
     * @param device         扫描到的蓝牙设备
     * @param signalStrength 扫描到的设备的信号强度
     */
    void onLeScan(BluetoothDevice device, int signalStrength);

    /**
     * 扫描停止
     */
    void onStopScan();
}
