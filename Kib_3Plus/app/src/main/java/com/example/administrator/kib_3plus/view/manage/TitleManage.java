package com.example.administrator.kib_3plus.view.manage;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.EventBusUtils.TitleMessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cui on 2017/6/26.
 */

public class TitleManage implements View.OnClickListener {
    private static TitleManage mTitleManage;
    private Activity mContext;

    private LinearLayout title_ll;
    private RelativeLayout title_main_rl;
    private ImageView title_back_iv;
    private TextView title_name_tv,title_main_name_tv,title_right_tv;

    private int mTitleColor=R.color.colorViolet; //默认为紫色
    //背景色+保留什么控件
    public static final int TITLE_GONE=0;
    public static final int TITLE_PURPLE_BACK_MAINNAME=1;
    public static final int TITLE_PURPLE_BACK_MAINNAME_SAVE=2;
    public static final int TITLE_PURPLE_MAINNAME=3;
    public static final int TITLE_WHITE_MAINNAME=4;
    public static final int TITLE_WHITE_BACK_NAME_EDIT=5;



    public static TitleManage getInstance(){
        if(mTitleManage==null){
            mTitleManage=new TitleManage();
        }
        return mTitleManage;
    }
    public void init(Activity activity){
        mContext=activity;
        title_ll= (LinearLayout)activity.findViewById(R.id.title_ll);

        title_main_rl= (RelativeLayout)activity.findViewById(R.id.title_main_rl);

        title_back_iv= (ImageView)activity.findViewById(R.id.title_back_iv);
        title_name_tv= (TextView)activity.findViewById(R.id.title_name_tv);
        title_main_name_tv= (TextView)activity.findViewById(R.id.title_main_name_tv);
        title_right_tv= (TextView)activity.findViewById(R.id.title_right_tv);
        setListener();
    }

    private void setListener(){
        title_back_iv.setOnClickListener(this);
        title_right_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_iv:
                LogUtils.i("title_back_iv");
                ContentViewManage.getInstance().onBackPressed();
                break;
            case R.id.title_right_tv:
                LogUtils.i("title_right_tv");
                String message=title_right_tv.getText().toString();
                TitleMessageEvent titleMessageEvent=new TitleMessageEvent(message);
                EventBus.getDefault().post(titleMessageEvent);
                break;
        }
    }
    public void setType(int type){
        setType(type,null,null,null);
    }
    /**
     * 设置title的状态
     * 下面参数没有就传null
     * @param type  6种状态
     * @param mainName 标题中间名
     * @param name  back右边名
     * @param right 右边save或edit
     *
     */
    public void setType(int type,String mainName,String name,String right){
        LogUtils.i("setType"+type);
        switch(type){
            case TITLE_PURPLE_BACK_MAINNAME:
                title_ll.setVisibility(View.VISIBLE);
                mTitleColor=R.color.colorViolet;
                title_main_rl.setBackgroundColor(mContext.getResources().getColor(mTitleColor));
                title_back_iv.setVisibility(View.VISIBLE);
                title_main_name_tv.setVisibility(View.VISIBLE);
                title_name_tv.setVisibility(View.GONE);
                title_right_tv.setVisibility(View.GONE);
                title_main_name_tv.setText(mainName);
                title_main_name_tv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                title_back_iv.setImageResource(R.mipmap.title_back);
                setWindowStatusBarColor(mContext,mTitleColor);
                LogUtils.i("setType"+type);

                break;
            case TITLE_PURPLE_BACK_MAINNAME_SAVE:
                title_ll.setVisibility(View.VISIBLE);
                mTitleColor=R.color.colorViolet;
                title_main_rl.setBackgroundColor(mContext.getResources().getColor(mTitleColor));
                title_back_iv.setVisibility(View.VISIBLE);
                title_main_name_tv.setVisibility(View.VISIBLE);
                title_name_tv.setVisibility(View.GONE);
                title_right_tv.setVisibility(View.VISIBLE);
                title_main_name_tv.setText(mainName);
                title_main_name_tv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                title_right_tv.setText(right);
                title_right_tv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                title_back_iv.setImageResource(R.mipmap.title_back);
                title_back_iv.invalidate();
                setWindowStatusBarColor(mContext,mTitleColor);

                LogUtils.i("setType"+type);

                break;
            case TITLE_PURPLE_MAINNAME:
                title_ll.setVisibility(View.VISIBLE);
                mTitleColor=R.color.colorViolet;
                title_main_rl.setBackgroundColor(mContext.getResources().getColor(mTitleColor));
                title_back_iv.setVisibility(View.GONE);
                title_main_name_tv.setVisibility(View.VISIBLE);
                title_name_tv.setVisibility(View.GONE);
                title_right_tv.setVisibility(View.GONE);
                title_main_name_tv.setText(mainName);
                title_main_name_tv.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                setWindowStatusBarColor(mContext,mTitleColor);
                LogUtils.i("setType"+type);

                break;
            case TITLE_WHITE_MAINNAME:
                title_ll.setVisibility(View.VISIBLE);
                mTitleColor=R.color.colorWhite;
                title_main_rl.setBackgroundColor(mContext.getResources().getColor(mTitleColor));
                title_back_iv.setVisibility(View.GONE);
                title_main_name_tv.setVisibility(View.VISIBLE);
                title_name_tv.setVisibility(View.GONE);
                title_right_tv.setVisibility(View.GONE);
                title_main_name_tv.setText(mainName);
                title_main_name_tv.setTextColor(mContext.getResources().getColor(R.color.menber_green_color));
                setWindowStatusBarColor(mContext,mTitleColor);
                LogUtils.i("setType"+type);

                break;
            case TITLE_WHITE_BACK_NAME_EDIT:
                title_ll.setVisibility(View.VISIBLE);
                mTitleColor=R.color.colorWhite;
                title_main_rl.setBackgroundColor(mContext.getResources().getColor(mTitleColor));
                title_back_iv.setVisibility(View.VISIBLE);
                title_main_name_tv.setVisibility(View.GONE);
                title_name_tv.setVisibility(View.VISIBLE);
                title_right_tv.setVisibility(View.VISIBLE);
                title_back_iv.setImageResource(R.mipmap.black_back);
                title_back_iv.invalidate();
                title_name_tv.setText(name);
                title_right_tv.setText(right);
                title_right_tv.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                setWindowStatusBarColor(mContext,mTitleColor);
                LogUtils.i("setType"+type);

                break;
            case TITLE_GONE:
                title_ll.setVisibility(View.GONE);
                LogUtils.i("setType"+type);

                break;

        }

    }
    public int getTitleColor(){
        return mTitleColor;
    }

    /**
     * 状态栏相关工具类
     *
     */
        public void setWindowStatusBarColor(Activity activity, int colorResId) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = activity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(activity.getResources().getColor(colorResId));

                    //底部导航栏
                    //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setWindowStatusBarColor(Dialog dialog, int colorResId) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = dialog.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                    //底部导航栏
                    //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
