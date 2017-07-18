package com.example.administrator.kib_3plus.ui.DialogFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.kib_3plus.R;

/**
 * Created by cui on 2017/7/6.
 */

public class MemberCoinsDialogFragment extends DialogFragment {

    private static MemberCoinsDialogFragment instance;
    private static View.OnClickListener onclicks;
    private  ImageView coins_del_iv;
    private Button coins_give_bt,coins_deduct_bt;
    public static MemberCoinsDialogFragment newInstance(View.OnClickListener onclick){
        if(instance==null){
            instance=new MemberCoinsDialogFragment();
        }
        onclicks=onclick;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.member_coins_dialog,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(v);
        return v;
    }

    private void initView(View v) {
        coins_del_iv=(ImageView)  v.findViewById(R.id.coins_del_iv);
        coins_give_bt=(Button)  v.findViewById(R.id.coins_give_bt);
        coins_deduct_bt=(Button)  v.findViewById(R.id.coins_deduct_bt);
        coins_give_bt.setOnClickListener(onclicks);
        coins_deduct_bt.setOnClickListener(onclicks);
        coins_del_iv.setOnClickListener(onclicks);
    }
}
