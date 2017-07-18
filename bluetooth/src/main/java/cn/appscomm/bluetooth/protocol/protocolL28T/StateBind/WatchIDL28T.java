package cn.appscomm.bluetooth.protocol.protocolL28T.StateBind;

import java.util.Arrays;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;

/**
 * 28T获取watchid
 * Created by cui on 2017/6/12.
 */

public class WatchIDL28T extends Leaf {
    /**
     * 构造器
     *
     * @param iBluetoothResultCallback 用于回调蓝牙结果
     * @param content              需要发送的命令
     */
    public WatchIDL28T(IBluetoothResultCallback iBluetoothResultCallback, byte[] content){
        /**
         * 因为L28T的命令格式是0x6E, 0x01, (byte) 0x03, 0x03, (byte) 0x8F
         * 用于判断所以只需要传用于判断是否是L28T的命令码
         */
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_L28T_WATCH_ID,BluetoothCommandConstant.ACTION_CODE_L28T);
        super.setContent(content);
        super.setIsL28T(true);
    }

    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        //6E 01 04 46 43 4C 32 38 54 31 37 30 31 31 37 37 37 30 38 37 38 33 30 8F
        LogUtil.i("contents=");
        if (len > 0) {
            String Sn="";
            try {
                Sn = new String(Arrays.copyOfRange(contents, 3, 23), "US-ASCII");
            }catch (Exception e){

            }
            LogUtil.i("contents="+Sn);

            bluetoothVar.watchIDL28T =Sn ;
            ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
        }
        return ret;
    }
}
