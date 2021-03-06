package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by cui on 2017/7/14.
 */

public class SlidingButtonView extends HorizontalScrollView {
    private ImageView mTextView_Delete;
    private ImageView c_r_item_text_iv;
    private RelativeLayout c_r_item_mian_rl;
    private int mScrollWidth;
    private IonSlidingButtonListener mIonSlidingButtonListener;
    private Boolean isOpen = false;
    private Boolean once = false;

    public SlidingButtonView(Context context) {
        this(context, null);
    }
    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!once){
            mTextView_Delete = (ImageView) findViewById(R.id.c_r_item_delete_iv);
            c_r_item_text_iv = (ImageView) findViewById(R.id.c_r_item_text_iv);
            c_r_item_mian_rl = (RelativeLayout) findViewById(R.id.c_r_item_mian_rl);
            once = true;
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx(){
        if(getScrollX() >= (mScrollWidth/2)){
            this.smoothScrollTo(mScrollWidth, 0);
            LogUtils.i("changeScrollx----1");
            isOpen = true;
            c_r_item_text_iv.setVisibility(View.GONE);
            c_r_item_mian_rl.setBackgroundResource(R.color.colorRegS);
            mIonSlidingButtonListener.onMenuIsOpen(this);

        }else{
            LogUtils.i("changeScrollx----2");
            c_r_item_text_iv.setVisibility(View.VISIBLE);
            c_r_item_mian_rl.setBackground(null);

            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }
    /**
     * 打开菜单
     */
    public void openMenu()    {
        if (isOpen){
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }
    /**
     * 关闭菜单
     */
    public void closeMenu()    {
        if (!isOpen){
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }
    public void setSlidingButtonListener(IonSlidingButtonListener listener){
        mIonSlidingButtonListener = listener;
    }
    public interface IonSlidingButtonListener{
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingButtonView slidingButtonView);
    }

}
