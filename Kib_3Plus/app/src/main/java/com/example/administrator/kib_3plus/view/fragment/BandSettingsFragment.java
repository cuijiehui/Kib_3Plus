package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.TimeUtils;
import com.example.administrator.kib_3plus.mode.BandSettingsMode;
import com.example.administrator.kib_3plus.view.fragment.Adapter.BandSettingsListAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.SpoetL28TDB;
import cn.appscomm.presenter.implement.PDB;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by cui on 2017/7/5.
 */

public class BandSettingsFragment extends BaseFragment implements MyItemClickListener {
   private RecyclerView band_settings_rv;
    private List<BandSettingsMode> bandSettingsModes=new ArrayList<>();
    private BandSettingsListAdapter bandSettingsListAdapter;

    private static BandSettingsFragment instance;
    public static BandSettingsFragment getInstance(){
        if(instance==null){
            instance=new BandSettingsFragment();
        }
        return instance;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.band_settings_layout;
    }

    @Override
    public void initData() {
        super.initData();
        List<ChildInfoDB> childDataList= PDB.INSTANCE.getAllChildInfo();
        List<SpoetL28TDB> dataList= new ArrayList<>();
        String date= TimeUtils.getInstance().getTimeType("yyyy-MM-dd");
        LogUtils.i("date"+date);
        LogUtils.i("childDataList.size="+childDataList.size());
        for(ChildInfoDB childInfoDB : childDataList){
            LogUtils.i("childInfoDB="+childInfoDB.getMac());
            SpoetL28TDB spoetL28TDB= PDB.INSTANCE.getSportL28T(date,childInfoDB.getId());
            if(spoetL28TDB!=null){

                dataList.add(spoetL28TDB);
            }else{
                long time= TimeUtils.getInstance().getTime(date,"yyyy-MM-dd");
                spoetL28TDB=new SpoetL28TDB(childInfoDB.getuId(),childInfoDB.getName(),childInfoDB.getMac(),0,0,0,time,date);
                PDB.INSTANCE.addSportL28T(spoetL28TDB);
                dataList.add(spoetL28TDB);
        }
        }
        bandSettingsModes.clear();
        for (SpoetL28TDB spoetL28TDB:dataList){
            BandSettingsMode bandSettingsMode=new BandSettingsMode(spoetL28TDB.getuId(),spoetL28TDB.getName(),"",true,spoetL28TDB.getMac());
            bandSettingsModes.add(bandSettingsMode);
        }
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        band_settings_rv=findViewById(R.id.band_settings_rv);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        band_settings_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        bandSettingsListAdapter=new BandSettingsListAdapter(getContext(),bandSettingsModes);
        bandSettingsListAdapter.setOnItemClickListener(this);
        band_settings_rv.setAdapter(bandSettingsListAdapter);
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_SETTINGS_FRAGMENT);
        return true;
    }

    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("postion="+postion);
        BandSettingsMode bandSettingsMode= bandSettingsModes.get(postion);
        Bundle bundle=new Bundle();
        bundle.putString("mac",bandSettingsMode.getMac());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.BAND_SETTINGS_SETTING_FRAGMENT,bundle);
    }
}
