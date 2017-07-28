package com.example.administrator.kib_3plus.view.manage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;

import cn.appscomm.presenter.implement.PSP;
import cn.appscomm.sp.SPKey;

/**
 * Created by cui on 2017/6/26.
 */

public class NavigationManage implements View.OnClickListener {
    private static NavigationManage mNavigationManage;
    private Context mContext;

    private LinearLayout navigation_bar_ll;
    private TextView navigation_family_tv,navigation_race_tv,navigation_leaderboard_tv,navigation_settings_tv;

    public static final int NA_TYPE_GONE=0;
    public static final int NA_TYPE_VISIBLE=1;
    public static NavigationManage getInstance(){
        if(mNavigationManage==null){
            mNavigationManage=new NavigationManage();
        }
        return mNavigationManage;
    }

    public void init(Activity activity){
        mContext=activity.getApplicationContext();
        //navigation_bar_ll
        navigation_bar_ll=(LinearLayout)activity.findViewById(R.id.navigation_bar_ll);
        navigation_family_tv=(TextView)activity.findViewById(R.id.navigation_family_tv);
        navigation_race_tv=(TextView)activity.findViewById(R.id.navigation_race_tv);
        navigation_leaderboard_tv=(TextView)activity.findViewById(R.id.navigation_leaderboard_tv);
        navigation_settings_tv=(TextView)activity.findViewById(R.id.navigation_settings_tv);
        setListener();
    }

    /**
     * 设置导航栏的状态
     * @param type 0为隐藏 1为显示
     */
    public void setType(int type){
       switch (type){
           case NA_TYPE_GONE:
               navigation_bar_ll.setVisibility(View.GONE);
               break;
           case NA_TYPE_VISIBLE:
               navigation_bar_ll.setVisibility(View.VISIBLE);
               break;
       }
    }

    private void setListener(){
        navigation_family_tv.setOnClickListener(this);
        navigation_race_tv.setOnClickListener(this);
        navigation_leaderboard_tv.setOnClickListener(this);
        navigation_settings_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigation_family_tv:
                LogUtils.i("navigation_family_tv");
//                TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME,"Create an account",null,null);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);
                break;
            case R.id.navigation_race_tv:
                LogUtils.i("navigation_race_tv");
                boolean isRace=(boolean) PSP.INSTANCE.getSPValue(SPKey.SP_RACE_GAME_START,PSP.DATA_BOOLEAN);
                if(isRace){
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.RACE_ING_FRAGMENT);

                }else{
                    ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_RACE_FRAGMENT);
                }

                break;
            case R.id.navigation_leaderboard_tv:
                LogUtils.i("navigation_leaderboard_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_LEADERBOARD_FRAGMENT);

                break;
            case R.id.navigation_settings_tv:
                LogUtils.i("navigation_settings_tv");
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_SETTINGS_FRAGMENT);

                break;
        }
    }
}
