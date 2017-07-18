package cn.appscomm.bluetooth;

import android.content.Intent;
import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import cn.appscomm.bluetooth.implement.RemoteControlSend;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;

import static android.R.attr.id;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙管理类
 * 1、蓝牙服务的开启和关闭
 * 2、蓝牙数据的统一发送(数据由BluetoothSend整理好)
 * 3、蓝牙数据的接收(使用总线来接收,需要解析就给BluetoothParse类来解析)
 */
public enum BluetoothManager {
    INSTANCE;

    private static final String TAG = BluetoothManager.class.getSimpleName();
    private BluetoothLeService mBluetoothLeService;                                                 // 蓝牙服务对象
    private final int MAX_RECONNECT_COUNT = 10;                                                     // 最大重连次数10，若超过则清空缓存

    // 重置数据，若为null则断开所有的设备，并清空所有的设备；若不为null，则断开该设备并清空发送命令
    private void resetData(AppsCommDevice appsCommDevice) {
        try {
            LogUtil.i(TAG, "重置数据...");
            if (mBluetoothLeService != null) {
                if (appsCommDevice == null) {
                    mBluetoothLeService.disconnectAll();
                    mBluetoothLeService = null;
                    BluetoothLeService.deviceMap.clear();
                } else {
                    appsCommDevice.disconnect();
                    appsCommDevice.bluetoothSend.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开或关闭EventBus
    private void setEventBus(boolean isOpen) {
        if ((isOpen && EventBus.getDefault().isRegistered(this)) || (!isOpen && !EventBus.getDefault().isRegistered(this)))
            return;
        if (isOpen) {
            EventBus.getDefault().register(this);
        } else {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 开启服务
     */
    public void startService() {
        setEventBus(true);
        Intent gattServiceIntent = new Intent(BluetoothAppContext.INSTANCE.getContext(), BluetoothLeService.class);
        BluetoothAppContext.INSTANCE.getContext().startService(gattServiceIntent);
    }

    /**
     * 重新连接设备
     *
     * @param mac 要连接的设备的MAC地址
     * @return 需要连接的mac地址不为空，可以重启服务
     */
    public boolean reConnect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        LogUtil.i(TAG, "重启BluetoothLeService服务...");
        if (TextUtils.isEmpty(mac)) {
            LogUtil.i(TAG, "传入的mac为空，无法重启服务");
            return false;
        }
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        if (appsCommDevice != null) {
            disConnect(mac);
            connect(appsCommDevice.mac, isSupportHeartRate,isSend03);
            return true;
        }
        return false;
    }


    /**
     * 断开连接
     *
     * @param mac 需要断开连接的设备
     */
    public void disConnect(String mac) {
        if (TextUtils.isEmpty(mac)) return;
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        if (appsCommDevice != null) {
            resetData(appsCommDevice);
        }
    }

    /**
     * 结束服务
     */
    public void endService() {
        resetData(null);
        setEventBus(false);
        Intent gattServiceIntent = new Intent(BluetoothAppContext.INSTANCE.getContext(), BluetoothLeService.class);
        BluetoothAppContext.INSTANCE.getContext().stopService(gattServiceIntent);
        LogUtil.i(TAG, "BluetoothLeService服务关闭");
    }

    /**
     * 手机是否连接设备
     *
     * @return true：已连接 false：未连接
     */
    public boolean isConnect(String mac) {
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        return appsCommDevice != null && appsCommDevice.mBluetoothDiscoverFlag;
    }

    /**
     * 蓝牙服务是否开启
     *
     * @return true：开启；false：关闭
     */
    public boolean isBluetoothLeServiceRunning() {
        return mBluetoothLeService != null;
    }

    /**
     * 连接设备
     *
     * @param mac                要连接设备的mac地址
     * @param isSupportHeartRate 是否支持心率
     */
    public void connect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        if (TextUtils.isEmpty(mac)) {
            LogUtil.i(TAG, "mac为空，不能连接");
            return;
        }
        if (mBluetoothLeService == null) {
            LogUtil.i(TAG, "mBluetoothLeService为null，但mac(" + mac + ")不为空，重启服务");
            startService();
            return;
        }
        if (!mBluetoothLeService.connect(mac, isSupportHeartRate,isSend03)) {
            LogUtil.i(TAG, "连接设备失败，重启服务");
            reConnect(mac, isSupportHeartRate,isSend03);
        }
    }

    /**
     * 发送数据
     *
     * @param mac 要发送数据的设备的mac地址
     */
    public boolean send(String mac) {
        if (TextUtils.isEmpty(mac)) return false;
        LogUtil.i(TAG, "要发送数据的设备的mac : " + mac);
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        if (appsCommDevice != null) {
            if (!appsCommDevice.mBluetoothDiscoverFlag) {
                LogUtil.e(TAG, "发送失败：已连接设备，但没有发现服务");
                return false;
            }
            if (appsCommDevice.isSendDataFlag) {
                LogUtil.e(TAG, "发送失败：有数据在发送中...");
                return false;
            }
            appsCommDevice.leaf = appsCommDevice.bluetoothSend.getSendCommand();
            if (appsCommDevice.leaf == null) {
                LogUtil.e(TAG, "发送失败：没有需要发送的数据");
                return false;
            }
            byte[] sendData=null;

            /**
             * 因为L28T的协议与新协议不一致，所以整合部分做区分
             */
            if(appsCommDevice.leaf.isL28T){
                sendData=appsCommDevice.leaf.getContent();
            }else{
                sendData = appsCommDevice.leaf.getSendData();

            }

            if (sendData != null && sendData.length > 0) {
                LogUtil.w(TAG, "发送：" + ParseUtil.byteArrayToHexString(sendData));
                appsCommDevice.isSendDataFlag = true;
                appsCommDevice.sendDataToDevice(sendData, appsCommDevice.leaf.getBluetoothSendType());
                return true;
            } else {
                LogUtil.e(TAG, "发送失败：Leaf整理为要发送的byte[]时有误");
            }
        }
        return false;
    }
    /**
     * 发送数据
     *
     * @param mac 要发送数据的设备的mac地址
     */
    public boolean send(String mac,boolean isL28T) {
        if (TextUtils.isEmpty(mac)) return false;
        LogUtil.i(TAG, "要发送数据的设备的mac : " + mac);
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        if (appsCommDevice != null) {
            if (!appsCommDevice.mBluetoothDiscoverFlag) {
                LogUtil.e(TAG, "发送失败：已连接设备，但没有发现服务");
                return false;
            }
            if (appsCommDevice.isSendDataFlag) {
                LogUtil.e(TAG, "发送失败：有数据在发送中...");
                return false;
            }
            appsCommDevice.leaf = appsCommDevice.bluetoothSend.getSendCommand();
            if (appsCommDevice.leaf == null) {
                LogUtil.e(TAG, "发送失败：没有需要发送的数据");
                return false;
            }
            byte[] sendData=null;
            LogUtil.e(TAG, "发送：是不是L28T"+isL28T);

            if(isL28T){
                sendData=appsCommDevice.leaf.getContent();
            }else{
                sendData = appsCommDevice.leaf.getSendData();

            }
            if (sendData != null && sendData.length > 0) {
                LogUtil.w(TAG, "发送：" + ParseUtil.byteArrayToHexString(sendData));
                appsCommDevice.isSendDataFlag = true;
                appsCommDevice.sendDataToDevice(sendData, appsCommDevice.leaf.getBluetoothSendType());
                return true;
            } else {
                LogUtil.e(TAG, "发送失败：Leaf整理为要发送的byte[]时有误");
            }
        }
        return false;
    }
    // 继续发送(内部使用)
    private void continueSend(AppsCommDevice appsCommDevice) {
        appsCommDevice.isSendDataFlag = false;
        send(appsCommDevice.mac);
    }

    // 蓝牙断开，断开次数大于10次，清空缓存
    private void doReconnect(AppsCommDevice appsCommDevice) {
        LogUtil.i(TAG, "蓝牙断连次数:" + (appsCommDevice.reconnectCount + 1));
        if (++appsCommDevice.reconnectCount > MAX_RECONNECT_COUNT) {
            appsCommDevice.bluetoothSend.clear();
            appsCommDevice.reconnectCount = 0;
        }
        connect(appsCommDevice.mac, appsCommDevice.isSupportHeartRate,appsCommDevice.isSend03);
    }

    // EventBus 事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBluetoothMessageHandle(BluetoothMessage event) {
        if (event != null && !TextUtils.isEmpty(event.msgType)) {

            switch (event.msgType) {

                case BluetoothMessage.START_SERVICE_SUCCESS:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 开启服务成功(" + event.msgType + ")--------------------------");
                    mBluetoothLeService = event.bluetoothLeService;
                    break;

                case BluetoothMessage.ACTION_GATT_CONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 已连接(" + event.msgType + ")----------------------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice != null) {
                            appsCommDevice.mBluetoothConnectFlag = true;
                            appsCommDevice.mBluetoothDiscoverFlag = false;
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_DISCONNECTED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 断开连接(" + event.msgType + ")-----------------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice != null) {
                            appsCommDevice.mBluetoothConnectFlag = false;
                            appsCommDevice.mBluetoothDiscoverFlag = false;
                            doReconnect(appsCommDevice);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_TIMEOUT:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 超时(" + event.msgType + ")----------------------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice != null) {
                            doCallBack(BluetoothCommandConstant.RESULT_CODE_FAIL, appsCommDevice);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_DISCOVERED:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 发现服务(" + event.msgType + ")----------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        LogUtil.i(TAG, "appsCommDevice : " + (appsCommDevice != null));
                        if (appsCommDevice != null) {
                            appsCommDevice.reconnectCount = 0;                                      // 重连次数清0
                            LogUtil.i(TAG, "appsCommDevice : " + appsCommDevice.mBluetoothConnectFlag);
                            if (appsCommDevice.mBluetoothConnectFlag) {                             // mBluetoothConnectFlag = true 说明有连接
                                appsCommDevice.mBluetoothDiscoverFlag = true;                       // 只有mmBluetoothConnectFlag为true，mBluetoothDiscoverFlag才可以为true
                                appsCommDevice.isSendDataFlag = false;                              // 连接并发现服务后，释放发送标志，以便可以继续发送数据
                                send(appsCommDevice.mac);                                           // 发送数据
                            } else {
                                appsCommDevice.mBluetoothDiscoverFlag = false;                      // 还没有CONNECTED状态，却有DISCOVERED状态，有问题
                                reConnect(appsCommDevice.mac, appsCommDevice.isSupportHeartRate,appsCommDevice.isSend03);   // 重启服务
                            }
                        }
                    }
                    for (Map.Entry<String, AppsCommDevice> entry : BluetoothLeService.deviceMap.entrySet()) {
                        LogUtil.i(TAG, "mac(" + entry.getKey() + ") appsCommDevice(" + entry.getValue() + ")");
                    }

                    break;

                case BluetoothMessage.ACTION_DATA_8002_AVAILABLE:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 接收到数据(" + event.msgType + ")------------------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice != null) {
                            appsCommDevice.mBluetoothConnectFlag = true;
                            appsCommDevice.mBluetoothDiscoverFlag = true;
                            if (appsCommDevice.leaf != null) {
                                BluetoothParse bluetoothParse = appsCommDevice.bluetoothParse;
                                byte[] bytes = null;
                                if(appsCommDevice.leaf.isL28T){
                                    bytes=bluetoothParse.proReceiveDataL28T(event.msgContent);
                                }else{
                                    bytes = bluetoothParse.proReceiveData(event.msgContent);
                                }
                                if (bytes == null) {
                                    LogUtil.i(TAG, "整理：接收到的数据有异常了，无法整理...");
                                    // TODO
                                    return;
                                }
                                if (bytes.length == 1) {
                                    switch (bytes[0]) {
                                        case BluetoothCommandConstant.RESULT_CODE_PROTOCOL_ERROR:   // 回调失败
                                            doCallBack(BluetoothCommandConstant.RESULT_CODE_ERROR, appsCommDevice);
                                            break;
                                        case BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE: // 继续接收
                                            return;
                                        case BluetoothCommandConstant.RESULT_CODE_EXCEPTION:        // 异常
                                            // TODO
                                            break;
                                    }
                                } else {
                                    LogUtil.i(TAG, "整条命令：" + ParseUtil.byteArrayToHexString(bytes));
                                    appsCommDevice.leaf.bluetoothVar = appsCommDevice.bluetoothVar;
                                    int ret = bluetoothParse.parseBluetoothData(bytes, appsCommDevice.leaf);
                                    if (ret == BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE) { // 继续接收命令
                                        LogUtil.i(TAG, "数据还没有接收完，继续接收数据...");
                                        return;
                                    }
                                    if (ret == BluetoothCommandConstant.RESULT_CODE_RE_SEND) {      // 命令需要重新发送
                                        continueSend(appsCommDevice);
                                        return;
                                    }
                                    doCallBack(ret, appsCommDevice);
                                }
                            }
                            continueSend(appsCommDevice);
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_DATA_8003_SEND_CALLBACK:
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice != null) {
                            if (appsCommDevice.leaf != null && !appsCommDevice.leaf.isActiveCommand()) { // 被动命令，设备不会发送数据，这里可以继续发送数据了
                                appsCommDevice.bluetoothSend.removeFirst();
                                continueSend(appsCommDevice);
                            }
                        }
                    }
                    break;

                case BluetoothMessage.ACTION_DATA_8004_AVAILABLE:                                   // 远程控制命令(音乐、拍照、寻找手机)
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 远程控制(" + event.msgType + ")------------------------------");
                    if (event.msgContent != null && event.msgContent.length > 0) {
                        RemoteControlSend.INSTANCE.parse(event.msgContent);
                    }
                    break;

                case BluetoothMessage.ACTION_GATT_REAL_TIME_HEART_RATE:
                    LogUtil.v(TAG, "----------------------蓝牙消息 : 实时心率(" + event.msgType + ")----------------------");
                    if (mBluetoothLeService != null) {
                        AppsCommDevice appsCommDevice = mBluetoothLeService.getDevice(event.bluetoothGatt);
                        if (appsCommDevice.bluetoothVar != null) {
                            appsCommDevice.bluetoothVar.realTimeHeartRateValue = event.msgContent[1] & 0xFF;
                            LogUtil.v(TAG, "心率值:" + appsCommDevice.bluetoothVar.realTimeHeartRateValue);
                        }
                    }
                    break;
            }
        }
    }

