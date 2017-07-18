package cn.appscomm.bluetooth.protocol.Sport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * Created by Administrator on 2016/7/20.
 */
public class HeartRateAlarmThreshold extends Leaf {

    /**
     * 心率报警门限
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public HeartRateAlarmThreshold(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_HEART_RATE_ALARM_THRESHOLD, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 心率报警门限
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param minValue                 心率最大值
     * @param maxValue                 心率最小值
     * @param isEnable                 是否报警(true:报警 false:不报警)
     */
    public HeartRateAlarmThreshold(IBluetoothResultCallback iBluetoothResultCallback, int len, byte maxValue, byte minValue, boolean isEnable) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_HEART_RATE_ALARM_THRESHOLD, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[]{maxValue, minValue, isEnable ? (byte) 0x01 : (byte) 0x00};
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为3
     * 例子:
     * 设备端持续发送直到最后一条
     * 6F 5D 80   03 00   64 32 01   8F
     * 1、	    心率最大值(100)
     * 2、	    心率最小值(50)
     * 3、	    是否报警(0x01:报警 0x00:不报警)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 3) {
            bluetoothVar.heartRateMaxValue = contents[0] & 0xFF;
            bluetoothVar.heartRateMinValue = contents[1] & 0xFF;
            bluetoothVar.heartRateAlarmSwitch = (contents[2] & 0xFF) == 1;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
