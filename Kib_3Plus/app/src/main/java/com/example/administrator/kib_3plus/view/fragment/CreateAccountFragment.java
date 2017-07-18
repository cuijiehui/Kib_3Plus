package com.example.administrator.kib_3plus.view.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kib_3plus.Utils.KeyboardUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.CallBackMsgMode;
import com.example.administrator.kib_3plus.ui.PickerView;
import com.example.administrator.kib_3plus.ui.DialogFragment.WeightWheelDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by cui on 2017/6/26.
 */

public class CreateAccountFragment extends BaseFragment implements View.OnClickListener {
    private EditText account_name_et,account_eamil_et,account_password_et;
    private RadioButton account_male_rb,account_female_rb;
    private RadioButton account_imperial_rb,account_metric_rb;
    private TextView account_height_tv,account_weight_tv,account_birthday_tv;
    private Button account_sign_up_bt;
    RelativeLayout account_include;

    boolean isHeight=true;//判断弹框是体重还是身高
    private String leftHeight="5'",rightHeight="6\"",leftWeight="88",rightWeight=".0";
    private String leftHeightCm="167",rightHeightCm=".6",leftWeightCm="39",rightWeightCm=".8";
    private String rightNumHeightCache="6\"",leftNumHeightCache="5'",rightNumWeightCache=".0",leftNumWeightCache="88";
    private ProgressDialog regProgressDialog = null;

    private WeightWheelDialogFragment weightWheelDialogFragment;


    Handler mHandler;
    public static final int ACCOUNT_REG_OK=20170629;
    public static final int ACCOUNT_REG_FAIL=201706291;

    private static CreateAccountFragment mCreateAccountFragment;
    public static CreateAccountFragment getInstance(){
        if(mCreateAccountFragment==null){
            mCreateAccountFragment=new CreateAccountFragment();
        }
        return mCreateAccountFragment;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.create_account_layout;
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        super.initView(inflateView, savedInstanceState);
        account_male_rb.setChecked(true);
        account_imperial_rb.setChecked(true);
        account_height_tv.setText(leftHeight+rightHeight);
        account_weight_tv.setText(leftWeight+rightWeight+"lbs");
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        account_name_et= findViewById(R.id.account_name_et);
        account_eamil_et= findViewById(R.id.account_eamil_et);
        account_password_et= findViewById(R.id.account_password_et);
        account_male_rb= findViewById(R.id.account_male_rb);
        account_female_rb= findViewById(R.id.account_female_rb);
        account_imperial_rb= findViewById(R.id.account_imperial_rb);
        account_metric_rb= findViewById(R.id.account_metric_rb);
        account_height_tv= findViewById(R.id.account_height_tv);
        account_weight_tv= findViewById(R.id.account_weight_tv);
        account_birthday_tv= findViewById(R.id.account_birthday_tv);
        account_sign_up_bt= findViewById(R.id.account_sign_up_bt);

//        account_include= findViewById(R.id.account_include);

    }

