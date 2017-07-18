package cn.appscomm.bluetooth.protocol.protocolL28T;

import java.util.LinkedList;
import java.util.TimeZone;
import java.util.logging.Logger;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBTL28T;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

import static android.R.attr.offset;
import static android.os.Build.VERSION_CODES.N;

/**
 * Created by cui on 2017/7/10.
 */

public class SleepDataL28T extends Leaf {
    private int len;
    /**
     * 构造器
     *
     * @param iBluetoothResultCallback 用于回调蓝牙结果
     * @param content              需要发送的命令
     */
    public SleepDataL28T(IBluetoothResultCallback iBluetoothResultCallback, byte[] content, int len){
        /**
         * 因为L28T的命令格式是0x6E, 0x01, (byte) 0x03, 0x03, (byte) 0x8F
         * 用于判断所以只需要传用于判断是否是L28T的命令码
         */
        super(iBluetoothResultCallback, BluetoothCommandConstant.COMMAND_CODE_L28T_SLEEP_DATA,BluetoothCommandConstant.ACTION_CODE_L28T);
        super.setContent(content);
        super.setIsL28T(true);
        this.len=len;
    }

    @Override
    public int parse80BytesArray(int len, byte[] contents) {
        if (bluetoothVar == null) return BluetoothCommandConstant.RESULT_CODE_BLUETOOTH_VAR_NULL;
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        LogUtil.i("contents="+contents.toString());
        if (len > 0) {
            // mSportsData.sport_time_stamp = (long) NumberUtils.byteReverseToInt(new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]});
            //mSportsData.sport_steps = NumberUtils.byteReverseToInt(new byte[]{bytes[8], bytes[9], bytes[10], bytes[11]});
            //mSportsData.sport_cal = NumberUtils.byteReverseToInt(new byte[]{bytes[12], bytes[13], bytes[14], bytes[15]});
            if(contents.length == 6 && contents[0] == 0x6e && contents[2] == 0x01
                    && (contents[3] == 0x31 || contents[3] == 0x16) && contents[5] == (byte) 0x8f){
                if(contents[4]==0x00){
                    ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
                }
            }else{
                int type=0;
                long timeStamp=0;
                type = ParseUtil.byteToInt(new byte[]{(byte) (contents[3] & (byte) 0x7F)});
                long d = (long) ParseUtil.byteReverseToInt(new byte[]{contents[4],
                        contents[5], contents[6], contents[7]});
                long sleep_time_stamp = d;        // summer: annotate + 3600 * 8;// 手表是东8区时间//ZeFit需要+8小时，L28S不用，中国时区
                // 20140724

                //-----begin
                TimeZone tz = TimeZone.getDefault();
                int offset = tz.getRawOffset() / 3600000;
                LogUtil.e(TAG, "______________offset:" + offset + " /ID:" + tz.getID());
                sleep_time_stamp = sleep_time_stamp + 8 * 3600;
                timeStamp=sleep_time_stamp;
                SleepBT sleepBT=new SleepBT(type,timeStamp);
                if(bluetoothVar.sleepBTDataListL28T==null||bluetoothVar.sleepBTDataListL28T.size()==0){
                    bluetoothVar.sleepBTDataListL28T=new LinkedList<>();
                }
                bluetoothVar.sleepBTDataListL28T.add(sleepBT);
                ret = BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE;
            }

        }
        return ret;
    }
}
