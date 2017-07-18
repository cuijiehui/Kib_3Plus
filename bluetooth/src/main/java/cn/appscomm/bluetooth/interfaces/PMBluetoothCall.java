package cn.appscomm.bluetooth.interfaces;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.BluetoothVar;

/**
 * 作者：hsh
 * 日期：2017/3/06
 * 说明：蓝牙PM间的接口定义
 */
public interface PMBluetoothCall {
    int SMS_NOTIFY_TYPE_SMS = 0;
    int SMS_NOTIFY_TYPE_SOCIAL = 1;
    int SMS_NOTIFY_TYPE_EMAIL = 2;
    int SMS_NOTIFY_TYPE_SCHEDULE = 3;

    int REMINDER_TYPE_EAT = BluetoothCommandConstant.REMIND_TYPE_EAT;                               // 吃饭
    int REMINDER_TYPE_MEDICINE = BluetoothCommandConstant.REMIND_TYPE_MEDICINE;                     // 吃药
    int REMINDER_TYPE_DRINK = BluetoothCommandConstant.REMIND_TYPE_DRINK;                           // 喝水
    int REMINDER_TYPE_SLEEP = BluetoothCommandConstant.REMIND_TYPE_SLEEP;                           // 睡觉
    int REMINDER_TYPE_AWAKE = BluetoothCommandConstant.REMIND_TYPE_AWAKE;                           // 清醒
    int REMINDER_TYPE_SPORT = BluetoothCommandConstant.REMIND_TYPE_SPORT;                           // 运动
    int REMINDER_TYPE_MEETING = BluetoothCommandConstant.REMIND_TYPE_MEETING;                       // 会议
    int REMINDER_TYPE_CUSTOM = BluetoothCommandConstant.REMIND_TYPE_CUSTOM;                         // 自定义

    int SWITCH_TYPE_ANTI = BluetoothCommandConstant.SWITCH_TYPE_ANTI;                               // 防丢开关
    int SWITCH_TYPE_AUTO_SYNC = BluetoothCommandConstant.SWITCH_TYPE_AUTO_SYNC;                     // 自动同步开关
    int SWITCH_TYPE_SLEEP = BluetoothCommandConstant.SWITCH_TYPE_SLEEP;                             // 睡眠开关
    int SWITCH_TYPE_AUTO_SLEEP = BluetoothCommandConstant.SWITCH_TYPE_AUTO_SLEEP;                   // 自动睡眠开关
    int SWITCH_TYPE_CALL = BluetoothCommandConstant.SWITCH_TYPE_CALL;                               // 来电开关
    int SWITCH_TYPE_MISS_CALL = BluetoothCommandConstant.SWITCH_TYPE_MISS_CALL;                     // 未接来电开关
    int SWITCH_TYPE_SMS = BluetoothCommandConstant.SWITCH_TYPE_SMS;                                 // 短信开关
    int SWITCH_TYPE_SOCIAL = BluetoothCommandConstant.SWITCH_TYPE_SOCIAL;                           // 社交开关
    int SWITCH_TYPE_EMAIL = BluetoothCommandConstant.SWITCH_TYPE_EMAIL;                             // 邮件开关
    int SWITCH_TYPE_CALENDAR = BluetoothCommandConstant.SWITCH_TYPE_CALENDAR;                       // 日程开关
    int SWITCH_TYPE_SEDENTARY = BluetoothCommandConstant.SWITCH_TYPE_SEDENTARY;                     // 久坐开关
    int SWITCH_TYPE_LOW_POWER = BluetoothCommandConstant.SWITCH_TYPE_LOW_POWER;                     // 低电量提醒开关
    int SWITCH_TYPE_SECOND_REMINDER = BluetoothCommandConstant.SWITCH_TYPE_SECOND_REMINDER;         // 二次提醒开关
    int SWITCH_TYPE_RING = BluetoothCommandConstant.SWITCH_TYPE_RING;                               // 铃声开关
    int SWITCH_TYPE_RAISE_WAKE = BluetoothCommandConstant.SWITCH_TYPE_RAISE_WAKE;                   // 抬手亮屏开关

