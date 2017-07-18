package cn.appscomm.bluetooth;

import cn.appscomm.bluetooth.util.LogUtil;
import cn.appscomm.bluetooth.util.ParseUtil;
import cn.appscomm.bluetooth.protocol.Leaf;

import static android.R.attr.action;

/**
 * 作者：hsh
 * 日期：2017/3/17
 * 说明：蓝牙解析类
 */
public class BluetoothParse {
    private static final String TAG = BluetoothParse.class.getSimpleName();
    private byte[] bigBytes = null;                                                                 // 大字节数组，用于接收超过20字节的数据
    private int pos = 0;                                                                            // 存储位置
    private int bigBytesLen = 0;                                                                    // 大字节数组的长度
    private boolean isBigBytesStartFlag = false;                                                    // 大字节数组开始标志，以防中间数据出现以0x6F开始 以0x8F结尾的数据

    private byte[] firstBytes=null;                                                                 //28T用于保存第一包数据
    // 通过数据长度，检查是否单条命令，有些命令可能是大字节数组，但第一条发送过来的，可能以0x6F开始，0x8F结束
    private boolean isSingleCommandByDataLen(byte[] bytes) {
        int bytesLen = (bytes[3] & 0xFF) + ((bytes[4] & 0xFF) << 8);                                // 命令解析出来的长度
        int realLen = bytes.length - 6;                                                             // 真正接收到的长度
        if (bytesLen != realLen) {
            LogUtil.i(TAG, "该命令虽然以0x6F开始,0x8F结束,但不是单条命令!!!");
        }
        return bytesLen == realLen;
    }
    // 通过数据长度，检查是否单条命令，有些命令可能是大字节数组，但第一条发送过来的，可能以0x6F开始，0x8F结束
    private boolean isSingleCommandByDataLenL28T(byte[] bytes) {
        if(bytes.length==20&&bytes!=null){
            if ((bytes[0] == 0x6e) && (bytes[1] == 0x01)  //L28T 有2个命令需要分包处理
                    && ((bytes[2] == 0x03) || (bytes[2] == 0x04) || (bytes[2] == 0x05))) {
                return false;
            }
        }
     return true;
    }
    /**
     * 整理接收到的数据，并整理为一个完整的数组
     *
     * @param bytes 接收到的数据
     * @return 返回值类型很多，需要根据不同情况做不同处理
     */
    public byte[] proReceiveData(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;

        byte startFlag = bytes[0];
        byte endFlag = bytes[bytes.length - 1];

        // 单条数组
        if (startFlag == BluetoothCommandConstant.FLAG_START &&                                     // 0x6F开始
                endFlag == BluetoothCommandConstant.FLAG_END &&                                     // 0x8F结尾
                !isBigBytesStartFlag &&                                                             // 大字节标志为false
                isSingleCommandByDataLen(bytes)) {                                                  // 单条命令
            return bytes;
        }

        // 大字节数组：第一次进行接收
        else if (startFlag == BluetoothCommandConstant.FLAG_START &&                                // 0x6F开始
                !isBigBytesStartFlag) {                                                             // 大字节标志为false
            try {
                pos = 0;
                bigBytesLen = (bytes[3] & 0xFF) + ((bytes[4] & 0xFF) << 8) + 6;
                bigBytes = new byte[bigBytesLen];

                System.arraycopy(bytes, 0, bigBytes, pos, bytes.length);
                pos += bytes.length;
                isBigBytesStartFlag = true;
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE};
            } catch (ArrayIndexOutOfBoundsException e) {
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_EXCEPTION};
            }
        }

        // 大字节数组：第二次~最后一次数据接收
        else if (isBigBytesStartFlag) {
            try {
                LogUtil.i(TAG, "大字节数组:继续接收(" + ParseUtil.byteArrayToHexString(bytes) + ")...");
                LogUtil.i(TAG, "指针位置pos : " + pos + " 此条数组长度bytes.len : " + bytes.length + " 总长度totallen : " + bigBytesLen);
                System.arraycopy(bytes, 0, bigBytes, pos, bytes.length);
                pos += bytes.length;
                isBigBytesStartFlag = true;

                if (pos == bigBytesLen) {
                    LogUtil.i(TAG, "endFlag : " + endFlag + " xxx : " + BluetoothCommandConstant.FLAG_END);
                    if (endFlag == BluetoothCommandConstant.FLAG_END) {                             // 最后一包数据,接收成功
                        isBigBytesStartFlag = false;
                        return bigBytes;
                    } else {
                        return new byte[]{BluetoothCommandConstant.RESULT_CODE_ERROR};
                    }
                }
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE};
            } catch (ArrayIndexOutOfBoundsException e) {                                            // 丢包处理(可能上一包数据还没有接受完，下一包的数据马上过来)，需清空以前的数据，重新接收
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_EXCEPTION};
            }
        }

        // 其他情况，返回空
        else {
            return null;
        }
    }
    /**
     * 整理接收到的数据，并整理为一个完整的数组
     *
     * @param bytes 接收到的数据
     * @return 返回值类型很多，需要根据不同情况做不同处理
     */
    public byte[] proReceiveDataL28T(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;

        byte startFlag = bytes[0];
        byte endFlag = bytes[bytes.length - 1];

        // 单条数组
        if (startFlag == BluetoothCommandConstant.FLAG_START_L28T &&                                     // 0x6E开始
                endFlag == BluetoothCommandConstant.FLAG_END &&                                     // 0x8F结尾
                !isBigBytesStartFlag &&                                                             // 大字节标志为false
                isSingleCommandByDataLenL28T(bytes)) {                                                  // 单条命令
            return bytes;
        }

        // 大字节数组：第一次进行接收
        else if (startFlag == BluetoothCommandConstant.FLAG_START_L28T &&                                // 0x6E开始
                !isBigBytesStartFlag) {                                                             // 大字节标志为false
            try {
                LogUtil.i(TAG, "大字节数组:第一次进行接收(" + ParseUtil.byteArrayToHexString(bytes) + ")...");
                pos = 0;
                firstBytes = new byte[bytes.length];
                System.arraycopy(bytes, 0, firstBytes, 0, bytes.length);
                LogUtil.i(TAG, "大字节数组:第一次进行接收(" + ParseUtil.byteArrayToHexString(firstBytes) + ")...");
                isBigBytesStartFlag = true;
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE};
            } catch (ArrayIndexOutOfBoundsException e) {
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_EXCEPTION};
            }
        }

        // 大字节数组：第二次~最后一次数据接收
        else if (isBigBytesStartFlag) {
            try {
                LogUtil.i(TAG, "大字节数组:继续接收(" + ParseUtil.byteArrayToHexString(bytes) + ")...");
                LogUtil.i(TAG, "大字节数组:继续接收(" + ParseUtil.byteArrayToHexString(firstBytes) + ")...");
                LogUtil.i(TAG, "大字节数组:继续接收(" + firstBytes.length+bytes.length + ")...");
                byte[] contentBytes=new byte[firstBytes.length+bytes.length];
                System.arraycopy(firstBytes, 0, contentBytes, 0, firstBytes.length);
                System.arraycopy(bytes, 0, contentBytes, firstBytes.length, bytes.length);
                bigBytes=new byte[contentBytes.length];
                System.arraycopy(contentBytes, 0, bigBytes, 0, contentBytes.length);
                LogUtil.i(TAG, "大字节数组:继续接收(" + ParseUtil.byteArrayToHexString(bigBytes) + ")...");
                isBigBytesStartFlag = true;
                if (endFlag == BluetoothCommandConstant.FLAG_END&&isSingleCommandByDataLenL28T(bytes)) {
                    LogUtil.i(TAG, "endFlag : " + endFlag + " xxx : " + BluetoothCommandConstant.FLAG_END);
                    if (endFlag == BluetoothCommandConstant.FLAG_END) {                             // 最后一包数据,接收成功
                        isBigBytesStartFlag = false;
                        return bigBytes;
                    } else {
                        return new byte[]{BluetoothCommandConstant.RESULT_CODE_ERROR};
                    }
                }
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_CONTINUE_RECEIVE};
            } catch (Exception e) {                                            // 丢包处理(可能上一包数据还没有接受完，下一包的数据马上过来)，需清空以前的数据，重新接收
                return new byte[]{BluetoothCommandConstant.RESULT_CODE_EXCEPTION};
            }
        }

        // 其他情况，返回空
        else {
            return null;
        }
    }
    /**
     * 解析完整的蓝牙数据
     *
     * @param bytes 蓝牙数据
     * @param leaf  正在处理的发送命令
     * @return 0:成功 1:失败 2:协议解析错误 3:数据还没有接收完 4:数据接收不完整 5:重新发送该命令 -1:没有进行解析命令 -2:大字节数组接收错误
     */
    public int parseBluetoothData(byte[] bytes, Leaf leaf) {
        int ret = BluetoothCommandConstant.RESULT_CODE_ERROR;
        if(leaf.getIsL28T()){
            /**
             * 解析部分，要注意L28T返回的没有发送标识符如下：
             * 0x6E, 0x01, (byte) 0x03, 0x03, (byte) 0x8F//获取 软件版本
             * 6E 01 09 01 02 05 8F//返回的数据
             * 所以设置commandCode需要注意
             * 下面是设置的：
             * 6E 01 34 01 8F //设置公斤
             * 6E 01 01 34 00 8F //返回的数据
             */
            int len=bytes.length;
            LogUtil.i(TAG, "接受到的长度(" +len  + ")...");
            LogUtil.i(TAG, "接受到的命令码(" +leaf.getAction()  + ")...");
            if(leaf.getAction()==BluetoothCommandConstant.ACTION_CODE_L28T){//
                byte commandCode = bytes[2];
                LogUtil.i(TAG, "接受到的amdCode(" +commandCode  + ")...");
                LogUtil.i(TAG, "发送到的amdCode(" +leaf.getCommandCode()  + ")...");
                if(commandCode == leaf.getCommandCode()||(bytes.length == 6 &&(bytes[3] == 0x06||bytes[3] == 0x31))
                        ){     //后面的判断是用于 运动数据上传完成做处理的
                    // 发送的命令和回复的命令一致
                    ret = leaf.parse80BytesArray(len, bytes);

                }
            }
            else if(leaf.getAction()==BluetoothCommandConstant.ACTION_SET_CODE_L28T){
                byte commandCode = bytes[3];
                LogUtil.i(TAG, "接受到的amdCode(" +commandCode  + ")...");
                LogUtil.i(TAG, "发送到的amdCode(" +leaf.getCommandCode()  + ")...");

                if(commandCode == leaf.getCommandCode())                                           // 发送的命令和回复的命令一致
                        {
                            ret = leaf.parse81BytesArray(bytes[4]);

                }
            }

        }else{
            byte commandCode = bytes[1];
            byte action = bytes[2];
            try {
                // 0x80处理
                if (commandCode == leaf.getCommandCode() &&                                             // 发送的命令和回复的命令一致
                        action == BluetoothCommandConstant.ACTION_CHECK_RESPONSE) {                     // 动作为0x80
                    int len = (bytes[3] & 0xFF) + ((bytes[4] & 0xFF) << 8);
                    byte[] contents = new byte[len];
                    System.arraycopy(bytes, 5, contents, 0, len);
                    ret = leaf.parse80BytesArray(len, contents);
                }

                // 0x81处理
                else if (commandCode == BluetoothCommandConstant.COMMAND_CODE_RESPONSE &&               // 命令码为0x01
                        action == BluetoothCommandConstant.ACTION_SET_RESPONSE &&                       // 动作为0x81
                        bytes.length == BluetoothCommandConstant.ACTION_SET_RESPONSE_LEN &&             // 长度固定为8
                        bytes[5] == leaf.getCommandCode()) {                                            // 发送的命令和回复的命令一致
                    ret = leaf.parse81BytesArray(bytes[6]);
                }

            } catch (Exception e) {
            }
        }

        return ret;
    }
}
