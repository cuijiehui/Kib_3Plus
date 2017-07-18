package cn.appscomm.bluetooth.protocol.protocolL28T;

import java.util.LinkedList;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.mode.SportBTL28T;
import cn.appscomm.bluetooth.protocol.Leaf;
import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;

/**
 * 28T获取运动的详情数据
 * Created by cui on 2017/6/6.
 */

public class StepDate extends Leaf {
    private int len;
    /**
     * 构造器
     *
     * @param iBluetoothResultCallback 用于回调蓝牙结果
     * @param content              需要发送的命令
     */
    public StepDate(IBluetoothResultCallback iBluetoothResultCallback, byte[] content,int len){
        /**
         * 因为L28T的命令格式是0x6E, 0x01, (byte) 0x03, 0x03, (byte) 0x8F
         * 用于判断所以只需要传用于判断是否是L28T的命令码
         */
        super(iBluetoothResultCallback,BluetoothCommandConstant.COMMAND_CODE_L28T_STEP_DATE,BluetoothCommandConstant.ACTION_CODE_L28T);
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
                    && (contents[3] == 0x06 || contents[3] == 0x04) && contents[5] == (byte) 0x8f){
                if(contents[4]==0x00){
                    ret = BluetoothCommandConstant.RESULT_CODE_SUCCESS;
                }
            }else{
                long sportTime = ParseUtil.byteReverseToInt(new byte[]{
                        contents[4], contents[5], contents[6], contents[7]});
                int stepData = ParseUtil.byteReverseToInt(new byte[]{
                        contents[8], contents[9], contents[10], contents[11]});
                int calData = ParseUtil.byteReverseToInt(new byte[]{
                        contents[12], contents[13], contents[14], contents[15]});
                int lens = ParseUtil.byteReverseToInt(new byte[]{
                        contents[16]});
                LogUtil.i("运动数据的步数="+stepData);
                LogUtil.i("运动数据的lens="+this.len);
                LogUtil.i("运动数据的len="+lens);
                SportBTL28T sportBTL28T=new SportBTL28T(lens,stepData,calData,sportTime);
                if(bluetoothVar.sportBTDataListL28T==null||bluetoothVar.sportBTDataListL28T.size()==0||lens==0){
                    bluetoothVar.sportBTDataListL28T=new LinkedList<>();
                }
                bluetoothVar.sportBTDataListL28T.add(sportBTL28T);
                ret = BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE;
            }

        }
        return ret;
    }
}
