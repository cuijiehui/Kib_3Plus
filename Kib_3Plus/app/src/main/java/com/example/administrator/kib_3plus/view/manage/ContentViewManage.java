package com.example.administrator.kib_3plus.view.manage;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransactionBugFixHack;
import android.widget.FrameLayout;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.About3PlusFragemnt;
import com.example.administrator.kib_3plus.view.fragment.AddMemberFragment;
import com.example.administrator.kib_3plus.view.fragment.BandSettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.BandSettingsSettingFragment;
import com.example.administrator.kib_3plus.view.fragment.CatalogChoresFragment;
import com.example.administrator.kib_3plus.view.fragment.ChangePasswordFragment;
import com.example.administrator.kib_3plus.view.fragment.ChoresFragment;
import com.example.administrator.kib_3plus.view.fragment.ContinentFragment;
import com.example.administrator.kib_3plus.view.fragment.CreateAccountFragment;
import com.example.administrator.kib_3plus.view.fragment.CreateFamilyFragment;
import com.example.administrator.kib_3plus.view.fragment.CreateNewAccountFragment;
import com.example.administrator.kib_3plus.view.fragment.DataParticularsFragment;
import com.example.administrator.kib_3plus.view.fragment.FAQFragment;
import com.example.administrator.kib_3plus.view.fragment.FamilyCreatedFrafment;
import com.example.administrator.kib_3plus.view.fragment.FamilyNameSettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.FamilySettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.ForgotPasswordFragment;
import com.example.administrator.kib_3plus.view.fragment.GiveCoinsFragment;
import com.example.administrator.kib_3plus.view.fragment.HeadFamilySettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.InactivityAlertFragment;
import com.example.administrator.kib_3plus.view.fragment.InviteGuardianFragment;
import com.example.administrator.kib_3plus.view.fragment.JoinFamilyFragment;
import com.example.administrator.kib_3plus.view.fragment.LoginFragment;
import com.example.administrator.kib_3plus.view.fragment.MainFailyFragment;
import com.example.administrator.kib_3plus.view.fragment.MainLeaderboardFragment;
import com.example.administrator.kib_3plus.view.fragment.MainRaceFragment;
import com.example.administrator.kib_3plus.view.fragment.MainSettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.MemberParticularFragment;
import com.example.administrator.kib_3plus.view.fragment.MemderRewardsFragemnt;
import com.example.administrator.kib_3plus.view.fragment.PresetSleepFragment;
import com.example.administrator.kib_3plus.view.fragment.RaceIngFragment;
import com.example.administrator.kib_3plus.view.fragment.RaceReadyFragment;
import com.example.administrator.kib_3plus.view.fragment.RewardsFragment;
import com.example.administrator.kib_3plus.view.fragment.SelectContinentFragment;
import com.example.administrator.kib_3plus.view.fragment.SelectDeviceFragment;
import com.example.administrator.kib_3plus.view.fragment.SetUpDeviceFragment;
import com.example.administrator.kib_3plus.view.fragment.SetUpNewDeviceFragment;
import com.example.administrator.kib_3plus.view.fragment.SettingResetFragment;
import com.example.administrator.kib_3plus.view.fragment.SettingTimeFragment;
import com.example.administrator.kib_3plus.view.fragment.SettingUnitsFragment;
import com.example.administrator.kib_3plus.view.fragment.SettingVibrationFragment;
import com.example.administrator.kib_3plus.view.fragment.WelcomeFragmet;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

import static android.R.attr.fragment;
import static android.os.Looper.getMainLooper;
import static com.example.administrator.kib_3plus.view.manage.ContentViewManage.LaunchMode.STANDARD;

/**
 * Created by cui on 2017/6/16.
 */

public class ContentViewManage {
    private FragmentActivity mContext;
    private static ContentViewManage mContentViewManage;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FrameLayout content_view_rl;

    public static ContentViewManage getInstance(){
        if(mContentViewManage==null){
            mContentViewManage=new ContentViewManage();
        }
        return mContentViewManage;
    }
    public void init(FragmentActivity activity){
        mContext=activity;
        fragmentManager = activity.getSupportFragmentManager();
         fragmentTransaction = fragmentManager.beginTransaction();
        content_view_rl=(FrameLayout) activity.findViewById(R.id.content_view_rl);
    }