    int SHOCK_TYPE_ANTI = BluetoothCommandConstant.SHOCK_ACTION_ANTI;                               // 震动类型：防丢
    int SHOCK_TYPE_CALL = BluetoothCommandConstant.SHOCK_ACTION_INCOME_CALL;                        // 震动类型：来电
    int SHOCK_TYPE_MISS_CALL = BluetoothCommandConstant.SHOCK_ACTION_MISS_CALL;                     // 震动类型：未接来电
    int SHOCK_TYPE_SMS = BluetoothCommandConstant.SHOCK_ACTION_SMS;                                 // 震动类型：短信
    int SHOCK_TYPE_SOCIAL = BluetoothCommandConstant.SHOCK_ACTION_SOCIAL;                           // 震动类型：社交
    int SHOCK_TYPE_EMAIL = BluetoothCommandConstant.SHOCK_ACTION_EMAIL;                             // 震动类型：邮件
    int SHOCK_TYPE_CALENDAR = BluetoothCommandConstant.SHOCK_ACTION_CALENDAR;                       // 震动类型：日历

    int SHOCK_MODE_NO = BluetoothCommandConstant.SHOCK_MODE_NO;                                     // 不震动
    int SHOCK_MODE_SIGNAL_LONG = BluetoothCommandConstant.SHOCK_MODE_SIGNAL_LONG;                   // 单次长震动
    int SHOCK_MODE_SIGNAL_SHORT = BluetoothCommandConstant.SHOCK_MODE_SIGNAL_SHORT;                 // 单次短震动
    int SHOCK_MODE_INTERVAL_TWO_LONG = BluetoothCommandConstant.SHOCK_MODE_INTERVAL_TWO_LONG;       // 间隔2次长震动
    int SHOCK_MODE_INTERVAL_TWO_SHORT = BluetoothCommandConstant.SHOCK_MODE_INTERVAL_TWO_SHORT;     // 间隔2次短震动
    int SHOCK_MODE_INTERVAL_LONG_SHORT = BluetoothCommandConstant.SHOCK_MODE_INTERVAL_LONG_SHORT;   // 长震动和短震动交替
    int SHOCK_MODE_ALWAY_LONG = BluetoothCommandConstant.SHOCK_MODE_ALWAY_LONG;                     // 一直长震动
    int SHOCK_MODE_ALWAY_SHORT = BluetoothCommandConstant.SHOCK_MODE_ALWAY_SHORT;                   // 一直短震动
    int SHOCK_MODE_INTERVAL_FIVE_LONG = BluetoothCommandConstant.SHOCK_MODE_INTERVAL_FIVE_LONG;     // 间隔5次长震动

    byte PHONE_NAME_PUSH_TYPE_INCOME_CALL = BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_INCOME_CALL;
    byte MSG_PUSH_TYPE_INCOME_CALL = BluetoothCommandConstant.MSG_PUSH_TYPE_INCOME_CALL;
    byte MSG_PUSH_TYPE_OFF_CALL = BluetoothCommandConstant.MSG_PUSH_TYPE_OFF_CALL;
    byte PHONE_NAME_PUSH_TYPE_MISS_CALL = BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_MISS_CALL;
    byte PHONE_NAME_PUSH_TYPE_MISS_CALL_DATETIME = BluetoothCommandConstant.PHONE_NAME_PUSH_TYPE_MISS_CALL_DATETIME;
    byte MSG_PUSH_TYPE_MISS_CALL = BluetoothCommandConstant.MSG_PUSH_TYPE_MISS_CALL;
    byte SMS_PUSH_TYPE_NAME_OR_NUMBER = BluetoothCommandConstant.SMS_PUSH_TYPE_NAME_OR_NUMBER;
    byte SMS_PUSH_TYPE_CONTENT = BluetoothCommandConstant.SMS_PUSH_TYPE_CONTENT;
    byte SMS_PUSH_TYPE_DATETIME = BluetoothCommandConstant.SMS_PUSH_TYPE_DATETIME;
    byte MSG_PUSH_TYPE_SMS = BluetoothCommandConstant.MSG_PUSH_TYPE_SMS;
    byte SOCIAL_PUSH_TYPE_TITLE = BluetoothCommandConstant.SOCIAL_PUSH_TYPE_TITLE;
    byte SOCIAL_PUSH_TYPE_CONTENT = BluetoothCommandConstant.SOCIAL_PUSH_TYPE_CONTENT;
    byte SOCIAL_PUSH_TYPE_DATETIME = BluetoothCommandConstant.SOCIAL_PUSH_TYPE_DATETIME;
    byte EMAIL_PUSH_TYPE_ADDRESS = BluetoothCommandConstant.EMAIL_PUSH_TYPE_ADDRESS;
    byte EMAIL_PUSH_TYPE_CONTENT = BluetoothCommandConstant.EMAIL_PUSH_TYPE_CONTENT;
    byte EMAIL_PUSH_TYPE_DATETIME = BluetoothCommandConstant.EMAIL_PUSH_TYPE_DATETIME;
    byte MSG_PUSH_TYPE_EMAIL = BluetoothCommandConstant.MSG_PUSH_TYPE_EMAIL;
    byte SCHEDULE_PUSH_TYPE_CONTENT = BluetoothCommandConstant.SCHEDULE_PUSH_TYPE_CONTENT;
    byte SCHEDULE_PUSH_TYPE_DATETIME = BluetoothCommandConstant.SCHEDULE_PUSH_TYPE_DATETIME;
    byte MSG_PUSH_TYPE_CALENDAR = BluetoothCommandConstant.MSG_PUSH_TYPE_CALENDAR;
    byte GOAL_TYPE_STEP = BluetoothCommandConstant.GOAL_TYPE_STEP;
    byte GOAL_TYPE_CALORIE = BluetoothCommandConstant.GOAL_TYPE_CALORIE;
    byte GOAL_TYPE_DISTANCE = BluetoothCommandConstant.GOAL_TYPE_DISTANCE;
    byte GOAL_TYPE_SPORT_TIME = BluetoothCommandConstant.GOAL_TYPE_SPORT_TIME;
    byte GOAL_TYPE_SLEEP = BluetoothCommandConstant.GOAL_TYPE_SLEEP;
    byte MSG_PUSH_TYPE_MAX_NAME_LEN = BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN;

