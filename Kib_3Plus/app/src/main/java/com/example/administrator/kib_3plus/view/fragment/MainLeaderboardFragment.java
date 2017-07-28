package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.Utils.TimeUtils;
import com.example.administrator.kib_3plus.mode.LeaderboardMode;
import com.example.administrator.kib_3plus.view.fragment.Adapter.LeaderboardListAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.SpoetL28TDB;
import cn.appscomm.presenter.implement.PDB;

import static com.example.administrator.kib_3plus.R.id.main_family_item_rl;

/**
 * Created by cui on 2017/7/5.
 */

public class MainLeaderboardFragment extends BaseFragment implements MyItemClickListener {
    List<SpoetL28TDB> dataList= new ArrayList<>();
    List<LeaderboardMode> leaderboardModes= new ArrayList<>();
    List<ChildInfoDB> childDataList = new ArrayList<>();
    RecyclerView leaderboard_data_rl;
    LeaderboardListAdapter mLeaderboardListAdapter;
    private static MainLeaderboardFragment mMainLeaderboardFragment;
    public static MainLeaderboardFragment getInstance(){
        if(mMainLeaderboardFragment==null){
            mMainLeaderboardFragment=new MainLeaderboardFragment();

        }
        return mMainLeaderboardFragment;
    }


    @Override
    public int getCreateViewLayoutId() {
        return R.layout.main_leaderboard_layout;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtils.i("initdata");
        childDataList= PDB.INSTANCE.getAllChildInfo();
        dataList.clear();
        String date= TimeUtils.getInstance().getTimeType("yyyy-MM-dd");
        LogUtils.i("date"+date);
        LogUtils.i("childDataList.size="+childDataList.size());
        for(ChildInfoDB childInfoDB : childDataList){
            LogUtils.i("childInfoDB="+childInfoDB.getMac());
            SpoetL28TDB spoetL28TDB= PDB.INSTANCE.getSportL28T(date,childInfoDB.getuId());
            if(spoetL28TDB!=null){
                dataList.add(spoetL28TDB);
            }else{
                long time= TimeUtils.getInstance().getTime(date,"yyyy-MM-dd");
                spoetL28TDB=new SpoetL28TDB(childInfoDB.getuId()
                        ,childInfoDB.getName()
                        ,childInfoDB.getMac()
                        ,childInfoDB.getIcon()
                        ,childInfoDB.isIcon()
                        ,childInfoDB.getIconUrl()
                        , NumberUtils.INSTANCE.getFavorite(childInfoDB.getFavorite())
                        ,0
                        ,0
                        ,0
                        ,time
                        ,date);
                PDB.INSTANCE.addSportL28T(spoetL28TDB);
                dataList.add(spoetL28TDB);
            }
        }
        leaderboardModes.clear();
        LogUtils.i("dataList.size="+dataList.size());
        for(SpoetL28TDB spoetL28TDB: dataList){
            LogUtils.i("spoetL28TDB.toString="+spoetL28TDB.toString());
            LeaderboardMode leaderboardMode=  new LeaderboardMode(spoetL28TDB.getuId()
                    ,spoetL28TDB.getName()
                    ,spoetL28TDB.getActivity()
                    ,spoetL28TDB.getSportTime()
                    ,spoetL28TDB.getIcon()
                    ,spoetL28TDB.isIcon()
                    ,spoetL28TDB.getFavorite()
                    ,spoetL28TDB.getIconUrl()
                    ,true);
            leaderboardModes.add(leaderboardMode);
        }

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        LogUtils.i("leaderboardModes.size="+leaderboardModes.size());
        mLeaderboardListAdapter=new LeaderboardListAdapter(getContext(),leaderboardModes);
        leaderboard_data_rl.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderboard_data_rl.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        leaderboard_data_rl.setAdapter(mLeaderboardListAdapter);
        mLeaderboardListAdapter.setOnItemClickListener(this);
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        leaderboard_data_rl= findViewById(R.id.leaderboard_data_rl);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("onItemClick="+postion);
    }
}
