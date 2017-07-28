package com.example.administrator.kib_3plus.ui.DialogFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.Adapter.AddMemberIconAdapter;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

/**
 * Created by cui on 2017/7/6.
 */

public class AddIconDialogFragment extends DialogFragment {

    private static AddIconDialogFragment instance;
    private static View.OnClickListener onclicks;
    private  ImageView add_icon_iv;
    private RecyclerView add_icon_list_rv;
    private AddMemberIconAdapter mAddMemberIconAdapter;
    private static MyItemClickListener mMyItemClickListener;
    public static AddIconDialogFragment newInstance( View.OnClickListener onclick, MyItemClickListener myItemClickListener){
        if(instance==null){
            instance=new AddIconDialogFragment();
        }
        onclicks=onclick;
        mMyItemClickListener=myItemClickListener;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_member_icon_dialog,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initData();
        initView(v);
        return v;
    }

    private void initData() {
        mAddMemberIconAdapter=new AddMemberIconAdapter(getContext(), PublicData.iconListData);
        mAddMemberIconAdapter.setOnItemClickListener(mMyItemClickListener);
    }

    private void initView(View v) {
        add_icon_list_rv=(RecyclerView)v.findViewById(R.id.add_icon_list_rv);
        add_icon_iv=(ImageView)v.findViewById(R.id.add_icon_iv);
        add_icon_list_rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        add_icon_list_rv.setAdapter(mAddMemberIconAdapter);
        add_icon_iv.setOnClickListener(onclicks);
    }
}
