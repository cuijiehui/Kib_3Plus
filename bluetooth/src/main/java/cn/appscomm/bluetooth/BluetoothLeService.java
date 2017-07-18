package cn.appscomm.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙服务类
 * 1、主要工作是：
 * 2、连接：通过mac地址连接设备，并打开各种特征通道
 * 3、接收：通过系统回调，接收设备发送过来的数据
 * 4、回调：通过EventBus把数据发送给蓝牙管理者去解析
 */
public class BluetoothLeService extends Service {

    private static final String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter = null;                                              // 蓝牙适配器
    public static Map<String, AppsCommDevice> deviceMap = new HashMap<>();                          // 多台手环集合

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.START_SERVICE_SUCCESS, this));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, TAG + "服务：创建");
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager != null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, TAG + "服务：销毁");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 连接设备
     *
     * @param mac 要连接的MAC地址
     * @return false：连接有误 true：连接中
     */
    public boolean connect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        try {
            BluetoothDevice bluetoothdevice;
            if (mBluetoothAdapter == null || (bluetoothdevice = mBluetoothAdapter.getRemoteDevice(mac)) == null) {
                return false;
            } else {
                AppsCommDevice appsCommDevice = deviceMap.get(mac);
                LogUtil.i(TAG, "要连接的mac(" + mac + ") appsCommDevice(" + (appsCommDevice != null) + ")");
                if (appsCommDevice != null) {
                    appsCommDevice.disconnect();
                    appsCommDevice.bluetoothGatt = bluetoothdevice.connectGatt(this, android.os.Build.VERSION.SDK_INT < 19, bluetoothGattCallback);
                } else {
                    BluetoothGatt mBluetoothGatt = bluetoothdevice.connectGatt(this, android.os.Build.VERSION.SDK_INT < 19, bluetoothGattCallback);
                    deviceMap.put(mac, new AppsCommDevice(mac, mBluetoothGatt, isSupportHeartRate,isSend03));
                }
                LogUtil.w(TAG, "-------------连接设备(通过mac地址连接设备,mac : " + mac + "   绑定状态是 : " + bluetoothdevice.getBondState() + ")");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 断开连接
     */
    public void disconnectAll() {
        if (deviceMap != null) {
            for (Map.Entry<String, AppsCommDevice> entry : deviceMap.entrySet()) {
                AppsCommDevice appsCommDevice = entry.getValue();
                if (appsCommDevice.bluetoothGatt != null) {
                    appsCommDevice.disconnect();
                }
            }
        }
    }

    /**
     * 打开监听
     */
    public void openNotification(BluetoothGatt bluetoothGatt, LinkedList<AppsCommDevice.NotificationInfo> notifications) {
        try {
            BluetoothGattCharacteristic characteristic = bluetoothGatt.getService(notifications.getFirst().service).getCharacteristic(notifications.getFirst().characteristic);
            bluetoothGatt.setCharacteristicNotification(characteristic, true);

            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(BluetoothCommandConstant.UUID_CONFIG_DESCRIPTOR);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            bluetoothGatt.writeDescriptor(descriptor);
        } catch (Exception e) {
            e.printStackTrace();
            AppsCommDevice appsCommDevice = getDevice(bluetoothGatt);
            if (appsCommDevice != null) {
                appsCommDevice.disconnect();                                                        // 打开监听失败，直接断开连接
            }
        }
    }

    /**
     * 通过bluetoothGatt获取手环
     *
     * @param bluetoothGatt 想要获取手环的bluetoothGatt
     * @return null:没找到;不为null:找到手环
     */
    public AppsCommDevice getDevice(BluetoothGatt bluetoothGatt) {
        if (deviceMap == null || deviceMap.size() == 0) return null;
        for (Map.Entry<String, AppsCommDevice> entry : deviceMap.entrySet()) {
            AppsCommDevice appsCommDevice = entry.getValue();
            if (appsCommDevice != null && appsCommDevice.bluetoothGatt == bluetoothGatt) {
                return appsCommDevice;
            }
        }
        return null;
    }

    // 蓝牙广播回调
    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {

        // 连接：状态回调
        public void onConnectionStateChange(BluetoothGatt bluetoothgatt, int state, int newState) {

            AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
            if (appsCommDevice != null) {

                // 断开连接
                if (newState == 0) {
                    LogUtil.e(TAG, "xxxxxxxxxxxxx连接状态回调(state=" + state + " newState=" + newState + " 断开连接)");
                    appsCommDevice.disconnect();
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothgatt, null));
                }

                // 连接失败回调(此处断开一直重连)
                else if ((state == 133) && (newState == 2)) {
                    LogUtil.e(TAG, "+++++++++++++连接状态回调(state=" + state + " newState=" + newState + " 未连接到设备,准备重新连接)");
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothgatt, null));
                }

                // 已经连接
                else if ((newState == 2) && (state == 0)) {
                    LogUtil.w(TAG, "==>>1、连接状态回调(state=" + state + " newState=" + newState + " (" + "已连接),准备发现服务...!!!)");
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_CONNECTED, bluetoothgatt, null));
                    appsCommDevice.bluetoothGatt.discoverServices();
                }
            }
        }

        // 连接：发现服务
        @Override
        public void onServicesDiscovered(BluetoothGatt bluetoothgatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtil.w(TAG, "==>>2、已发现服务(onServicesDiscovered),准备打开8002监听...!!!");
                AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
                if (appsCommDevice != null) {
                    appsCommDevice.setNotification();
                    openNotification(appsCommDevice.bluetoothGatt, appsCommDevice.notifications);
                }
            } else {
                LogUtil.e(TAG, "==>>onServicesDiscovered,有异常...!!!");
            }
        }

        // 连接：打开监听回调
        @Override
        public void onDescriptorWrite(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor descriptor, int i) {
            if (BluetoothCommandConstant.UUID_CONFIG_DESCRIPTOR.equals(descriptor.getUuid())) {
                AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
                if (appsCommDevice != null) {
                    if (appsCommDevice.notifications != null && appsCommDevice.notifications.size() > 0) {
                        AppsCommDevice.NotificationInfo info = appsCommDevice.notifications.removeFirst();
                        if (info.service.equals(BluetoothCommandConstant.UUID_SERVICE_BASE)) {
                            LogUtil.w(TAG, "==>>3.1、" + appsCommDevice.mac + "已打开8001监听(onDescriptorWrite),准备打开其他功能监听...!!!");
                        } else if (info.service.equals(BluetoothCommandConstant.UUID_SERVICE_EXTEND)) {
                            LogUtil.w(TAG, "==>>3.2、" + appsCommDevice.mac + "已打开其他功能监听(onDescriptorWrite)");
                        } else if (info.service.equals(BluetoothCommandConstant.UUID_SERVICE_HEART_RATE)) {
                            LogUtil.w(TAG, "==>>3.3、" + appsCommDevice.mac + "已打开心率监听(onDescriptorWrite),准备发送Discovered广播...!!!");
                        }
                        if (appsCommDevice.notifications.size() > 0) {
                            openNotification(appsCommDevice.bluetoothGatt, appsCommDevice.notifications);
                        } else {
                            appsCommDevice.bluetoothGatt.readDescriptor(descriptor);
                        }
                    } else {
                        LogUtil.i(TAG, "开启监听有问题,断开连接...");
                        appsCommDevice.disconnect();
                        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCONNECTED, bluetoothgatt, null));
                    }
                }
            } else {
                LogUtil.e(TAG, "==>>onDescriptorWrite,有异常...!!!");
            }
        }

        // 连接：连接完成
        public void onDescriptorRead(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor descriptor, int i) {
            if (BluetoothCommandConstant.UUID_CONFIG_DESCRIPTOR.equals(descriptor.getUuid())) {
                LogUtil.w(TAG, "==>>4、已经连接完毕(onDescriptorRead),发送Discovered广播...!!!");
                EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_DISCOVERED, bluetoothgatt, null));
            } else {
                LogUtil.e(TAG, "==>>onDescriptorRead,有异常...!!!");
            }
        }

        // 发送：写回调
        public void onCharacteristicWrite(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {

            // 8001通道写回调
            if (BluetoothCommandConstant.UUID_CHARACTERISTIC_8001.equals(bluetoothgattcharacteristic.getUuid())) {
                LogUtil.i(TAG, "==>>onCharacteristicWrite(系统返回8001通道写回调)");
                AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
                if (appsCommDevice != null) {
                    if (!appsCommDevice.continueSendBytes(AppsCommDevice.SEND_TYPE_8001) && appsCommDevice.isSend03) {
                        appsCommDevice.send03ToDevice(BluetoothCommandConstant.UUID_SERVICE_BASE, BluetoothCommandConstant.UUID_CHARACTERISTIC_8002);
                    }
                }
            }

            // 8003通道写回调
            else if (BluetoothCommandConstant.UUID_CHARACTERISTIC_8003.equals(bluetoothgattcharacteristic.getUuid())) {
                AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
                if (appsCommDevice != null) {
                    if (!appsCommDevice.continueSendBytes(AppsCommDevice.SEND_TYPE_8003)) {
                        LogUtil.i(TAG, "==>>onCharacteristicWrite(系统返回8003通道写回调)");
                        EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8003_SEND_CALLBACK, bluetoothgatt, null));
                    }
                }
            }
        }

        // 接收：设备返回数据到手机
        public void onCharacteristicChanged(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic) {
            byte[] bytes = bluetoothgattcharacteristic.getValue();
            AppsCommDevice appsCommDevice = getDevice(bluetoothgatt);
            if (appsCommDevice != null) {
                // 8002通道
                if (BluetoothCommandConstant.UUID_CHARACTERISTIC_8002.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(8002) : " + ParseUtil.byteArrayToHexString(bytes));
                    LogUtil.i(TAG, " ");
                    appsCommDevice.sendTimeOutCount = 0;
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8002_AVAILABLE, bluetoothgatt, bytes));
                }

                // 8004通道
                else if (BluetoothCommandConstant.UUID_CHARACTERISTIC_8004.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(8004) : " + ParseUtil.byteArrayToHexString(bytes));
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_DATA_8004_AVAILABLE, bluetoothgatt, bytes));
                }

                // 心率通道
                else if (BluetoothCommandConstant.UUID_CHARACTERISTIC_HEART_RATE.equals(bluetoothgattcharacteristic.getUuid())) {
                    LogUtil.e(TAG, "<<<<<<<<<<获取到设备返回的数据(心率) : " + ParseUtil.byteArrayToHexString(bytes));
                    EventBus.getDefault().post(new BluetoothMessage(BluetoothMessage.ACTION_GATT_REAL_TIME_HEART_RATE, bluetoothgatt, bytes));
                }
            }
        }

        // 读回调
        public void onCharacteristicRead(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            LogUtil.i(TAG, "==>>onCharacteristicRead(系统返回读回调)");
        }
    };
}