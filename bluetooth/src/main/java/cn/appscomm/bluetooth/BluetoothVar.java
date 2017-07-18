package cn.appscomm.bluetooth;

import java.util.LinkedList;

import cn.appscomm.bluetooth.mode.HeartRateBT;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.mode.SleepBT;
import cn.appscomm.bluetooth.mode.SportBT;
import cn.appscomm.bluetooth.mode.SportBTL28T;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙变量类，用于存储蓝牙交互后的蓝牙数据
 */
public class BluetoothVar {

    public String softVersionL28T;                                                                  //28T软件版本
    public String watchIDL28T;                                                                      //28TwatchID
    public int batteryPowerL28T;                                                                    // 28T电量
    public int sportSleepModeL28T;                                                                  // 28T运动/睡眠模式
    public int sportCountL28T;                                                                      // 28T运动条数
    public int sleepCountL28T;                                                                      // 28T睡眠条数
    public LinkedList<SportBTL28T> sportBTDataListL28T;                                                 // 28T存储运动数据
    public LinkedList<SleepBT> sleepBTDataListL28T;                                                 // 28T存储睡眠数据
    public int deviceDisplayStepL28T;                                                               // L28T设备端显示的步数
    public int deviceDisplayCalorieL28T;                                                            // L28T设备端显示的卡路里
    public int deviceDisplayDistanceL28T;                                                           // L28T设备端显示的距离
    public int deviceDisplaySleepL28T;                                                              // L28T设备端显示的睡眠
    public int deviceDisplaySportTimeL28T;                                                          // L28T设备端显示的运动时长



            /*-------------------------------------------万恶的分割线-------------------------------------------*/

    public String watchID;                                                                          // watchID

    public String deviceType;                                                                       // 设备类型
    public String softVersion;                                                                      // 软件版本
    public String hardwareVersion;                                                                  // 硬件版本
    public String commProtocol;                                                                     // 通信协议
    public String functionVersion;                                                                  // 功能版本
    public String otherVersion;                                                                     // 其他

    public int sportCount;                                                                          // 运动条数
    public int sleepCount;                                                                          // 睡眠条数
    public int heartRateCount;                                                                      // 心率条数
    public int moodCount;                                                                           // 情绪和疲劳度条数
    public int bloodPressureCount;                                                                  // 血压条数

    public int batteryPower;                                                                        // 电量
    public int sportSleepMode;                                                                      // 运动/睡眠模式
    public String dateTime;                                                                         // 日期时间

    public LinkedList<SportBT> sportBTDataList;                                                     // 存储运动数据
    public LinkedList<SleepBT> sleepBTDataList;                                                     // 存储睡眠数据
    public LinkedList<HeartRateBT> heartRateBTDataList;                                             // 存储心率数据
    public LinkedList<Integer> indexResendCommand;                                                  // 需要单独获取的索引号集合

    public int stepGoalsValue;                                                                      // 步数目标值 单位是步
    public int stepGoalsFlag;                                                                       // 步数目标标志
    public int calorieGoalsValue;                                                                   // 卡路里目标 单位是千卡
    public int calorieGoalsFlag;                                                                    // 卡路里目标标志
    public int distanceGoalsValue;                                                                  // 距离目标 单位是千米
    public int distanceGoalsFlag;                                                                   // 距离目标标志
    public int sportTimeGoalsValue;                                                                 // 运动时长目标 单位是分钟
    public int sportTimeGoalsFlag;                                                                  // 运动时长目标标志
    public int sleepGoalsValue;                                                                     // 睡眠时间目标 单位是小时
    public int sleepGoalsFlag;                                                                      // 睡眠时间目标标志

    public int dateFormat;                                                                          // 日期格式
    public int timeFormat;                                                                          // 时间格式
    public int batteryShow;                                                                         // 电池显示
    public int lunarFormat;                                                                         // 农历格式
    public int screenFormat;                                                                        // 屏幕格式
    public int backgroundStyle;                                                                     // 背景风格
    public int sportDataShow;                                                                       // 运动数据显示
    public int usernameFormat;                                                                      // 用户名格式

    public int screenBrightness;                                                                    // 屏幕亮度

    public int volume;                                                                              // 音量
    public int shockStrength;                                                                       // 震动强度
    public int brightScreenTime;                                                                    // 亮屏时间
    public int[] mainBackgroundColor;                                                               // 主界面背景颜色
    public int[] alarmBackgroundColor;                                                              // 报警界面背景颜色
    public int workMode;                                                                            // 工作模式

    public int language;                                                                            // 语言

