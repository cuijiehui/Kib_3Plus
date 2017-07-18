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
 * Created by cui on 2017/6/27.
 */

public class WeightWheelDialogFragment extends DialogFragment {

    private static WeightWheelDialogFragment mWeightWheelDialogFragment;
    private static List<String> lsData,rsData;
    private PickerView wheel_right_pv,wheel_left_pv;
    private TextView wheel_done_tv,wheel_cancel_tv;
    private static  PickerView.onSelectListener lsListener,rsListener;
    private static View.OnClickListener onclicks;
    private int leftInt=0,rightInt=0;
    public static WeightWheelDialogFragment newInstance(List<String> lData,List<String> rData,PickerView.onSelectListener lListener
            ,PickerView.onSelectListener rListener,View.OnClickListener onclick){
        if(mWeightWheelDialogFragment==null){
            mWeightWheelDialogFragment=new WeightWheelDialogFragment();
        }
        lsData=lData;
        rsData=rData;
        lsListener=lListener;
        rsListener=rListener;
        onclicks=onclick;
        return mWeightWheelDialogFragment;
    }

    public WeightWheelDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.wheel_weight_include,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        init(v);
        return v;
    }

    private void init(View v) {
        wheel_left_pv=(PickerView) v.findViewById(R.id.wheel_left_pv);
        wheel_right_pv=(PickerView) v.findViewById(R.id.wheel_right_pv);
        wheel_done_tv=(TextView) v.findViewById(R.id.wheel_done_tv);
        wheel_cancel_tv=(TextView) v.findViewById(R.id.wheel_cancel_tv);
        wheel_left_pv.setData(lsData);
        wheel_left_pv.setOnSelectListener(lsListener);
        wheel_right_pv.setData(rsData);
        wheel_right_pv.setOnSelectListener(rsListener);
        wheel_done_tv.setOnClickListener(onclicks);
        wheel_cancel_tv.setOnClickListener(onclicks);
        wheel_left_pv.setSelected(leftInt);
        wheel_right_pv.setSelected(rightInt);
    }
    public void setSelected(int leftInt,int rightInt){
this.leftInt=leftInt;
this.rightInt=rightInt;

    }
}
