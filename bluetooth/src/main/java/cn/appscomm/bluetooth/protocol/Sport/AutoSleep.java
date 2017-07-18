package cn.appscomm.bluetooth.protocol.Sport;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;

/**
 * 自动睡眠监测
 * Created by Administrator on 2016/1/27.
 */
public class AutoSleep extends Leaf {

    /**
     * 自动睡眠监测
     * 构造函数(0x70)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param content70                内容
     */
    public AutoSleep(IBluetoothResultCallback iBluetoothResultCallback, int len, int content70) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_AUTO_SLEEP, BluetoothCommandConstant.ACTION_CHECK);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = ParseUtil.intToByteArray(content70, len);
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * 自动睡眠监测
     * 构造函数(0x71)
     *
     * @param iBluetoothResultCallback
     * @param len                      内容长度
     * @param enterSleepHour           进入睡眠时
     * @param enterSleepMin            进入睡眠分
     * @param quitSleepHour            退出睡眠时
     * @param quitSleepMin             退出睡眠分
     * @param remindSleepCycle         提醒周期(位对应从最低位到次高位对应周一到周日)
     */
    public AutoSleep(IBluetoothResultCallback iBluetoothResultCallback, int len, byte enterSleepHour, byte enterSleepMin, byte quitSleepHour, byte quitSleepMin, byte remindSleepCycle) {
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_AUTO_SLEEP, BluetoothCommandConstant.ACTION_SET);
        byte[] contentLen = ParseUtil.intToByteArray(len, 2);
        byte[] content = new byte[5];
        content[0] = enterSleepHour;
        content[1] = enterSleepMin;
        content[2] = quitSleepHour;
        content[3] = quitSleepMin;
        content[4] = remindSleepCycle;
        super.setContentLen(contentLen);
        super.setContent(content);
    }

    /**
     * contents字节数组解析：
     * 长度固定为5
     * 例子:
     * 6F 59 80   05 00   16 00 07 00 7F   8F(进入睡眠(22:00) 退出睡眠(07:00) 提醒周期为每天)
     * 1、进入睡眠时
     * 2、进入睡眠分
     * 3、退出睡眠时
     * 4、退出睡眠分
     * 5、提醒周期(位对应从最低位到次高位对应周一到周日)
     */
    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if (len == 5) {
            bluetoothVar.bedTimeHour = contents[0] & 0xFF;                                          // 进入睡眠时
            bluetoothVar.bedTimeMin = contents[1] & 0xFF;                                           // 进入睡眠分
            bluetoothVar.awakeTimeHour = contents[2] & 0xFF;                                        // 退出睡眠时
            bluetoothVar.awakeTimeMin = contents[3] & 0xFF;                                         // 退出睡眠分
            bluetoothVar.remindSleepCycle = contents[4] & 0xFF;                                     // 提醒睡眠周期
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
