package cn.appscomm.ota;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.appscomm.ota.interfaces.IUpdateProgressCallBack;
import cn.appscomm.ota.mode.CommandInfo;
import cn.appscomm.ota.util.LogUtil;
import cn.appscomm.ota.util.OtaPrintf;
import cn.appscomm.ota.util.OtaUtil;

/**
 * 作者：hsh
 * 日期：2017/5/27
 * 说明：Apollo系列升级命令管理
 * 1、命令的生成
 * 2、命令的发送
 * 3、命令的解析
 */

public enum OtaApolloCommand {
    INSTANCE;

    private static final String TAG = OtaApolloCommand.class.getSimpleName();
    private LinkedList<CommandInfo> commandInfoList = new LinkedList<>();
    private final byte PACKAGE_COUNT = 0x0a;                                                        // 一次发送的包数

    private final byte UPDATE_TYPE_APOLLO = (byte) 0x01;                                            // 升级类型 : APOLLO
    private final byte UPDATE_TYPE_TOUCH = (byte) 0x02;                                             // 升级类型 : 触摸芯片
    private final byte UPDATE_TYPE_HEART_RATE = (byte) 0x03;                                        // 升级类型 : 心率
    private final byte UPDATE_TYPE_PICTURE_LANGUAGE = (byte) 0x04;                                  // 升级类型 : 图库或字库

    private final String NOTE_01_INIT = "NOTE_01_INIT";                                             // 01：初始化
    private final String NOTE_02_SET = "NOTE_02_SET";                                               // 02：设置
    private final String NOTE_03_CHECK_CALLBACK = "NOTE_03_CHECK_CALLBACK";                         // 03：查询或回传
    private final String NOTE_04_CRC = "NOTE_04_CRC";                                               // 04：CRC
    private final String NOTE_05_REBOOT = "NOTE_05_REBOOT";                                         // 05：重启

    private final boolean UUID_1531 = true;
    private final boolean UUID_1532 = false;

    public static final int UPDATE_SUCCESS = 100;
    public static final int UPDATE_ING = 101;
    public static final int UPDATE_FAIL = 102;

    private OtaService otaService;
    private IUpdateProgressCallBack iUpdateProgressCallBack;
    private int totalPackage = 0;

    /**
     * 开始升级
     *
     * @param otaService              OtaService:用于发送命令
     * @param iUpdateProgressCallBack 升级的进度、结果回调
     */
    public boolean start(OtaService otaService, IUpdateProgressCallBack iUpdateProgressCallBack) {
        this.otaService = otaService;
        this.iUpdateProgressCallBack = iUpdateProgressCallBack;
        if (this.otaService == null) return false;
        if (commandInfoList == null || commandInfoList.size() == 0) return false;
        if (this.iUpdateProgressCallBack != null) {
            this.iUpdateProgressCallBack.curUpdateMax(totalPackage);
        }
        send();
        return true;
    }

    // 打印命令
    private void printfCommand() {
        if (commandInfoList != null && commandInfoList.size() > 0) {
            int total = 0;
            for (CommandInfo commandInfo : commandInfoList) {
                total += commandInfo.content.length;
                LogUtil.i(TAG, "" + OtaUtil.byteArrayToHexString(commandInfo.content) + " (" + commandInfo.content.length + "---" + total + ")");
            }
        }
    }