    /**
     * Fragment回退管理的队列
     */
    private Deque<String> mFragmentBackDeque = new ArrayDeque<>();
    /**
     * 当前显示的Fragment
     */
    private Fragment mCurrentFragment;

    /**
     * 调用popBackStack系列方法，可通过设置此变量实现通信，为onSupportBackPressed()入参
     */
    private Bundle mSupportBackStackArguments;
    private Handler mHandler;



    public boolean onBackPressed() {
        if (mFragmentBackDeque == null || mCurrentFragment == null) {
            return false;
        }

        //检查当前Fragment的ChildFragmentManager回退栈是否需要回退
        int childStackEntryCount = mCurrentFragment.getChildFragmentManager().getBackStackEntryCount();
        if (childStackEntryCount > 0) {
            mCurrentFragment.getChildFragmentManager().popBackStackImmediate();
            return true;
        }

        //检查当前Fragment的自维护的回退栈是否需要回退
        if (mFragmentBackDeque.size() >= 2) {
            showOneFragmentOnBackPressed();
            return true;
        }

        if(mCurrentFragment instanceof BaseFragment){
            BaseFragment basicFragment = (BaseFragment)mCurrentFragment;
            if(basicFragment.onBackPressed()){
                return true;
            }
        }

        return false;

    }


    public void replaceOneFragment(String fragmentTab,Fragment fragment) {
        replaceOneFragment(0, fragmentTab, null, true, fragment);
    }

    public void replaceOneFragment(String fragmentTab, Bundle bundle,Fragment fragment) {
        replaceOneFragment(0, fragmentTab, bundle, true, fragment);
    }

    public void replaceOneFragment(@IdRes int resId, String fragmentTab,Fragment fragment) {
        replaceOneFragment(resId, fragmentTab, null, true, fragment);
    }

    public void replaceOneFragment(@IdRes int resId, String fragmentTab, Bundle bundle,Fragment fragment) {
        replaceOneFragment(resId, fragmentTab, bundle, true, fragment);
    }

    public void replaceOneFragment(String fragmentTab, boolean isAddToBack,Fragment fragment) {
        replaceOneFragment(0, fragmentTab, null, isAddToBack, fragment);
    }

    public void replaceOneFragment(@IdRes int resId, String fragmentTab, boolean isAddToBack,Fragment fragment) {
        replaceOneFragment(resId, fragmentTab, null, isAddToBack, fragment);
    }

    public void replaceOneFragment(String fragmentTab, Bundle bundle, boolean isAddToBack,Fragment fragment) {
        replaceOneFragment(0, fragmentTab, bundle, isAddToBack, fragment);
    }

    /**
     * 替换当前Fragment里的某个FrameLayout布局
     * @param resId 被替换的布局ID
     * @param fragmentTab 新的Fragment名
     * @param arguments 传入新的Fragment的Bundle
     * @param isAddToBack 是否加入回退栈
     */
    private void replaceOneFragment(@IdRes int resId, String fragmentTab, Bundle arguments, boolean isAddToBack,Fragment fragment) {
        int childrenFragmentContainerResID = ((BaseFragment) mCurrentFragment).getChildrenFragmentContainerResID();
        int layoutId = resId <= 0 ? childrenFragmentContainerResID : resId;
        Bundle bundle=arguments;
        if (layoutId == -1) {
            throw new IllegalStateException("You should overwrite getChildrenFragmentContainerResID from BasicFragment");
        }
        if(fragment==mCurrentFragment){
            return;
        }
        FragmentManager manager = mContext.getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
//
            transaction
                    .setCustomAnimations(R.anim.right_enter, R.anim.left_exit, R.anim.left_enter, R.anim.right_exit);
            transaction.replace(R.id.content_view_rl,fragment , fragmentTab);

            if (false) {
                transaction.addToBackStack(fragmentTab);
            }
            manager.popBackStack(null,0);
            if(arguments!=null){
                if(fragment.getArguments()!=null){
                   Bundle bu= fragment.getArguments();
                    bu.clear();
                    bu.putAll(bundle);
                }else{
                    fragment.setArguments((Bundle)bundle);

                }
            }
            transaction.commit();
        }

