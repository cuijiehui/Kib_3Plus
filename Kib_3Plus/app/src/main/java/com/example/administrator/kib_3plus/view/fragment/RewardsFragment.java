package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.ChoresListMode;
import com.example.administrator.kib_3plus.mode.RewardsListMode;
import com.example.administrator.kib_3plus.ui.DialogFragment.ChoresWriteDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.Adapter.MyDecoration;
import com.example.administrator.kib_3plus.view.fragment.Adapter.RewardsRecyclerAdapter;
import com.example.administrator.kib_3plus.view.fragment.Adapter.SlidingRecyclerAdapter;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.ChoresL28TDB;
import cn.appscomm.db.mode.RewardsL28TDB;
import cn.appscomm.presenter.implement.PDB;

import static com.example.administrator.kib_3plus.R.id.chores_data_list_rv;

/**
 * Created by cui on 2017/7/14.
 */

public class RewardsFragment extends BaseFragment implements RewardsRecyclerAdapter.IonSlidingViewClickListener{
    private RecyclerView rewards_data_list_rv;
    private RewardsRecyclerAdapter rewardsRecyclerAdapter;
    private ChildInfoDB childInfoDB;
    private List<RewardsL28TDB> rewardsL28TDBs =new ArrayList<>();
    private ChoresWriteDialogFragment choresWriteDialogFragment;
    private static RewardsFragment instance;
    private RewardsListMode mRewardsListMode;

    private List<RewardsL28TDB> addRewardsL28TDBs =new ArrayList<>();
    private List<RewardsL28TDB> dleRewardsL28TDBs =new ArrayList<>();


    public static RewardsFragment getInstance(){
        if(instance==null){
            instance=new RewardsFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.rewards_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);

        rewards_data_list_rv=  findViewById(R.id.rewards_data_list_rv);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Save":
                LogUtils.i("Save");
                if(dleRewardsL28TDBs!=null||dleRewardsL28TDBs.size()>0){
                    //
                    for (RewardsL28TDB rewardsL28TDB :dleRewardsL28TDBs){
                       PDB.INSTANCE.deleteRewardsL28TDB(rewardsL28TDB.getuId(),rewardsL28TDB.getChoresId());
                    }
                }
                if(addRewardsL28TDBs!=null||addRewardsL28TDBs.size()>0){
                    for (RewardsL28TDB rewardsL28TDB :addRewardsL28TDBs){
                        PDB.INSTANCE.addRewardsL28TDB(rewardsL28TDB);
                    }
                }
                Bundle bundle=new Bundle();
                bundle.putInt("uId",childInfoDB.getuId());
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.Memder_REWARDS_FRAGMENT,bundle);
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
        rewardsL28TDBs= PDB.INSTANCE.getRewardsL28TDBs(uId);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        rewards_data_list_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardsRecyclerAdapter=new RewardsRecyclerAdapter(getContext(),this, PublicData.rewardsListData,rewardsL28TDBs);
        rewards_data_list_rv.setItemAnimator(new DefaultItemAnimator());
        rewards_data_list_rv.setAdapter(rewardsRecyclerAdapter);
        rewards_data_list_rv.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.Memder_REWARDS_FRAGMENT,bundle);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onItemClick(View view, int position) {
        LogUtils.i("onItemClick"+position);
        mRewardsListMode= PublicData.rewardsListData.get(position);
        choresWriteDialogFragment=ChoresWriteDialogFragment.newInstance(this,mRewardsListMode.getName());
        choresWriteDialogFragment.show(getChildFragmentManager(),"choresFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chores_dialog_cancel_bt:
                LogUtils.i("chores_dialog_cancel_bt");
                if(choresWriteDialogFragment!=null){
                    choresWriteDialogFragment.dismiss();
                }
                break;
            case R.id.chores_dialog_confirm_bt:
                LogUtils.i("chores_dialog_confirm_bt");
                if(choresWriteDialogFragment!=null){
                   String choreReward= choresWriteDialogFragment.getChoreReward();
                   String frequency= choresWriteDialogFragment.getFrequency();
                    LogUtils.i("choreReward="+choreReward);
                    LogUtils.i("frequency="+frequency);
                    if(frequency.equals("0000000")){
                        choresWriteDialogFragment.dismiss();
                    }else{
                        saveChoresL28TDBs(choreReward,frequency);
                        choresWriteDialogFragment.dismiss();
                        rewardsRecyclerAdapter.notifyDataSetChanged();
                    }

                }
                break;
            case R.id.chore_delete_bt:
                LogUtils.i("chore_delete_bt");
                if(choresWriteDialogFragment!=null){
                    choresWriteDialogFragment.dismiss();
                }
                break;
        }
    }

    private void saveChoresL28TDBs(String choreReward,String frequency) {
        int gold=new Integer(choreReward);
        RewardsL28TDB rewardsL28TDB=new RewardsL28TDB(childInfoDB.getuId()
                ,mRewardsListMode.getName()
                ,mRewardsListMode.getChoresId()
                ,gold
                ,mRewardsListMode.getIcon()
                ,frequency
                ,false);
        addRewardsL28TDBs.add(rewardsL28TDB);
        rewardsL28TDBs.add(rewardsL28TDB);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        LogUtils.i("onDeleteBtnCilck"+position);
        ChoresListMode choresListMode= PublicData.choresListData.get(position);
        RewardsL28TDB dleRewardsL28TDB=null;
        for(RewardsL28TDB rewardsL28TDB:rewardsL28TDBs){
            if(choresListMode.getChoresId()==rewardsL28TDB.getChoresId()){
                dleRewardsL28TDBs.add(rewardsL28TDB);
                dleRewardsL28TDB=rewardsL28TDB;
            }
        }
        if(dleRewardsL28TDB!=null&&rewardsL28TDBs!=null&&rewardsL28TDBs.size()>0){
            rewardsL28TDBs.remove(dleRewardsL28TDB);

        }

        rewardsRecyclerAdapter.notifyDataSetChanged();
    }
}
