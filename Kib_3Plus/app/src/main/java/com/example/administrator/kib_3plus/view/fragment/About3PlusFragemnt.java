package com.example.administrator.kib_3plus.view.fragment;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

/**
 * Created by cui on 2017/7/6.
 */

public class About3PlusFragemnt extends BaseFragment {


    private static About3PlusFragemnt instance;
    public static About3PlusFragemnt getInstance(){
        if(instance==null){
            instance=new About3PlusFragemnt();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.about_3plus_layout;
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_SETTINGS_FRAGMENT);
        return true;
    }
}