    /**
     * 生成要升级的命令
     *
     * @param pathList 升级文件路径集合
     * @return true:成功 false:失败
     */
    public boolean create(List<String> pathList) {
        try {
            OtaPrintf.INSTANCE.init();
            commandInfoList.clear();
            totalPackage = 0;
            int iBinFileTotalLength = 0;

            for (String path : pathList) {
                byte updateType = getUpdateType(path);

                byte[] binContent;
                byte[] binAddress = new byte[4];
                binContent = OtaUtil.getBinContent(path, 0);
                byte[] tempBinContent = new byte[binContent.length - 4];
                System.arraycopy(binContent, 0, binAddress, 0, 4);
                System.arraycopy(binContent, 4, tempBinContent, 0, tempBinContent.length);
                binContent = tempBinContent;

                iBinFileTotalLength += binContent.length;
                byte[] binLength = OtaUtil.getBinLength(binContent.length, true);
                byte[] crcCheck = OtaUtil.getCrcCheck("", binContent);
                if (binContent.length > 0 && binLength.length > 0 && crcCheck.length > 0) {
                    int curUpdateTypeLen = addMiddleCommand(binContent, binAddress, binLength, crcCheck, updateType);
                    OtaPrintf.INSTANCE.setUpdateTypeLen(updateType, curUpdateTypeLen, true);
                }
            }
            if (iBinFileTotalLength > 0) {
                byte[] binTotalLength = OtaUtil.getBinLength(iBinFileTotalLength, true);
                addStartCommand(binTotalLength);
                addEndCommand();
            }
            printfCommand();
            totalPackage = commandInfoList.size();
            OtaPrintf.INSTANCE.setMax(totalPackage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // 加入要升级命令的头部
    private void addStartCommand(byte[] binTotalLength) {
        byte[] bytes = new byte[binTotalLength.length + 1];
        bytes[0] = 0x01;
        System.arraycopy(binTotalLength, 0, bytes, 1, binTotalLength.length);
        commandInfoList.addFirst(new CommandInfo(bytes, UUID_1531, NOTE_01_INIT));                  // 01(bin总大小)
    }

    // 加入要升级命令的中间部分
    private int addMiddleCommand(byte[] binContent, byte[] binAddress, byte[] binLength, byte[] crcCheck, byte updateType) {
        byte[] headBytes = new byte[15];
        headBytes[0] = (byte) 0x02;
        headBytes[1] = updateType;
        headBytes[14] = PACKAGE_COUNT;
        System.arraycopy(binAddress, 0, headBytes, 2, 4);
        System.arraycopy(binLength, 0, headBytes, 6, 4);
        System.arraycopy(crcCheck, 0, headBytes, 10, 4);
        commandInfoList.addLast(new CommandInfo(headBytes, UUID_1531, NOTE_02_SET));                // 02(各个bin的大小、地址、crc校验)

        Set<Integer> posSet = new TreeSet<>();
        posSet.add(0);
        int count2048 = binContent.length / 2048;
        if (count2048 > 0) {
            for (int i = 0, pos = 0; i < count2048; i++) {
                for (int j = 0; j < 11; j++) {                                                      // 前面10个200字节，后面1个48字节，刚好是2048
                    pos += j == 10 ? 48 : 200;
                    posSet.add(pos);
                }
            }
        }
        int lastLen = binContent.length % 2048;
        if (lastLen > 200) {
            for (int i = 200; i < lastLen; i += 200) {
                posSet.add(i + count2048 * 2048);
            }
        }
        posSet.remove(binContent.length);
        Object[] posArray = posSet.toArray();
        for (int i = 0; i < posArray.length; i++) {
            byte[] tempBytes = (i + 1) == posArray.length ? new byte[binContent.length - (int) posArray[i]] : new byte[(int) posArray[i + 1] - (int) posArray[i]];
            System.arraycopy(binContent, (int) posArray[i], tempBytes, 0, tempBytes.length);
            commandInfoList.addLast(new CommandInfo(tempBytes, UUID_1532, NOTE_03_CHECK_CALLBACK)); // 03(具体的数据)
        }

        commandInfoList.addLast(new CommandInfo(new byte[]{0x04}, UUID_1531, NOTE_04_CRC));         // 04(crc校验)
        return posArray.length;

    }

    // 加入要升级命令的尾部
    private void addEndCommand() {
        commandInfoList.addLast(new CommandInfo(new byte[]{0x05}, UUID_1531, NOTE_05_REBOOT));      // 05(重启)
    }

    private byte getUpdateType(String path) {
        if (path.contains("apollo")) {
            return UPDATE_TYPE_APOLLO;
        }
        if (path.contains("TouchPanel")) {
            return UPDATE_TYPE_TOUCH;
        }
        if (path.contains("Heartrate")) {
            return UPDATE_TYPE_HEART_RATE;
        }
        return UPDATE_TYPE_PICTURE_LANGUAGE;
    }

    public int parse(byte[] bytes) {
        if (commandInfoList.size() > 0) {                                                           // 集合里还有命令才需要解析
            String note = commandInfoList.getFirst().note;
            byte[] content = commandInfoList.getFirst().content;

            // 回调返回(一般为1532通道)
            if (bytes == null) {
                if (note.equals(NOTE_01_INIT) || note.equals(NOTE_02_SET) || note.equals(NOTE_03_CHECK_CALLBACK) || note.equals(NOTE_04_CRC)) {                                          // 需要等设备返回的命令
//                    LogUtil.i(TAG, "不需要继续发送，等设备返回数据");
                } else if (content.length == 1 && content[0] == 0x05) {                             // 05
                    commandInfoList.clear();
                    return UPDATE_SUCCESS;
                } else {                                                                            // 其他命令
//                    LogUtil.i(TAG, "继续发送...");
                    commandInfoList.removeFirst();
                    if (!send()) {
                        return UPDATE_FAIL;
                    }
                }
            }
            // 设备返回数据(一般为1531通道)
            else {
                OtaPrintf.INSTANCE.receive(bytes);
                boolean flag = false;
                if (bytes.length > 1) {
                    if (bytes[1] == 0x01) {
                        switch (bytes[0]) {
                            case 0x01:
                                flag = note.equals(NOTE_01_INIT);
                                break;
                            case 0x02:
                                flag = note.equals(NOTE_02_SET);
                                break;
                            case 0x03:
                                flag = note.equals(NOTE_03_CHECK_CALLBACK);
                                if (bytes.length == 7) {
                                    LogUtil.i(TAG, "设备已经收到的包数:" + OtaUtil.bytesToLong(bytes, 3, 6));
                                }
                                break;
                            case 0x04:
                                flag = note.equals(NOTE_04_CRC);
                                break;
                            case 0x05:
                                flag = note.equals(NOTE_05_REBOOT);
                                break;
                            default:
                                LogUtil.i(TAG, "存在错误，错误结果为:" + OtaUtil.byteArrayToHexString(bytes) + " 上次发送的命令是:" + OtaUtil.byteArrayToHexString(commandInfoList.getFirst().content));
                                return UPDATE_FAIL;
                        }
                    }
                }
                if (flag) {
                    commandInfoList.removeFirst();
                    if (!send()) {
                        return UPDATE_FAIL;
                    }
                } else {
                    return UPDATE_FAIL;
                }
            }
            return UPDATE_ING;
        } else {
            return UPDATE_FAIL;
        }
    }

    /**
     * 发送数据到设备,顺便把进度回调给apk
     */
    private boolean send() {
        if (commandInfoList != null && commandInfoList.size() > 0 && otaService != null) {
            byte[] content = commandInfoList.getFirst().content;
            OtaPrintf.INSTANCE.send(commandInfoList.size(), content);
            int sendType = commandInfoList.getFirst().is1531Flag ? OtaService.SEND_TYPE_1531 : OtaService.SEND_TYPE_1532;
            otaService.sendDataToDevice(content, sendType);
            OtaPrintf.INSTANCE.progress(commandInfoList.size());
            if (iUpdateProgressCallBack != null && totalPackage > 0) {
                iUpdateProgressCallBack.curUpdateProgress(totalPackage - commandInfoList.size());
            }
            return true;
        }
        return false;
    }
}