    // 回调结果给调用者(0:成功 1:失败 2:协议解析错误 3:数据还没有接收完 -1:没有进行解析命令 -2:大字节数组接收错误)，最后释放发送标志，并接续发送数据
    private void doCallBack(int result, AppsCommDevice appsCommDevice) {
        if (appsCommDevice.bluetoothSend.getSendDataSize() > 0) {
            appsCommDevice.bluetoothSend.removeFirst();
            IBluetoothResultCallback iBluetoothResultCallback = appsCommDevice.leaf.getIBluetoothResultCallback();
            if (iBluetoothResultCallback != null) {
                switch (result) {
                    case BluetoothCommandConstant.RESULT_CODE_SUCCESS:                              // 0、成功
                        LogUtil.i(TAG, "成功,准备回调!!!");
                        iBluetoothResultCallback.onSuccess(appsCommDevice.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_FAIL:                                 // 1、失败
                        LogUtil.i(TAG, "失败,准备回调!!!");
                        iBluetoothResultCallback.onFailed(appsCommDevice.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_PROTOCOL_ERROR:                       // 2、协议解析错误
                        LogUtil.i(TAG, "协议解析错误,准备回调!!!");
                        iBluetoothResultCallback.onFailed(appsCommDevice.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_ERROR:                                // -1、错误
                        LogUtil.i(TAG, "错误,重发并准备回调!!!");
                        iBluetoothResultCallback.onFailed(appsCommDevice.mac);
                        break;
                    case BluetoothCommandConstant.RESULT_CODE_LARGE_BYTES_ERROR:                     // -2、大字节数组接收错误
                        LogUtil.i(TAG, "大字节数组接收错误,准备回调!!!");
                        iBluetoothResultCallback.onFailed(appsCommDevice.mac);
                        break;
                }
            } else {
                LogUtil.i(TAG, "iBluetoothResultCallback 为null,不需要回调");
            }
        }
    }
}
