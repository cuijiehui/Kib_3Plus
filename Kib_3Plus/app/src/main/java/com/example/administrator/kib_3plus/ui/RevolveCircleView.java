package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.Utils.NumberUtils;

import static com.example.administrator.kib_3plus.ui.DataViewChart.VIEW_STEP;

/**
 * Created by cui on 2017/6/21.
 */

public class RevolveCircleView extends View {
    /**
     * 内圆弧paint
     * 外圆弧paint
     * 内圆的paint
     * 点paint
     */
    private Paint mBgPaint;
    private Paint mRgPaint;
    private Paint paint2;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private Paint mTextPaint2;
    /**
     * View的宽度
     */
    private int rootViewWidth;

    /**
     * 外圈弧和点的宽度
     * 内圈弧的宽度
     *
     */
    private float circleStrokeWidth;
    private float circleBgStrokeWidth;
    /**
     * 画弧的外框（纸）
     */
    private RectF mColorWheelRectangle = new RectF();//圆圈的矩形范围
    /**
     * 半径
     */
    private int radio;
    /**
     * 圆点
     */
    private int px1;
    private int py1;
    /**
     * 外圈弧开始点
     */
    private int px;
    private int py;
    private int X_AND_B;//边界圆变到屏幕的距离
    private int X_AND_R;//内圆与外边框距离

    /**
     * 外弧的实质数值，不直接使用mSwwepAngle的原因是需要做动画
     * 如果你不需要动画就直接使用msweepAngle就可以了
     */
    private float mSweepAnglePer;//扇形弧度百分比
    /**
     * 外弧的动画
     */
    private BarAnimation anim;
    /**
     * 加载的数值，外弧的弧度
     */
    private float mSweepAngle;//扇形弧度

    private int mPercent;
    /**
     *外弧的颜色
     */
    private int mFrontArcColor;

    /**
     *是否为睡眠类型
     * 默认为false
     */
    private boolean isSleep=false;
    /**
     *类型名字
     */
    private String mTypeName="STEPS";
    private String mData="0";
    /**
     * 显示的类型
     * 3为步数
     * 4为卡路里
     * 6为睡眠
     * 会根据传过来的类型改变类型和颜色
     * 默认为 0
     */
    private int mType=3;
    public RevolveCircleView(Context context) {
        super(context);
    }

    public RevolveCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public RevolveCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        circleStrokeWidth= NumberUtils.INSTANCE.px2dip(40);
        circleBgStrokeWidth= NumberUtils.INSTANCE.px2dip(10);
        mSweepAngle=0;
        X_AND_B= NumberUtils.INSTANCE.px2dip(40);
        X_AND_R= NumberUtils.INSTANCE.px2dip(80);
        mFrontArcColor=getResources().getColor(R.color.colorViolet);//默认颜色

        //初始化内弧
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(mFrontArcColor);//默认内弧颜色
        mBgPaint.setStrokeWidth(circleBgStrokeWidth);//圆圈的线条粗细
        //初始化外弧
        mRgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRgPaint.setStyle(Paint.Style.STROKE);
        mRgPaint.setColor(mFrontArcColor);//默认化外弧颜色
        mRgPaint.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        //初始化外弧圆点
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(mFrontArcColor);//默认化外圆点弧颜色
        paint2.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        //初始化内圆
        mCirclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mFrontArcColor);//默认内圆点弧颜色
        mCirclePaint.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        //初始化text这个不用改变，因为都是白色的
        mTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(getResources().getColor(R.color.colorWhite));//默认text颜色
        mTextPaint.setStrokeWidth(circleBgStrokeWidth);//圆圈的线条粗细
        mTextPaint.setTextSize( NumberUtils.INSTANCE.px2dip(30));
        //初始化text 这个颜色跟type颜色
        mTextPaint2=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setStyle(Paint.Style.FILL);
        mTextPaint2.setColor(mFrontArcColor);//默认text颜色
        mTextPaint2.setStrokeWidth(circleBgStrokeWidth);//圆圈的线条粗细
        mTextPaint2.setTextSize( 70);


        anim = new BarAnimation();
        anim.setDuration(1000);
        setType();

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        LogUtils.i("px1="+px1);
//        LogUtils.i("py1="+py1);
        LogUtils.i("radio="+radio);
        //画内弧
        canvas.drawCircle(px1, py1, radio, mBgPaint);
        //画内圈
        canvas.drawCircle(px1, py1, radio-X_AND_R, mCirclePaint);
        mTextPaint.setTextSize( 100);
        float textWidth = getTextWidth(mTextPaint,mTypeName);
