package cn.appscomm.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;
import android.os.Looper;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.UUID;

import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

/**
 * 作者：hsh
 * 日期：2017/5/10
 * 说明：设备类
 * 1、存储一些连接的状态及设备的信息
 * 2、发送数据
 * 3、断开连接
 */

public class AppsCommDevice {

    public String mac;                                                                              // 设备的mac地址
    public BluetoothGatt bluetoothGatt;                                                             // 设备的bluetoothGatt
    public boolean isSupportHeartRate;                                                              // 是否支持心率
    public LinkedList<NotificationInfo> notifications = new LinkedList<>();                         // 设备要开启监听的集合

    private Handler mHandler = new Handler(Looper.getMainLooper());                                 // Handler用于计时写回调超时
    public int sendTimeOutCount = 0;                                                                // 发送超时时间

    public boolean mBluetoothConnectFlag;                                                           // 是否已连接
    public boolean mBluetoothDiscoverFlag;                                                          // 是否已发现服务
    public int reconnectCount;                                                                      // 重连次数
    public boolean isSendDataFlag;                                                                  // 是否处于发送数据中
    public Leaf leaf;                                                                               // 当前发送的Leaf

    public BluetoothSend bluetoothSend = new BluetoothSend();                                       // 每个设备都有自己的BluetoothSend
    public BluetoothParse bluetoothParse = new BluetoothParse();                                    // 每个设备都有自己的BluetoothParse
    public BluetoothVar bluetoothVar = new BluetoothVar();                                          // 每个设备都有自己的BluetoothVar

    public static final int SEND_TYPE_8001 = 0;                                                     // 使用8001通道发送
    public static final int SEND_TYPE_8003 = 1;                                                     // 使用8003通道发送
    public boolean isSend03 = true;                                                                 // 是否发送03命令

    private byte[] sendBytes = null;                                                                // 发送的字节数组
    private int sendBytesPacketCount = 0;                                                           // 发送的包数
    private final int SEND_DATA_MAX_LEN = 20;                                                       // 一包发送数据的最大长度
    private final int SEND_DATA_TIMEOUT = 10;                                                       // 发送数据超时时间10秒

    public AppsCommDevice(String mac, BluetoothGatt bluetoothGatt, boolean isSupportHeartRate,boolean isSend03) {
        this.mac = mac;
        this.bluetoothGatt = bluetoothGatt;
        this.isSupportHeartRate = isSupportHeartRate;
        this.isSend03=isSend03;
    }

    public class NotificationInfo {
        public UUID service;
        public UUID characteristic;

        NotificationInfo(UUID service, UUID characteristic) {
            this.service = service;
            this.characteristic = characteristic;
        }
    }

