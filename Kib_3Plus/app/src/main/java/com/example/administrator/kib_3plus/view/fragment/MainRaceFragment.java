package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.mode.RaceMainMode;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RaceMainRecyclerAdapter;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RaceMainSeeRecyclerAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.race_child_rv;
import static com.example.administrator.kib_3plus.R.id.race_stared_bt;

/**
 * Created by cui on 2017/7/5.
 */

public class MainRaceFragment extends BaseFragment implements MyItemClickListener {
    private Button race_stared_bt,race_stared_see_bt;
    private  RecyclerView race_child_rv,race_child_see_rv;
    private RaceMainRecyclerAdapter mRaceMainRecyclerAdapter;
    private RaceMainSeeRecyclerAdapter mRaceMainSeeRecyclerAdapter;
    private  RelativeLayout race_bottom_rl,race_bottom_see_rl;
    List<ChildInfoDB> childDataList = new ArrayList<>();
    List<RaceMainMode> raceMainModes = new ArrayList<>();
    List<RaceMainMode> clickRaceMainModes = new ArrayList<>();

    private static MainRaceFragment instance;
    public static MainRaceFragment getInstance(){
        if(instance==null){
            instance=new MainRaceFragment();
        }
        return instance;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        race_child_rv= findViewById(R.id.race_child_rv);
        race_stared_bt= findViewById(R.id.race_stared_bt);
        race_bottom_rl= findViewById(R.id.race_bottom_rl);
        race_bottom_see_rl= findViewById(R.id.race_bottom_see_rl);
        race_stared_see_bt= findViewById(R.id.race_stared_see_bt);
        race_child_see_rv= findViewById(R.id.race_child_see_rv);
    }

    @Override
    public void initListener() {
        super.initListener();
        race_stared_bt.setOnClickListener(this);
        race_stared_see_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.race_stared_bt:
                    LogUtils.i("race_stared_bt");
                    if(clickRaceMainModes==null||clickRaceMainModes.size()<=0){
                        showToast("请选择一名孩子");
                    }else{
                        race_bottom_rl.setVisibility(View.GONE);
                        race_bottom_see_rl.setVisibility(View.VISIBLE);
                    }
                break;
                case R.id.race_stared_see_bt:
                    LogUtils.i("race_stared_see_bt");
                    StringBuffer uidS=new StringBuffer();

                    for (int i=0;i<clickRaceMainModes.size();i++){
                        uidS.append(clickRaceMainModes.get(i).getuId()+"");
                        if(clickRaceMainModes.size()>0){
                            if(i!=clickRaceMainModes.size()-1){
                                uidS.append(",");
                            }
                        }

                    }
                    PSP.INSTANCE.setSPValue(SPKey.SP_RACE_CONTINENT_UID,uidS.toString());
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.SELECT_CONTINENT_FRAGMENT);
                    break;
            }
    }

    @Override
    public void initData() {
        super.initData();
        childDataList= PDB.INSTANCE.getAllChildInfo();
        raceMainModes.clear();
        for(ChildInfoDB childInfoDB:childDataList){
            raceMainModes.add(new RaceMainMode(childInfoDB.getuId()
                    ,childInfoDB.getName()
                    ,childInfoDB.getIcon()
                    ,childInfoDB.isIcon()
                    ,childInfoDB.getIconUrl()
                    , NumberUtils.INSTANCE.getFavorite(childInfoDB.getFavorite())
                    ,false));
        }
        mRaceMainRecyclerAdapter=new RaceMainRecyclerAdapter(getContext(),raceMainModes);
        mRaceMainRecyclerAdapter.setOnItemClickListener(this);
        mRaceMainSeeRecyclerAdapter=new RaceMainSeeRecyclerAdapter(getContext(),clickRaceMainModes);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        race_child_rv.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        race_child_rv.setAdapter(mRaceMainRecyclerAdapter);
        race_child_see_rv.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        race_child_see_rv.setAdapter(mRaceMainSeeRecyclerAdapter);

    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.main_race_layout;
    }

    @Override
    public boolean onBackPressed() {
        if(race_bottom_see_rl.getVisibility()==View.VISIBLE){
            race_bottom_see_rl.setVisibility(View.GONE);
            race_bottom_rl.setVisibility(View.VISIBLE);
            return true;

        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        race_bottom_see_rl.setVisibility(View.GONE);
        race_bottom_rl.setVisibility(View.VISIBLE);
        clickRaceMainModes.clear();
    }

    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("postion="+postion);
       RaceMainMode raceMainMode= raceMainModes.get(postion);
        raceMainMode.setClick(!raceMainMode.isClick());
        if(raceMainMode.isClick()){
            clickRaceMainModes.add(raceMainMode);
        }else{
            clickRaceMainModes.remove(raceMainMode);
        }
        mRaceMainRecyclerAdapter.notifyDataSetChanged();
        LogUtils.i("raceMainModes="+raceMainMode.toString());

    }
}
