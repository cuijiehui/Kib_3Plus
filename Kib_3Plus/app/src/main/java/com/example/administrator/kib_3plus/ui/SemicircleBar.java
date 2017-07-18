package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.NumberUtils;

/**
 * Created by cui on 2017/6/19.
 */

public class SemicircleBar extends View {
    /**
     * View的宽度
     */
    private int rootViewWidth;
    /**
     * 圆圈的线条粗细
     */
    private float circleStrokeWidth;
    /**
     * 与外框的一点距离
     */
    private float pressExtraStrokeWidth;
    /**
     * 弧的半径
     */
    private float mColorWheelRadius;

    /**
     * 画弧的外框（纸）
     */
    private RectF mColorWheelRectangle = new RectF();//圆圈的矩形范围


    /**
     * 背景弧，被我称为内弧
     * 内弧的paint
     */
    private Paint mBgArcPaint;
    /**
     * 根据数值改变的弧，被我称为外弧
     * 外弧的paint
     *
     */
    private Paint mFrontArcPain;
    /**
     *外弧的颜色
     */
    private int mFrontArcColor =getResources().getColor(R.color.blueColorTextBtn);
    /**
     * 加载的数值，外弧的弧度
     */
    private float mSweepAngle;//扇形弧度

    /**
     * 外弧的实质数值，不直接使用mSwwepAngle的原因是需要做动画
     * 如果你不需要动画就直接使用msweepAngle就可以了
     */
    private float mSweepAnglePer;//扇形弧度百分比
    /**
     * 外弧的动画
     */
    private BarAnimation anim;

    public SemicircleBar(Context context) {
        super(context);
    }

    public SemicircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context,attrs);
        init();

    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SemicircleBar);
        if (typedArray != null) {
            LogUtils.i("------------------------------------------mFrontArcColor="+mFrontArcColor);

            mFrontArcColor = typedArray.getColor(R.styleable.SemicircleBar_percent_progress_color, getResources().getColor(R.color.blueColorTextBtn));
            typedArray.recycle();

        }
        LogUtils.i("------------------------------------------mFrontArcColor="+mFrontArcColor);

    }

    public SemicircleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){

        //初始化一些值
        circleStrokeWidth = NumberUtils.INSTANCE.dip2px( 10);
        pressExtraStrokeWidth = NumberUtils.INSTANCE.dip2px( 2);
        mSweepAngle=0;
        //默认内弧的画笔
        mBgArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgArcPaint.setColor(getResources().getColor(R.color.BgBarcolor));
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形
        //默认外弧的画笔
        mFrontArcPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFrontArcPain.setColor(mFrontArcColor);
        mFrontArcPain.setStyle(Paint.Style.STROKE);
        mFrontArcPain.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        mFrontArcPain.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形

        anim = new BarAnimation();
        anim.setDuration(1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * Math.min 为两个值的最小值
         *
         */
        rootViewWidth= Math.min(getMeasuredWidth(),getMeasuredHeight());
        setMeasuredDimension(rootViewWidth, rootViewWidth);
        mColorWheelRadius = rootViewWidth - circleStrokeWidth - pressExtraStrokeWidth;
        mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth, circleStrokeWidth + pressExtraStrokeWidth,
                mColorWheelRadius, mColorWheelRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mColorWheelRectangle, -180, 180, false, mBgArcPaint);//画内弧
        canvas.drawArc(mColorWheelRectangle, -180, mSweepAnglePer, false, mFrontArcPain);//画外弧
    }

    /**
     * 设置外弧的颜色，传进了的一定要是颜色
     * @param frontArcColor 颜色数值
     */
    public void setFrontArcColor(int frontArcColor){
        mFrontArcColor=frontArcColor;
    }

    /**
     * 设置外弧的弧长
     * @param sweepAngle 0到180
     */
    public void setSweepAngle(int sweepAngle){
        if(sweepAngle<0){
            sweepAngle=0;
        }
        if(sweepAngle>180){
            sweepAngle=180;
        }
        mSweepAngle=sweepAngle;
        mSweepAnglePer=mSweepAngle;
        postInvalidate();
//        startCustomAnimation();//设置完，就启动动画

    }

    /**
     * 启动动画
     */
    public void startCustomAnimation() {
        this.startAnimation(anim);
    }
    /**
     * 外弧动画内部类，也可以抽出去，只要传view和msweepangleper和msweepangle进来就可以了
     */
    public class BarAnimation extends Animation {

        public BarAnimation() {

        }

//        * 动画类利用了applyTransformation参数中的interpolatedTime参数(从0到1)的变化特点，
//                * 实现了该View的某个属性随时间改变而改变。原理是在每次系统调用animation的applyTransformation()方法时，
//                * 改变mSweepAnglePer，mCount的值，
//                * 然后调用postInvalidate()不停的绘制view。
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //mSweepAnglePer，mCount这两个属性只是动画过程中要用到的临时属性，
            //mText和mSweepAngle才是动画结束之后表示扇形弧度和中间数值的真实值。
            if (interpolatedTime < 1.0f) {
                mSweepAnglePer = interpolatedTime * mSweepAngle;
            } else {
                mSweepAnglePer = mSweepAngle;
            }
            postInvalidate();
        }
    }
}
