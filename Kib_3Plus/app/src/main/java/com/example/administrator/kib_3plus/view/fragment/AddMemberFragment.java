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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.AddMenberJsonMode;
import com.example.administrator.kib_3plus.http.mode.AddMenberMode;
import com.example.administrator.kib_3plus.ui.DialogFragment.OneWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.PickerView;
import com.example.administrator.kib_3plus.ui.DialogFragment.WeightWheelDialogFragment;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static okhttp3.internal.Internal.instance;


/**
 * Created by cui on 2017/6/26.
 */

public class AddMemberFragment extends BaseFragment {
    /**
     * rl为点击事件的处理
     * tv为显示
     *
     */
    private RelativeLayout add_picture_bg_rl,add_gender_rl,add_age_rl,add_height_rl,add_weight_rl,add_birthday_rl;
    private LinearLayout add_add_view_ll;
    private RelativeLayout add_welcome_view_rl;
    private Button add_welcome_pair_bt;
    private ImageView add_picture_iv;
    private EditText add_name_et;
    private TextView add_text_fint_tv;
    private TextView add_gender_tv,add_age_tv,add_height_tv,add_weigh_tv,add_birthday_tv;
    private OneWheelDialogFragment mOneWheelDialogFragment;
    private WeightWheelDialogFragment weightWheelDialogFragment;

    boolean isHeight=true;//判断弹框是体重还是身高
    private String rightNumHeightCache="6\"",leftNumHeightCache="5'",rightNumWeightCache=".0",leftNumWeightCache="88";
    private String leftHeight="5'",rightHeight="6\"",leftWeight="88",rightWeight=".0";
    private ProgressDialog regProgressDialog = null;

    public static final int ADD_MEMBER_OK=202;
    public static final int ADD_MEMBER_FAIL=102;
    public AddMenberMode addMenberMode;
    public boolean isMain=false;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ADD_MEMBER_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    ChildInfoDB childInfoDB=new ChildInfoDB(
                            addMenberMode.getData().getId()
                            ,addMenberMode.getData().getFamilyId()
                            ,addMenberMode.getData().getName()
                            ,addMenberMode.getData().getGender()
                            ,addMenberMode.getData().getAge()
                            ,addMenberMode.getData().getHeight()
                            ,addMenberMode.getData().getWeight()
                            ,addMenberMode.getData().getBrithday()
                            ,addMenberMode.getData().getFavorite()
                            ,addMenberMode.getData().getUrl()
                            ,SetUpNewDeviceFragment.NO_BIND
                    ,0);
                    LogUtils.i("ADD_MEMBER_OK="+addMenberMode.toString());
                    PDB.INSTANCE.addChildInfo(childInfoDB);
                    changeView(true);

