package cn.appscomm.bluetooth.protocol.State;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 开关设置
 * Created by Administrator on 2016/1/27.
 */
public class SwitchSetting extends Leaf {

    /**
     * 开关设置
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public SwitchSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SWITCH_SETTING, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 开关设置
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param sendMode                 发送模式(0x00:以Bit形式发送   0x01:以Byte形式发送)
     * @param value1                   若byte形式(表示开关类型) 若bit形式(则每一位对应着一个开关类型)
     * @param value2                   若byte形式(表示开关状态) 若bit形式(则每一位对应着一个开关类型)
     */
    public SwitchSetting(IBluetoothResultCallback iBluetoothResultCallback, int len, byte sendMode, byte value1, byte value2) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_SWITCH_SETTING, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[3];
        content[0] = sendMode;
        content[1] = value1;
        content[2] = value2;
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度不固定
     * 例子:
     * 6F 90 80   02 00   FE 0F   8F(以Bit形式返回)
     * 1~2、以bit 形式发送(最低位在第1字节，对应Bit值为0代表该开关关闭，1代表该开关打开)
     * 说明：Bit形式发送,则发送所有的开关值到设备端
     * Bit   开关
     * 0     防丢开关
     * 1     自动同步开关
     * 2     睡眠开关
     * 3     自动睡眠监测开关
     * 4     来电提醒开关
     * 5     未接来电提醒开关
     * 6     短信提醒开关
     * 7     社交提醒开关
     * 8     邮件提醒开关
     * 9     日历提醒开关
     * 10    久坐提醒开关
     * 11    超低功耗功能开关
     * 12    二次提醒开关
     * 13    铃声开关
     * 14    抬手亮屏开关
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 2) {
            bluetoothVar.antiLostSwitch = (contents[0] & 1) != 0;                                   // 防丢开关
            bluetoothVar.autoSyncSwitch = (contents[0] & (1 << 1)) != 0;                            // 自动同步开关
            bluetoothVar.sleepSwitch = (contents[0] & (1 << 2)) != 0;                               // 睡眠开关
            bluetoothVar.autoSleepSwitch = (contents[0] & (1 << 3)) != 0;                          // 自动睡眠监测开关
            bluetoothVar.incomeCallSwitch = (contents[0] & (1 << 4)) != 0;                          // 来电提醒开关
            bluetoothVar.missCallSwitch = (contents[0] & (1 << 5)) != 0;                            // 未接来电提醒开关
            bluetoothVar.smsSwitch = (contents[0] & (1 << 6)) != 0;                                 // 短信提醒开关
            bluetoothVar.socialSwitch = (contents[0] & (1 << 7)) != 0;                              // 社交提醒开关

            bluetoothVar.emailSwitch = (contents[1] & 1) != 0;                                      // 邮件提醒开关
            bluetoothVar.calendarSwitch = (contents[1] & (1 << 1)) != 0;                            // 日历开关
            bluetoothVar.sedentarySwitch = (contents[1] & (1 << 2)) != 0;                           // 久坐提醒开关
            bluetoothVar.lowPowerSwitch = (contents[1] & (1 << 3)) != 0;                            // 超低功耗功能开关
            bluetoothVar.secondRemindSwitch = (contents[1] & (1 << 4)) != 0;                        // 二次提醒开关

            bluetoothVar.raiseWakeSwitch = (contents[1] & (1 << 6)) != 0;                           // 抬手亮屏开关

            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
