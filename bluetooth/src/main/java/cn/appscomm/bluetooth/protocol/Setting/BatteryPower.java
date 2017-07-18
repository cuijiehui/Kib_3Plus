package cn.appscomm.bluetooth.protocol.Setting;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 电池电量
 * Created by Administrator on 2016/1/27.
 */
public class BatteryPower extends Leaf {

    /**
     * 电池电量
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public BatteryPower(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_BATTERY_POWER, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        LogUtil.i(TAG, "查询 : 准备获取电量值...");
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为1
     * 例子:
     * 6F 08 80   01 00   28   8F(0x28:40)
     * 十六进制转十进制
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if(bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 1) {
            bluetoothVar.batteryPower = contents[0] & 0xFF;
            LogUtil.i(TAG, "查询返回 : 电量值是" + bluetoothVar.batteryPower);
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