//        LogUtils.i("textWidth="+textWidth);
//        LogUtils.i("textWidth="+px2dip(getContext(),textWidth));
        //画类型
        canvas.drawText(mTypeName,px1-(textWidth/2),radio-radio/3,mTextPaint);
        mTextPaint.setTextSize( 90);
        textWidth = getTextWidth(mTextPaint,mData);
        //画步数
        canvas.drawText(mData,px1-(textWidth/2),radio-radio/11,mTextPaint);
        //画睡眠时间单位
        if(isSleep){
            mTextPaint.setTextSize(30);
            textWidth = getTextWidth(mTextPaint,"hours");
            canvas.drawText("hours",px1-(textWidth/2),radio,mTextPaint);
        }
        //画百分百白圈
        RectF oval3 = new RectF(px1- px1/3
                , py1+ py1/3
                ,px1+  px1/3
                ,py1+  py1/2);// 设置个新的长方形 这个调节百分比的宽大小
        canvas.drawRoundRect(oval3, px1, py1, mTextPaint);//第二个参数是x半径，第三个参数是y半径
        //画百分百
        textWidth = getTextWidth(mTextPaint2,(int)mPercent+"%");
        canvas.drawText((int)mPercent+"%",px1-(textWidth/2),py1+  NumberUtils.INSTANCE.px2dip(30)+((radio-X_AND_R)/2),mTextPaint2);
        //画外弧
        canvas.drawArc(mColorWheelRectangle, -90, mSweepAnglePer, false, mRgPaint);//画外弧
//        LogUtils.i("高="+Math.sin(mSweepAnglePer*Math.PI/180));
//        LogUtils.i("长="+Math.cos(mSweepAnglePer*Math.PI/180));//不能使用Math。toDegrees

        /**
         * 画外弧结束点
         */
        float h=(float) Math.sin(mSweepAnglePer*Math.PI/180)*radio;
        float d=(float)Math.cos(mSweepAnglePer*Math.PI/180)*radio;
        if(mSweepAnglePer<=90){
            canvas.drawCircle(px+Math.abs(h) , py+(radio-Math.abs(d)),5, paint2);
        }else if(mSweepAnglePer<=180){
            canvas.drawCircle(px+Math.abs(h) , py+(radio+Math.abs(d)),5, paint2);

        }else if(mSweepAnglePer<=270){
            canvas.drawCircle(px-Math.abs(h) , py+(radio+Math.abs(d)),5, paint2);

        }else if(mSweepAnglePer<=360){
            canvas.drawCircle(px-Math.abs(h) , py+(radio-Math.abs(d)),5, paint2);

        }

//        LogUtils.i("h3="+h);
//        LogUtils.i("d3="+d);
    }

    private void setType() {
        if(mType==DataViewChart.VIEW_CALORIES){
            mFrontArcColor=getResources().getColor(R.color.colorRegS);//默认圆环颜色
            isSleep=false;
            mTypeName="CALORIES";
        }else if(mType==DataViewChart.VIEW_SLEEP){
            mFrontArcColor=getResources().getColor(R.color.colorViolet);//默认圆环颜色
            isSleep=true;
            mTypeName="SLEEP";

        }else{
            mFrontArcColor=getResources().getColor(R.color.colorViolet);//默认圆环颜色
            isSleep=false;
            mTypeName="STEPS";
        }
        mBgPaint.setColor(mFrontArcColor);//默认内弧颜色
        mRgPaint.setColor(mFrontArcColor);//默认化外弧颜色
        paint2.setColor(mFrontArcColor);//默认化外圆点弧颜色
        mCirclePaint.setColor(mFrontArcColor);//默认内圆点弧颜色
        mTextPaint2.setColor(mFrontArcColor);//默认text颜色
    }

    /**
     * 数据传入
     * @param type 数据类型   * 0为步数
     * 1为卡路里
     * 3为睡眠
     * @param data  数据
     * @param percent   百分比
     */
    public void setData(int type,String data,int percent,int sweepAngle){
        mType=type;
        mData=data;
        setType();
        mPercent=percent;
        setSweepAngle(sweepAngle);//这个要最后，因为动画在这里
    }

    public float getTextWidth(Paint textPaint, String text) {
        return textPaint.measureText(text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * Math.min 为两个值的最小值
         *
         */
        rootViewWidth= Math.min(getMeasuredWidth(),getMeasuredHeight());
        radio=rootViewWidth/2-X_AND_B;
        px1=getMeasuredWidth()/2;
        py1=getMeasuredHeight()/2;
        px=rootViewWidth/2;
        py=X_AND_B;
        setMeasuredDimension(rootViewWidth, rootViewWidth);
        mColorWheelRectangle.set(X_AND_B,X_AND_B,getMeasuredWidth()-X_AND_B,getMeasuredHeight()-X_AND_B);
    }

    /**
     * 设置外弧的弧长
     * @param sweepAngle 0到180
     */
    private void setSweepAngle(int sweepAngle){
        if(sweepAngle>360){
            sweepAngle=360;
        }
        if(sweepAngle<0){
            sweepAngle=0;
        }
        mSweepAngle=sweepAngle;
        mSweepAnglePer=sweepAngle;
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
