package com.example.administrator.kib_3plus.view.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.DialogUtil;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;
import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;
import com.example.administrator.kib_3plus.http.OkHttpUtils;
import com.example.administrator.kib_3plus.http.mode.AddIconCallbackMode;
import com.example.administrator.kib_3plus.http.mode.AddMenberJsonMode;
import com.example.administrator.kib_3plus.http.mode.AddMenberMode;
import com.example.administrator.kib_3plus.ui.DialogFragment.AddIconDialogFragment;
import com.example.administrator.kib_3plus.ui.DialogFragment.AddIconSelectDialogFragment;
import com.example.administrator.kib_3plus.ui.DialogFragment.OneWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.DialogFragment.WeightWheelDialogFragment;
import com.example.administrator.kib_3plus.ui.PickerView;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.base.BaseFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;
import com.example.administrator.kib_3plus.view.manage.ContentViewManage;
import com.example.administrator.kib_3plus.view.manage.TitleManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.presenter.implement.PDB;
import cn.appscomm.sp.SPKey;
import cn.appscomm.sp.SPManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by cui on 2017/6/26.
 */

public class AddMemberFragment extends BaseFragment implements MyItemClickListener {
    /**
     * rl为点击事件的处理
     * tv为显示
     *
     */
    private RelativeLayout add_picture_bg_rl,add_gender_rl,add_age_rl,add_height_rl,add_weight_rl,add_birthday_rl;
    private LinearLayout add_add_view_ll;
    private RelativeLayout add_welcome_view_rl;
    private Button add_welcome_pair_bt;
    private RoundImageView add_picture_iv;
    private EditText add_name_et;
    private TextView add_text_fint_tv;
    private TextView add_gender_tv,add_age_tv,add_height_tv,add_weigh_tv,add_birthday_tv;
    private ToggleButton add_like_yellow_bt,add_like_mazarine_bt,add_like_red_bt,add_like_violet_bt,add_like_blue_bt,add_like_grean_bt,add_like_orange_bt;
    private ToggleButton oldView =null;
    private static final int IMAGE = 1000;
    Uri photoUri;
    private OneWheelDialogFragment mOneWheelDialogFragment;
    private WeightWheelDialogFragment weightWheelDialogFragment;
    private int mIcon=-1;
    boolean isHeight=true;//判断弹框是体重还是身高
    private String rightNumHeightCache="6\"",leftNumHeightCache="5'",rightNumWeightCache=".0",leftNumWeightCache="88";
    private String leftHeight="5'",rightHeight="6\"",leftWeight="88",rightWeight=".0";
    private ProgressDialog regProgressDialog = null;
    AddIconDialogFragment mAddIconDialogFragment;
    AddIconSelectDialogFragment addIconSelectDialogFragment;
    public static final int ADD_MEMBER_OK=202;
    public static final int ADD_MEMBER_FAIL=102;
    public static final int ADD_MEMBER_ICON_OK=207;
    public AddMenberMode addMenberMode;
    private String mFavorite;
    private String url="";
    private String imgUrl="";
    private boolean isUrl=false;
    public boolean isMain=false;
    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ADD_MEMBER_OK:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    boolean isIcon=false;
                    if(mIcon>0){
                        isIcon=true;
                    }
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
                            ,mIcon
                            ,isIcon
                            ,SetUpNewDeviceFragment.NO_BIND
                            ,0);
                    LogUtils.i("ADD_MEMBER_OK="+addMenberMode.toString());
                    PDB.INSTANCE.addChildInfo(childInfoDB);
                    changeView(true);

                    break;
                case ADD_MEMBER_FAIL:
                    DialogUtil.INSTANCE.closeProgressDialog();
                    DialogUtil.INSTANCE.commonDialog(getContext(),getString(R.string.app_name),"Fail");

                    break;
                case ADD_MEMBER_ICON_OK:
                    updateChild();
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

        add_like_yellow_bt=findViewById(R.id.add_like_yellow_bt);
        add_like_mazarine_bt=findViewById(R.id.add_like_mazarine_bt);
        add_like_red_bt=findViewById(R.id.add_like_red_bt);
        add_like_violet_bt=findViewById(R.id.add_like_violet_bt);
        add_like_blue_bt=findViewById(R.id.add_like_blue_bt);
        add_like_grean_bt=findViewById(R.id.add_like_grean_bt);
        add_like_orange_bt=findViewById(R.id.add_like_orange_bt);

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
        mFavorite=getString(R.string.like_blue);
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
//                updateIcon();
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
        if(mIcon==-1&&url.equals("")){
            showToast("Please enter the Icon ");
            return;
        }
        if(TextUtils.isEmpty(add_name_et.getText().toString().trim())){
            showToast("Please enter the Name ");
            return;
        }
        if(add_name_et.getText().toString().trim().getBytes().length>11){
            showToast("The length of the name can't more than 11");
            return;
        }
        if(TextUtils.isEmpty(add_gender_tv.getText().toString().trim())){
            showToast("Please enter the gender ");
            return;
        }
        if(TextUtils.isEmpty(add_age_tv.getText().toString().trim())){
            showToast("Please enter the age ");
            return;
        }
        if(TextUtils.isEmpty(add_weigh_tv.getText().toString().trim())){
            showToast("Please enter the weigh");
            return;
        }
        if(TextUtils.isEmpty(add_height_tv.getText().toString().trim())){
            showToast("Please enter the height");
            return;
        }
        if(TextUtils.isEmpty(add_birthday_tv.getText().toString().trim())){
            showToast("Please enter the birthday");
            return;
        }
        if(isUrl){
            updateIcon();
        }else{
            updateChild();
        }


    }
    private void updateIcon(){
        String icon68=  CameraUtils.INSTANCE.bitmapToString(url);
        HashMap map=new HashMap();
        map.put("image",icon68);
        map.put("imageSuffix","jpg");
        OkHttpUtils.getInstance().postAsynHttp(OkHttpUtils.HOST+OkHttpUtils.UPLOAD_ICON,callback,map,"addChildIcon");

        regProgressDialog = DialogUtil.INSTANCE.logining(getContext());
        regProgressDialog.show();
    }

    private void updateChild(){
        int familyID=(int)SPManager.INSTANCE.getSPValue(SPKey.SP_FAMILY_ID_L28t,SPManager.DATA_INT);
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
                ,mFavorite
                ,imgUrl
        );
        String json=GsonUtils.objectToString(addMenberJsonMode);

        OkHttpUtils.getInstance().postJsonAsynHttp(OkHttpUtils.HOST+OkHttpUtils.ADD_CHILD,callback,json,"addChild");
        if(regProgressDialog==null){
            regProgressDialog = DialogUtil.INSTANCE.logining(getContext());
            regProgressDialog.show();
        }

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
            if(call.request().tag().equals("addChildIcon")){
                LogUtils.i("SJ="+sj);
                AddIconCallbackMode  addIconCallbackMode= GsonUtils.stringToClss(sj,AddIconCallbackMode.class);
                LogUtils.i("SJ="+addIconCallbackMode.getData().getImgUrl());
                if(addIconCallbackMode.getResult().equals("0")){
                    imgUrl=addIconCallbackMode.getData().getImgUrl();
                   String[] data= imgUrl.split("/");
                   String newName= data[data.length-1];
                    String newUrl=CameraUtils.INSTANCE.SAVE_IMG_PATH+ File.separator+newName;
                    LogUtils.i("newUrl="+newUrl);
                    LogUtils.i("url="+url);
                    if(CameraUtils.INSTANCE.changeIconName(url,newUrl)){
                        url=newUrl;
                    }
                    mHandler.sendEmptyMessage(ADD_MEMBER_ICON_OK);

                }else{
                    mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);
                }
            } else if(call.request().tag().equals("addChild")){
                try{
                    addMenberMode= GsonUtils.stringToClss(sj,AddMenberMode.class);
                    if(addMenberMode.getResult().equals("0")){
                        mHandler.sendEmptyMessage(ADD_MEMBER_OK);
                    }else{
                        mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);
                    }
                }catch (Exception e){
                    mHandler.sendEmptyMessage(ADD_MEMBER_FAIL);
                }

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

        add_like_yellow_bt.setOnClickListener(this);
        add_like_mazarine_bt.setOnClickListener(this);
        add_like_red_bt.setOnClickListener(this);
        add_like_violet_bt.setOnClickListener(this);
        add_like_blue_bt.setOnClickListener(this);
        add_like_grean_bt.setOnClickListener(this);
        add_like_orange_bt.setOnClickListener(this);


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




                addIconSelectDialogFragment= AddIconSelectDialogFragment.newInstance(this);
                addIconSelectDialogFragment.show(getChildFragmentManager(),"AddIconSelectDialogFragment");
                LogUtils.i("add_picture_iv");
                break;
            case R.id.add_icon_album_tv:
                LogUtils.i("add_icon_album_tv");
                if (addIconSelectDialogFragment!=null){
                    addIconSelectDialogFragment.dismiss();
                }
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMAGE);
                break;
            case R.id.add_icon_camera_tv:
                LogUtils.i("add_icon_camera_tv");
                if (addIconSelectDialogFragment!=null){
                    addIconSelectDialogFragment.dismiss();
                }
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoUri = CameraUtils.INSTANCE.getMediaFileUri(CameraUtils.TYPE_TAKE_PHOTO);
                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                getActivity().startActivityForResult(takeIntent, CameraUtils.CODE_TAKE_PHOTO);
                break;
            case R.id.add_icon_3plus_avater_tv:
                LogUtils.i("add_icon_3plus_avater_tv");
                if (addIconSelectDialogFragment!=null){
                    addIconSelectDialogFragment.dismiss();
                }
                mAddIconDialogFragment=AddIconDialogFragment.newInstance(this,this);
                mAddIconDialogFragment.show(getChildFragmentManager(),"addFragment");
                break;
            case R.id.add_icon_iv:
                if(mAddIconDialogFragment!=null){
                    mAddIconDialogFragment.dismiss();

                }
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

            case R.id.add_like_yellow_bt:
                LogUtils.i("add_like_yellow_iv");
                mFavorite=getString(R.string.like_yellow);
                add_picture_bg_rl.setBackgroundResource(R.color.like_yellow);

                break;

            case R.id.add_like_mazarine_bt:
                LogUtils.i("add_like_mazarine_iv");
                mFavorite=getString(R.string.like_mazarine);
                add_picture_bg_rl.setBackgroundResource(R.color.like_mazarine);
                setLike(add_like_mazarine_bt);

                break;
            case R.id.add_like_red_bt:
                LogUtils.i("add_like_red_iv");
                mFavorite=getString(R.string.like_red);
                add_picture_bg_rl.setBackgroundResource(R.color.like_red);
                setLike(add_like_red_bt);

                break;
            case R.id.add_like_violet_bt:
                LogUtils.i("add_like_violet_iv");
                mFavorite=getString(R.string.like_violet);
                add_picture_bg_rl.setBackgroundResource(R.color.like_violet);
                setLike(add_like_violet_bt);

                break;
            case R.id.add_like_blue_bt:
                LogUtils.i("add_like_blue_iv");
                mFavorite=getString(R.string.like_blue);
                add_picture_bg_rl.setBackgroundResource(R.color.like_blue);
                setLike(add_like_blue_bt);

                break;
            case R.id.add_like_grean_bt:
                LogUtils.i("add_like_grean_iv");
                mFavorite=getString(R.string.like_green);
                add_picture_bg_rl.setBackgroundResource(R.color.like_green);
                setLike(add_like_grean_bt);

                break;
            case R.id.add_like_orange_bt:
                LogUtils.i("add_like_orange_iv");
                mFavorite=getString(R.string.like_orange);
                add_picture_bg_rl.setBackgroundResource(R.color.like_orange);
                setLike(add_like_orange_bt);
                break;


        }
    }

    private void setLike(ToggleButton v){
        if(oldView!=null){
            oldView.setChecked(false);
        }
        oldView=v;

    }

    private void finish() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(this);
        transaction.commit();

    }

    @Override
    public void onActivityReenter(int requestCode, int resultCode, Intent data) {
        LogUtils.i("requestCode+"+requestCode);
        LogUtils.i("resultCode+"+resultCode);

        if (requestCode==CameraUtils.CODE_TAKE_PHOTO){
            CameraUtils.INSTANCE.startPhotoZoom(photoUri);
        }else if (requestCode==CameraUtils.CROP_REQUEST_CODE){
            CameraUtils.INSTANCE.getImage(data);
            LogUtils.i("photoUri+"+CameraUtils.INSTANCE.SAVE_IMG_PATH);
            LogUtils.i("photoUri+"+CameraUtils.INSTANCE.CROPED_FACE_IMG);
            Bitmap bitmap= BitmapFactory.decodeFile(CameraUtils.INSTANCE.SAVE_IMG_PATH+ File.separator +CameraUtils.INSTANCE.CROPED_FACE_IMG);
            add_picture_iv.setImageBitmap(bitmap);
            add_picture_bg_rl.setBackgroundResource(NumberUtils.INSTANCE.getFavorite(mFavorite));
            bitmap=null;
            url=CameraUtils.INSTANCE.SAVE_IMG_PATH+ File.separator +CameraUtils.INSTANCE.CROPED_FACE_IMG;
            isUrl=true;
        }
        if(requestCode == 66536 ){//不要修改这个，这个是调用相机返回的数值
            LogUtils.i("photoUri+"+IMAGE);
            Uri selectedImage = data.getData();
            LogUtils.i("photoUri+"+selectedImage.toString());
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            LogUtils.i("imagePath+"+imagePath);
            url=imagePath;
            Bitmap bitmap= BitmapFactory.decodeFile(url);
            add_picture_iv.setImageBitmap(bitmap);
            add_picture_bg_rl.setBackgroundResource(NumberUtils.INSTANCE.getFavorite(mFavorite));
            bitmap=null;
            c.close();
            isUrl=true;

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


    @Override
    public void onItemClick(View view, int postion) {
        LogUtils.i("", "----点击:" + postion);
        mIcon= PublicData.iconListData.get(postion);
        add_picture_iv.setImageResource(mIcon);
        add_picture_bg_rl.setBackgroundResource(NumberUtils.INSTANCE.getFavorite(mFavorite));
        url="";
        isUrl=false;
        if(mAddIconDialogFragment!=null){
            mAddIconDialogFragment.dismiss();
        }
    }
}
