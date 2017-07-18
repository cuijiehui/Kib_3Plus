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
import com.example.administrator.kib_3plus.ui.DialogFragment.ChoresWriteDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.Adapter.MyDecoration;
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
import cn.appscomm.presenter.implement.PDB;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.administrator.kib_3plus.PublicData.choresListData;

/**
 * Created by cui on 2017/7/14.
 */

public class ChoresFragment extends BaseFragment implements SlidingRecyclerAdapter.IonSlidingViewClickListener{
    private RecyclerView chores_data_list_rv;
    private SlidingRecyclerAdapter SlidingRecyclerAdapter;
    private ChildInfoDB childInfoDB;
    private List<ChoresL28TDB> choresL28TDBs =new ArrayList<>();
    private ChoresWriteDialogFragment choresWriteDialogFragment;
    private static ChoresFragment instance;
    private ChoresListMode mChoresListMode;

    private List<ChoresL28TDB> addChoresL28TDBs =new ArrayList<>();
    private List<ChoresL28TDB> dleChoresL28TDBs =new ArrayList<>();


    public static ChoresFragment getInstance(){
        if(instance==null){
            instance=new ChoresFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.chores_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);

        chores_data_list_rv=  findViewById(R.id.chores_data_list_rv);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Save":
                LogUtils.i("Save");
                if(dleChoresL28TDBs!=null||dleChoresL28TDBs.size()>0){
                    //
                    for (ChoresL28TDB choresL28TDB :dleChoresL28TDBs){
                        PDB.INSTANCE.deleteChoresL28TDB(choresL28TDB.getuId(),choresL28TDB.getChoresId());
                    }
                }
                if(addChoresL28TDBs!=null||addChoresL28TDBs.size()>0){
                    for (ChoresL28TDB choresL28TDB :addChoresL28TDBs){
                        PDB.INSTANCE.addChoresL28TDB(choresL28TDB);
                    }
                }
                Bundle bundle=new Bundle();
                bundle.putInt("uId",childInfoDB.getuId());
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CATALOG_CHORES_FRAGMENT,bundle);
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
        choresL28TDBs= PDB.INSTANCE.getChoresL28TDBs(uId);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        chores_data_list_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        SlidingRecyclerAdapter=new SlidingRecyclerAdapter(getContext(),this, PublicData.choresListData,choresL28TDBs);
        chores_data_list_rv.setItemAnimator(new DefaultItemAnimator());
        chores_data_list_rv.setAdapter(SlidingRecyclerAdapter);
        chores_data_list_rv.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.CATALOG_CHORES_FRAGMENT,bundle);
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
        mChoresListMode= PublicData.choresListData.get(position);
        choresWriteDialogFragment=ChoresWriteDialogFragment.newInstance(this,mChoresListMode.getName());
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
                        SlidingRecyclerAdapter.notifyDataSetChanged();
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
        ChoresL28TDB choresL28TDB=new ChoresL28TDB(childInfoDB.getuId()
                ,mChoresListMode.getName()
                ,mChoresListMode.getChoresId()
                ,gold
                ,mChoresListMode.getIcon()
                ,frequency
                ,false);
        addChoresL28TDBs.add(choresL28TDB);
        choresL28TDBs.add(choresL28TDB);
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        LogUtils.i("onDeleteBtnCilck"+position);
        ChoresListMode choresListMode= PublicData.choresListData.get(position);
        ChoresL28TDB dleChoresL28TDB=null;
        for(ChoresL28TDB choresL28TDB:choresL28TDBs){
            if(choresListMode.getChoresId()==choresL28TDB.getChoresId()){
                dleChoresL28TDBs.add(choresL28TDB);
                dleChoresL28TDB=choresL28TDB;
            }
        }
        if(dleChoresL28TDB!=null&&choresL28TDBs!=null&&choresL28TDBs.size()>0){
            choresL28TDBs.remove(dleChoresL28TDB);

        }

        SlidingRecyclerAdapter.notifyDataSetChanged();
    }
}