    @Override
    public void initData() {
        super.initData();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case ACCOUNT_REG_OK:
                        DialogUtil.INSTANCE.closeProgressDialog();
                        Bundle bundle=new Bundle();
                        bundle.putString(LoginFragment.BUNDLE_EMAIL,account_eamil_et.getText().toString().trim());
                        bundle.putString(LoginFragment.BUNDLE_PASSWORD,account_password_et.getText().toString().trim());
                        ContentViewManage.getInstance().setFragmentType(ContentViewManage.LOGIN_FRAGMENT,bundle);

                        break;
                    case ACCOUNT_REG_FAIL:
                        DialogUtil.INSTANCE.closeProgressDialog();
                        DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                        break;
                }
            }
        };
    }

    @Override
    public void initListener() {
        super.initListener();
        account_sign_up_bt.setOnClickListener(this);
        account_birthday_tv.setOnClickListener(this);
        account_height_tv.setOnClickListener(this);
        account_weight_tv.setOnClickListener(this);

        account_male_rb.setOnClickListener(this);
        account_female_rb.setOnClickListener(this);
        account_imperial_rb.setOnClickListener(this);
        account_metric_rb.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_sign_up_bt:
                signUp();
                LogUtils.i("account_sign_up_bt");
                break;
            case R.id.account_birthday_tv:
                LogUtils.i("account_birthday_tv");
                getDate();
                break;
            case R.id.account_height_tv:
                LogUtils.i("account_birthday_tv");
                isHeight=true;
                if(account_imperial_rb.isChecked()){
                    int leftInt=PublicData.left_height_imperilal_Int.indexOf(leftHeight);
                    int rghtInt=PublicData.right_height_imperilal_Int.indexOf(rightHeight);
                    LogUtils.i("rghtInt="+rghtInt+"--leftInt="+leftInt);
                    weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_height_imperilal_Int, PublicData.right_height_imperilal_Int
                            ,LeftSelectLin ,RightSelectLin,this);
                    if(rghtInt>=0||leftInt>=0){
                        weightWheelDialogFragment.setSelected(leftInt,rghtInt);
                    }
                    weightWheelDialogFragment.show(getChildFragmentManager(),"height");

                }else{
                    int leftInt=PublicData.left_height_metric_Int.indexOf(leftHeightCm);
                    int rghtInt=PublicData.height_metric_Int.indexOf(rightHeightCm);
                    LogUtils.i("rghtInt="+rghtInt+"--leftInt="+leftInt);
                    weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_height_metric_Int, PublicData.height_metric_Int
                            ,LeftSelectLin ,RightSelectLin,this);
                    if(rghtInt>=0||leftInt>=0){
                        weightWheelDialogFragment.setSelected(leftInt,rghtInt);
                    }
                    weightWheelDialogFragment.show(getChildFragmentManager(),"height");
                }

                break;
            case R.id.account_weight_tv:
                LogUtils.i("account_birthday_tv");
                LogUtils.i("account_birthday_tv");
                isHeight=false;
                if(account_imperial_rb.isChecked()){
                    int leftInt=PublicData.left_weight_imperilal_Int.indexOf(leftWeight);
                    int rghtInt=PublicData.height_metric_Int.indexOf(rightWeight);
                    LogUtils.i("rghtInt="+rghtInt+"--leftInt="+leftInt);

                    weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_weight_imperilal_Int, PublicData.height_metric_Int
                            ,LeftSelectLin ,RightSelectLin,this);
                    if(rghtInt>=0||leftInt>=0){
                        weightWheelDialogFragment.setSelected(leftInt,rghtInt);
                    }
                    weightWheelDialogFragment.show(getChildFragmentManager(),"Weight");
                }else{
                    int leftInt=PublicData.left_weight_metric_Int.indexOf(leftWeightCm);
                    int rghtInt=PublicData.height_metric_Int.indexOf(rightWeightCm);
                    weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_weight_metric_Int, PublicData.height_metric_Int
                            ,LeftSelectLin ,RightSelectLin,this);
                    if(rghtInt>=0||leftInt>=0){
                        weightWheelDialogFragment.setSelected(leftInt,rghtInt);
                    }
                    weightWheelDialogFragment.show(getChildFragmentManager(),"Weight");
                }

                break;
            case R.id.account_male_rb:
                LogUtils.i("account_male_rb");
                account_male_rb.setChecked(true);
                account_female_rb.setChecked(false);
                break;
            case R.id.account_female_rb:
                LogUtils.i("account_female_rb");
                account_male_rb.setChecked(false);
                account_female_rb.setChecked(true);
                break;
            case R.id.account_imperial_rb:
                LogUtils.i("account_imperial_rb");
                account_imperial_rb.setChecked(true);
                account_metric_rb.setChecked(false);
                switchUnit();
                break;
            case R.id.account_metric_rb:
                LogUtils.i("account_metric_rb");
                account_imperial_rb.setChecked(false);
                account_metric_rb.setChecked(true);
                switchUnit();
                break;
            case R.id.wheel_done_tv:
                LogUtils.i("wheel_done_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                    if(isHeight){
                        if(account_imperial_rb.isChecked()){
                            leftHeight=leftNumHeightCache;
                            rightHeight=rightNumHeightCache;
                            leftHeightCm=NumberUtils.INSTANCE.ftToCmLeft(leftHeight+rightHeight);
                            rightHeightCm=NumberUtils.INSTANCE.ftToCmRight(leftHeight+rightHeight);
                            LogUtils.i("leftHeight="+leftHeight+"--rightHeight="+rightHeight);
                            account_height_tv.setText(leftHeight+rightHeight);
                        }else{
                            leftHeightCm=leftNumHeightCache;
                            rightHeightCm=rightNumHeightCache;
                            leftHeight=NumberUtils.INSTANCE.cmToFtLeft(leftHeightCm+rightHeightCm);
                            rightHeight=NumberUtils.INSTANCE.cmToFtRight(leftHeightCm+rightHeightCm);
                            LogUtils.i("leftHeight="+leftHeightCm+"--rightHeight="+rightHeightCm);
                            account_height_tv.setText(leftHeightCm+rightHeightCm);
                        }

                    }else{
                        if(account_imperial_rb.isChecked()){
                            leftWeight=leftNumWeightCache;
                            rightWeight=rightNumWeightCache;
                            leftWeightCm=NumberUtils.INSTANCE.lbsToKgLeft(leftWeight+rightWeight);
                            rightWeightCm=NumberUtils.INSTANCE.lbsToKgRight(leftWeight+rightWeight);

                            LogUtils.i("leftWeight="+leftWeight+"--rightWeight="+rightWeight);
                            account_weight_tv.setText(leftWeight+rightWeight+"lbs");
                        }else{
                            leftWeightCm=leftNumWeightCache;
                            rightWeightCm=rightNumWeightCache;
                            leftWeight=NumberUtils.INSTANCE.kgToLbsLeft(leftWeightCm+rightWeightCm);
                            rightWeight=NumberUtils.INSTANCE.kgToLbsRight(leftWeightCm+rightWeightCm);
                            LogUtils.i("leftWeight="+leftWeightCm+"--rightWeight="+rightWeightCm);
                            account_weight_tv.setText(leftWeightCm+rightWeightCm+"lbs");
                        }

                    }
                }
                break;
            case R.id.wheel_cancel_tv:
                LogUtils.i("wheel_done_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                    if(isHeight){
                        if(account_imperial_rb.isChecked()){
                            leftNumHeightCache=leftHeight;
                            rightNumHeightCache= rightHeight;
                            LogUtils.i("leftHeight="+leftHeight+"--rightHeight="+rightHeight);
                        }else{
                            leftNumHeightCache= leftHeightCm;
                            rightNumHeightCache=  rightHeightCm;
                            LogUtils.i("leftHeight="+leftHeightCm+"--rightHeight="+rightHeightCm);
                        }

                    }else{
                        if(account_imperial_rb.isChecked()){
                            leftNumWeightCache=leftWeight;
                            rightNumWeightCache=rightWeight=rightNumWeightCache;
                            LogUtils.i("leftWeight="+leftWeight+"--rightWeight="+rightWeight);
                        }else{
                            leftNumWeightCache=leftWeightCm;
                            rightNumWeightCache=rightWeightCm;
                            LogUtils.i("leftWeight="+leftWeightCm+"--rightWeight="+rightWeightCm);
                        }

                    }
                }
                break;
        }
    }


    private void switchUnit() {
      if(account_imperial_rb.isChecked()){
          account_height_tv.setText(leftHeight+rightHeight);
          account_weight_tv.setText(leftWeight+rightWeight+"lbs");

      }else {
          account_height_tv.setText(leftHeightCm+rightHeightCm);
          account_weight_tv.setText(leftWeightCm+rightWeightCm+"kg");
      }
    }
    Callback callback=new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            LogUtils.i("onFailure="+e.getMessage()+"-call="+call.toString());
            mHandler.sendEmptyMessage(ACCOUNT_REG_FAIL);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