    int COMMAND_TYPE_BIND = BluetoothSend.COMMAND_TYPE_BIND;
    int COMMAND_TYPE_CALL = BluetoothSend.COMMAND_TYPE_CALL;
    int COMMAND_TYPE_PAGE = BluetoothSend.COMMAND_TYPE_PAGE;
    int COMMAND_TYPE_REMOTE_CONTROL = BluetoothSend.COMMAND_TYPE_REMOTE_CONTROL;
    int COMMAND_TYPE_SYNC = BluetoothSend.COMMAND_TYPE_SYNC;
    int COMMAND_TYPE_NOTIFY = BluetoothSend.COMMAND_TYPE_NOTIFY;
    int COMMAND_TYPE_NORMAL = BluetoothSend.COMMAND_TYPE_NORMAL;
    int COMMAND_TYPE_CONTACT = BluetoothSend.COMMAND_TYPE_CONTACT;

    /**
     * 开启服务
     */
    void startService();

    /**
     * 重启服务
     *
     * @param mac                需要连接的MAC地址，如果为空，则重连以前的MAC地址
     * @param isSupportHeartRate 是否支持心率功能
     * @return true：需要连接的mac地址不为空，可以重启服务 false：mac地址为空，不能重启服务
     */
    boolean reConnect(String mac, boolean isSupportHeartRate,boolean isSend03);

    /**
     * 结束服务
     */
    void endService();

    /**
     * 蓝牙服务是否运行
     *
     * @return true:运行 false:停止
     */
    boolean isBluetoothLeServiceRunning();

    /**
     * 连接指定mac的设备
     *
     * @param mac                指定的mac
     * @param isSupportHeartRate 是否支持心率
     */
    void connect(String mac, boolean isSupportHeartRate,boolean isSend03);

    /**
     * 断开连接
     *
     * @param mac 需要断开的设备
     */
    void disConnect(String mac);

    /**
     * 手机是否连接设备
     *
     * @param mac 指定mac的设备是否连接
     * @return true：已连接 false：未连接
     */
    boolean isConnect(String mac);
    /*--------------------------------------------------L28T命令--------------------------------------------------*/

    /**
     * 获取28T软件版本
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getSoftwareVersion(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 获取28TWatchID
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getWatchIDL28T(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 绑定28Tuid
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void bindUID(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 设置name
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setName(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置个人信息
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setBaseInfo(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置时间
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setBaseTime(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置时间格式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setTimeFormat(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 获取电电量
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getPower(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 获取睡眠状态
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getSleepState(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置睡眠状态
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setSleepState(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 获取运动总数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getStepTotal(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 获取睡眠总数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getSleepTotal(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 获取运动详细
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getStepData(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content,int len, String... macs);
    /**
     * 获取睡眠详细
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void getSleepDataL28T(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content,int len, String... macs);
    /**
     * 删除睡眠详细
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void delSleepData(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 删除运动详细
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void delStepData(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 设置目标
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setGoal(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置震动强度
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setVibration(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置单位
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setUnit(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 设置预设睡眠
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setSleepSwitch(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 重置设备
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setReset(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 久坐提醒
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setAlert(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     * 设置通知
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setNotification(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /**
     * 是否需要发送03
     * @param isSend03
     */
    void setIsSend03(boolean isSend03);

