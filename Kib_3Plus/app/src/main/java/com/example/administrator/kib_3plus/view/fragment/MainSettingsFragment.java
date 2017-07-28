package com.example.administrator.kib_3plus.view.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

import static android.R.attr.version;
import static com.example.administrator.kib_3plus.view.manage.ContentViewManage.ABOUT_3PLUS_FRAGMENT;
import static com.example.administrator.kib_3plus.view.manage.ContentViewManage.FAMILY_SETTINGS_FRAGMENT;

/**
 * Created by cui on 2017/7/5.
 */

public class MainSettingsFragment extends BaseFragment {
   private static   MainSettingsFragment instance;
    TextView settings_band_settings_tv,settings_family_member_tv,settings_faq_tv,settings_feedback_tv,settings_about_3plus_tv,settings_ap_version_tv;
    Button settings_sign_out_bt;
    public static MainSettingsFragment getInstance(){
        if(instance==null){
            instance=new MainSettingsFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.main_settings_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        settings_band_settings_tv=findViewById(R.id.settings_band_settings_tv);
        settings_family_member_tv=findViewById(R.id.settings_family_member_tv);
        settings_faq_tv=findViewById(R.id.settings_faq_tv);
        settings_feedback_tv=findViewById(R.id.settings_feedback_tv);
        settings_about_3plus_tv=findViewById(R.id.settings_about_3plus_tv);
        settings_ap_version_tv=findViewById(R.id.settings_ap_version_tv);
        settings_sign_out_bt=findViewById(R.id.settings_sign_out_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        settings_band_settings_tv.setOnClickListener(this);
        settings_family_member_tv.setOnClickListener(this);
        settings_faq_tv.setOnClickListener(this);
        settings_feedback_tv.setOnClickListener(this);
        settings_about_3plus_tv.setOnClickListener(this);
        settings_ap_version_tv.setOnClickListener(this);
        settings_sign_out_bt.setOnClickListener(this);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        String version ="";
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(),0);
             version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        settings_ap_version_tv.setText(getString(R.string.settings_app_version_tv)+" "+version);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_band_settings_tv:
                LogUtils.i("settings_band_settings_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_FRAGMENT);

                break;
            case R.id.settings_family_member_tv:
                LogUtils.i("settings_family_member_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_SETTINGS_FRAGMENT);

                break;
            case R.id.settings_faq_tv:
                LogUtils.i("settings_faq_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAQ_FRAGMENT);

                break;
            case R.id.settings_feedback_tv:
                LogUtils.i("settings_feedback_tv");

                break;
            case R.id.settings_about_3plus_tv:
                LogUtils.i("settings_about_3plus_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.ABOUT_3PLUS_FRAGMENT);

                break;
            case R.id.settings_ap_version_tv:
                LogUtils.i("settings_ap_version_tv");

                break;
            case R.id.settings_sign_out_bt:
                LogUtils.i("settings_sign_out_bt");
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, false);
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_SIGN_L28t, false);
                String email=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,SPManager.DATA_STRING);
                String password=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_PASSWORD_l28t,SPManager.DATA_STRING);
                Bundle bundle=new Bundle();
                bundle.putString(LoginFragment.BUNDLE_EMAIL,email);
                bundle.putString(LoginFragment.BUNDLE_PASSWORD,password);
                PDB.INSTANCE.deleteAllChildInfo();
                PDB.INSTANCE.deleteAllSportL28T();
                PDB.INSTANCE.deleteAllBandSettingDB();
                PDB.INSTANCE.deleteAllSportCacheL28TDB();
                PDB.INSTANCE.deleteAllChoresL28TDB();
                PDB.INSTANCE.deleteAllRewardsL28TDB();
                PDB.INSTANCE.deleteAllRaceDB();
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.LOGIN_FRAGMENT);

                break;

        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
