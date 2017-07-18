package cn.appscomm.bluetooth.protocol.protocolL28T;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;

/**
 * 28T获取睡眠/运动状态
 * Created by cui on 2017/6/6.
 */

public class SportSleepModeL28T extends Leaf {
    /**
     * 构造器
     *
     * @param iBluetoothResultCallback 用于回调蓝牙结果
     * @param content              需要发送的命令
     */
    public SportSleepModeL28T(IBluetoothResultCallback iBluetoothResultCallback, byte[] content){
        /**
         * 因为L28T的命令格式是0x6E, 0x01, (byte) 0x03, 0x03, (byte) 0x8F
         * 用于判断所以只需要传用于判断是否是L28T的命令码
         */
        super(iBluetoothResultCallback,BluetoothCommandConstant.COMMAND_CODE_L28T_SLEEP_MODEL,BluetoothCommandConstant.ACTION_CODE_L28T);
        super.setContent(content);
        super.setIsL28T(true);
    }

    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        LogUtil.i("contents="+contents.toString());
        if (len > 0) {
            int model=contents[3];
            //1为睡眠0为运动
            bluetoothVar.sportSleepModeL28T =model;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
