package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.RaceContinentListMode;
import com.example.administrator.kib_3plus.view.fragment.Adapter.ContinentAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

import static cn.appscomm.sp.SPKey.SP_RACE_CONTINENT_STEP;

/**
 * Created by cui on 2017/7/21.
 */

public class ContinentFragment extends BaseFragment implements MyItemClickListener {
    private RecyclerView continent_rv;
    private String continentName;
    private  List<RaceContinentListMode> dataContinent=new ArrayList<>();
    private ContinentAdapter mContinentAdapter;
    private static ContinentFragment instance;
    public static ContinentFragment getInstance(){
        if(instance==null){
            instance=new ContinentFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        continent_rv= findViewById(R.id.continent_rv);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        continentName=bundle.getString(SPKey.SP_RACE_CONTINENT_NAME);
        if(continentName.equals(getString(R.string.continent_africa_tv))){
            dataContinent= PublicData.africaContinent;
        }else if(continentName.equals(getString(R.string.continent_asia_tv))){
            dataContinent= PublicData.asiaContinent;
        }else if(continentName.equals(getString(R.string.continent_australia_tv))){
            dataContinent= PublicData.australiaContinent;
        }else if(continentName.equals(getString(R.string.continent_europe_tv))){
            dataContinent= PublicData.europeContinent;
        }else if(continentName.equals(getString(R.string.continent_north_tv))){
            dataContinent= PublicData.northContinent;
        }else if(continentName.equals(getString(R.string.continent_south_tv))){
            dataContinent= PublicData.southContinent;
        }
        LogUtils.i("continentName="+continentName);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mContinentAdapter!=null){
            mContinentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        mContinentAdapter=  new  ContinentAdapter(getContext(),dataContinent);
        mContinentAdapter.setOnItemClickListener(this);
        continent_rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        continent_rv.setAdapter(mContinentAdapter);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.continent_layout;
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_CONTINENT_FRAGMENT);

        return true;
    }

    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("positon="+postion);
        RaceContinentListMode raceContinentListMode= dataContinent.get(postion);
        PSP.INSTANCE.setSPValue(SPKey.SP_RACE_CONTINENT_NAME,continentName);
        PSP.INSTANCE.setSPValue(SPKey.SP_RACE_CONTINENT_STEP,raceContinentListMode.getSteps());
        PSP.INSTANCE.setSPValue(SPKey.SP_RACE_NAME,raceContinentListMode.getName());
        PSP.INSTANCE.setSPValue(SPKey.SP_RACE_CONTINENT_ICON,raceContinentListMode.getIcon());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.RACE_READY_FRAGMENT);
        LogUtils.i("raceContinentListMode="+raceContinentListMode.toString());
    }
}
