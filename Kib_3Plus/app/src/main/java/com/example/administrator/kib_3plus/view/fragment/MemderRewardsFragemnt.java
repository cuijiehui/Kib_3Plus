package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RewardsListAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.RewardsL28TDB;
import cn.appscomm.presenter.implement.PDB;

import static android.media.CamcorderProfile.get;

/**
 * Created by cui on 2017/7/11.
 */

public class MemderRewardsFragemnt extends BaseFragment implements MyItemClickListener {
    private ChildInfoDB childInfoDB;
    private TextView rewards_gold_tv;
    private RelativeLayout rewards_no_data_rl;
    private RecyclerView reward_reward_item_rv;
    private  List<RewardsL28TDB> rewardsL28TDBs=new ArrayList<>();
    private RewardsListAdapter rewardsListAdapter;

    private static MemderRewardsFragemnt instance;
    public static MemderRewardsFragemnt getInstance(){
        if(instance==null){
            instance=new MemderRewardsFragemnt();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.memder_rewards_layout;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
        rewardsL28TDBs= PDB.INSTANCE.getRewardsL28TDBs(uId);
//        rewardsL28TDBs.add(new RewardsL28TDB(uId,"Allowance",15,R.mipmap.allowance_icon,"1111111",false));
//        rewardsL28TDBs.add(new RewardsL28TDB(uId,"Bedtime Story",15,R.mipmap.bedtime_story,"1111111",false));
//        rewardsL28TDBs.add(new RewardsL28TDB(uId,"Arts & Crafts",10,R.mipmap.arts_and_crafts_icon,"1111111",false));
        initAdapter();
    }
    private void initAdapter(){
        if(rewardsL28TDBs!=null&&rewardsL28TDBs.size()>0){
            rewardsListAdapter=new RewardsListAdapter(getContext(),rewardsL28TDBs);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Edit":
                LogUtils.i("Edit");
                Bundle bundle=new Bundle();
                bundle.putInt("uId",childInfoDB.getuId());
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.REWARDS_FRAGMENT,bundle);

                break;
        }
    }


    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        rewards_gold_tv= findViewById(R.id.rewards_gold_tv);
        rewards_no_data_rl= findViewById(R.id.rewards_no_data_rl);
        reward_reward_item_rv= findViewById(R.id.reward_reward_item_rv);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        if(rewardsL28TDBs!=null&&rewardsL28TDBs.size()>0){
            rewards_no_data_rl.setVisibility(View.GONE);
            reward_reward_item_rv.setVisibility(View.VISIBLE);
            reward_reward_item_rv.setLayoutManager(new GridLayoutManager(getContext(),2));
            reward_reward_item_rv.setAdapter(rewardsListAdapter);
            rewardsListAdapter.setOnItemClickListener(this);
        }else{
            rewards_no_data_rl.setVisibility(View.VISIBLE);
            reward_reward_item_rv.setVisibility(View.GONE);
        }
        rewards_gold_tv.setText(childInfoDB.getGoldCount()+"");
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MEMBER_PARTICULAR_FRAGMENT,bundle);
        return true;
    }

    @Override
    public void onItemClick(View view, int postion) {
        switch (view.getId()){
            case R.id.rewards_list_redeem_bt:
                LogUtils.i("rewards_list_redeem_bt"+postion);
                RewardsL28TDB rewardsL28TDB= rewardsL28TDBs.get(postion);
                break;
        }
    }
}
