package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.administrator.kib_3plus.PublicData;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.ISetViewVal;
import com.example.administrator.kib_3plus.mode.SleepTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static io.fabric.sdk.android.services.concurrency.AsyncTask.init;

/**
 * Created by cui on 2017/7/28.
 */

public class DataViewChart extends View {

    private static final String TAG = "DataViewChart";

    //3 kinds view ,   every view has 2 types(week,month) ,except sleep view has 3 kinds ( additional -> DATEVIEW_DAY);
    public static final int DATEVIEW_DAY = 0;
    public static final int DATEVIEW_WEEK = 1;
    public static final int DATEVIEW_MONTH = 2;

    private boolean isFirstCreate = true;

    //4 chart category : STEPS,Calories,Distance,Sleep
    public static final int VIEW_STEP = 3;         //步数查看
    public static final int VIEW_CALORIES = 4;     //卡路里
    public static final int VIEW_DISTANCE = 5;        //距离
    public static final int VIEW_SLEEP = 6;            //睡眠
    public static final int VIEW_ACTIVITY = 7;        //活动
    public static final int VIEW_DETAILSLEEP = 8;  //详细睡眠
    public static final int VIEW_HEART = 9;  //心率

    public static final int DRAW_HEART_COLOR = 0xffE70012;
    public static final int DRAW_HEART_LIGHT_COLOR = 0xffCD5C5C;

    public static final int DRAW_STEP_COLOR = 0xff1ba64e;
    public static final int DRAW_STEP_LIGHT_COLOR = 0xff96cc87;

    public static final int DRAW_ACTIVITY_COLOR = 0xffe97f02;
    public static final int DRAW_ACTIVITY_LIGHT_COLOR = 0xfff3b367;

    public static final int DRAW_CALORIES_COLOR = 0xffcd3f16;
    public static final int DRAW_CALORIES_LIGHT_COLOR = 0xffe85e42;


    public static final int DRAW_DISTANCE_COLOR = 0xff15a89b;
    public static final int DRAW_DISTANCE_LIGHT_COLOR = 0xff7fcac8;

    public static final int DRAW_SLEEP_COLOR = 0xffa2599e;
    public static final int DRAW_SLEEP_LIGHT_COLOR = 0xffad7ead;

    private Map<String, List<SleepTime>> mMapOneDaySleepTime;  //当前的睡眠记录

    private static final int NORMALSIZE = 10;
    private static final int CHECKEDSIZE = 14;


    private static final int bottomViewHeight = 20; //   bottom ico line height ,in dp ,not in pixel;


    private final int marginTop = 30;    //dp

    private final int marginBottom = 40;

    private int index = -1;
    SimpleDateFormat HH = new SimpleDateFormat("HH");
    SimpleDateFormat sf = new SimpleDateFormat("MM/dd");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");


    private int view_category = VIEW_STEP;

    private int view_timeType = DATEVIEW_WEEK;

    private int validDayCount = 31;    //valid Data Count //use for Month View  DRAW (
    // eg ,  Jan = 31  Feb= 28

    private float maxcolumnValue = 1;

    private int self_width, self_height;    //View's w,h   in pixel  Not in dp !


    private float self_data[] = new float[31];

    private String[] sortWeekArray;  //短星期的显示
    private float sumdata = 0;


    private int drawColor = 0, lightColor = 0;   //画图的深色和浅色

    private int mouse_x = 0;
    private int mouse_y = 0;

    // summer: add
    private int lineSize = 3, downX = 0, downY = 0, moveX = 0, moveY = 0, distance = 0, mDrawOffset = 0, mSaveOffset = 0;
    private long beginTime = 0, stopTime = 0;
    private float totalTime = 0;        // 总时间跨度，用于计算条状图宽度（利用比例）
    private List<SleepInfo> sleepInfoList = null;

    public static String[] curWeek = new String[7];        // summer: add 当前周

    private Context context;

    private boolean isTouch = false;

    private static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ciclePaint = new Paint();
    private Paint tipTextPaint = new Paint();
    private Paint tipPaint = new Paint();

    {
        tipTextPaint.setColor(Color.WHITE);
        tipTextPaint.setTextSize(20);
        tipTextPaint.setAntiAlias(true);

        tipPaint.setStyle(Paint.Style.FILL);
        tipPaint.setColor(Color.rgb(0x00, 0x89, 0xd8));
        tipPaint.setAntiAlias(true);

    }