    /**
     *
     * 设置删除
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setDel(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);
    /**
     *
     * 设置金币
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param isL28T      是不是L28T发送，用于判断怎么发送内容L28T直接发送content，其他需要整合才发送
     * @param content     发送的内容
     * @param macs        要下发命令的所有设备
     */
    void setGold(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs);

    /*--------------------------------------------------设置类--------------------------------------------------*/

    /**
     * 获取watchID
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getWatchID(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取设备版本
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDeviceVersion(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取设备的日期时间
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDateTime(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置设备的日期时间
     *
     * @param callback    回调结果
     * @param year        年
     * @param month       月
     * @param day         日
     * @param hour        时
     * @param min         分
     * @param sec         秒
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setDateTime(IBluetoothResultCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String... macs);

    /**
     * 获取设备时间界面设置
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getTimeSurfaceSetting(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置设备时间界面
     *
     * @param dateFormat      日期格式
     * @param timeFormat      时间格式
     * @param batteryFormat   电池格式
     * @param lunarFormat     日历格式
     * @param screenFormat    屏幕格式
     * @param backgroundStyle 背景格式
     * @param sportDataFormat 运动数据格式
     * @param usernameFormat  用户名格式
     * @param commandType     发送类型
     * @param macs            要下发命令的所有设备
     */
    void setTimeSurfaceSetting(IBluetoothResultCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, int commandType, String... macs);

    // TODO 一级界面获取设置未做

    /**
     * 获取屏幕亮度
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getScreenBrightness(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置屏幕亮度
     *
     * @param callback        回调结果
     * @param brightnessValue 屏幕亮度
     * @param commandType     发送类型
     * @param macs            要下发命令的所有设备
     */
    void setScreenBrightness(IBluetoothResultCallback callback, int brightnessValue, int commandType, String... macs);

    /**
     * 获取电池值
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getBatteryPower(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取音量值
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getVolume(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置音量值
     *
     * @param callback    回调结果
     * @param volume      音量值
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setVolume(IBluetoothResultCallback callback, int volume, int commandType, String... macs);

    /**
     * 获取震动模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getShockMode(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置震动模式
     *
     * @param callback    回调结果
     * @param shockType   震动类型
     * @param shockMode   震动模式
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setShockMode(IBluetoothResultCallback callback, int shockType, int shockMode, int commandType, String... macs);

    /**
     * 获取语言
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getLanguage(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置语言
     *
     * @param callback    回调结果
     * @param language    语言
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setLanguage(IBluetoothResultCallback callback, int language, int commandType, String... macs);

    /**
     * 获取单位
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUnit(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置单位
     *
     * @param callback    回调结果
     * @param unit        单位
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUnit(IBluetoothResultCallback callback, int unit, int commandType, String... macs);

    /**
     * 恢复出厂
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void restoreFactory(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 进入升级模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void enterUpdateMode(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 进入升级模式(需要填入飞思卡尔、触摸芯片、心率的地址)
     *
     * @param callback             回调结果
     * @param freescaleAddress     飞思卡尔地址
     * @param freescaleSectorCount 飞思卡尔扇区个数
     * @param touchAddress         触摸芯片地址
     * @param touchSectorCount     触摸芯片扇区个数
     * @param heartRateAddress     心率地址
     * @param heartRateSectorCount 心率扇区个数
     * @param commandType          发送类型
     * @param macs                 要下发命令的所有设备
     */
    void enterUpdateMode(IBluetoothResultCallback callback, byte[] freescaleAddress, byte freescaleSectorCount, byte[] touchAddress, byte touchSectorCount, byte[] heartRateAddress, byte heartRateSectorCount, int commandType, String... macs);

    /**
     * 获取震动强度
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getShockStrength(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置震动强度
     *
     * @param callback      回调结果
     * @param shockStrength 震动强度
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void setShockStrength(IBluetoothResultCallback callback, int shockStrength, int commandType, String... macs);

    // TODO 设置/获取 主界面和报警界面的背景颜色

    /**
     * 获取工作模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getWorkMode(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置工作模式
     *
     * @param callback    回调结果
     * @param workMode    工作模式
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setWorkMode(IBluetoothResultCallback callback, int workMode, int commandType, String... macs);

    /**
     * 获取亮屏时间
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getBrightScreenTime(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置亮屏时间
     *
     * @param callback         回调结果
     * @param brightScreenTime 亮屏时间
     * @param commandType      发送类型
     * @param macs             要下发命令的所有设备
     */
    void setBrightScreenTime(IBluetoothResultCallback callback, int brightScreenTime, int commandType, String... macs);

