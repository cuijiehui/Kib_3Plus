package cn.appscomm.bluetooth.protocol;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothManager;
import cn.appscomm.bluetooth.BluetoothVar;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.AppsCommDevice;
import cn.appscomm.bluetooth.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙基类
 * 1、除了组成蓝牙命令的基本成员外，额外新增bluetoothSendType和isActiveCommand
 * 2、bluetoothSendType：蓝牙发送类型，主要区分是8001或8003发送数据
 * 3、isActiveCommand：判断是否主动命令还是被动命令(如音乐、远程拍照、寻找手机属于被动命令)
 */
public abstract class Leaf {
    public static final String TAG = BluetoothManager.class.getSimpleName();


    public boolean isL28T = false;

    private byte commandCode;                                                                       // 命令码


    private byte action;                                                                            // 动作
    private byte[] contentLen = new byte[2];                                                        // 内容长
    private byte[] content;                                                                         // 内容

    private int bluetoothSendType = AppsCommDevice.SEND_TYPE_8001;                                  // 蓝牙发送类型(8001和8003)
    private boolean isActiveCommand = true;                                                         // 是否主动命令(主动命令则设备会返回数据，被动命令则设备不会返回数据)
    public BluetoothVar bluetoothVar;                                                               // 蓝牙变量引用，每个设备都会给该变量赋值，然后子命令解释后把结果保存到该变量中

    private IBluetoothResultCallback iBluetoothResultCallback;                                      // 用于回调

    /**
     * 构造器
     *
     * @param iBluetoothResultCallback 用于回调蓝牙结果
     * @param commandCode              命令码
     * @param action                   动作
     */
    public Leaf(IBluetoothResultCallback iBluetoothResultCallback, byte commandCode, byte action) {
        this.iBluetoothResultCallback = iBluetoothResultCallback;
        this.commandCode = commandCode;
        this.action = action;
    }

    /**
     * 整理最终要发送的命令
     *
     * @return true：则整理成功；false：则整理出错，有异常
     */
    public byte[] getSendData() {
        try {
            int totalLen = 6 + content.length;                                                      // 总字节长度 = 1(开始标识符) + 1(命令码) + 1(动作) + 2(内容长度) + 1(结束标识符) + N(命令长度)
            byte[] sendData = new byte[totalLen];
            sendData[0] = BluetoothCommandConstant.FLAG_START;                                      // 开始标识符
            sendData[1] = commandCode;                                                              // 命令码
            sendData[2] = action;                                                                   // 动作
            sendData[totalLen - 1] = BluetoothCommandConstant.FLAG_END;                             // 结束标识符
            System.arraycopy(contentLen, 0, sendData, 3, 2);                                        // 内容长度
            System.arraycopy(content, 0, sendData, 5, content.length);                              // 内容
            return sendData;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析设置返回(0x81)数据
     *
     * @param result 81命令的结果
     * @return 结果
     */
    public int parse81BytesArray(byte result) {
        int ret = -1;
        switch (result) {
            case BluetoothCommandConstant.RESPONSE_SUCCESS:                                         // 成功
                ret = BluetoothCommandConstant.RESPONSE_SUCCESS;
                LogUtil.i(TAG, "修改返回 : 上一条命令执行成功!!!");
                break;
            case BluetoothCommandConstant.RESPONSE_FAIL:                                            // 失败
                ret = BluetoothCommandConstant.RESPONSE_FAIL;
                LogUtil.i(TAG, "修改返回 : 上一条命令执行失败!!!");
                break;
            case BluetoothCommandConstant.RESPONSE_ERROR:                                           // 协议解析错误
                ret = BluetoothCommandConstant.RESPONSE_ERROR;
                LogUtil.i(TAG, "修改返回 : 上一条命令出错:协议解析错误!!!");
                break;
        }
        return ret;
    }

    /**
     * 解析查询返回(0x80)数据，由于每个业务的解析方法都不一样，所以需要具体的业务去解析
     *
     * @param len      内容长度
     * @param contents 内容
     * @return 如果没有80命令，则直接返回成功，有80命令的，子类会单独解析并返回结果
     */
    public int parse80BytesArray(int len, byte[] contents) {
        return BluetoothCommandConstant.RESPONSE_SUCCESS;
    }

    /**
     * 获取内容长
     */
    public byte[] getContentLen() {
        return contentLen;
    }

    /**
     * 设置内容长
     */
    public void setContentLen(byte[] contentLen) {
        this.contentLen = contentLen;
    }

    /**
     * 获取是否为28T设备
     * @return
     */
    public boolean getIsL28T() {
        return isL28T;
    }

    /**
     * 设置是否为28T设备
     * @return
     */
    public void setIsL28T(boolean l28T) {
        isL28T = l28T;
    }

    /**
     * 返回命令
     * @return
     */
    public byte getAction() {
        return action;
    }


    /**
     * 获取内容
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * 设置内容
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * 获取发送类型
     */
    public int getBluetoothSendType() {
        return bluetoothSendType;
    }

    /**
     * 设置发送类型，一般用于音乐、远程拍照、寻找手机
     */
    protected void setBluetoothSendType(int bluetoothSendType) {
        this.bluetoothSendType = bluetoothSendType;
    }

    /**
     * 判断主动或被动命令
     */
    public boolean isActiveCommand() {
        return isActiveCommand;
    }

    /**
     * 设置被动或者主动命令，默认为主动，被动才需要显示设置
     */
    protected void setActiveCommand(boolean activeCommand) {
        isActiveCommand = activeCommand;
    }

    /**
     * 获取命令码
     *
     * @return 命令码
     */
    public byte getCommandCode() {
        return commandCode;
    }

    /**
     * 获取回调对象
     */
    public IBluetoothResultCallback getIBluetoothResultCallback() {
        return iBluetoothResultCallback;
    }
}
