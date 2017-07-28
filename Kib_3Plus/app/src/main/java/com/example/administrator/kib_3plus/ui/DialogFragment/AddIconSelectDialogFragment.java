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

import static android.R.attr.name;
import static com.example.administrator.kib_3plus.R.id.delete_dialog_hint_tv;

/**
 * Created by cui on 2017/7/6.
 */

public class AddIconSelectDialogFragment extends DialogFragment {

    private static AddIconSelectDialogFragment instance;
    private static View.OnClickListener onclicks;
    private TextView add_icon_album_tv,add_icon_camera_tv,add_icon_3plus_avater_tv;
    public static AddIconSelectDialogFragment newInstance(View.OnClickListener onclick){
        if(instance==null){
            instance=new AddIconSelectDialogFragment();
        }
        onclicks=onclick;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_icon_select_dialog,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(v);
        return v;
    }

    private void initView(View v) {
        add_icon_album_tv= (TextView)v.findViewById(R.id.add_icon_album_tv);
        add_icon_camera_tv= (TextView)v.findViewById(R.id.add_icon_camera_tv);
        add_icon_3plus_avater_tv= (TextView)v.findViewById(R.id.add_icon_3plus_avater_tv);
        add_icon_album_tv.setOnClickListener(onclicks);
        add_icon_camera_tv.setOnClickListener(onclicks);
        add_icon_3plus_avater_tv.setOnClickListener(onclicks);
    }
}
