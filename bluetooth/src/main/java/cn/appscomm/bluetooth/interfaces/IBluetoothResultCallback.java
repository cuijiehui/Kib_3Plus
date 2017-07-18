package cn.appscomm.bluetooth.interfaces;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：蓝牙通信结果回调
 */
public interface IBluetoothResultCallback {

    /**
     * 发送蓝牙命令成功时回调
     *
     * @param mac 该命令对应的设备的mac地址
     */
    void onSuccess(String mac);

    /**
     * 发送蓝牙命令失败时回调
     *
     * @param mac 该命令对应的设备的mac地址
     */
    void onFailed(String mac);
}
