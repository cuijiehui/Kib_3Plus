package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RaceReadyListAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.RaceDB;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

/**
 * Created by cui on 2017/7/21.
 */

public class RaceReadyFragment extends BaseFragment  {

    private ImageView race_ready_top_iv;
    private RecyclerView  race_ready_another_rv;
    private  TextView race_ready_hint_tv;
    private List<RaceDB> raceDBs;
    private  Button race_ready_go_bt;
    private String mContinentName,mContinentsName,mStep,mUId;
    private int mIcon;
    private RaceReadyListAdapter mRaceReadyListAdapter;
    private static RaceReadyFragment instance;
    public static RaceReadyFragment getInstance(){
        if(instance==null){
            instance=new RaceReadyFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        race_ready_top_iv=  findViewById(R.id.race_ready_top_iv);
        race_ready_another_rv=  findViewById(R.id.race_ready_another_rv);
        race_ready_go_bt=  findViewById(R.id.race_ready_go_bt);
        race_ready_hint_tv=  findViewById(R.id.race_ready_hint_tv);

    }

    @Override
    public void initListener() {
        super.initListener();
        race_ready_go_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.race_ready_go_bt:
                LogUtils.i("race_ready_go_bt+SP_RACE_GAME_START");
                PSP.INSTANCE.setSPValue(SPKey.SP_RACE_GAME_START,true);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.RACE_ING_FRAGMENT);

                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        mContinentName= (String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_NAME,PSP.DATA_STRING);
        mStep=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_STEP,PSP.DATA_STRING);
        mContinentsName=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_NAME,PSP.DATA_STRING);
        mUId=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_UID,PSP.DATA_STRING);
        mIcon= (int)PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_ICON,PSP.DATA_INT);

        String[] uIdS= mUId.split(",");
        raceDBs=new ArrayList<>();
        for (String uId: uIdS){
            int id=new Integer(uId);
            int step=new Integer(mStep);
            ChildInfoDB childInfoDB= PDB.INSTANCE.getChildInfo(id);
            RaceDB raceDB=  new RaceDB(id
                    ,0
                    ,step,childInfoDB.getIcon()
                    ,R.mipmap.race_goal_blue
                    ,childInfoDB.isIcon()
                    , NumberUtils.INSTANCE.getFavorite(childInfoDB.getFavorite())
                    ,childInfoDB.getIconUrl());
            PDB.INSTANCE.addRaceDB(raceDB);
            raceDBs.add(raceDB);
        }
        mRaceReadyListAdapter=new RaceReadyListAdapter(getContext(),raceDBs);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        TitleManage.getInstance().setTitleMainName(mContinentsName);
       String hint= race_ready_hint_tv.getText().toString();
        hint=  hint.replace("stepss",mStep);
        hint=  hint.replace("name",mContinentsName);
        race_ready_hint_tv.setText(hint);
        race_ready_another_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        race_ready_another_rv.setAdapter(mRaceReadyListAdapter);
        race_ready_top_iv.setImageResource(mIcon);

    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.race_ready_layout;
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_CONTINENT_FRAGMENT);
        return true;
    }

}
