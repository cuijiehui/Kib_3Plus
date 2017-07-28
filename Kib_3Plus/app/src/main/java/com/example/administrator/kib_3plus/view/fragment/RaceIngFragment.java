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
import com.example.administrator.kib_3plus.view.fragment.Adapter.RaceIngListAdapter;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RaceReadyListAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.RaceDB;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

import static com.example.administrator.kib_3plus.R.id.race_ready_another_rv;
import static com.example.administrator.kib_3plus.R.id.race_ready_hint_tv;
import static com.example.administrator.kib_3plus.R.id.race_ready_top_iv;

/**
 * Created by cui on 2017/7/21.
 */

public class RaceIngFragment extends BaseFragment implements MyItemClickListener {

    private ImageView race_ing_top_iv;
    private RecyclerView  race_ing_another_rv;
    private  TextView race_ing_hint_tv;
    private List<RaceDB> raceDBs;
    private RaceDB mRaceDB;
    private String mContinentName,mContinentsName,mStep,mUId;
    private int mIcon;
    private RaceIngListAdapter mRaceIngListAdapter;
    private static RaceIngFragment instance;
    public static RaceIngFragment getInstance(){
        if(instance==null){
            instance=new RaceIngFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        race_ing_top_iv=  findViewById(R.id.race_ing_top_iv);
        race_ing_another_rv=  findViewById(R.id.race_ing_another_rv);
        race_ing_hint_tv=  findViewById(R.id.race_ing_hint_tv);

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.race_ing_leave_bt:
                LogUtils.i("race_ing_leave_bt");
                if(mRaceDB!=null){
                    PDB.INSTANCE.delRaceDB(mRaceDB.getuId()+"");
                    raceDBs.remove(mRaceDB);
                }
                if(checkEndGame()){
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_RACE_FRAGMENT);
                }else{
                    mRaceIngListAdapter.setOnClickPosition(-1);
                    mRaceIngListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.race_ing_cancel_bt:
                LogUtils.i("race_ing_cancel_bt");
                mRaceDB=null;
                mRaceIngListAdapter.setOnClickPosition(-1);
                mRaceIngListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private boolean checkEndGame() {
        boolean isEnd=false;
        if(raceDBs==null||raceDBs.size()<=0){
            isEnd=true;
            PSP.INSTANCE.setSPValue(SPKey.SP_RACE_GAME_START,false);
        }
        return isEnd;
    }

    @Override
    public void initData() {
        super.initData();
        mContinentName= (String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_NAME,PSP.DATA_STRING);
        mStep=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_STEP,PSP.DATA_STRING);
        mContinentsName=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_NAME,PSP.DATA_STRING);
        mUId=(String) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_UID,PSP.DATA_STRING);
        mIcon= (int)PSP.INSTANCE.getSPValue(SPKey.SP_RACE_CONTINENT_ICON,PSP.DATA_INT);
        raceDBs=new ArrayList<>();
        raceDBs= PDB.INSTANCE.getAllRaceDBs();
        mRaceIngListAdapter=new RaceIngListAdapter(getContext(),raceDBs);
        mRaceIngListAdapter.setOnItemClickListener(this,this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        TitleManage.getInstance().setTitleMainName(mContinentsName);
       String hint= race_ing_hint_tv.getText().toString();
        hint=  hint.replace("stepss",mStep);
        hint=  hint.replace("name",mContinentsName);
        race_ing_hint_tv.setText(hint);
        race_ing_another_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        race_ing_another_rv.setAdapter(mRaceIngListAdapter);
        race_ing_top_iv.setImageResource(mIcon);
        if(checkEndGame()){
            ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_RACE_FRAGMENT);
        }
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.race_ing_layout;
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_CONTINENT_FRAGMENT);
        return true;
    }

    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("postion="+postion);
        mRaceDB=raceDBs.get(postion);
        mRaceIngListAdapter.setOnClickPosition(postion);
        mRaceIngListAdapter.notifyDataSetChanged();
    }
}
