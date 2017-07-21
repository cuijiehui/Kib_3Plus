package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.sp.SPKey;

/**
 * Created by cui on 2017/7/21.
 */

public class SelectContinentFragment extends BaseFragment {


    private Button continent_africa_bt,continent_asia_bt
            ,continent_australia_bt,continent_europe_bt
            ,continent_north_bt,continent_south_bt;

    private static SelectContinentFragment instance;
    public static SelectContinentFragment getInstance(){
        if(instance==null){
            instance=new SelectContinentFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.select_continent_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        continent_africa_bt= findViewById(R.id.continent_africa_bt);
        continent_asia_bt= findViewById(R.id.continent_asia_bt);
        continent_australia_bt= findViewById(R.id.continent_australia_bt);
        continent_europe_bt= findViewById(R.id.continent_europe_bt);
        continent_north_bt= findViewById(R.id.continent_north_bt);
        continent_south_bt= findViewById(R.id.continent_south_bt);
    }

    @Override
    public void initListener() {
        super.initListener();
        continent_africa_bt.setOnClickListener(this);
        continent_asia_bt.setOnClickListener(this);
        continent_australia_bt.setOnClickListener(this);
        continent_europe_bt.setOnClickListener(this);
        continent_north_bt.setOnClickListener(this);
        continent_south_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String continentNmae="";
        switch (v.getId()){
            case R.id.continent_africa_bt:
                LogUtils.i("continent_africa_bt");
                continentNmae=getString(R.string.continent_africa_tv);
                break;
            case R.id.continent_asia_bt:
                LogUtils.i("continent_asia_bt");
                continentNmae=getString(R.string.continent_asia_tv);

                break;
            case R.id.continent_australia_bt:
                LogUtils.i("continent_australia_bt");
                continentNmae=getString(R.string.continent_australia_tv);

                break;
            case R.id.continent_europe_bt:
                LogUtils.i("continent_europe_bt");
                continentNmae=getString(R.string.continent_europe_tv);

                break;
            case R.id.continent_north_bt:
                LogUtils.i("continent_north_bt");
                continentNmae=getString(R.string.continent_north_tv);

                break;
            case R.id.continent_south_bt:
                LogUtils.i("continent_south_bt");
                continentNmae=getString(R.string.continent_south_tv);

                break;
        }
        Bundle bundle=new Bundle();
        bundle.putString(SPKey.SP_RACE_CONTINENT_NAME,continentNmae);
        LogUtils.i("continentNmae="+continentNmae);
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CONTINENT_FRAGMENT,bundle);

    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_RACE_FRAGMENT);
        return true;
    }
}
