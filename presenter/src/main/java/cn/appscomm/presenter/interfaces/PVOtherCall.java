package cn.appscomm.presenter.interfaces;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：其他命令(如远程控制)PV间的接口
 */

public interface PVOtherCall {

    /**
     * 注册远程拍照
     *
     * @param iRemoteControl 远程控制结果回调
     */
    void registerRemoteTakePhoto(IRemoteControl iRemoteControl);
}
