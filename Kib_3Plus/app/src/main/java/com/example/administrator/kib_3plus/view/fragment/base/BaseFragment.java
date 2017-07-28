package com.example.administrator.kib_3plus.view.fragment.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.ToastUtil;
import com.example.administrator.kib_3plus.view.fragment.interfaces.IBaseFragment;

import org.greenrobot.eventbus.EventBus;

import static android.R.attr.fragment;

/**
 * BaseFragment
 * Created by cui on 2017/6/16.
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment ,View.OnClickListener{

    public static final String FRAGMENT_INDEX="fragment_index";
    /**
     * Fragment Content view
     */
    private View inflateView;
    /**
     * 所属Activity
     */
    private Activity activity;
    /**
     * 记录是否已经创建了,防止重复创建
     */
    private boolean viewCreated;

    /**
     * hide后，再次show一个Fragment时传入的bundle
     */
    private Bundle mSupportArguments;
    public BaseFragment() {
    }

    /**
     * 显示Toast消息
     *
     * @param msg 消息文本
     */
    public final void showToast(@NonNull String msg) {
        ToastUtil.INSTANCE.toast(msg);
    }

    /**
     * 显示Toast消息
     *
     * @param resId 消息文本字符串资源ID
     */
    public final void showToast(@StringRes int resId) {
//        ToastUtil.showShortToastMessage(resId);
    }
    public <VIEWS extends View> VIEWS getRootView(){
        return (VIEWS) inflateView;
    }

    /**
     * 通过ID查找控件
     *
     * @param viewId 控件资源ID
     * @param <VIEW> 泛型参数，查找得到的View
     * @return 找到了返回控件对象，否则返回null
     */
    final public <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        return (VIEW) inflateView.findViewById(viewId);
    }
    public void setSupportArguments(Bundle supportArguments) {
        mSupportArguments = supportArguments;
    }
    private Bundle getSupportArguments() {
        return mSupportArguments;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            parseArguments(getSupportArguments());
        }
    }
    /**
     * Fragment Argument解析
     * @param arguments
     */
    protected void parseArguments(Bundle arguments){

    }
    //    /**
//     * 发送消息,用于各个组件之间通信
//     *
//     * @param event 消息事件对象
//     */
//    public final <EVENT extends BaseEvent> void sendMessage(@NonNull EVENT event) {
//        // 发布事件
//        EventBus.getDefault().post(event);
//    }
    /**
     * 获取要替换的布局ID
     *
     * @return 替换的布局ID
     */
    public int getChildrenFragmentContainerResID() {
        return R.id.content_view_rl;
    }
    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    final public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止重复调用onCreate方法，造成在initData方法中adapter重复初始化问题
        parseArguments(getArguments());
        if (!viewCreated) {
            viewCreated = true;
            initData();
        }
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == inflateView) {
            // 强制竖屏显示
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            int layoutResId = getCreateViewLayoutId();
            if (layoutResId > 0)
                inflateView = inflater.inflate(getCreateViewLayoutId(), container, false);

            // 解决点击穿透问题
            inflateView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        return inflateView;
    }

    @Override
    final public void onViewCreated(View view, Bundle savedInstanceState) {
        if (viewCreated) {
            findView(view, savedInstanceState);
            initView(view, savedInstanceState);
            initDialog();
            initListener();
            viewCreated = false;
        }
    }


    public void onActivityReenter(int requestCode, int resultCode, Intent data){

    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解决ViewPager中的问题
        if (null != inflateView) {
            ((ViewGroup)  inflateView.getParent()).removeView(inflateView);
        }
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
    }

    @CallSuper
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void initData() {
    }

    @CallSuper
    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
    }

    @CallSuper
    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
    }

    @CallSuper
    @Override
    public void initListener() {
    }

    @CallSuper
    @Override
    public void initDialog() {
    }

    @Override
    public void onClick(View v) {
    }


}