    /*-----------------------------------------------个人信息类-------------------------------------------------*/

    /**
     * 获取个人信息
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUserInfo(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置个人信息
     *
     * @param callback    回调结果
     * @param sex         性别
     * @param age         年龄
     * @param height      身高
     * @param weight      体重
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUserInfo(IBluetoothResultCallback callback, int sex, int age, int height, int weight, int commandType, String... macs);

    /**
     * 获取用户习惯
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUsageHabits(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置用户习惯
     *
     * @param callback    回调结果
     * @param usageHabits 用户习惯
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUsageHabits(IBluetoothResultCallback callback, int usageHabits, int commandType, String... macs);

    /**
     * 获取用户名称
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUserName(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置用户名称
     *
     * @param callback    回调结果
     * @param userName    用户名称
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUserName(IBluetoothResultCallback callback, String userName, int commandType, String... macs);

    /*-----------------------------------------------运动相关类-------------------------------------------------*/

    /**
     * 获取目标
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getGoal(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置目标
     *
     * @param callback    回调结果
     * @param goalType    目标类型
     * @param goalValue   目标值
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setGoal(IBluetoothResultCallback callback, int goalType, int goalValue, int commandType, String... macs);

    /**
     * 获取运动睡眠模式
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getSportSleepMode(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取所有类型的条数(运动、睡眠、心率、血压等)
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAllDataTypeCount(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 删除运动数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteSportData(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取运动数据
     *
     * @param callback       回调结果
     * @param sportDataCount 运动数据条数
     * @param commandType    发送类型
     * @param macs           要下发命令的所有设备
     */
    void getSportData(IBluetoothResultCallback callback, int sportDataCount, int commandType, String... macs);

    /**
     * 删除睡眠数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteSleepData(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取睡眠数据
     *
     * @param callBack       回调结果
     * @param sleepDataCount 睡眠数据条数
     * @param commandType    发送类型
     * @param macs           要下发命令的所有设备
     */
    void getSleepData(IBluetoothResultCallback callBack, int sleepDataCount, int commandType, String... macs);

    /**
     * 获取设备显示
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getDeviceDisplay(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取自动睡眠
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAutoSleep(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置自动睡眠
     *
     * @param callback    回调结果
     * @param enterHour   进入小时
     * @param enterMin    进入分钟
     * @param quitHour    退出小时
     * @param quitMin     退出分钟
     * @param cycle       周期
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setAutoSleep(IBluetoothResultCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, int commandType, String... macs);

    /**
     * 获取心率总数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getHeartRateCount(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 删除心率数据
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void deleteHeartRateData(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取心率数据
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void getHeartRateData(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String... macs);

    /**
     * 获取自动心率
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getAutoHeartRateFrequency(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置自动心率
     *
     * @param callback           回调结果
     * @param heartRateFrequency 心率间隔
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void setAutoHeartRateFrequency(IBluetoothResultCallback callback, int heartRateFrequency, int commandType, String... macs);

    /**
     * 获取心率报警门限
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getHeartRateAlarmThreshold(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置心率报警门限
     *
     * @param callback             回调结果
     * @param heartRateAlarmSwitch 心率开关
     * @param heartRateMinValue    心率最小值报警
     * @param heartRateMaxValue    心率最大值报警
     * @param commandType          发送类型
     * @param macs                 要下发命令的所有设备
     */
    void setHeartRateAlarmThreshold(IBluetoothResultCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, int commandType, String... macs);

    /**
     * 获取久坐提醒
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getInactivityAlert(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置久坐提醒
     *
     * @param callback    回调结果
     * @param isOpen      是否开启
     * @param cycle       周期
     * @param interval    间隔
     * @param startHour   开始小时
     * @param startMin    开始分钟
     * @param endHour     结束小时
     * @param endMin      结束分钟
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setInactivityAlert(IBluetoothResultCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, int commandType, String... macs);

    /**
     * 获取卡路里类型
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getCaloriesType(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置卡路里类型
     *
     * @param callback    回调结果
     * @param enable      开关
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setCaloriesType(IBluetoothResultCallback callback, boolean enable, int commandType, String... macs);

    /**
     * 获取心率数据(一条命令返回2条心率值)
     *
     * @param callback           回调结果
     * @param heartRateDataCount 心率数据条数
     * @param commandType        发送类型
     * @param macs               要下发命令的所有设备
     */
    void getHeartRateDataEx(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String... macs);

