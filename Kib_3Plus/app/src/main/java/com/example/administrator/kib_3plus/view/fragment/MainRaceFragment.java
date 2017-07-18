package com.example.administrator.kib_3plus.view.fragment;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;

/**
 * Created by cui on 2017/7/5.
 */

public class MainRaceFragment extends BaseFragment {
    private static MainRaceFragment mMainRaceFragment;
    public static MainRaceFragment getInstance(){
        if(mMainRaceFragment==null){
            mMainRaceFragment=new MainRaceFragment();
        }
        return mMainRaceFragment;
    }
    @Override
    public int getCreateViewLayoutId() {
        return R.layout.main_race_layout;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
