package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.BluetoothUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.DialogFragment.MainDeleteDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;

import static com.example.administrator.kib_3plus.R.id.family_setting_family_name_tv;
import static com.example.administrator.kib_3plus.R.id.family_setting_head_name_tv;
import static com.example.administrator.kib_3plus.R.id.family_setting_invite_tv;

/**
 * Created by cui on 2017/7/25.
 */

public class HeadFamilySettingsFragment extends BaseFragment {

    TextView head_account_emil_tv;
    EditText head_family_name_et;
    Button head_family_sign_bt;
    String userEmail="";
    MainDeleteDialogFragment mMainDeleteDialogFragment;

    private static HeadFamilySettingsFragment instance;

    public static HeadFamilySettingsFragment getInstance(){
        if(instance==null){
            instance=new HeadFamilySettingsFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        head_account_emil_tv= findViewById(R.id.head_account_emil_tv);
        head_family_name_et= findViewById(R.id.head_family_name_et);
        head_family_sign_bt= findViewById(R.id.head_family_sign_bt);

    }

    @Override
    public void initListener() {
        super.initListener();
        head_family_sign_bt.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        userEmail= (String) PSP.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,PSP.DATA_STRING);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        head_account_emil_tv.setText(userEmail);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.head_family_setting_layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_family_sign_bt:
                LogUtils.i("head_family_sign_bt");
                String hint="Are you sure you want to sign out";
                mMainDeleteDialogFragment= MainDeleteDialogFragment.newInstance(hint,this);
                mMainDeleteDialogFragment.show(getChildFragmentManager(),"HeadFamilySettingsFragment");
                break;
            case R.id.delete_dialog_cancel_bt:
                LogUtils.i("delete_dialog_cancel_bt");
                if(mMainDeleteDialogFragment!=null){
                    mMainDeleteDialogFragment.dismiss();
                }
                break;
            case R.id.delete_dialog_confirm_bt:
                LogUtils.i("delete_dialog_confirm_bt");
                if(mMainDeleteDialogFragment!=null){
                    mMainDeleteDialogFragment.dismiss();
                }
                String email=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_EMAIL_L28T,SPManager.DATA_STRING);
                String password=(String) SPManager.INSTANCE.getSPValue(SPKey.SP_USER_PASSWORD_l28t,SPManager.DATA_STRING);
                Bundle bundle=new Bundle();
                bundle.putString(LoginFragment.BUNDLE_EMAIL,email);
                bundle.putString(LoginFragment.BUNDLE_PASSWORD,password);
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_FAMILY_L28t, false);
                SPManager.INSTANCE.setSPValue(SPKey.SP_IS_SIGN_L28t, false);
                PDB.INSTANCE.deleteAllChildInfo();
                PDB.INSTANCE.deleteAllSportL28T();
                PDB.INSTANCE.deleteAllBandSettingDB();
                PDB.INSTANCE.deleteAllSportCacheL28TDB();
                PDB.INSTANCE.deleteAllChoresL28TDB();
                PDB.INSTANCE.deleteAllRewardsL28TDB();
                PDB.INSTANCE.deleteAllRaceDB();
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.LOGIN_FRAGMENT,bundle);
                break;
            case R.id.delete_dialog_delete_iv:
                LogUtils.i("delete_dialog_delete_iv");
                if(mMainDeleteDialogFragment!=null){
                    mMainDeleteDialogFragment.dismiss();
                }
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_SETTINGS_FRAGMENT);
        return true;
    }
}
