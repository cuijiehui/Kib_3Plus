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
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.ui.PickerView;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by cui on 2017/7/6.
 */

public class MainDeleteDialogFragment extends DialogFragment {

    private static MainDeleteDialogFragment instance;
    private static View.OnClickListener onclicks;
    private static String name;
    private  ImageView delete_dialog_delete_iv;
    private TextView delete_dialog_hint_tv;
    private Button delete_dialog_cancel_bt,delete_dialog_confirm_bt;
    public static MainDeleteDialogFragment newInstance(String names, View.OnClickListener onclick){
        if(instance==null){
            instance=new MainDeleteDialogFragment();
        }
        name=names;
        onclicks=onclick;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.main_delete_dialog,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(v);
        return v;
    }

    private void initView(View v) {
        delete_dialog_delete_iv= (ImageView) v.findViewById(R.id.delete_dialog_delete_iv);
        delete_dialog_hint_tv= (TextView)v.findViewById(R.id.delete_dialog_hint_tv);
        delete_dialog_cancel_bt=  (Button)v.findViewById(R.id.delete_dialog_cancel_bt);
        delete_dialog_confirm_bt=(Button)  v.findViewById(R.id.delete_dialog_confirm_bt);
        String hint=delete_dialog_hint_tv.getText().toString();
       String newHint= hint.replace("name",name);
        delete_dialog_hint_tv.setText(newHint);
        delete_dialog_cancel_bt.setOnClickListener(onclicks);
        delete_dialog_confirm_bt.setOnClickListener(onclicks);
        delete_dialog_delete_iv.setOnClickListener(onclicks);
    }
}