    // TODO 获取血压总数
    // TODO 删除血压命令
    // TODO 获取血压数据
    // TODO 血压芯片学习
    // TODO 设备计时运动总数
    // TODO 获取计时运动数据
    // TODO 删除计时运动数据

    /*-------------------------------------------------通知类---------------------------------------------------*/

    /**
     * 发送来电姓名或号码
     *
     * @param callback      回调结果
     * @param len           姓名或号码字节数组的长度
     * @param callType      呼叫类型(未接或来电)
     * @param bNameOrNumber 姓名或号码
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void sendPhoneName(IBluetoothResultCallback callback, int len, int callType, byte[] bNameOrNumber, int commandType, String... macs);

    /**
     * 发送消息条数
     *
     * @param callback    回调结果
     * @param msgType     消息类型
     * @param msgCount    消息条数
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void sendMessageCount(IBluetoothResultCallback callback, int msgType, int msgCount, int commandType, String... macs);

    /**
     * 发送短信和通知
     *
     * @param callback      回调结果
     * @param pushLen       推送长度
     * @param pushType      推送类型
     * @param pushData      推送数据
     * @param smsNotifyType 短信通知类型(短信、社交、邮件、日程)
     * @param commandType   发送类型
     * @param macs          要下发命令的所有设备
     */
    void sendSMSAndNotify(IBluetoothResultCallback callback, int pushLen, int pushType, byte[] pushData, int smsNotifyType, int commandType, String... macs);

    /*-----------------------------------------------状态设置类-------------------------------------------------*/

    /**
     * 获取开关设置
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getSwitchSetting(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置开关
     *
     * @param callback    回调结果
     * @param switchType  开关类型
     * @param enable      开关
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setSwitchSetting(IBluetoothResultCallback callback, int switchType, boolean enable, int commandType, String... macs);

    /**
     * 获取提醒条数
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getReminderCount(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 获取提醒
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getReminder(IBluetoothResultCallback callback, int reminderCount, int commandType, String... macs);

    /**
     * 设置提醒
     *
     * @param callback    回调结果
     * @param type        提醒操作(0x00:新增 0x01:修改 0x02:删除 0x03:全部删除)
     * @param oldReminder 旧提醒
     * @param newReminder 新提醒
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setReminder(IBluetoothResultCallback callback, int type, ReminderBT oldReminder, ReminderBT newReminder, int commandType, String... macs);

    /**
     * 获取UID
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void getUID(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 设置UID
     *
     * @param callback    回调结果
     * @param UID         UID
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void setUID(IBluetoothResultCallback callback, int UID, int commandType, String... macs);

    /**
     * 检查初始化
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void checkInit(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 绑定开始
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void bindStart(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 绑定结束
     *
     * @param callback    回调结果
     * @param commandType 发送类型
     * @param macs        要下发命令的所有设备
     */
    void bindEnd(IBluetoothResultCallback callback, int commandType, String... macs);

    /**
     * 发送歌曲名
     *
     * @param musicState  音乐状态(true:播放 false:暂停)
     * @param songName    歌曲名
     * @param commandType 发送类型
     * @param macs        若macs长度为0则下发所有已连接的设备，若长度大于0，则下发给定的设备
     */
    void sendSongName(boolean musicState, String songName, int commandType, String... macs);

    /**
     * 无音乐播放
     *
     * @param commandType 发送类型
     * @param macs        若macs长度为0则下发所有已连接的设备，若长度大于0，则下发给定的设备
     */
    void sendStop(int commandType, String... macs);

    /**
     * 设置音量
     *
     * @param volume      音量值
     * @param commandType 发送类型
     * @param macs        若macs长度为0则下发所有已连接的设备，若长度大于0，则下发给定的设备
     */
    void sendVolume(int volume, int commandType, String... macs);

    /**
     * 通过mac地址获取相应的BluetoothVar
     *
     * @param mac mac地址
     * @return null：没找到 不为null：返回相应的BluetoothVar
     */
    BluetoothVar getBluetoothVarByMAC(String mac);

    /**
     * 清除发送命令
     *
     * @param mac 要清除发送命令的所有设备的mac地址
     */
    void clearSendCommand(String... mac);
}
