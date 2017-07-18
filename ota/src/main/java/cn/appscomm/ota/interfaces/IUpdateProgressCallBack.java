package cn.appscomm.ota.interfaces;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：OTA升级进度及结果回调
 */
public interface IUpdateProgressCallBack {

    /**
     * 升级进度
     *
     * @param curPackage 目前已经发送包数
     */
    void curUpdateProgress(int curPackage);

    /**
     * 升级总包数
     *
     * @param totalPackage 总包数
     */
    void curUpdateMax(int totalPackage);

    /**
     * 升级结果
     *
     * @param result true:成功 false:失败
     */
    void updateResult(boolean result);
}
