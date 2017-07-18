package com.example.administrator.kib_3plus.ui.DialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.ui.PickerView;

import java.util.List;

/**
 * Created by cui on 2017/6/28.
 */

public class OneWheelDialogFragment extends DialogFragment {
    private static OneWheelDialogFragment mOneWheelDialogFragment;
    private static List mData;
    private static PickerView.onSelectListener mListener;
    private static View.OnClickListener onclicks;
    private  PickerView one_wheel_left_pv;
    private TextView one_wheel_done_tv,one_wheel_cancel_tv;
    private int mSelected;
    public static OneWheelDialogFragment newInstance(List<String> data, PickerView.onSelectListener listener
            ,  View.OnClickListener onclick){
        if(mOneWheelDialogFragment==null){
            mOneWheelDialogFragment=new OneWheelDialogFragment();
        }
        mData=data;
        mListener=listener;
        onclicks=onclick;
        return mOneWheelDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.one_wheel_include,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView(v);
        return v;
    }

    private void initView(View v) {
        one_wheel_left_pv=(PickerView) v.findViewById(R.id.one_wheel_left_pv);
        one_wheel_done_tv=(TextView) v.findViewById(R.id.one_wheel_done_tv);
        one_wheel_cancel_tv=(TextView) v.findViewById(R.id.one_wheel_cancel_tv);
        one_wheel_left_pv.setData(mData);
        one_wheel_left_pv.setOnSelectListener(mListener);
        one_wheel_done_tv.setOnClickListener(onclicks);
        one_wheel_cancel_tv.setOnClickListener(onclicks);
        one_wheel_left_pv.setSelected(mSelected);
    }
    public void setSelected(int selected){
        mSelected=selected;
    }
}
