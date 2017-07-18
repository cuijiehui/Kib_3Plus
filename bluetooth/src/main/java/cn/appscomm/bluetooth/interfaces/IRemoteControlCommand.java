package cn.appscomm.bluetooth.interfaces;

/**
 * 作者：hsh
 * 日期：2017/4/27
 * 说明：手环下发到手机的远程命令接口
 * 1、P层需要实现该接口，然后把引用给蓝牙层
 * 2、蓝牙层会检查引用是否为空，不为空则下发命令
 */

public interface IRemoteControlCommand {

    /**
     * 检查音乐状态
     */
    void checkMusicStatus();

    /**
     * 下一首(手环设置手机)
     */
    void setPhoneNextSong();

    /**
     * 上一首(手环设置手机)
     */
    void setPhonePreSong();

    /**
     * 播放(手环设置手机)
     */
    void setPhonePlay();

    /**
     * 暂停(手环设置手机)
     */
    void setPhonePause();

    /**
     * 开始拍照
     */
    void startTakePhoto();

    /**
     * 结束拍照
     */
    void endTakePhoto();

    /**
     * 开始寻找手机
     */
    void startFindPhone();

    /**
     * 结束寻找手机
     */
    void endFindPhone();

    /**
     * 设置音量(手环设置手机)
     *
     * @param volumes 音量值
     */
    void setPhoneVolumes(int volumes);
}
