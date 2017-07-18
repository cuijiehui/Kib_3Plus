package com.example.administrator.kib_3plus.view.fragment.interfaces;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * baseFragment接口
 * Created by cui on 2017/6/16.
 */

public interface IBaseFragment {
    /**
     * 用于Fragment在oncreateview方法中设置view
     * @return 必须是资源文件的id
     */
    @LayoutRes
    int getCreateViewLayoutId();

    /**
     * 用于初始化成员变量和获取intent传递过来的数据
     */
    void initData();

    /**
     * 用于初始化view
     * @param inflateView
     * @param savedInstanceState
     */
    void findView(View inflateView, Bundle savedInstanceState);

    /**
     * 用于设置view显示的数据
     * @param inflateView
     * @param savedInstanceState
     */
    void initView(View inflateView, Bundle savedInstanceState);

    /**
     * 设置监听
     */
    void initListener();

    /**
     * 用于初始化对话框
     */
    void initDialog();

    /**
     * 处理返回键的事件
     * @return
     */
    boolean onBackPressed();
}
