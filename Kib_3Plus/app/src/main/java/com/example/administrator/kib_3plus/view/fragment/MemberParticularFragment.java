package com.example.administrator.kib_3plus.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.DataViewChart;
import com.example.administrator.kib_3plus.ui.DialogFragment.MemberCoinsDialogFragment;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PDB;

/**
 * Created by cui on 2017/7/11.
 */

public class MemberParticularFragment extends BaseFragment {

    private ChildInfoDB childInfoDB;
    private RelativeLayout particular_top_bg_rl;
    private RelativeLayout particular_activity_rl,particular_weekly_chore_rl,particular_rewards_rl,particular_sleep_rl;
    private RoundImageView particular_icon_riv;
    private MemberCoinsDialogFragment memberCoinsDialogFragment;
    private ImageView member_back_iv;
    private TextView particular_name_tv,particular_gold_tv,particular_edit_tv;
    private static MemberParticularFragment instance;
    public static MemberParticularFragment getInstance(){
        if(instance==null){
            instance=new MemberParticularFragment();
        }
        return instance;
    }
    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        particular_top_bg_rl=findViewById(R.id.particular_top_bg_rl);
        particular_activity_rl=findViewById(R.id.particular_activity_rl);
        particular_weekly_chore_rl=findViewById(R.id.particular_weekly_chore_rl);
        particular_rewards_rl=findViewById(R.id.particular_rewards_rl);
        particular_sleep_rl=findViewById(R.id.particular_sleep_rl);
        particular_icon_riv=findViewById(R.id.particular_icon_riv);
        particular_name_tv=findViewById(R.id.particular_name_tv);
        particular_gold_tv=findViewById(R.id.particular_gold_tv);
        particular_edit_tv=findViewById(R.id.particular_edit_tv);
        member_back_iv=findViewById(R.id.member_back_iv);
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.member_particular_layout;
    }

    @Override
    public void initData() {
        super.initData();
       Bundle bundle= getArguments();
       int uId= bundle.getInt("uId");
        childInfoDB= PDB.INSTANCE.getChildInfo(uId);
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        particular_name_tv.setText(childInfoDB.getName());
        particular_gold_tv.setText(childInfoDB.getGoldCount()+"");
        if(childInfoDB.isIcon()){
            particular_icon_riv.setImageResource(childInfoDB.getIcon());

        }else{
            LogUtils.i(childInfoDB.getIconUrl());
            String poth=CameraUtils.INSTANCE.getIcon(childInfoDB.getIconUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(childInfoDB.getIconUrl());
                particular_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }

    }

    @Override
    public void initListener() {
        super.initListener();
        particular_edit_tv.setOnClickListener(this);
        particular_activity_rl.setOnClickListener(this);
        particular_weekly_chore_rl.setOnClickListener(this);
        particular_rewards_rl.setOnClickListener(this);
        particular_sleep_rl.setOnClickListener(this);
        member_back_iv.setOnClickListener(this);
        particular_gold_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putInt("uId",childInfoDB.getuId());
        bundle.putString("name",childInfoDB.getName());
        switch (v.getId()){
            case R.id.particular_edit_tv:
                LogUtils.i("particular_edit_tv");

                break;
            case R.id.particular_activity_rl:
                LogUtils.i("particular_activity_rl");
                bundle.putInt("type", DataViewChart.VIEW_STEP);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.DATA_PARTICULARS_FRAGMENT,bundle);

                break;
            case R.id.particular_weekly_chore_rl:
                LogUtils.i("particular_weekly_chore_rl");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.CATALOG_CHORES_FRAGMENT,bundle);

                break;
            case R.id.particular_rewards_rl:
                LogUtils.i("particular_rewards_rl");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.Memder_REWARDS_FRAGMENT,bundle);

                break;
            case R.id.particular_sleep_rl:
                LogUtils.i("particular_sleep_rl");
                bundle.putInt("type", DataViewChart.VIEW_SLEEP);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.DATA_PARTICULARS_FRAGMENT,bundle);

                break;
            case R.id.coins_give_bt:
                LogUtils.i("coins_give_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.GIVE_COINS_FRAGMENT,bundle);
                if(memberCoinsDialogFragment!=null){
                    memberCoinsDialogFragment.dismiss();
                }
                break;
            case R.id.coins_deduct_bt:
                LogUtils.i("coins_deduct_bt");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.DEDUCT_COINS_FRAGMENT,bundle);
                if(memberCoinsDialogFragment!=null){
                    memberCoinsDialogFragment.dismiss();
                }
                break;
            case R.id.coins_del_iv:
                LogUtils.i("coins_del_iv");
                if(memberCoinsDialogFragment!=null){
                    memberCoinsDialogFragment.dismiss();
                }
                break;
            case R.id.member_back_iv:
                LogUtils.i("member_back_iv");
                onBackPressed();
                break;
            case R.id.particular_gold_tv:
                memberCoinsDialogFragment=MemberCoinsDialogFragment.newInstance(this);
                memberCoinsDialogFragment.show(getChildFragmentManager(),"particular_edit_tv");
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);

        return true;
    }
}