        mCurrentFragment=fragment;
    }


    /**
     * 显示特定Tag的Fragment,如果是第一次显示,则新建并添加该Fragment
     *
     * @param fragmentTab
     */
    public void showOneFragment(String fragmentTab,Fragment fragment) {
        showOneFragment(fragmentTab, fragment, null, true, STANDARD,true);
    }

    /**
     * 显示特定Tag的Fragment,如果是第一次显示,则新建并添加该Fragment
     *
     * @param fragmentTab
     * @param isAddToStack 第一次显示时，是否加入回退栈
     */
    public void showOneFragment(String fragmentTab,Fragment fragment, boolean isAddToStack) {
        showOneFragment(fragmentTab, fragment, null, isAddToStack, STANDARD,true);
    }

    /**
     * 显示特定Tag的Fragment,如果是第一次显示,则新建并添加该Fragment
     *
     * @param fragmentTab
     * @param isAddToStack
     * @param launchMode
     * @param transitionAnimationEnable
     */
    public void showOneFragment(String fragmentTab,Fragment fragment, boolean isAddToStack,LaunchMode launchMode, boolean transitionAnimationEnable) {
        showOneFragment(fragmentTab, null, isAddToStack, launchMode,transitionAnimationEnable);
    }


    /**
     * 显示特定Tag的Fragment,如果是第一次显示,则新建并添加该Fragment
     *
     * @param fragmentTab
     * @param arguments
     */
    public void showOneFragment(String fragmentTab,Fragment fragment, Bundle arguments) {
        showOneFragment(fragmentTab, fragment, arguments, true, STANDARD,true);
    }

    public void showOneFragment(String fragmentTab,Fragment fragment, Bundle arguments, LaunchMode launchMode) {
        showOneFragment(fragmentTab, fragment, arguments, true, launchMode,true);
    }

    /**
     * 显示特定Tag的Fragment,如果是第一次显示,则新建并添加该Fragment
     *
     * @param fragmentTab    Fragment标签名
     * @param arguments      传入Fragment的参数Bundle
     * @param isAddBackStack 是否加入FragmentManager回退栈
     * @param launchMode     启动模式 分为： STANDARD，SINGLE，SINGLE_ENHANCEMENT
     */
    private void showOneFragment(String fragmentTab,Fragment fragment, Bundle arguments, boolean isAddBackStack, LaunchMode launchMode, boolean transitionAnimationEnable) {
        LogUtils.i("fragmentTab="+fragmentTab);
        FragmentManager manager = mContext.getSupportFragmentManager();
        if (manager == null) {
            return;
        }

        Fragment fragmentByTag = manager.findFragmentByTag(fragmentTab);

        if (fragmentByTag != null && launchMode == LaunchMode.SINGLE_ENHANCEMENT) {
            popMultipleBackStack(fragmentTab, arguments);
            return;
        }

        FragmentTransaction transaction = manager.beginTransaction();

        //设置过渡动画
        if(transitionAnimationEnable) {
            transaction.setCustomAnimations(R.anim.right_enter, R.anim.left_exit, 0, 0);
        }

        //隐藏当前所有fragment
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment f : fragments) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }
        //第一次添加该Fragment
        if (fragmentByTag == null) {
            mCurrentFragment = fragment;
            if(launchMode != LaunchMode.DEFAULT) {
                mFragmentBackDeque.push(fragmentTab);
            }
            transaction.add(getFragmentContainerResID(), mCurrentFragment, fragmentTab);
            if (isAddBackStack) {
                transaction.addToBackStack(fragmentTab);
            }
            transaction.commitAllowingStateLoss();
            return;
        }

        if (!(fragmentByTag instanceof BaseFragment)) {
            throw new ClassCastException("fragment must extends BasicFragment");
        }

        //更新Arguments，按后退键时Fragment里的后退方法里使用
        if (arguments != null) {
            setSupportBackStackArguments(arguments);
        }

        //根据启动模式类型，采取不同的方式维护后退栈
        switch (launchMode) {
            case STANDARD:
                mFragmentBackDeque.push(fragmentTab);
                break;
            case SINGLE:
                synchronizeFragmentBackDequeWhenSingleLaunchMode(fragmentTab);
                break;
        }

        BaseFragment basicFragment = (BaseFragment) fragmentByTag;
        mCurrentFragment = fragmentByTag;
        basicFragment.setSupportArguments(arguments);
        transaction.show(fragmentByTag);
        transaction.commitAllowingStateLoss();
    }


    /**
     * 获取要替换的布局ID
     *
     * @return fragment替换的布局ID
     */
    protected int getFragmentContainerResID() {
        return R.id.content_view_rl;
    }

    /**
     * 提供fragment
     * @param fragmentTab Fragment标签
     * @param arguments 传入Fragment的参数
     * @return
     */
    protected BaseFragment fragmentProvider(String fragmentTab, Bundle arguments) {
        return null;
    }

    /**
     * 返回键显示特定Tag的Fragment
     */
    private void showOneFragmentOnBackPressed() {
        mFragmentBackDeque.pop();
        String fragmentTab = mFragmentBackDeque.peek();

        FragmentManager manager = mContext.getSupportFragmentManager();
        if (manager != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.left_enter, R.anim.right_exit, 0, 0);

            List<Fragment> fragments = manager.getFragments();
            for (Fragment f : fragments) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
            Fragment fragmentByTag = manager.findFragmentByTag(fragmentTab);
            if (fragmentByTag != null) {
                mCurrentFragment = fragmentByTag;
                transaction.show(fragmentByTag);
            }

            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 一次弹出多个Fragment
     *
     * @param tag
     * @param popFlag 0 弹出不包括tag所指的Fragment；1 表示弹出包括当前tag的fragment
     * @param bundle  回退栈 传输参数
     */
    public void popMultipleBackStack(String tag, int popFlag, Bundle bundle) {
        if (bundle != null) {
            setSupportBackStackArguments(bundle);
        }
        //维护后退栈内容，保持同步
        if (mFragmentBackDeque.contains(tag)) {
            String peekElement = mFragmentBackDeque.peek();
            while (!tag.equals(peekElement)) {
                if (mFragmentBackDeque.isEmpty()) {
                    break;
                }
                mFragmentBackDeque.pop();
                peekElement = mFragmentBackDeque.peek();
            }

            if (popFlag == 1) {
                if (!mFragmentBackDeque.isEmpty()) {
                    mFragmentBackDeque.pop();
                }
            }
        }

        FragmentManager manager = mContext.getSupportFragmentManager();
        manager.popBackStackImmediate(tag, popFlag);
    }

    /**
     * 一次弹出多个Fragment
     *
     * @param tag
     * @param bundle 回退栈 传输参数
     */
    private void popMultipleBackStack(String tag, Bundle bundle) {
        if (bundle != null) {
            setSupportBackStackArguments(bundle);
        }
        synchronizeFragmentBackDequeWhenSingleLaunchMode(tag);

        FragmentManager manager = mContext.getSupportFragmentManager();
        manager.popBackStackImmediate(tag, 0);

        reorderAvailIndicesToFixBug();
    }

    /**
     * 修复Fragment出栈后，栈内顺序不正确的bug
     */
    private void reorderAvailIndicesToFixBug(){
        if(mHandler == null) {
            mHandler = new Handler(getMainLooper());
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                FragmentTransactionBugFixHack.reorderIndices(mContext.getSupportFragmentManager());
            }
        });
    }

    /**
     * 单例模式下，管理自维护的Fragment后退栈
     *
     * @param tag
     */
    private void synchronizeFragmentBackDequeWhenSingleLaunchMode(String tag) {
        if (mFragmentBackDeque.contains(tag)) {
            String peekElement = mFragmentBackDeque.peek();
            while (!tag.equals(peekElement)) {
                if (mFragmentBackDeque.isEmpty()) {
                    break;
                }
                mFragmentBackDeque.pop();
                peekElement = mFragmentBackDeque.peek();
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (mFragmentBackDeque == null || mCurrentFragment == null) {
//            return;
//        }
//
//        //检查当前Fragment的ChildFragmentManager回退栈是否需要回退
//        int childStackEntryCount = mCurrentFragment.getChildFragmentManager().getBackStackEntryCount();
//        if (childStackEntryCount > 0) {
//            mCurrentFragment.getChildFragmentManager().popBackStackImmediate();
//            return;
//        }
//
//        //检查当前Fragment的自维护的回退栈是否需要回退
//        if (mFragmentBackDeque.size() >= 2) {
//            showOneFragmentOnBackPressed();
//            return;
//        }
//
//        if(mCurrentFragment instanceof BasicFragment){
//            BasicFragment basicFragment = (BasicFragment)mCurrentFragment;
//            if(basicFragment.onBackPressed()){
//                return;
//            }
//        }
//
//        finish();
//    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public Deque<String> getFragmentBackDeque() {
        return mFragmentBackDeque;
    }

    public Bundle getSupportBackStackArguments() {
        if (mSupportBackStackArguments == null) {
            mSupportBackStackArguments = new Bundle();
        }
        return mSupportBackStackArguments;
    }

    public void setSupportBackStackArguments(Bundle supportBackStackArguments) {
        this.mSupportBackStackArguments = supportBackStackArguments;
    }
    public void setFragmentType(String className){
        setFragmentType(className,null);
    }
    /**
     * 因为界面和标题导航栏是配套的所以只需要把calss的名字传进来就可以了；
     * @param className
     */
    public void setFragmentType(String className,Bundle bundle){
        switch (className){
            case WELCOME_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.colorWhite);
                replaceOneFragment(WelcomeFragmet.class.getSimpleName(),bundle, WelcomeFragmet.getInstance());
                break;
            case CREATE_ACCOUNT_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_create_account),null,null);
                replaceOneFragment(CreateAccountFragment.class.getSimpleName(),bundle, CreateAccountFragment.getInstance());
                break;
            case LOGIN_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_login_kids),null,null);
                replaceOneFragment(LoginFragment.class.getSimpleName(),bundle, LoginFragment.getInstance());
                break;
            case FORGOT_PASSWORD_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_forgot_password),null,null);
                replaceOneFragment(ForgotPasswordFragment.class.getSimpleName(),bundle, ForgotPasswordFragment.getInstance());
                break;
            case CHANGE_PASSWORD_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_change_password),null,null);
                replaceOneFragment(ChangePasswordFragment.class.getSimpleName(),bundle, ChangePasswordFragment.getInstance());
                break;
            case CREATE_NEW_ACCOUNT_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.colorWhite);
                replaceOneFragment(CreateNewAccountFragment.class.getSimpleName(),bundle, CreateNewAccountFragment.getInstance());
                break;
            case ADD_MEMBER_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,mContext.getString(R.string.title_add_member),null,mContext.getString(R.string.title_save));
                replaceOneFragment(AddMemberFragment.class.getSimpleName(),bundle, AddMemberFragment.getInstance());
                break;
            case CREATE_FAMILY_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.colorWhite);
                replaceOneFragment(CreateFamilyFragment.class.getSimpleName(),bundle, CreateFamilyFragment.getInstance());
                break;
            case FAMILY_CREATED_FRAFMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.colorWhite);
                replaceOneFragment(FamilyCreatedFrafment.class.getSimpleName(),bundle, FamilyCreatedFrafment.getInstance());
                break;
            case JOIN_FAMILY_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.colorWhite);
                replaceOneFragment(JoinFamilyFragment.class.getSimpleName(),bundle, JoinFamilyFragment.getInstance());
                break;
            case SELECT_DEVICE_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_select_device),null,null);
                replaceOneFragment(SelectDeviceFragment.class.getSimpleName(),bundle, SelectDeviceFragment.getInstance());
                break;
            case SET_UP_DEVICE_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_set_up_device),null,null);
                replaceOneFragment(SetUpDeviceFragment.class.getSimpleName(),bundle, SetUpDeviceFragment.getInstance());
                break;
            case SET_UP_NEW_DEVICE_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_GONE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_set_up_device),null,null);
                replaceOneFragment(SetUpNewDeviceFragment.class.getSimpleName(),bundle, SetUpNewDeviceFragment.getInstance());
                break;
            case MAIN_FAILY_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
               String familyName=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_FAMILY_NAME_L28t,SPManager.DATA_STRING);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_MAINNAME,familyName+"Family",null,null);
                replaceOneFragment(MainFailyFragment.class.getSimpleName(),bundle, MainFailyFragment.getInstance());
                break;
            case MAIN_SETTINGS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_MAINNAME,mContext.getString(R.string.title_settings),null,null);
                replaceOneFragment(MainSettingsFragment.class.getSimpleName(),bundle, MainSettingsFragment.getInstance());
                break;
            case MAIN_LEADERBOARD_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_MAINNAME,mContext.getString(R.string.title_leaderboard),null,null);
                replaceOneFragment(MainLeaderboardFragment.class.getSimpleName(),bundle, MainLeaderboardFragment.getInstance());
                break;
            case MAIN_RACE_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.blueColorTextBtn);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE,mContext.getString(R.string.title_leaderboard),null,null);
                replaceOneFragment(MainRaceFragment.class.getSimpleName(),bundle, MainRaceFragment.getInstance());
                break;
            case BAND_SETTINGS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_band_settings),null,null);
                replaceOneFragment(BandSettingsFragment.class.getSimpleName(),bundle, BandSettingsFragment.getInstance());
                break;
            case BAND_SETTINGS_SETTING_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_band_settings),null,null);
                replaceOneFragment(BandSettingsSettingFragment.class.getSimpleName(),bundle, BandSettingsSettingFragment.getInstance());
                break;
            case SETTING_UNITS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_units),null,null);
                replaceOneFragment(SettingUnitsFragment.class.getSimpleName(),bundle, SettingUnitsFragment.getInstance());
                break;
            case SETTING_VIBRATION_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_units),null,null);
                replaceOneFragment(SettingVibrationFragment.class.getSimpleName(),bundle, SettingVibrationFragment.getInstance());
                break;
            case SETTING_TIME_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_time_format),null,null);
                replaceOneFragment(SettingTimeFragment.class.getSimpleName(),bundle, SettingTimeFragment.getInstance());
                break;
            case ABOUT_3PLUS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_about_3plus),null,null);
                replaceOneFragment(About3PlusFragemnt.class.getSimpleName(),bundle, About3PlusFragemnt.getInstance());
                break;
            case FAQ_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_faq),null,null);
                replaceOneFragment(FAQFragment.class.getSimpleName(),bundle, FAQFragment.getInstance());
                break;
            case PRESET_SLEEP_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,mContext.getString(R.string.title_preset_sleep),null,mContext.getString(R.string.title_save));
                replaceOneFragment(PresetSleepFragment.class.getSimpleName(),bundle, PresetSleepFragment.getInstance());
                break;
            case SEEING_RESET_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_reset),null,null);
                replaceOneFragment(SettingResetFragment.class.getSimpleName(),bundle, SettingResetFragment.getInstance());
                break;
            case MEMBER_PARTICULAR_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE,mContext.getString(R.string.title_reset),null,null);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.menber_green_color);
                replaceOneFragment(MemberParticularFragment.class.getSimpleName(),bundle, MemberParticularFragment.getInstance());
                break;
            case DATA_PARTICULARS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE,mContext.getString(R.string.title_reset),null,null);
                TitleManage.getInstance().setWindowStatusBarColor(mContext,R.color.main_bg_color);
                replaceOneFragment(DataParticularsFragment.class.getSimpleName(),bundle, DataParticularsFragment.getInstance());
                break;
            case CATALOG_CHORES_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
               String name= bundle.getString("name");
                TitleManage.getInstance().setType(TitleManage.TITLE_WHITE_BACK_NAME_EDIT,mContext.getString(R.string.title_reset),name,mContext.getString(R.string.edit_tv));
                replaceOneFragment(CatalogChoresFragment.class.getSimpleName(),bundle, CatalogChoresFragment.getInstance());
                break;
            case Memder_REWARDS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                String nameR= bundle.getString("name");
                TitleManage.getInstance().setType(TitleManage.TITLE_WHITE_BACK_NAME_EDIT,mContext.getString(R.string.title_reset),nameR,mContext.getString(R.string.edit_tv));
                replaceOneFragment(MemderRewardsFragemnt.class.getSimpleName(),bundle, MemderRewardsFragemnt.getInstance());
                break;
            case INACTIVITY_ALERT_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,mContext.getString(R.string.title_inactivity_alert),null,mContext.getString(R.string.title_save));
                replaceOneFragment(InactivityAlertFragment.class.getSimpleName(),bundle, InactivityAlertFragment.getInstance());
                break;
            case GIVE_COINS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_give_coins),null,mContext.getString(R.string.title_save));
                replaceOneFragment(GiveCoinsFragment.class.getSimpleName(),bundle, GiveCoinsFragment.getInstance());
                break;
            case DEDUCT_COINS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_give_coins),null,mContext.getString(R.string.title_save));
                replaceOneFragment(GiveCoinsFragment.class.getSimpleName(),bundle, GiveCoinsFragment.getInstance());
                break;
            case CHORES_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,mContext.getString(R.string.title_chores),null,mContext.getString(R.string.title_save));
                replaceOneFragment(ChoresFragment.class.getSimpleName(),bundle, ChoresFragment.getInstance());
                break;
            case REWARDS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,mContext.getString(R.string.title_chores),null,mContext.getString(R.string.title_save));
                replaceOneFragment(RewardsFragment.class.getSimpleName(),bundle, RewardsFragment.getInstance());
                break;
            case SELECT_CONTINENT_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_GONE,mContext.getString(R.string.title_chores),null,mContext.getString(R.string.title_save));
                replaceOneFragment(SelectContinentFragment.class.getSimpleName(),bundle, SelectContinentFragment.getInstance());
                break;
            case CONTINENT_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
               String continentName= bundle.getString(SPKey.SP_RACE_CONTINENT_NAME);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,continentName,null,mContext.getString(R.string.title_save));
                replaceOneFragment(ContinentFragment.class.getSimpleName(),bundle, ContinentFragment.getInstance());
                break;
            case RACE_READY_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,"",null,mContext.getString(R.string.title_save));
                replaceOneFragment(RaceReadyFragment.class.getSimpleName(),bundle, RaceReadyFragment.getInstance());
                break;
            case RACE_ING_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,"",null,mContext.getString(R.string.title_save));
                replaceOneFragment(RaceIngFragment.class.getSimpleName(),bundle, RaceIngFragment.getInstance());
                break;
            case FAMILY_SETTINGS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_family_member_settings),null,mContext.getString(R.string.title_save));
                replaceOneFragment(FamilySettingsFragment.class.getSimpleName(),bundle, FamilySettingsFragment.getInstance());
                break;
            case HEAD_FAMILY_SETTINGS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_family_member_settings),null,mContext.getString(R.string.title_save));
                replaceOneFragment(HeadFamilySettingsFragment.class.getSimpleName(),bundle, HeadFamilySettingsFragment.getInstance());
                break;
            case FAMILY_NAME_SETTINGS_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_family_member_settings),null,mContext.getString(R.string.title_save));
                replaceOneFragment(FamilyNameSettingsFragment.class.getSimpleName(),bundle, FamilyNameSettingsFragment.getInstance());
                break;
            case INVITE_GUARDIAN_FRAGMENT:
                NavigationManage.getInstance().setType(NavigationManage.NA_TYPE_VISIBLE);
                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,mContext.getString(R.string.title_invite_new_guardian),null,mContext.getString(R.string.title_save));
                replaceOneFragment(InviteGuardianFragment.class.getSimpleName(),bundle, InviteGuardianFragment.getInstance());
                break;
        }
    }
    /**
     * Fragment的calss名称
     * 操作时可以打开Fragment的包，需要哪个Fragment，直接选择这个Fragment，然后ctrl+C在复制到这里来就可以了
     * 这样不用打还能防止打错
     *
     */
    public static final String WELCOME_FRAGMENT="WelcomeFragmet";
    public static final String CREATE_ACCOUNT_FRAGMENT="CreateAccountFragment";
    public static final String LOGIN_FRAGMENT= "LoginFragment";
    public static final String FORGOT_PASSWORD_FRAGMENT= "ForgotPasswordFragment";
    public static final String CHANGE_PASSWORD_FRAGMENT= "ChangePasswordFragment";
    public static final String CREATE_NEW_ACCOUNT_FRAGMENT= "CreateNewAccountFragment";
    public static final String ADD_MEMBER_FRAGMENT= "AddMemberFragment";
    public static final String CREATE_FAMILY_FRAGMENT= "CreateFamilyFragment";
    public static final String FAMILY_CREATED_FRAFMENT= "FamilyCreatedFrafment";
    public static final String JOIN_FAMILY_FRAGMENT= "JoinFamilyFragment";
    public static final String SELECT_DEVICE_FRAGMENT= "SelectDeviceFragment";
    public static final String SET_UP_DEVICE_FRAGMENT= "SetUpDeviceFragment";
    public static final String SET_UP_NEW_DEVICE_FRAGMENT= "SetUpNewDeviceFragment";
    public static final String MAIN_FAILY_FRAGMENT= "MainFailyFragment";
    public static final String MAIN_SETTINGS_FRAGMENT= "MainSettingsFragment";
    public static final String MAIN_LEADERBOARD_FRAGMENT= "MainLeaderboardFragment";
    public static final String MAIN_RACE_FRAGMENT= "MainRaceFragment";
    public static final String BAND_SETTINGS_FRAGMENT= "BandSettingsFragment";
    public static final String BAND_SETTINGS_SETTING_FRAGMENT= "BandSettingsSettingFragment";
    public static final String SETTING_UNITS_FRAGMENT= "SettingUnitsFragment";
    public static final String SETTING_VIBRATION_FRAGMENT= "SettingVibrationFragment";
    public static final String SETTING_TIME_FRAGMENT= "SettingTimeFragment";
    public static final String ABOUT_3PLUS_FRAGMENT= "About3PlusFragemnt";
    public static final String FAQ_FRAGMENT= "FAQFragment";
    public static final String PRESET_SLEEP_FRAGMENT= "PresetSleepFragment";
    public static final String SEEING_RESET_FRAGMENT= "SettingResetFragment";
    public static final String MEMBER_PARTICULAR_FRAGMENT= "MemberParticularFragment";
    public static final String DATA_PARTICULARS_FRAGMENT= "DataParticularsFragment";
    public static final String CATALOG_CHORES_FRAGMENT= "CatalogChoresFragment";
    public static final String Memder_REWARDS_FRAGMENT= "MemderRewardsFragemnt";
    public static final String INACTIVITY_ALERT_FRAGMENT= "InactivityAlertFragment";
    public static final String GIVE_COINS_FRAGMENT= "GiveCoinsFragment";
    public static final String DEDUCT_COINS_FRAGMENT= "DeductCoinsFragment";
    public static final String CHORES_FRAGMENT= "ChoresFragment";
    public static final String REWARDS_FRAGMENT= "RewardsFragment";
    public static final String SELECT_CONTINENT_FRAGMENT= "SelectContinentFragment";
    public static final String CONTINENT_FRAGMENT= "ContinentFragment";
    public static final String RACE_READY_FRAGMENT= "RaceReadyFragment";
    public static final String RACE_ING_FRAGMENT= "RaceIngFragment";
    public static final String FAMILY_SETTINGS_FRAGMENT= "FamilySettingsFragment";
    public static final String HEAD_FAMILY_SETTINGS_FRAGMENT= "HeadFamilySettingsFragment";
    public static final String FAMILY_NAME_SETTINGS_FRAGMENT= "FamilyNameSettingsFragment";
    public static final String INVITE_GUARDIAN_FRAGMENT= "InviteGuardianFragment";

    /**
     * fragment 启动模式
     */
    public enum LaunchMode {

        /**
         * 默认 不记录回退记录
         */
        DEFAULT,
        /**
         * 标准模式
         */
        STANDARD,
        /**
         * 单例模式，其他Fragment从自维护的mFragmentBackDeque栈里退出
         */
        SINGLE,
        /**
         * 强化版单例模式，其他Fragment从FragmentManager栈和自维护的mFragmentBackDeque栈里退出
         */
        SINGLE_ENHANCEMENT,
    }
}