    /**
     * 设置要开启的监听
     */
    public void setNotification() {
        notifications.clear();
        notifications.addLast(new NotificationInfo(BluetoothCommandConstant.UUID_SERVICE_BASE, BluetoothCommandConstant.UUID_CHARACTERISTIC_8002));
//        notifications.addLast(new NotificationInfo(BluetoothCommandConstant.UUID_SERVICE_EXTEND, BluetoothCommandConstant.UUID_CHARACTERISTIC_8004));
        if (isSupportHeartRate)
            notifications.addLast(new NotificationInfo(BluetoothCommandConstant.UUID_SERVICE_HEART_RATE, BluetoothCommandConstant.UUID_CHARACTERISTIC_HEART_RATE));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        sendBytes = null;
        sendBytesPacketCount = 0;
        sendTimeOutCount = 0;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        initData();
        try {
            if (bluetoothGatt != null) {
                bluetoothGatt.close();
                bluetoothGatt.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送数据到设备超时处理
    private Runnable checkSendTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            if (sendTimeOutCount > 0) {
                LogUtil.i("service_timeout", "距离上次发送数据，已经用时:" + (SEND_DATA_TIMEOUT - sendTimeOutCount) + "秒...!!!");
                if (--sendTimeOutCount <= 0) {
                    LogUtil.i("service_timeout", "---发送的数据已超时" + SEND_DATA_TIMEOUT + "秒");
                    sendTimeOutCount = 0;
                    disconnect();
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothGatt, null));
                    return;
                }
                if (mHandler != null)
                    mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
            }
        }
    };

    /**
     * 发送03命令到设备
     *
     * @param service        服务
     * @param characteristic 特征值
     */
    public void send03ToDevice(UUID service, UUID characteristic) {
        LogUtil.i("BluetoothLeService", "==>>命令已经发送到设备了，写03到设备结束...");
        if (bluetoothGatt != null) {
            BluetoothGattCharacteristic bluetoothgattcharacteristic = bluetoothGatt.getService(service).getCharacteristic(characteristic);
            bluetoothgattcharacteristic.setValue(new byte[]{0x03});
            bluetoothGatt.writeCharacteristic(bluetoothgattcharacteristic);
        }
    }

    /**
     * 发送数据到设备
     *
     * @param bytes    要发送的数据
     * @param sendType 发送类型
     */
    public void sendDataToDevice(byte[] bytes, int sendType) {
        sendBytes = null;
        sendBytesPacketCount = 0;
        if (bytes != null) {
            byte[] firstBytes = null;
            if (bytes.length > SEND_DATA_MAX_LEN) {
                firstBytes = new byte[SEND_DATA_MAX_LEN];
                sendBytes = bytes;
                sendBytesPacketCount = sendBytes.length / SEND_DATA_MAX_LEN + (sendBytes.length % SEND_DATA_MAX_LEN == 0 ? 0 : 1);
                System.arraycopy(sendBytes, 0, firstBytes, 0, SEND_DATA_MAX_LEN);
                LogUtil.i("test_sendLargeBytes", "大字节数组发送第一包:" + ParseUtil.byteArrayToHexString(firstBytes) + " 共" + sendBytesPacketCount + "包数据!!!");
                sendBytesPacketCount--;                                                                 // 发了一包，总数减一
            }
            send(firstBytes == null ? bytes : firstBytes, sendType);
        }
    }

    /**
     * 继续发送数据
     *
     * @param sendType 发送类型
     * @return false：已经发送完毕 true：继续发送
     */
    public boolean continueSendBytes(int sendType) {
        if (sendBytesPacketCount != 0) {
            byte[] tmpBytes = sendBytesPacketCount == 1 ?
                    new byte[sendBytes.length % SEND_DATA_MAX_LEN == 0 ? SEND_DATA_MAX_LEN : sendBytes.length % SEND_DATA_MAX_LEN] :
                    new byte[SEND_DATA_MAX_LEN];
            int count = sendBytes.length / SEND_DATA_MAX_LEN + (sendBytes.length % SEND_DATA_MAX_LEN == 0 ? 0 : 1);
            int index = count - sendBytesPacketCount;
            System.arraycopy(sendBytes, SEND_DATA_MAX_LEN * index, tmpBytes, 0, tmpBytes.length);
            LogUtil.i("test_sendLargeBytes", "还有" + (sendBytesPacketCount == 1 ? "最后一" : sendBytesPacketCount) + "包没有发!!!");
            LogUtil.i("test_sendLargeBytes", "索引 : " + index + "   长度 : " + tmpBytes.length + "   总长度 : " + sendBytes.length);
            LogUtil.i("test_sendLargeBytes", "包数据是：" + ParseUtil.byteArrayToHexString(tmpBytes));
            sendBytesPacketCount--;                                                                 // 每发一包，减一包
            send(tmpBytes, sendType);
            return true;
        }
        return false;
    }

    // 发送(真正发送数据到设备的接口)
    private void send(byte bytes[], int sendType) {
        if (bluetoothGatt == null) return;
        BluetoothGattCharacteristic characteristic;
        try {
            switch (sendType) {
                case SEND_TYPE_8001:
                    if (mHandler != null) {
                        mHandler.removeCallbacks(checkSendTimeoutRunnable);
                        sendTimeOutCount = SEND_DATA_TIMEOUT;
                        mHandler.postDelayed(checkSendTimeoutRunnable, 1000);
                    }
                    LogUtil.v("BluetoothLeService", ">>>>>>>>>>>>>>>>>>>>写数据到设备(8001) : " + ParseUtil.byteArrayToHexString(bytes));
                    characteristic = bluetoothGatt.getService(BluetoothCommandConstant.UUID_SERVICE_BASE).getCharacteristic(BluetoothCommandConstant.UUID_CHARACTERISTIC_8001);
//                    characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                    break;
                default:
                    LogUtil.v("BluetoothLeService", ">>>>>>>>>>>>>>>>>>>>写数据到设备(8003) : " + ParseUtil.byteArrayToHexString(bytes));
                    characteristic = bluetoothGatt.getService(BluetoothCommandConstant.UUID_SERVICE_EXTEND).getCharacteristic(BluetoothCommandConstant.UUID_CHARACTERISTIC_8003);
                    characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    break;
            }
            characteristic.setValue(bytes);
            bluetoothGatt.writeCharacteristic(characteristic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