    public int unit;                                                                                // 单位

    public int sex;                                                                                 // 性别
    public int age;                                                                                 // 年龄
    public int height;                                                                              // 身高
    public float weight;                                                                            // 体重

    public int usageHabits;                                                                         // 用户习惯
    public String userName;                                                                         // 用户名

    public int deviceDisplayStep;                                                                   // 设备端显示的步数
    public int deviceDisplayCalorie;                                                                // 设备端显示的卡路里
    public int deviceDisplayDistance;                                                               // 设备端显示的距离
    public int deviceDisplaySleep;                                                                  // 设备端显示的睡眠
    public int deviceDisplaySportTime;                                                              // 设备端显示的运动时长
    public int deviceDisplayHeartRate;                                                              // 设备端显示的心率值
    public int deviceDisplayMood;                                                                   // 设备端显示的情绪疲劳度值

    public boolean antiLostSwitch;                                                                  // 防丢开关
    public boolean autoSyncSwitch;                                                                  // 自动同步开关
    public boolean sleepSwitch;                                                                     // 睡眠开关
    public boolean autoSleepSwitch;                                                                 // 自动睡眠监测开关
    public boolean incomeCallSwitch;                                                                // 来电提醒开关
    public boolean missCallSwitch;                                                                  // 未接来电提醒开关
    public boolean smsSwitch;                                                                       // 短信提醒开关
    public boolean socialSwitch;                                                                    // 社交提醒开关
    public boolean emailSwitch;                                                                     // 邮件提醒开关
    public boolean calendarSwitch;                                                                  // 日历开关
    public boolean sedentarySwitch;                                                                 // 久坐提醒开关
    public boolean lowPowerSwitch;                                                                  // 超低功耗功能开关
    public boolean secondRemindSwitch;                                                              // 二次提醒开关
    public boolean raiseWakeSwitch;                                                                 // 抬手亮屏开关

    public int bedTimeHour;                                                                         // 进入睡眠时
    public int bedTimeMin;                                                                          // 进入睡眠分
    public int awakeTimeHour;                                                                       // 退出睡眠时
    public int awakeTimeMin;                                                                        // 退出睡眠分
    public int remindSleepCycle;                                                                    // 提醒睡眠周期

    public int UID;                                                                                 // 绑定开始UID
    public boolean initFlag;                                                                        // 绑定结束(初始化标志)

    public String cardNumber;                                                                       // 卡号
    public String money;                                                                            // 余额
    public StringBuffer record;                                                                     // 记录
    public int recordCount;                                                                         // 记录条数
    public boolean haveRecord;                                                                      // 是否有记录

    public int remindCount;                                                                         // 提醒条数
    public LinkedList<ReminderBT> reminderBTDataList;                                               // 所有提醒数据

    public int[] arrItems;                                                                          // 界面显示

    public int[] shockItems;                                                                        // 震动
    public int antiShock;                                                                           // 防丢失震动
    public int clockShock;                                                                          // 闹钟震动
    public int callShock;                                                                           // 来电震动
    public int missCallShock;                                                                       // 未接来电震动
    public int smsShock;                                                                            // 短信震动
    public int socialShock;                                                                         // 社交震动
    public int emailShock;                                                                          // 邮件震动
    public int calendarShock;                                                                       // 日历震动
    public int sedentaryShock;                                                                      // 久坐震动
    public int lowPowerShock;                                                                       // 低点震动

    public boolean heartRateAutoTrackSwitch;                                                        // 是否启动自动心率(自动心率大于0则打开，为0则关闭)
    public int heartRateFrequency;                                                                  // 自动心率频次
    public int heartRateMaxValue;                                                                   // 最大心率值
    public int heartRateMinValue;                                                                   // 最小心率值
    public boolean heartRateAlarmSwitch;                                                            // 是否心率报警

    public boolean inactivityAlertSwitch;                                                            // 久坐提醒 开关
    public int inactivityAlertInterval;                                                             // 久坐提醒 间隔
    public int inactivityAlertStartHour;                                                            // 久坐提醒 开始时
    public int inactivityAlertStartMin;                                                             // 久坐提醒 开始分
    public int inactivityAlertEndHour;                                                              // 久坐提醒 结束时
    public int inactivityAlertEndMin;                                                               // 久坐提醒 结束分
    public int inactivityAlertCycle;                                                                // 久坐提醒 周期

    public int caloriesType;                                                                        // 卡路里类型
    public int ultraviolet;                                                                         // 紫外线

    public int realTimeHeartRateValue;                                                              // 实时心率值
}