//            LogUtils.i("onResponse="+response.body().string()+"-call="+call.toString());
          String sj= (String) response.body().string();//需要添加stirng强转
            LogUtils.i("SJ="+sj);
            CallBackMsgMode callBackMsgMode=   GsonUtils.stringToClss(sj,CallBackMsgMode.class);
            LogUtils.i("SJ="+callBackMsgMode.toString());
            if(callBackMsgMode.getResult().equals("0")){
                mHandler.sendEmptyMessage(ACCOUNT_REG_OK);
            }else{
                mHandler.sendEmptyMessage(ACCOUNT_REG_FAIL);
            }
        }
    };
    private void signUp() {
        if(TextUtils.isEmpty(account_name_et.getText().toString().trim())){
            showToast("Please enter a name");
                return;
        }
        if(TextUtils.isEmpty(account_eamil_et.getText().toString().trim())){
            showToast("Please enter a eamil");

            return;
        }
        if(TextUtils.isEmpty(account_password_et.getText().toString().trim())){
            showToast("Please enter a password");

            return;
        }
        if(TextUtils.isEmpty(account_birthday_tv.getText().toString().trim())){
            showToast("Please enter a birthday");
            return;
        }
        int gender=account_male_rb.isChecked()?0:1;
        HashMap map = new HashMap();
        map.put("userName",account_name_et.getText().toString().trim());
        map.put("email",account_eamil_et.getText().toString().trim());
        map.put("password",NumberUtils.INSTANCE.MD5(account_password_et.getText().toString().trim()));
        map.put("gender",gender+"");
        map.put("birthDay",account_birthday_tv.getText().toString().trim());//日期要这种模式 1993-09-04
        map.put("height",leftHeightCm+rightHeightCm);
        map.put("weight",leftWeightCm+rightWeightCm);
        map.put("heightUnit","1");//默认为公制来保存
        map.put("weightUnit","1");//默认为公制来保存
        map.put("countryCode","0");
        map.put("imgUrl","");
        map.put("encryptMode","1");
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.REG,callback,map,"reg");
        regProgressDialog = DialogUtil.INSTANCE.logining(getContext());
        regProgressDialog.show();

    }

    public PickerView.onSelectListener LeftSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text+"---isHeight="+isHeight);
            if(isHeight){
                leftNumHeightCache=text;

            }else{
                leftNumWeightCache=text;

            }
        }
    };
    public PickerView.onSelectListener RightSelectLin=new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的text是="+text+"---isHeight="+isHeight);
            if(isHeight){
                rightNumHeightCache=text;

            }else{
                rightNumWeightCache=text;

            }
        }
    };

    @Override
    public boolean onBackPressed() {
        KeyboardUtils.hideKeyBoard(getContext(),getView());
        ContentViewManage.getInstance().setFragmentType(ContentViewManage.WELCOME_FRAGMENT);
        return true;
    }

    public void getDate() {
        String strDate = "";
        int year = Integer.parseInt(NumberUtils.INSTANCE.getCurYear(new Date()));
        int month = Integer.parseInt(NumberUtils.INSTANCE.getCurMonthOfYear(new Date())) - 1;
        int day = Integer.parseInt(NumberUtils.INSTANCE.getCurDayOfMonth(new Date()));
        strDate= account_birthday_tv.getText().toString().trim();
        try{
            LogUtils.i("","strDate=-"+strDate);
            LogUtils.i("","year="+year);
            LogUtils.i("","month="+month);
            LogUtils.i("","day="+day);
            if(!strDate.equals("")){
                int monthIndex=strDate.indexOf("/");
                LogUtils.i("","monthIndex=-"+monthIndex);

                month=Integer.parseInt(strDate.substring(0,monthIndex).trim());
//				if ("en".equals(PublicData.currentLang)){
                month=month-1;
//				}

                strDate=strDate.substring(monthIndex+1,strDate.length());
                int dayIndex=strDate.indexOf("/");
                LogUtils.i("","dayIndex=-"+dayIndex);

                day=Integer.parseInt(strDate.substring(0,dayIndex).trim());
                strDate=strDate.substring(dayIndex+1,strDate.length());
                year=Integer.parseInt(strDate.trim());


            }
        }catch (Exception e){

        }




        new DatePickerDialog(getContext(),3,
                new DatePickerDialog.OnDateSetListener() {
                    // 这三个参数就是用户选择完成时的时间
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        if (!view.isShown()) return;


                        Calendar cal = Calendar.getInstance();

                        cal.set(year, monthOfYear, dayOfMonth);

                        if ( cal.getTimeInMillis() > System.currentTimeMillis() ) return;


                        StringBuilder sb = new StringBuilder();



                        sb.append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth);


//						if ("en".equals(PublicData.currentLang))
//						{
                        account_birthday_tv.setText(year+"-"+ (monthOfYear+1) +"-" +dayOfMonth );
//							reg_birthday.setText(PublicData.english_monthL[monthOfYear] + " "+ dayOfMonth +" " + year);
//						}
//						else reg_birthday.setText( dayOfMonth +" / " +(monthOfYear+1) +" / "+year);

//                        reg_birthS = sb.toString();

                        //	reg_birthday.setText(sb.toString());
                        LogUtils.i("", ">>date:" + sb.toString());

                    }

                },
                year, month, day).show();

        LogUtils.i("", "----选择返回:" + strDate);
    }
}