    public DataViewChart(Context context, int view_category, int view_timeType) {
        super(context);
        LogUtils.i(TAG, "--->DataViewChart()");

        this.view_category = view_category;
        this.view_timeType = view_timeType;


    }

    public void setView_category(int view_category) {
        this.view_category = view_category;
    }

    public void setView_timeType(int view_timeType) {
        this.view_timeType = view_timeType;
    }

    public DataViewChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DataViewChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        sortWeekArray = context.getResources().getStringArray(R.array.shortweek);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                // Once on layoutchange (that's mean OnMesure occur ) --> save w,h   ,

                self_width = getWidth();
                self_height = getHeight();

            }
        });
        this.context = context;

        sleepInfoList = new ArrayList<SleepInfo>();
    }
    public void cleanData() {
        LogUtils.i(TAG, "--->cleanData()");

        for (int i = 0; i < 31; i++) {

            self_data[i] = 0;
        }

    }

    public void setSleepDetailData(Map<String, List<SleepTime>> MapOneDaySleepTime) {
        LogUtils.i(TAG, "--->setSleepDetailData()");
        mMapOneDaySleepTime = MapOneDaySleepTime;  // shadow copy

        beginTime = 0;
        stopTime = 0;
        mDrawOffset = 0;
        // summer: add
        view_category = VIEW_DETAILSLEEP;
        LogUtils.i(TAG, "context : " + this.context);
        setDateView();
        invalidate();
    }


    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(float[] data, int dayCount) {

        LogUtils.i(TAG, "--->setData()");

        sumdata = 0;


        for (int i = 0; i < data.length; i++) {

            if (view_timeType == DATEVIEW_DAY) {
                if (i == 24) break;
            } else if (view_timeType == DATEVIEW_WEEK) {
                if (i == 7) break;
            } else if (view_timeType == DATEVIEW_MONTH) {
                if (i == 31) break;
            }

            if (data[i] > maxcolumnValue) maxcolumnValue = data[i];

            self_data[i] = data[i];
            sumdata = sumdata + data[i];
        }

        if (dayCount > 27) validDayCount = dayCount;

        index = -1;
        isSetData = true;
        invalidate();

    }

    List<SleepCanvas> deepSleepDate = new ArrayList<SleepCanvas>();
    List<SleepCanvas> lightSleepDate = new ArrayList<SleepCanvas>();
    List<SleepCanvas> awakeSleepDate = new ArrayList<SleepCanvas>();

    class SleepCanvas {
        public int left;
        public int right;
        public int top;
        public int bottom;

        public SleepCanvas() {
        }

        @Override
        public String toString() {
            return "SleepCanvas{" +
                    "left=" + left +
                    ", right=" + right +
                    ", top=" + top +
                    ", bottom=" + bottom +
                    '}';
        }
    }

    public void setDateView() {
        if (mMapOneDaySleepTime == null) return;
        List<SleepTime> deepSleepTimes = mMapOneDaySleepTime.get("DEEP");
        List<SleepTime> lightSleepTimes = mMapOneDaySleepTime.get("LIGHT");
        List<SleepTime> awakeSleepTimes = mMapOneDaySleepTime.get("AWAKE");
        int maxHeight = self_height - dp2px(marginTop + marginBottom);
        int high = self_height - dp2px(marginBottom) - self_height / 6;        // 最高的长度
        for (SleepTime awakeSleepTime : awakeSleepTimes) {
            if (beginTime == 0) {
                LogUtils.i("", "beginTime=" + awakeSleepTime.startTime);
                beginTime = awakeSleepTime.startTime;
            }
            if (awakeSleepTime.endTime > stopTime) {
                stopTime = awakeSleepTime.endTime;
            }
        }
        for (SleepTime lightSleepTime : lightSleepTimes) {
            if (lightSleepTime.startTime < beginTime) {
                LogUtils.i("", "beginTime=" + lightSleepTime.startTime);
                beginTime = lightSleepTime.startTime;
            }
            if (lightSleepTime.endTime > stopTime) {
                stopTime = lightSleepTime.endTime;
            }
        }
        for (SleepTime deepSleepTime : deepSleepTimes) {
            if (deepSleepTime.startTime < beginTime) {
                LogUtils.i("", "beginTime=" + deepSleepTime.startTime);
                beginTime = deepSleepTime.startTime;
            }
            if (deepSleepTime.endTime > stopTime) {
                stopTime = deepSleepTime.endTime;
            }
        }
        totalTime = stopTime - beginTime;
        TimeZone tz = TimeZone.getDefault();
        int offset1 = tz.getRawOffset() / 3600000;
        LogUtils.i(TAG, "______________offset:" + offset1 + " /ID:" + tz.getID());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        if (beginTime != stopTime)
            ((ISetViewVal) (context)).setSleepRange(sdf.format(new Date(beginTime * 1000L)), sdf.format(new Date(stopTime * 1000L)));

//			LogUtils.i(TAG, "===>>beginTime = " + beginTime);
        LogUtils.i(TAG, "===>>stopTime = " + stopTime);
        LogUtils.i(TAG, "===>>stopTime = " + sdf.format(new Date(stopTime * 1000L)));
        LogUtils.i(TAG, "===>>stopTime = " + sdf.format(new Date(1489657259 * 1000L)));
//			LogUtils.i(TAG, "===>>totalTime = " + totalTime);16:58:55 16:3:12
        awakeSleepDate.clear();
        // 画清醒柱状图
        for (SleepTime awakeSleepTime : awakeSleepTimes) {
            mPaint.setColor(Color.parseColor("#ddfae159"));
            mPaint.setStyle(Paint.Style.FILL);

            int left = dp2px(15) + (int) ((float) (awakeSleepTime.startTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int right = dp2px(15) + (int) ((awakeSleepTime.endTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int top = self_height / 6;
            int bottom = maxHeight + dp2px(marginTop);
            SleepCanvas sleepCanvas = new SleepCanvas();
            sleepCanvas.left = left;
            sleepCanvas.right = right;
            sleepCanvas.top = top;
            sleepCanvas.bottom = bottom;
            LogUtils.i("","画清醒柱状图="+"left="+left+" right="+right+" top="+top+" bottom"+bottom);
            LogUtils.i("","画清醒柱状图="+sleepCanvas.toString());

            awakeSleepDate.add(sleepCanvas);
            LogUtils.i("","画清醒柱状图="+awakeSleepDate.toString());

            sleepInfoList.add(new SleepInfo(SleepInfo.AWAKE, awakeSleepTime.startTime, awakeSleepTime.endTime));
        }

        lightSleepDate.clear();
        // 画浅睡柱状图
        for (SleepTime lightSleepTime : lightSleepTimes) {
            mPaint.setColor(Color.parseColor("#ddb559e5"));
            mPaint.setStyle(Paint.Style.FILL);
            int left = dp2px(15) + (int) ((lightSleepTime.startTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int right = dp2px(15) + (int) ((lightSleepTime.endTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int top = self_height / 6 + high / 3;
            int bottom = maxHeight + dp2px(marginTop);
            SleepCanvas sleepCanvas = new SleepCanvas();

            sleepCanvas.left = left;
            sleepCanvas.right = right;
            sleepCanvas.top = top;
            sleepCanvas.bottom = bottom;
            LogUtils.i("","画浅睡柱状图="+"left="+left+" right="+right+" top="+top+" bottom"+bottom);
            LogUtils.i("","画浅睡柱状图="+sleepCanvas.toString());

            lightSleepDate.add(sleepCanvas);
            LogUtils.i("","画浅睡柱状图="+lightSleepDate.toString());

            sleepInfoList.add(new SleepInfo(SleepInfo.LIGHT, lightSleepTime.startTime, lightSleepTime.endTime));
        }
        deepSleepDate.clear();
        // 画深睡柱状图
        for (SleepTime deepSleepTime : deepSleepTimes) {
            mPaint.setColor(Color.parseColor("#dd7922cc"));
            mPaint.setStyle(Paint.Style.FILL);
            int left = dp2px(15) + (int) ((deepSleepTime.startTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int right = dp2px(15) + (int) ((deepSleepTime.endTime - beginTime) / totalTime * (self_width - dp2px(30)));
            int top = self_height / 6 + high * 2 / 3;
            int bottom = maxHeight + dp2px(marginTop);
            SleepCanvas sleepCanvas = new SleepCanvas();
            sleepCanvas.left = left;
            sleepCanvas.right = right;
            sleepCanvas.top = top;
            sleepCanvas.bottom = bottom;
            LogUtils.i("","画深睡柱状图="+"left="+left+" right="+right+" top="+top+" bottom"+bottom);
            LogUtils.i("","画深睡柱状图="+sleepCanvas.toString());
            deepSleepDate.add(sleepCanvas);
            LogUtils.i("","画深睡柱状图="+deepSleepDate.toString());

            sleepInfoList.add(new SleepInfo(SleepInfo.DEEP, deepSleepTime.startTime, deepSleepTime.endTime));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.i(TAG, "--->onDraw()");
        LogUtils.i(TAG, "--->view_timeType()" + view_timeType);
        LogUtils.i(TAG, "--->view_category()" + view_category);
        int space = 5;


        int fontsize = 10;


        switch (view_category) {
            case VIEW_STEP:
                drawColor = DRAW_STEP_COLOR;
                lightColor = DRAW_STEP_LIGHT_COLOR;

                break;

            case VIEW_CALORIES:
                drawColor = DRAW_CALORIES_COLOR;
                lightColor = DRAW_CALORIES_LIGHT_COLOR;
                break;

            case VIEW_ACTIVITY:

                drawColor = DRAW_ACTIVITY_COLOR;
                lightColor = DRAW_ACTIVITY_LIGHT_COLOR;
                break;

            case VIEW_DISTANCE:

                drawColor = DRAW_DISTANCE_COLOR;
                lightColor = DRAW_DISTANCE_LIGHT_COLOR;
                break;

            case VIEW_SLEEP:

                drawColor = DRAW_SLEEP_COLOR;
                lightColor = DRAW_SLEEP_LIGHT_COLOR;
                break;

            case VIEW_DETAILSLEEP:

                drawColor = DRAW_SLEEP_COLOR;
                lightColor = DRAW_SLEEP_LIGHT_COLOR;
                break;



        }


        switch (view_timeType) {
            case DATEVIEW_DAY:

                validDayCount = 24;
                fontsize = 12;
                space = 5;
                break;

            case DATEVIEW_WEEK:
                validDayCount = 7;
                fontsize = 14;
                space = 30;
                break;


            case DATEVIEW_MONTH:

                fontsize = 8;
                space = 3;
                break;

        }


        int dh = self_height / 6;

        mPaint.setStrokeWidth(dp2px(1));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(lightColor);
//        if (view_category != VIEW_DETAILSLEEP) {
////            for (int i = 0; i < 5; i++)  // 画5条等分线，分成6个部分
////            {
////                canvas.drawLine(0, (i + 1) * dh, self_width, (i + 1) * dh, mPaint);
////            }
//        }

        int perwidth = (self_width - dp2px(space) * (validDayCount - 1)) / validDayCount;

        int maxHeight = self_height - dp2px(marginTop + marginBottom);


        mPaint.setColor(Color.parseColor("#6a000000"));
        mPaint.setStyle(Paint.Style.FILL);
        LogUtils.e("==>>index000: " + index);
        LogUtils.e("==>>index000: " + (self_height - dp2px(marginBottom)) + "," + self_width + "," + self_height);

        canvas.drawRect(0, self_height - dp2px(marginBottom), self_width, self_height, mPaint);
        //画最下面的黑色矩形

        if (self_data[(mouse_x - 2) / (perwidth + dp2px(space))] >= 0)        // summer: change > to >=
        //保存上次保持的有效的数据列（为0的不更改）
        {
//			LogUtils.e(TAG, "==>>index00: " + index);
            if (index == -1) {
                if (PublicData.curShowCal == null) {
                    PublicData.curShowCal = Calendar.getInstance();
                }
                if (view_timeType == DATEVIEW_WEEK) {
                    index = (PublicData.curShowCal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
                } else if (view_timeType == DATEVIEW_MONTH) {
                    index = PublicData.curShowCal.get(Calendar.DAY_OF_MONTH) - 1;
                }
            } else if (isTouch) {
                index = (mouse_x - 2) / (perwidth + dp2px(space));
            }
            LogUtils.i(TAG, "==>>index000: " + index);
        }


        if (view_category == VIEW_DETAILSLEEP) {
            //画睡眠详细

            if (mMapOneDaySleepTime == null) return;

            int high = self_height - dp2px(marginBottom) - self_height / 6;        // 最高的长度

            // mMapOneDaySleepTime.get()

            // summer: add

            if (awakeSleepDate.size()>0) {
                // 画清醒柱状图
                for (SleepCanvas awakeSleepTime : awakeSleepDate) {
                    LogUtils.i("","画清醒柱状图"+awakeSleepTime.toString());

                    mPaint.setColor(Color.parseColor("#ddfae159"));
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(awakeSleepTime.left,awakeSleepTime.top, awakeSleepTime.right, awakeSleepTime.bottom, mPaint);
                }

            }
            if(deepSleepDate.size()>0){
                // 画深睡柱状图
                for (SleepCanvas deepSleepTime : deepSleepDate) {
                    LogUtils.i("","画深睡柱状图"+deepSleepTime.toString());
                    mPaint.setColor(Color.parseColor("#dd7922cc"));
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(deepSleepTime.left,deepSleepTime.top, deepSleepTime.right, deepSleepTime.bottom, mPaint);

                }
            }
            if (lightSleepDate.size()>0) {
                // 画浅睡柱状图
                for (SleepCanvas awakeSleepTime : lightSleepDate) {
                    LogUtils.i("","画浅睡柱状图"+awakeSleepTime+toString());
                    mPaint.setColor(Color.parseColor("#ddb559e5"));

                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(awakeSleepTime.left,awakeSleepTime.top, awakeSleepTime.right, awakeSleepTime.bottom, mPaint);
                }

            }
            // 画线条旁的文字
            mPaint.setColor(Color.parseColor("#000000"));
            mPaint.setTextSize(dp2px(12));
            /*DisplayMetrics dm = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
			int mScreenWidth = dm.widthPixels;
			if (mScreenWidth <= 480) {
				mPaint.setTextSize(15);
			}*/
            if (mDrawOffset < self_width / 2)
                mPaint.setTextAlign(Paint.Align.LEFT);
            else
                mPaint.setTextAlign(Paint.Align.RIGHT);

            LogUtils.i("", "coordinateX2TimeString(mDrawOffset)" + coordinateX2TimeString(mDrawOffset));
            LogUtils.i("", "coordinateX2TimeString(mDrawOffset)" + mDrawOffset);
//            Calendar calendar = PublicData.curShowCal;

            long curTime = beginTime + (stopTime - beginTime) * (mDrawOffset) / (self_width - dp2px(30));

            LogUtils.i("", "curTime=" + curTime);
            LogUtils.i("", "beginTime=" + beginTime);
            LogUtils.i("", "stopTime=" + stopTime);
            LogUtils.i("", "mDrawOffset=" + mDrawOffset);
            LogUtils.i("", "self_width=" + self_width);
            String itemType = "SLEEP";
            int itemTop = -1;
            for (SleepInfo sleepInfo : sleepInfoList) {
                LogUtils.i("", "timeStamp=" + sleepInfo.timeStamp);
                LogUtils.i("", "timeStamp=" + sleepInfo.timeStop);
                if ((sleepInfo.timeStamp <= curTime && curTime <= sleepInfo.timeStop)) {
                    LogUtils.i("", "SleepInfo.tpye==" + sleepInfo.sleepType);
                    switch (sleepInfo.sleepType) {
                        case 0:
                            itemType = "DEEP SLEEP";
                            itemTop = self_height / 6 + high * 2 / 3;
                            break;
                        case 1:
                            itemType = "LIGHT SLEEP";
                            itemTop = self_height / 6 + high / 3;
                            break;
                        case 2:
                            itemType = "AWAKE SLEEP";
                            itemTop = self_height / 6;
                            break;
                    }
                }
//                if(sleepInfo.timeStamp==curTime){
//                    LogUtils.i("","timeStamp="+sleepInfo.timeStamp);
//                }
            }
            float x, y;

            float textWidth = getTextWidth(tipTextPaint, itemType);
            float textHeight = getTextHeight(tipTextPaint);
            float cicleHeight = textHeight;
            x = dp2px(15) + mDrawOffset - textWidth / 2;
            y = itemTop - cicleHeight; // 蓝色框等上移大圆的半径

            Date d = new Date(curTime * 1000);
            sf.setTimeZone(TimeZone.getTimeZone("GMT"));

            String data = sf.format(d);

            LogUtils.i("", "xxxxxxxxxxxxxxxxxxxx=" + x);
            LogUtils.i("", "(x - textWidth / 8)(x - textWidth / 8)(x - textWidth / 8)=" + (x - textWidth / 8));
            if ((x - textWidth / 8) < 0) {
                float mun = textWidth / 8 - x;
                x = x + mun + 45;
            }
            if ((x + textWidth * 1.25f) > self_width) {
                float mun = (x + textWidth * 1.25f) - self_width;
                x = x - mun - 45;
            }
            LogUtils.i("", "xxxxxxxxxxxxxxxxxxxx=" + x);
            canvas.drawRect(x - textWidth / 8, y - textHeight - dp2px(15), x + textWidth * 1.25f, y, tipPaint);
//            canvas.drawText(coordinateX2TimeString(mDrawOffset), dp2px(20) + lineSize + mDrawOffset, self_height / 12, tipTextPaint);
//            canvas.drawText(itemType, dp2px(15) + lineSize + mDrawOffset, (self_height / 12)+dp2px(15), tipTextPaint);

            canvas.drawText(coordinateX2TimeString(mDrawOffset), x + 35, y - textHeight / 12 - dp2px(15), tipTextPaint);
            canvas.drawText(itemType, x + 5, y - textHeight / 12, tipTextPaint);

            ciclePaint.setStyle(Paint.Style.FILL);
            ciclePaint.setColor(Color.argb(0x77, 0xFF, 0xFF, 0xFF));
            ciclePaint.setAlpha(170);
            ciclePaint.setAntiAlias(true);
            if (itemTop != -1) {
                canvas.drawCircle(dp2px(15) + mDrawOffset, itemTop, cicleHeight, ciclePaint);
                ciclePaint.setColor(Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
                canvas.drawCircle(dp2px(15) + mDrawOffset, itemTop, cicleHeight / 2, ciclePaint);
            }

            mPaint.setColor(getCurLineColor(mDrawOffset));
            // 画可拖动的线条
            canvas.drawLine(dp2px(15) + mDrawOffset, 0, dp2px(15) + mDrawOffset, maxHeight + dp2px(marginTop), mPaint);


            // 画时间刻度
            mPaint.setColor(Color.parseColor("#727296"));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(dp2px(marginBottom) * 5 / 12);

            LogUtils.i(TAG, "setTextSize " + dp2px(marginBottom) * 5 / 12);

            // 间隔设置
            int offset = (stopTime - beginTime) > (60 * 60 * 12) ? 4 * 60 : 2 * 60;
            if ((60 * 60 * 2) < (stopTime - beginTime) && (stopTime - beginTime) <= (60 * 60 * 5)) {
                offset = 60;
            } else if ((60 * 60) < (stopTime - beginTime) && (stopTime - beginTime) <= (60 * 60 * 2)) {
                offset = 30;
            } else if (30 * 60 < (stopTime - beginTime) && (stopTime - beginTime) <= (60 * 60)) {
                offset = 15;
            }
            TimeZone tz1 = TimeZone.getDefault();
            int offset2 = tz1.getRawOffset() / 3600000;
            LogUtils.i(TAG, "______________offset:" + offset2 + " /ID:" + tz1.getID());
            int max=24*60;

//            if (tz1.getID().toUpperCase().indexOf("LOS_ANGELES") != -1) {
//                max=max+7*60;
//            } else {
//                max=max+16*60;
//
//            }
            HH.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dataBT = HH.format(new Date(beginTime*1000));
            String dataST = HH.format(new Date(stopTime*1000));
            LogUtils.i(TAG, "datas= " + dataBT);
            LogUtils.i(TAG, "datas= " + dataST);
            for(int i=Integer.parseInt(dataBT);i<=Integer.parseInt(dataST);i++){
                LogUtils.i("","datas=--------i="+i);
                int px = time2coordinateX(i, 0);
                LogUtils.i("","datas=--------px="+px);
                if(px < dp2px(15)||px > (self_width - dp2px(15))){
                    continue;
                }
                String hour = (i < 10 ? ("0" + i) : "" + i);
                String min = "00";
                LogUtils.i(TAG, "--->draw()=hour="+hour+",="+"min"+min);
                canvas.drawRect(px, self_height - dp2px(marginBottom), px + 4, self_height - (dp2px(marginBottom) * 3 / 4), mPaint);
                canvas.drawText("   " + hour + ":" + min, px, self_height - dp2px(marginBottom + 12) / 4, mPaint);
                canvas.drawRect(px, self_height - dp2px(marginBottom) / 4, px + 4, self_height, mPaint);
            }

//            for (int i = 0; i < max; ) {
//                int h = i / 60, m = i % 60;
//                LogUtils.i(TAG, "===>>>h = " + h+"---"+m);
//
//                int px = time2coordinateX(h, m);
//                LogUtils.i(TAG, "===>>>i = " + i);
//                LogUtils.i(TAG, "===>>>tz1 = " + tz1.getID().toUpperCase());
//
//                if (tz1.getID().toUpperCase().indexOf("LOS_ANGELES") != -1) {
//                    h = h + 7;
//                } else {
//                    h = h + 8 + 8;
//                }
//
//                if (h >= 24) {
//                    h = h - 24;
//                }
////                if (h >= 8) {
////                    h = h - 8;
////                } else {
////                    for (int j = 0; j < 8; j++) {
////                        if (h == j) {
////                            h = 16 + j;
////                        }
////                    }
////                }
////                LogUtils.i(TAG, "===>>>h = " + h);
//
//                String hour = (h < 10 ? ("0" + h) : "" + h);
//                String min = (m < 10 ? ("0" + m) : "" + m);
//
//                LogUtils.d(TAG, "--->draw()=hour="+hour+",="+"min"+min);
//
//                i = i + offset;
//                LogUtils.i(TAG, "===>>>offset = " + offset);
//                LogUtils.i(TAG, "===>>>i = " + i);
//                LogUtils.i(TAG, "===>>>dp2px(15) = " + dp2px(15));
//                LogUtils.i(TAG, "===>>>px = " + px);
//                LogUtils.i(TAG, "===>>>(self_width - dp2px(15)) = " + (self_width - dp2px(15)));
//
//                if (px < dp2px(15) ){
//                    continue;
//                }
//                 if(px > (self_width - dp2px(15))){
//                     return;
//                 }
//
//                canvas.drawRect(px, self_height - dp2px(marginBottom), px + 4, self_height - (dp2px(marginBottom) * 3 / 4), mPaint);
//                canvas.drawText("   " + hour + ":" + min, px, self_height - dp2px(marginBottom + 12) / 4, mPaint);
//                canvas.drawRect(px, self_height - dp2px(marginBottom) / 4, px + 4, self_height, mPaint);
//            }
        }

    }

    private boolean isSetData = false;

    public float getTextHeight(Paint textPaint) {
        LogUtils.i("", "---------------getTextHeight");

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent) - 2;
    }

    public float getTextWidth(Paint textPaint, String text) {
        LogUtils.i("", "---------------getTextWidth");
        return textPaint.measureText(text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        int action = event.getAction();
        if (action == 0 || action == 1 || action == 2) {
            isSetData = true;
        }
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                PublicData.isScrollable = false;
                if (view_timeType == DATEVIEW_DAY) {        // summer: add
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    mSaveOffset = mDrawOffset;
                } else {
                    mouse_x = (int) event.getX();
                    mouse_y = (int) event.getY();
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                PublicData.isScrollable = true;
                break;

            case MotionEvent.ACTION_MOVE:
                PublicData.isScrollable = false;
                if (view_timeType == DATEVIEW_DAY) {
                    moveX = (int) event.getX();
                    distance = moveX - downX;
                    mDrawOffset = mSaveOffset + distance;
                    LogUtils.i("", "mDrawOffset=" + mDrawOffset + ",mSaveOffset=" + mSaveOffset + ",distance=" + distance);
                    if (mDrawOffset < 0) {
                        mDrawOffset = 0;
                    }
                    if (mDrawOffset > self_width - dp2px(29) - lineSize) {
                        mDrawOffset = self_width - dp2px(29) - lineSize;
                    }

                    postInvalidate();
                } else {
                    mouse_x = (int) event.getX();
                    mouse_y = (int) event.getY();
                    invalidate();
                }
                break;

        }

        return true;
        //	return super.onTouchEvent(event);
    }

    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * summer:
     * 将当前线条的位置转换为对应的时间点
     */
    private String coordinateX2TimeString(int x) {
        TimeZone tz = TimeZone.getDefault();
        int offset = tz.getRawOffset() / 3600000;
        LogUtils.i(TAG, "______________offset:" + offset + " /ID:" + tz.getID());
        long curTime = beginTime + (stopTime - beginTime) * (x) / (self_width - dp2px(30));
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        LogUtils.i("", "beginTime=" + beginTime + ",stopTime" + stopTime);
//		LogUtils.i(TAG, "===>>>x = " + x);
//		LogUtils.i(TAG, "===>>>(stopTime - beginTime) * (x) = " + (stopTime - beginTime) * (x - dp2px(15)));
//		LogUtils.i(TAG, "===>>>(stopTime - beginTime) * (x) / (self_width - dp2px(30) = " + (stopTime - beginTime) * (x - dp2px(15)) / (self_width - dp2px(30)));
        LogUtils.i(TAG, "===>>>curTime = " + curTime);
//		LogUtils.i(TAG, "===>>>time = " + sdf.format(new Date(curTime*1000L)));
        return sdf.format(new Date(curTime * 1000L));
    }

    private int time2coordinateX(int h, int m) {
        LogUtils.i("","h+m="+h+"+"+m);
        Calendar calendar = PublicData.curShowCal;
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
        long time = calendar.getTimeInMillis() / 1000L;
        TimeZone tz1 = TimeZone.getDefault();
        LogUtils.i(TAG, "===>>>(time - beginTime) = " + (time - (beginTime-8*60*60)));
        LogUtils.i(TAG, "===>>>(time - beginTime) = " + time +",="+beginTime);
        LogUtils.i(TAG, "===>>>(time - beginTime) / (stopTime - beginTime) = " + (time - beginTime) / (totalTime));
        LogUtils.i(TAG, "===>>>(self_width - dp2px(30)) * (time - beginTime) / (stopTime - beginTime) = " + (self_width - dp2px(30)) * (time - beginTime) / (totalTime));

        if (tz1.getID().toUpperCase().indexOf("LOS_ANGELES") != -1) {
            return (int) ((self_width - dp2px(30)) * (time - (beginTime+7*60*60)) / (totalTime)) + dp2px(15);

        } else {
            return (int) ((self_width - dp2px(30)) * (time - (beginTime-8*60*60)) / (totalTime)) + dp2px(15);

        }

    }

    /**
     * summer: 柱状图的睡眠信息类
     */
    private class SleepInfo {
        public static final int DEEP = 0, LIGHT = 1, AWAKE = 2;
        public long timeStamp;
        public int sleepType;
        public long timeStop;

        public SleepInfo(int sleepType, long timeStamp, long timeStop) {
            this.timeStamp = timeStamp;
            this.sleepType = sleepType;
            this.timeStop = timeStop;
        }
    }

    /**
     * summer:
     * 根据当前线条位置返回对应的睡眠类型颜色
     *
     * @param px
     * @return
     */
    private int getCurLineColor(int px) {
        long curTime = beginTime + (stopTime - beginTime) * (px) / (self_width - dp2px(30));
        long dif = 0;
        int curType = 0;
        // 选出最接近的睡眠类型
        for (SleepInfo sleepInfo : sleepInfoList) {
            if (dif == 0 && (sleepInfo.timeStamp - curTime) >= 0)
                dif = sleepInfo.timeStamp - curTime;
            if ((sleepInfo.timeStamp - curTime) <= dif && (sleepInfo.timeStamp - curTime) >= 0) {
                dif = sleepInfo.timeStamp - curTime;
                curType = sleepInfo.sleepType;
            }
        }
        switch (curType) {
            case SleepInfo.DEEP:
                return DRAW_SLEEP_COLOR;
            case SleepInfo.LIGHT:
                return DRAW_SLEEP_LIGHT_COLOR;
            case SleepInfo.AWAKE:
                return DRAW_CALORIES_COLOR;
        }
        return DRAW_CALORIES_COLOR;
    }
    
    
    
}