                    break;
                case ADD_MEMBER_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"失败");

                    break;
            }
        }
    };

    List ageData;
    private String listenerType;
    private String cenderCache="Male",ageCache="24";
    private static AddMemberFragment instance;
    public static AddMemberFragment getInstance(){
        if(instance==null){
            instance=new AddMemberFragment();
        }
        return instance;
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.add_member_layout;
    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        super.findView(inflateView, savedInstanceState);
        EventBus.getDefault().register(this);
        add_picture_bg_rl=findViewById(R.id.add_picture_bg_rl);
        add_add_view_ll=findViewById(R.id.add_add_view_ll);
        add_welcome_view_rl=findViewById(R.id.add_welcome_view_rl);
        add_text_fint_tv=findViewById(R.id.add_text_fint_tv);

        add_welcome_pair_bt=findViewById(R.id.add_welcome_pair_bt);

        add_name_et=findViewById(R.id.add_name_et);

        add_gender_rl=findViewById(R.id.add_gender_rl);
        add_age_rl=findViewById(R.id.add_age_rl);
        add_height_rl=findViewById(R.id.add_height_rl);
        add_weight_rl=findViewById(R.id.add_weight_rl);
        add_birthday_rl=findViewById(R.id.add_birthday_rl);

        add_picture_iv=findViewById(R.id.add_picture_iv);

        add_gender_tv=findViewById(R.id.add_gender_tv);
        add_age_tv=findViewById(R.id.add_age_tv);
        add_height_tv=findViewById(R.id.add_height_tv);
        add_weigh_tv=findViewById(R.id.add_weigh_tv);
        add_birthday_tv=findViewById(R.id.add_birthday_tv);
         ageData=new ArrayList();
        for(int i=1;i<150;i++){
            ageData.add(i+"");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("onResume");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.getString(MainFailyFragment.MAIN_TAG).equals(MainFailyFragment.MAIN_TAG)){
                isMain=true;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy");
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTitleMessgeEvent(TitleMessageEvent messageEvent) {
        switch (messageEvent.getMessage()){
            case "Save":
                LogUtils.i("save");
                addMember();

                break;
        }
    }
    public void changeView(boolean isWelcome){
        if(isWelcome){
            add_welcome_view_rl.setVisibility(View.VISIBLE);
            add_add_view_ll.setVisibility(View.GONE);
            add_text_fint_tv.setVisibility(View.GONE);
            TitleManage.getInstance().setType(TitleManage.TITLE_WHITE_MAINNAME,"Welcome "+add_name_et.getText().toString().trim(),"","");
        }else{
            add_add_view_ll.setVisibility(View.VISIBLE);
            add_welcome_view_rl.setVisibility(View.GONE);
            add_text_fint_tv.setVisibility(View.VISIBLE);
            TitleManage.getInstance().setType(TitleManage.TITLE_PURPLE_BACK_MAINNAME_SAVE,getString(R.string.title_add_member),null,getString(R.string.title_save));

        }
    }
    private void addMember() {
        if(TextUtils.isEmpty(add_name_et.getText().toString().trim())){
            showToast("名字不能为空");
            return;
        }
        if(add_name_et.getText().toString().trim().getBytes().length>11){
            showToast("名字不能超过11");
            return;
        }
        if(TextUtils.isEmpty(add_gender_tv.getText().toString().trim())){
            showToast("性别不能为空");
            return;
        }
        if(TextUtils.isEmpty(add_age_tv.getText().toString().trim())){
            showToast("年龄不能为空");
            return;
        }
        if(TextUtils.isEmpty(add_weigh_tv.getText().toString().trim())){
            showToast("体重不能为空");
            return;
        }
        if(TextUtils.isEmpty(add_height_tv.getText().toString().trim())){
            showToast("身高不能为空");
            return;
        }
        if(TextUtils.isEmpty(add_birthday_tv.getText().toString().trim())){
            showToast("生日不能为空");
            return;
        }
        int familyID=(int)SPManager.INSTANCE.getSPValue(SPKey.SP_FAMILY_ID_L28t,SPManager.DATA_INT);
//        HashMap map=new HashMap();
//        map.put("familyId",familyID+"");
//        map.put("name",add_name_et.getText().toString().trim());
//        map.put("gender",add_gender_tv.getText().toString().trim());
//        map.put("age",add_age_tv.getText().toString().trim());
//        map.put("height",add_height_tv.getText().toString().trim());
//        map.put("weight",add_weigh_tv.getText().toString().trim());
//        map.put("brithday",add_birthday_tv.getText().toString().trim());
//        map.put("favorite","red");
//        map.put("url","");
        int gender=2;
        if(add_gender_tv.getText().toString().trim().equals(getString(R.string.sex_male_rbt))){
            gender=1;
        }
        AddMenberJsonMode addMenberJsonMode=new AddMenberJsonMode(
                familyID+""
                ,add_name_et.getText().toString().trim()
                ,gender+""
                ,add_age_tv.getText().toString().trim()
                ,add_height_tv.getText().toString().trim()
                ,add_weigh_tv.getText().toString().trim()
                ,add_birthday_tv.getText().toString().trim()
                ,"red"
                ,""
        );
        String json=GsonUtils.objectToString(addMenberJsonMode);

        OkHttpUtils.getInstance().postJsonAsynHttp(OkHttpUtils.HOST+OkHttpUtils.ADD_CHILD,callback,json,"addChild");
        regProgressDialog = DialogUtil.INSTANCE.logining(getContext());
        regProgressDialog.show();

    }
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String sj= (String) response.body().string();//需要添加stirng强转
            LogUtils.i("SJ="+sj);
            try{
                addMenberMode= GsonUtils.stringToClss(sj,AddMenberMode.class);

                if(addMenberMode.getResult().equals("0")){
                    mHandler.sendEmptyMessage(ADD_MEMBER_OK);

                }else{
                    mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);
//
                }
            }catch (Exception e){
                mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);
            }



        }
    };

    @Override
    public void initListener() {
        super.initListener();
        add_gender_rl.setOnClickListener(this);
        add_age_rl.setOnClickListener(this);
        add_height_rl.setOnClickListener(this);
        add_weight_rl.setOnClickListener(this);
        add_birthday_rl.setOnClickListener(this);
        add_welcome_pair_bt.setOnClickListener(this);
        add_picture_iv.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        if(isMain){
            ContentViewManage.getInstance().setFragmentType(ContentViewManage.MAIN_FAILY_FRAGMENT);

        }else{
            ContentViewManage.getInstance().setFragmentType(ContentViewManage.FAMILY_CREATED_FRAFMENT);

        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_gender_rl:
                listenerType="add_gender_rl";
                List data=new ArrayList();
                data.add(getString(R.string.sex_male_rbt));
                data.add(getString(R.string.sex_female_rbt));
//                int index= data.indexOf(cenderCache);
                mOneWheelDialogFragment=OneWheelDialogFragment.newInstance(data, mOnSelectListener,this);
                mOneWheelDialogFragment.setSelected(0);
                mOneWheelDialogFragment.show(getChildFragmentManager(),"add_gender_rl");
                LogUtils.i("add_gender_rl");
                break;
            case R.id.add_age_rl:
                listenerType="add_age_rl";
//               int indexx=ageData.indexOf(ageCache);
                mOneWheelDialogFragment=OneWheelDialogFragment.newInstance(ageData, mOnSelectListener,this);
                mOneWheelDialogFragment.setSelected(23);
                mOneWheelDialogFragment.show(getChildFragmentManager(),"add_age_rl");
                LogUtils.i("add_gender_rl");
                LogUtils.i("add_age_rl");
                break;
            case R.id.add_height_rl:
                isHeight=true;
                listenerType="add_height_rl";
                int leftInt= PublicData.left_height_imperilal_Int.indexOf(leftHeight);
                int rghtInt=PublicData.right_height_imperilal_Int.indexOf(rightHeight);
                LogUtils.i("rghtInt="+rghtInt+"--leftInt="+leftInt);
                weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_height_imperilal_Int, PublicData.right_height_imperilal_Int
                        ,LeftSelectLin ,RightSelectLin,this);
                if(rghtInt>=0||leftInt>=0){
                    weightWheelDialogFragment.setSelected(leftInt,rghtInt);
                }
                weightWheelDialogFragment.show(getChildFragmentManager(),"height");
                LogUtils.i("add_height_rl");
                break;
            case R.id.add_weight_rl:
                isHeight=false;
                listenerType="add_weight_rl";
                int leftInts=PublicData.left_weight_imperilal_Int.indexOf(leftWeight);
                int rghtInts=PublicData.height_metric_Int.indexOf(rightWeight);
                LogUtils.i("rghtInt="+leftInts+"--leftInt="+rghtInts);

                weightWheelDialogFragment=WeightWheelDialogFragment.newInstance(PublicData.left_weight_imperilal_Int
                        , PublicData.height_metric_Int
                        ,LeftSelectLin ,RightSelectLin,this);
                if(rghtInts>=0||leftInts>=0){
                    weightWheelDialogFragment.setSelected(leftInts,rghtInts);
                }
                weightWheelDialogFragment.show(getChildFragmentManager(),"Weight");
                LogUtils.i("add_weight_rl");
                break;
            case R.id.add_birthday_rl:
                getDate();
                LogUtils.i("add_birthday_rl");
                break;
            case R.id.add_picture_iv:
                LogUtils.i("add_picture_iv");
                break;
            case R.id.one_wheel_cancel_tv:
                if(mOneWheelDialogFragment!=null){
                    mOneWheelDialogFragment.dismiss();
                }
                switch (listenerType){
                    case "add_gender_rl" :
                        cenderCache=add_gender_tv.getText().toString().trim();
                        break;
                    case "add_age_rl" :
                        ageCache=add_age_tv.getText().toString().trim();
                        break;
                    case "add_height_rl" :
                        leftNumHeightCache= leftHeight;
                        rightNumHeightCache= rightHeight;

                        break;
                    case "add_weight_rl" :
                        leftNumWeightCache= leftWeight;
                        rightNumWeightCache= rightWeight;
                        break;

                }
                listenerType="";

                LogUtils.i("one_wheel_cancel_tv");
                break;
            case R.id.one_wheel_done_tv:
                LogUtils.i("one_wheel_done_tv");
                if(mOneWheelDialogFragment!=null){
                    mOneWheelDialogFragment.dismiss();
                }
                switch (listenerType){
                    case "add_gender_rl" :
                        add_gender_tv.setText(cenderCache);
                        break;
                    case "add_age_rl" :
                        add_age_tv.setText(ageCache);

                        break;
                    case "add_height_rl" :
                        leftHeight= leftNumHeightCache;
                        rightHeight= rightNumHeightCache;
                        add_height_tv.setText(leftHeight+rightHeight);
                        break;
                    case "add_weight_rl" :
                        leftWeight= leftNumWeightCache;
                        rightWeight= rightNumWeightCache;
                        add_weigh_tv.setText(leftWeight+rightWeight);
                        break;

                }
                listenerType="";
                break;
            case R.id.wheel_cancel_tv:
                LogUtils.i("one_wheel_done_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                }
                switch (listenerType){
                    case "add_gender_rl" :
                        add_gender_tv.setText(cenderCache);
                        break;
                    case "add_age_rl" :
                        add_age_tv.setText(ageCache);

                        break;
                    case "add_height_rl" :
                        leftHeight= leftNumHeightCache;
                        rightHeight= rightNumHeightCache;
                        add_height_tv.setText(leftHeight+rightHeight);
                        break;
                    case "add_weight_rl" :
                        leftWeight= leftNumWeightCache;
                        rightWeight= rightNumWeightCache;
                        add_weigh_tv.setText(leftWeight+rightWeight);
                        break;

                }
                listenerType="";
                break;
            case R.id.wheel_done_tv:
                LogUtils.i("one_wheel_done_tv");
                if(weightWheelDialogFragment!=null){
                    weightWheelDialogFragment.dismiss();
                }
                switch (listenerType){
                    case "add_gender_rl" :
                        add_gender_tv.setText(cenderCache);
                        break;
                    case "add_age_rl" :
                        add_age_tv.setText(ageCache);

                        break;
                    case "add_height_rl" :
                        leftHeight= leftNumHeightCache;
                        rightHeight= rightNumHeightCache;
                        add_height_tv.setText(leftHeight+rightHeight);
                        break;
                    case "add_weight_rl" :
                        leftWeight= leftNumWeightCache;
                        rightWeight= rightNumWeightCache;
                        add_weigh_tv.setText(leftWeight+rightWeight);
                        break;

                }
                listenerType="";
                break;
            case R.id.add_welcome_pair_bt:
                LogUtils.i("add_welcome_pair_bt");
                Bundle bundle=new Bundle();
                bundle.putSerializable(SetUpNewDeviceFragment.MEMBER_DATA,addMenberMode);
                ContentViewManage.getInstance().setFragmentType(ContentViewManage.SET_UP_DEVICE_FRAGMENT,bundle);

                break;

        }
    }
    PickerView.onSelectListener mOnSelectListener = new PickerView.onSelectListener() {
        @Override
        public void onSelect(String text) {
            LogUtils.i("选择的是="+text);
            switch (listenerType){
                case "add_gender_rl" :
                    cenderCache=text;
                    break;
                case "add_age_rl" :
                    ageCache=text;

                    break;


            }
        }
    };
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
    public void getDate() {
        String strDate = "";
        int year = Integer.parseInt(NumberUtils.INSTANCE.getCurYear(new Date()));
        int month = Integer.parseInt(NumberUtils.INSTANCE.getCurMonthOfYear(new Date())) - 1;
        int day = Integer.parseInt(NumberUtils.INSTANCE.getCurDayOfMonth(new Date()));
        strDate= add_birthday_tv.getText().toString().trim();
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




        new DatePickerDialog(getContext(), 3,
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
                        add_birthday_tv.setText( (monthOfYear+1) +"-" +dayOfMonth +"-"+year);
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
