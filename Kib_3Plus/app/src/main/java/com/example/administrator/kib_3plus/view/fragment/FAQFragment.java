package com.example.administrator.kib_3plus.view.fragment;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

/**
 * Created by cui on 2017/7/6.
 */

public class FAQFragment extends BaseFragment {

    private static FAQFragment instance;
    public static FAQFragment getInstance(){
        if(instance==null){
            instance=new FAQFragment();
        }
        return instance;
    }


    @Override
    public int getCreateViewLayoutId() {
        return R.layout.faq_layout;
    }

    @Override
    public boolean onBackPressed() {
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_SETTINGS_FRAGMENT);
        return true;
    }
}
