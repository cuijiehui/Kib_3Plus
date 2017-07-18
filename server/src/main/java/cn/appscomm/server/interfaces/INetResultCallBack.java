package cn.appscomm.server.interfaces;

import cn.appscomm.server.mode.base.BaseResponse;

/**
 * 作者：hsh
 * 日期：2017/3/27
 * 说明：网络结果回调
 */

public interface INetResultCallBack {

    /**
     * 请求网络成功时返回
     *
     * @param responseCode 响应码
     * @param baseResponse 响应结果
     */
    void onSuccess(int responseCode, BaseResponse baseResponse);

    /**
     * 请求网络失败时返回
     *
     * @param responseCode 响应码
     */
    void onFail(int responseCode);
}
