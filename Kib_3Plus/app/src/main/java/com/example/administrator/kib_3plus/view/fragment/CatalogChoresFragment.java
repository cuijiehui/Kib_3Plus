package com.example.administrator.kib_3plus.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.Adapter.ChoresListAdapter;
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

/**
 * Created by cui on 2017/7/11.
 */

public class CatalogChoresFragment extends BaseFragment {
    private ChildInfoDB childInfoDB;
    private  List<ChoresL28TDB> choresL28TDBs =new ArrayList<>();
    private ChoresListAdapter choresListAdapter;
    private TextView chores_gold_tv;
    private RoundImageView chores_icon_riv;
    private RelativeLayout chores_on_data_rl;
    private RecyclerView chores_chores_item_rv;
    private static CatalogChoresFragment instance;
    public static CatalogChoresFragment getInstance(){
        if(instance==null){
            instance=new CatalogChoresFragment();
        }
        return instance;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle= getArguments();
        int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
        choresL28TDBs= PDB.INSTANCE.getChoresL28TDBs(uId);
//        choresL28TDBs.add(new ChoresL28TDB(uId,"Bring in mail",3,R.mipmap.bring_in_mail,"1111111",false));
//        choresL28TDBs.add(new ChoresL28TDB(uId,"Brsh teeth",2,R.mipmap.brush_teeth,"1111111",false));
//        choresL28TDBs.add(new ChoresL28TDB(uId,"Bring in mail",3,R.mipmap.bring_in_mail,"1111111",false));
//        choresL28TDBs.add(new ChoresL28TDB(uId,"Brsh teeth",2,R.mipmap.brush_teeth,"1111111",false));
        initAdapter();
    }
    private void initAdapter(){
        if(choresL28TDBs!=null&&choresL28TDBs.size()>0){
                    choresListAdapter= new ChoresListAdapter(getContext(),choresL28TDBs);
        }
    }
    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        chores_icon_riv= findViewById(R.id.chores_icon_riv);
        chores_gold_tv= findViewById(R.id.chores_gold_tv);
        chores_chores_item_rv= findViewById(R.id.chores_chores_item_rv);
        chores_on_data_rl= findViewById(R.id.chores_on_data_rl);
    }

    @Override
    public void onResume() {
        super.onResume();
        choresL28TDBs= PDB.INSTANCE.getChoresL28TDBs(childInfoDB.getuId());
        if(choresListAdapter!=null){
            choresListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.catalog_chores_layout;
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        if(choresL28TDBs==null||choresL28TDBs.size()<=0){
            chores_chores_item_rv.setVisibility(View.GONE);
            chores_on_data_rl.setVisibility(View.VISIBLE);
        }else{
            chores_chores_item_rv.setVisibility(View.VISIBLE);
            chores_on_data_rl.setVisibility(View.GONE);
            chores_chores_item_rv.setLayoutManager(new GridLayoutManager(getContext(),2));
            chores_chores_item_rv.setAdapter(choresListAdapter);
        }
        chores_gold_tv.setText(childInfoDB.getGoldCount()+"");

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Edit":
                LogUtils.i("Edit");
                Bundle bundle=new Bundle();
                bundle.putInt("uId",childInfoDB.getuId());
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CHORES_FRAGMENT,bundle);

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MEMBER_PARTICULAR_FRAGMENT,bundle);
        return true;
    }
}
