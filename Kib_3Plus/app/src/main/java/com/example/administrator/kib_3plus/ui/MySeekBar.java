package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;

/**
 * Created by cui on 2017/7/24.
 */

public class MySeekBar extends View {
    int LEFT_MARGIN= NumberUtils.INSTANCE.px2dip(20);
    int BG_LEFT_MARGIN=NumberUtils.INSTANCE.px2dip(40);
    int BG_HIDTH=NumberUtils.INSTANCE.px2dip(80);
    int BG_TOP_MARGIN=NumberUtils.INSTANCE.px2dip(160);
    Bitmap bmp;
    int X_RADII=NumberUtils.INSTANCE.px2dip(80);
    int Y_RADII=NumberUtils.INSTANCE.px2dip(50);
    int percent=0;
    int MAX=1000;
    Paint bgPaint;
    //宽
    private int rootViewWidth;
    private int rootViewHidth;

    public MySeekBar(Context context) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        bgPaint=new Paint();
        bgPaint.setStyle(Paint.Style.FILL);//充满
        bgPaint.setColor(Color.GREEN);
        bgPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        Bitmap bitmp=BitmapFactory.decodeResource(getResources(),R.mipmap.race_goal_blue);
        bmp=bitmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆角矩形

//        canvas.drawText("画圆角矩形:", 10, 260, bgPaint);
        LogUtils.i("rootViewHidth="+rootViewHidth);
        LogUtils.i("rootViewWidth="+rootViewWidth);
        RectF oval3 = new RectF(LEFT_MARGIN, rootViewHidth/2, rootViewWidth-LEFT_MARGIN*2, rootViewHidth/2+BG_HIDTH);// 设置个新的长方形
        canvas.drawRoundRect(oval3, X_RADII, Y_RADII, bgPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawBitmap(bmp,LEFT_MARGIN+BG_LEFT_MARGIN+percent,rootViewHidth/2-BG_TOP_MARGIN,bgPaint);
    }

    public void setData(int step,Bitmap bmp,int max){
        setMax(max);
        if(step>max){
            step=max;
        }
        double in=(double)(rootViewWidth-LEFT_MARGIN*2-LEFT_MARGIN)/MAX;
        percent=(int)(step*in);
        this.bmp=bmp;
        invalidate();
    }
    public void setMax(int max){
        this.MAX=max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * Math.min 为两个值的最小值
         *
         */
        LogUtils.i("getMeasuredWidth()="+getMeasuredWidth());
        LogUtils.i("getMeasuredHeight()="+getMeasuredHeight());
        rootViewHidth= (getMeasuredWidth() <= getMeasuredHeight()) ? getMeasuredWidth() : getMeasuredHeight();
        rootViewWidth=(getMeasuredWidth()> getMeasuredHeight()) ? getMeasuredWidth() : getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

}
