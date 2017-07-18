package cn.appscomm.presenter.implement;

import cn.appscomm.presenter.interfaces.IRemoteControl;
import cn.appscomm.presenter.interfaces.PVOtherCall;
import cn.appscomm.presenter.remotecontrol.RemoteControlManager;

/**
 * 作者：hsh
 * 日期：2017/3/6
 * 说明：处理其他命令的P
 * 1、远程拍照
 */
public enum POther implements PVOtherCall {
    INSTANCE;

    @Override
    public void registerRemoteTakePhoto(IRemoteControl iRemoteControl) {
        RemoteControlManager.INSTANCE.registerRemoteTakePhoto(iRemoteControl);
    }
}
