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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.KeyboardUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;

import static android.os.Build.VERSION_CODES.N;
import static com.example.administrator.kib_3plus.R.id.delete_dialog_cancel_bt;
import static com.example.administrator.kib_3plus.R.id.delete_dialog_confirm_bt;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_cb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_f_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_m_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_sa_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_su_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_th_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_tu_tb;
import static com.example.administrator.kib_3plus.R.id.inactivity_alert_w_tb;
import static junit.runner.Version.id;

/**
 * Created by cui on 2017/7/6.
 */

public class ChoresWriteDialogFragment extends DialogFragment implements View.OnClickListener{

    private Button chore_delete_bt;
    private ToggleButton chores_alert_su_tb,chores_alert_m_tb
            ,chores_alert_tu_tb,chores_alert_w_tb
            ,chores_alert_th_tb,chores_alert_f_tb,chores_alert_sa_tb;
    private TextView chore_add_gold_1_tv,chore_add_gold_3_tv,chore_add_gold_5_tv,chore_custom_tv,chore_name_tv;
    private EditText chores_custom_gold_et;
    private Button chores_dialog_cancel_bt,chores_dialog_confirm_bt;

    private int mGold=0;
    private boolean isCustom=false;

    private static ChoresWriteDialogFragment instance;
    private static View.OnClickListener onclicks;
    private static String mChoresName;
    public static ChoresWriteDialogFragment newInstance(View.OnClickListener onclick,String name){
        if(instance==null){
            instance=new ChoresWriteDialogFragment();
        }
        onclicks=onclick;
        mChoresName=name;
        return instance;
    }

    public void setNmae(String name){
        mChoresName=name;
        chore_name_tv.setText(mChoresName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.chores_write_dialog,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(v);
        initListener();
        return v;
    }

    private void initListener() {
        chores_dialog_cancel_bt.setOnClickListener(onclicks);
        chores_dialog_confirm_bt.setOnClickListener(onclicks);
        chore_delete_bt.setOnClickListener(onclicks);

        chore_add_gold_1_tv.setOnClickListener(this);
        chore_add_gold_3_tv.setOnClickListener(this);
        chore_add_gold_5_tv.setOnClickListener(this);
        chore_custom_tv.setOnClickListener(this);
    }

    private void initView(View v) {
        chore_delete_bt=(Button)v.findViewById(R.id.chore_delete_bt);
        chores_dialog_cancel_bt=(Button)v.findViewById(R.id.chores_dialog_cancel_bt);
        chores_dialog_confirm_bt=(Button)v.findViewById(R.id.chores_dialog_confirm_bt);
        chore_add_gold_1_tv=(TextView)v.findViewById(R.id.chore_add_gold_1_tv);
        chore_add_gold_3_tv=(TextView)v.findViewById(R.id.chore_add_gold_3_tv);
        chore_add_gold_5_tv=(TextView)v.findViewById(R.id.chore_add_gold_5_tv);
        chore_custom_tv=(TextView)v.findViewById(R.id.chore_custom_tv);
        chore_name_tv=(TextView)v.findViewById(R.id.chore_name_tv);

        chores_alert_su_tb=(ToggleButton)v.findViewById(R.id.chores_alert_su_tb);
        chores_alert_m_tb=(ToggleButton)v.findViewById(R.id.chores_alert_m_tb);
        chores_alert_tu_tb=(ToggleButton)v.findViewById(R.id.chores_alert_tu_tb);
        chores_alert_w_tb=(ToggleButton)v.findViewById(R.id.chores_alert_w_tb);
        chores_alert_th_tb=(ToggleButton)v.findViewById(R.id.chores_alert_th_tb);
        chores_alert_f_tb=(ToggleButton)v.findViewById(R.id.chores_alert_f_tb);
        chores_alert_sa_tb=(ToggleButton)v.findViewById(R.id.chores_alert_sa_tb);

        chores_custom_gold_et=(EditText)v.findViewById(R.id.chores_custom_gold_et);


    }

    private void changeGold(View v){

        chore_add_gold_1_tv.setBackground(null);
        chore_add_gold_3_tv.setBackground(null);
        chore_add_gold_5_tv.setBackground(null);
        if(v!=null){
            v.setBackgroundResource(R.drawable.btn_green_bg_shape);
        }
    }

    /**
     * 获取周期
     * @return
     */
    public String getFrequency(){
        StringBuilder sb = new StringBuilder();
        if (chores_alert_m_tb.isChecked()) {//inactivity_alert_m_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_tu_tb.isChecked()) {//inactivity_alert_tu_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_w_tb.isChecked()) {//inactivity_alert_w_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_th_tb.isChecked()) {//inactivity_alert_th_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_f_tb.isChecked()) {//inactivity_alert_f_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_sa_tb.isChecked()) {//inactivity_alert_sa_tb
            sb.append('1');
        } else sb.append('0');

        if (chores_alert_su_tb.isChecked()) {//inactivity_alert_su_tb
            sb.append('1');
        } else sb.append('0');
        return sb.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mChoresName!=null){
            chore_name_tv.setText(mChoresName);
        }
        mGold=0;
        changeGold(null);
        setFrequency();
    }

    private void setFrequency() {
        chores_alert_m_tb.setChecked(false);
        chores_alert_tu_tb.setChecked(false);
        chores_alert_w_tb.setChecked(false);
        chores_alert_th_tb.setChecked(false);
        chores_alert_f_tb.setChecked(false);
        chores_alert_sa_tb.setChecked(false);
        chores_alert_su_tb.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chore_add_gold_1_tv:
                LogUtils.i("chore_add_gold_1_tv");

                changeGold(chore_add_gold_1_tv);
                mGold=1;
                break;
            case R.id.chore_add_gold_3_tv:
                LogUtils.i("chore_add_gold_3_tv");
                changeGold(chore_add_gold_3_tv);
                mGold=3;

                break;
            case R.id.chore_add_gold_5_tv:
                LogUtils.i("chore_add_gold_5_tv");
                changeGold(chore_add_gold_5_tv);
                mGold=5;
                break;
            case R.id.chore_custom_tv:
                LogUtils.i("chore_custom_tv");
                isCustom=!isCustom;
                changeCustom(isCustom);
                break;
        }
    }
    private void changeCustom(boolean b){
        if(b){
            chore_add_gold_1_tv.setVisibility(View.GONE);
            chore_add_gold_3_tv.setVisibility(View.GONE);
            chore_add_gold_5_tv.setVisibility(View.GONE);
            chores_custom_gold_et.setVisibility(View.VISIBLE);
        }else{
            KeyboardUtils.hideKeyBoard(getContext(),getView());
            chore_add_gold_1_tv.setVisibility(View.VISIBLE);
            chore_add_gold_3_tv.setVisibility(View.VISIBLE);
            chore_add_gold_5_tv.setVisibility(View.VISIBLE);
            chores_custom_gold_et.setVisibility(View.GONE);
        }
    }
    public String getChoreReward(){
        String choreReward="0";

        if(isCustom){
            LogUtils.i("chores_custom_gold_et");
            choreReward=chores_custom_gold_et.getText().toString();
        }else{
            LogUtils.i("mGold");
            choreReward=mGold+"";
        }
        return choreReward;
    }
}
