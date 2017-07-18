package cn.appscomm.bluetooth.implement;

import android.text.TextUtils;

import cn.appscomm.bluetooth.BluetoothCommandConstant;
import cn.appscomm.bluetooth.BluetoothLeService;
import cn.appscomm.bluetooth.BluetoothManager;
import cn.appscomm.bluetooth.BluetoothSend;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.interfaces.PMBluetoothCall;
import cn.appscomm.bluetooth.AppsCommDevice;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.bluetooth.BluetoothVar;
import cn.appscomm.bluetooth.protocol.Extend.Music;
import cn.appscomm.bluetooth.protocol.MessagePush.EmailPush;
import cn.appscomm.bluetooth.protocol.MessagePush.MsgCountPush;
import cn.appscomm.bluetooth.protocol.MessagePush.PhoneNamePush;
import cn.appscomm.bluetooth.protocol.MessagePush.SchedulePush;
import cn.appscomm.bluetooth.protocol.MessagePush.SmsPush;
import cn.appscomm.bluetooth.protocol.MessagePush.SocialPush;
import cn.appscomm.bluetooth.protocol.Setting.BatteryPower;
import cn.appscomm.bluetooth.protocol.Setting.BrightScreenTime;
import cn.appscomm.bluetooth.protocol.Setting.DateTime;
import cn.appscomm.bluetooth.protocol.Setting.DeviceVersion;
import cn.appscomm.bluetooth.protocol.Setting.Language;
import cn.appscomm.bluetooth.protocol.Setting.RestoreFactory;
import cn.appscomm.bluetooth.protocol.Setting.ScreenBrightnessSetting;
import cn.appscomm.bluetooth.protocol.Setting.ShockMode;
import cn.appscomm.bluetooth.protocol.Setting.ShockStrength;
import cn.appscomm.bluetooth.protocol.Setting.TimeSurfaceSetting;
import cn.appscomm.bluetooth.protocol.Setting.Unit;
import cn.appscomm.bluetooth.protocol.Setting.UpgradeMode;
import cn.appscomm.bluetooth.protocol.Setting.Volume;
import cn.appscomm.bluetooth.protocol.Setting.WatchID;
import cn.appscomm.bluetooth.protocol.Setting.WorkMode;
import cn.appscomm.bluetooth.protocol.Sport.AllDataTypeCount;
import cn.appscomm.bluetooth.protocol.Sport.CaloriesType;
import cn.appscomm.bluetooth.protocol.Sport.GetHeartRateDataEx;
import cn.appscomm.bluetooth.protocol.Sport.HeartRateAlarmThreshold;
import cn.appscomm.bluetooth.protocol.Sport.HeartRateFrequency;
import cn.appscomm.bluetooth.protocol.Sport.AutoSleep;
import cn.appscomm.bluetooth.protocol.Sport.DeleteHeartRateData;
import cn.appscomm.bluetooth.protocol.Sport.DeleteSleepData;
import cn.appscomm.bluetooth.protocol.Sport.DeleteSportData;
import cn.appscomm.bluetooth.protocol.Sport.DeviceDisplayData;
import cn.appscomm.bluetooth.protocol.Sport.GetHeartRateData;
import cn.appscomm.bluetooth.protocol.Sport.GetSleepData;
import cn.appscomm.bluetooth.protocol.Sport.GetSportData;
import cn.appscomm.bluetooth.protocol.Sport.Goal;
import cn.appscomm.bluetooth.protocol.Sport.HeartRateCount;
import cn.appscomm.bluetooth.protocol.Sport.InactivityAlert;
import cn.appscomm.bluetooth.protocol.Sport.SportSleepMode;
import cn.appscomm.bluetooth.protocol.State.BindEnd;
import cn.appscomm.bluetooth.protocol.State.BindStart;
import cn.appscomm.bluetooth.protocol.State.RemindCount;
import cn.appscomm.bluetooth.protocol.State.RemindSetting;
import cn.appscomm.bluetooth.protocol.State.SwitchSetting;
import cn.appscomm.bluetooth.protocol.User.UsageHabits;
import cn.appscomm.bluetooth.protocol.User.UserInfo;
import cn.appscomm.bluetooth.protocol.User.UserName;
import cn.appscomm.bluetooth.protocol.protocolL28T.AlertL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.AllSleepDataCount;
import cn.appscomm.bluetooth.protocol.protocolL28T.DelSleepDataL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.DelStepData;
import cn.appscomm.bluetooth.protocol.protocolL28T.GoldL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.PowerL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.AllDataCount;
import cn.appscomm.bluetooth.protocol.protocolL28T.PresetSleepL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.ResetL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.SleepDataL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.BaseTime;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.BindInfo;
import cn.appscomm.bluetooth.protocol.protocolL28T.SetDel;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.SetName;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.SetUID;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.SoftwareVersion;
import cn.appscomm.bluetooth.protocol.protocolL28T.SportSleepModeL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.StateBind.WatchIDL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.StepDate;
import cn.appscomm.bluetooth.protocol.protocolL28T.TimeFormatL28T;
import cn.appscomm.bluetooth.protocol.protocolL28T.VibrationL28T;
import cn.appscomm.bluetooth.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/3/20
 * 说明：蓝牙M模块实现类
 */
public enum MBluetooth implements PMBluetoothCall {
    INSTANCE {

    };

    /*------------------------------------------------------------------服务相关-------------------------------------------------------------------*/
    @Override
    public void startService() {
        BluetoothManager.INSTANCE.startService();
    }

    @Override
    public boolean reConnect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        return BluetoothManager.INSTANCE.reConnect(mac, isSupportHeartRate,isSend03);
    }

    @Override
    public void endService() {
        BluetoothManager.INSTANCE.endService();
    }

    @Override
    public boolean isBluetoothLeServiceRunning() {
        return BluetoothManager.INSTANCE.isBluetoothLeServiceRunning();
    }

    @Override
    public void connect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        BluetoothManager.INSTANCE.connect(mac, isSupportHeartRate,isSend03);
    }



    @Override
    public void disConnect(String mac) {
        BluetoothManager.INSTANCE.disConnect(mac);
    }

    @Override
    public boolean isConnect(String mac) {
        return BluetoothManager.INSTANCE.isConnect(mac);
    }
    /*------------------------------------------------------------------L28T设置-------------------------------------------------------------------*/
    @Override
    public void getSoftwareVersion(IBluetoothResultCallback callback, int commandType,boolean isL28T,byte[] content, String... macs) {
        BluetoothSend.addLeafAndSend(new SoftwareVersion(callback, content), commandType,isL28T, macs);
    }

    @Override
    public void getPower(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //电量
        BluetoothSend.addLeafAndSend(new PowerL28T(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void getSleepDataL28T(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content,int len, String... macs) {
        //睡眠数据
        BluetoothSend.addLeafAndSend(new SleepDataL28T(callback, content,len), commandType,isL28T, macs);

    }

    @Override
    public void getSleepState(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //睡眠状态
        BluetoothSend.addLeafAndSend(new SportSleepModeL28T(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void getSleepTotal(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //睡眠总条数
        BluetoothSend.addLeafAndSend(new AllSleepDataCount(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void getStepData(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content,int len, String... macs) {
        //运动数据
        BluetoothSend.addLeafAndSend(new StepDate(callback, content,len), commandType,isL28T, macs);

    }

    @Override
    public void getStepTotal(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //运动总数
        BluetoothSend.addLeafAndSend(new AllDataCount(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void getWatchIDL28T(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //watchid
        BluetoothSend.addLeafAndSend(new WatchIDL28T(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void setBaseInfo(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置个人信息
        BluetoothSend.addLeafAndSend(new BindInfo(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setName(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置个人信息
        BluetoothSend.addLeafAndSend(new SetName(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setBaseTime(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置时间
        BluetoothSend.addLeafAndSend(new BaseTime(callback, content), commandType,isL28T, macs);
    }
    @Override
    public void setTimeFormat(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置时间格式
        BluetoothSend.addLeafAndSend(new TimeFormatL28T(callback, content), commandType,isL28T, macs);
    }
    @Override
    public void setGoal(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置目标
    }

    @Override
    public void setNotification(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置通知
    }

    @Override
    public void setReset(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //重置设备
        BluetoothSend.addLeafAndSend(new ResetL28T(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setAlert(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //重置设备
        BluetoothSend.addLeafAndSend(new AlertL28T(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setSleepState(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置睡眠状态
    }

    @Override
    public void setSleepSwitch(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置预设睡眠
        BluetoothSend.addLeafAndSend(new PresetSleepL28T(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setVibration(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置震动强度
        BluetoothSend.addLeafAndSend(new VibrationL28T(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void setGold(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置金币
        BluetoothSend.addLeafAndSend(new GoldL28T(callback, content), commandType,isL28T, macs);

    }
    @Override
    public void bindUID(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //uid
        BluetoothSend.addLeafAndSend(new SetUID(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void delSleepData(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        BluetoothSend.addLeafAndSend(new DelSleepDataL28T(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void delStepData(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        BluetoothSend.addLeafAndSend(new DelStepData(callback, content), commandType,isL28T, macs);

    }

    @Override
    public void setIsSend03(boolean isSend03) {

    }

    @Override
    public void setUnit(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {

    }

    @Override
    public void setDel(IBluetoothResultCallback callback, int commandType, boolean isL28T, byte[] content, String... macs) {
        //设置时间
        BluetoothSend.addLeafAndSend(new SetDel(callback, content), commandType,isL28T, macs);
    }

    /*------------------------------------------------------------------设备属性-------------------------------------------------------------------*/
    public void getWatchID(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new WatchID(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getDeviceVersion(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DeviceVersion(callback, 1, 1), commandType, macs);
    }

    @Override
    public void getDateTime(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DateTime(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setDateTime(IBluetoothResultCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DateTime(callback, 7, year, month, day, hour, min, sec), commandType, macs);
    }

    @Override
    public void getTimeSurfaceSetting(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new TimeSurfaceSetting(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setTimeSurfaceSetting(IBluetoothResultCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new TimeSurfaceSetting(callback, 8, (byte) dateFormat, (byte) timeFormat, (byte) batteryFormat, (byte) lunarFormat, (byte) screenFormat, (byte) backgroundStyle, (byte) sportDataFormat, (byte) usernameFormat), commandType, macs);
    }

    @Override
    public void getScreenBrightness(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ScreenBrightnessSetting(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setScreenBrightness(IBluetoothResultCallback callback, int brightnessValue, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ScreenBrightnessSetting(callback, 1, (byte) brightnessValue), commandType, macs);
    }

    @Override
    public void getBatteryPower(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BatteryPower(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getVolume(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Volume(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setVolume(IBluetoothResultCallback callback, int volume, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Volume(callback, 1, (byte) volume), commandType, macs);
    }

    @Override
    public void getShockMode(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ShockMode(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setShockMode(IBluetoothResultCallback callback, int shockType, int shockMode, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ShockMode(callback, 2, (byte) shockType, (byte) shockMode), commandType, macs);
    }

    @Override
    public void getLanguage(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Language(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setLanguage(IBluetoothResultCallback callback, int language, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Language(callback, 1, (byte) language), commandType, macs);
    }

    @Override
    public void getUnit(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Unit(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setUnit(IBluetoothResultCallback callback, int unit, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Unit(callback, 1, (byte) unit), commandType, macs);
    }

    @Override
    public void restoreFactory(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new RestoreFactory(callback, 1, (byte) 0), commandType, macs);
    }

    @Override
    public void enterUpdateMode(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UpgradeMode(callback, 1, (byte) 0), commandType, macs);
    }

    @Override
    public void enterUpdateMode(IBluetoothResultCallback callback, byte[] freescaleAddress, byte freescaleSectorCount, byte[] touchAddress, byte touchSectorCount, byte[] heartRateAddress, byte heartRateSectorCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UpgradeMode(callback, 16, (byte) 1, freescaleAddress, freescaleSectorCount, touchAddress, touchSectorCount, heartRateAddress, heartRateSectorCount), commandType, macs);
    }

    @Override
    public void getShockStrength(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ShockStrength(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setShockStrength(IBluetoothResultCallback callback, int shockStrength, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new ShockStrength(callback, 1, (byte) shockStrength), commandType, macs);
    }

    @Override
    public void getWorkMode(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new WorkMode(callback, 1, 0), commandType, macs);

    }

    @Override
    public void setWorkMode(IBluetoothResultCallback callback, int workMode, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new WorkMode(callback, 1, (byte) workMode), commandType, macs);
    }

    @Override
    public void getBrightScreenTime(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BrightScreenTime(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setBrightScreenTime(IBluetoothResultCallback callback, int brightScreenTime, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BrightScreenTime(callback, 1, (byte) brightScreenTime), commandType, macs);
    }

    @Override
    public void getUserInfo(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UserInfo(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setUserInfo(IBluetoothResultCallback callback, int sex, int age, int height, int weight, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UserInfo(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getUsageHabits(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UsageHabits(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setUsageHabits(IBluetoothResultCallback callback, int usageHabits, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UsageHabits(callback, 1, (byte) usageHabits), commandType, macs);
    }

    @Override
    public void getUserName(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UserName(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setUserName(IBluetoothResultCallback callback, String userName, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new UserName(callback, 16, userName), commandType, macs);
    }

    @Override
    public void getGoal(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Goal(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setGoal(IBluetoothResultCallback callback, int goalType, int goalValue, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new Goal(callback, 4, (byte) goalType, goalValue, (byte) 0x01), commandType, macs);
    }

    @Override
    public void getSportSleepMode(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new SportSleepMode(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getAllDataTypeCount(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new AllDataTypeCount(callback, 1, 0), commandType, macs);
    }

    @Override
    public void deleteSportData(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DeleteSportData(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getSportData(IBluetoothResultCallback callback, int sportDataCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new GetSportData(callback, 2, 0, sportDataCount), commandType, macs);
    }

    @Override
    public void deleteSleepData(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DeleteSleepData(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getSleepData(IBluetoothResultCallback callBack, int sleepDataCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new GetSleepData(callBack, 1, 0, sleepDataCount), commandType, macs);
    }

    @Override
    public void getDeviceDisplay(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DeviceDisplayData(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getAutoSleep(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new AutoSleep(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setAutoSleep(IBluetoothResultCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new AutoSleep(callback, 5, (byte) enterHour, (byte) enterMin, (byte) quitHour, (byte) quitMin, (byte) cycle), commandType, macs);
    }

    @Override
    public void getHeartRateCount(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new HeartRateCount(callback, 1, 0), commandType, macs);
    }

    @Override
    public void deleteHeartRateData(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new DeleteHeartRateData(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getHeartRateData(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new GetHeartRateData(callback, 1, 0, heartRateDataCount), commandType, macs);
    }

    @Override
    public void getAutoHeartRateFrequency(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new HeartRateFrequency(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setAutoHeartRateFrequency(IBluetoothResultCallback callback, int heartRateFrequency, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new HeartRateFrequency(callback, 1, (byte) heartRateFrequency), commandType, macs);
    }

    @Override
    public void getHeartRateAlarmThreshold(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new HeartRateAlarmThreshold(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setHeartRateAlarmThreshold(IBluetoothResultCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new HeartRateAlarmThreshold(callback, 3, (byte) heartRateMaxValue, (byte) heartRateMinValue, heartRateAlarmSwitch == 1), commandType, macs);
    }

    @Override
    public void getInactivityAlert(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new InactivityAlert(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setInactivityAlert(IBluetoothResultCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, int commandType, String... macs) {
        cycle = isOpen == 1 ? cycle + (1 << 7) : cycle;
        BluetoothSend.addLeafAndSend(new InactivityAlert(callback, 8, cycle, interval, startHour, startMin, endHour, endMin, 100), commandType, macs);
    }

    @Override
    public void getCaloriesType(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new CaloriesType(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setCaloriesType(IBluetoothResultCallback callback, boolean enable, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new CaloriesType(callback, 1, enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
    }

    @Override
    public void getHeartRateDataEx(IBluetoothResultCallback callback, int heartRateDataCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new GetHeartRateDataEx(callback, 1, 0, heartRateDataCount), commandType, macs);
    }

    @Override
    public void sendPhoneName(IBluetoothResultCallback callback, int len, int callType, byte[] bNameOrNumber, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new PhoneNamePush(callback, len, (byte) callType, bNameOrNumber), commandType, macs);
    }

    @Override
    public void sendMessageCount(IBluetoothResultCallback callback, int msgType, int msgCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new MsgCountPush(callback, 2, (byte) msgType, (byte) msgCount), commandType, macs);
    }

    @Override
    public void sendSMSAndNotify(IBluetoothResultCallback callback, int pushLen, int pushType, byte[] pushData, int smsNotifyType, int commandType, String... macs) {
        switch (smsNotifyType) {
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SMS:
                BluetoothSend.addLeafAndSend(new SmsPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SOCIAL:
                BluetoothSend.addLeafAndSend(new SocialPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL:
                BluetoothSend.addLeafAndSend(new EmailPush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
            case PMBluetoothCall.SMS_NOTIFY_TYPE_SCHEDULE:
                BluetoothSend.addLeafAndSend(new SchedulePush(callback, pushLen, (byte) pushType, pushData), commandType, macs);
                break;
        }
    }

    @Override
    public void getSwitchSetting(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new SwitchSetting(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setSwitchSetting(IBluetoothResultCallback callback, int switchType, boolean enable, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new SwitchSetting(callback, 3, (byte) 0x01, (byte) switchType, enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
    }

    @Override
    public void getReminderCount(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new RemindCount(callback, 1, 0), commandType, macs);
    }

    @Override
    public void getReminder(IBluetoothResultCallback callback, int reminderCount, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new RemindSetting(callback, 1, 0, reminderCount), commandType, macs);
    }

    @Override
    public void setReminder(IBluetoothResultCallback callback, int type, ReminderBT reminderData, ReminderBT reminderData1, int commandType, String... macs) {
        int len = 6;
        byte[] remindContent = null;
        ReminderBT newReminderData = type == 3 ? new ReminderBT() : reminderData1;
        ReminderBT oldReminderData = new ReminderBT();
        oldReminderData.type = 0;
        oldReminderData.hour = 0;
        oldReminderData.min = 0;
        oldReminderData.cycle = 0;
        oldReminderData.enable = false;

        if (type == 1) {                                                                            // 修改
            len = 11;
            newReminderData = reminderData;
            oldReminderData = reminderData1;
        }
        if (!TextUtils.isEmpty(newReminderData.content)) {
            len += (newReminderData.content.getBytes().length > 24 ? 24 : newReminderData.content.getBytes().length);
            remindContent = new byte[len - type == 1 ? 11 : 6];
            System.arraycopy(newReminderData.content.getBytes(), 0, remindContent, 0, remindContent.length);
        }

        BluetoothSend.addLeafAndSend(new RemindSetting(callback, len, (byte) type,
                (byte) newReminderData.type, (byte) newReminderData.hour, (byte) newReminderData.min, (byte) newReminderData.cycle, newReminderData.enable ? (byte) 0x01 : (byte) 0x00,
                remindContent,
                (byte) oldReminderData.type, (byte) oldReminderData.hour, (byte) oldReminderData.min, (byte) oldReminderData.cycle, oldReminderData.enable ? (byte) 0x01 : (byte) 0x00), commandType, macs);
    }

    @Override
    public void getUID(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BindStart(callback, 1, 0), commandType, macs);
    }

    @Override
    public void setUID(IBluetoothResultCallback callback, int UID, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BindStart(callback, 5, BluetoothCommandConstant.BIND_START_SET_UID, UID), commandType, macs);
    }

    @Override
    public void checkInit(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BindEnd(callback, 1, 0), commandType, macs);
    }

    @Override
    public void bindStart(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BindStart(callback, 1, BluetoothCommandConstant.BIND_START_NO_UID, 0), commandType, macs);
    }

    @Override
    public void bindEnd(IBluetoothResultCallback callback, int commandType, String... macs) {
        BluetoothSend.addLeafAndSend(new BindEnd(callback, 1, (byte) 0x01), commandType, macs);
    }

    @Override
    public void sendSongName(boolean musicState, String songName, int commandType, String... macs) {
        if (BluetoothLeService.deviceMap != null && BluetoothLeService.deviceMap.size() > 0) {
            byte bMusicState = musicState ? BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_PLAY : BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_PAUSE;
            byte[] bOldSongName = songName.getBytes();
            int songLen = bOldSongName.length > BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN ? BluetoothCommandConstant.MSG_PUSH_TYPE_MAX_NAME_LEN : bOldSongName.length;
            byte[] bNewSongName = new byte[songLen];
            System.arraycopy(bOldSongName, 0, bNewSongName, 0, songLen);
            String[] realMacs = macs != null && macs.length > 0 ? macs : (String[]) BluetoothLeService.deviceMap.keySet().toArray();
            BluetoothSend.addLeafAndSend(new Music(bNewSongName.length + 1, bMusicState, bNewSongName), commandType, realMacs);
        }
    }

    @Override
    public void sendStop(int commandType, String... macs) {
        if (BluetoothLeService.deviceMap != null && BluetoothLeService.deviceMap.size() > 0) {
            String[] realMacs = macs != null && macs.length > 0 ? macs : (String[]) BluetoothLeService.deviceMap.keySet().toArray();
            BluetoothSend.addLeafAndSend(new Music(1, BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_STOP, null), commandType, realMacs);
        }
    }

    @Override
    public void sendVolume(int volume, int commandType, String... macs) {
        if (BluetoothLeService.deviceMap != null && BluetoothLeService.deviceMap.size() > 0) {
            String[] realMacs = macs != null && macs.length > 0 ? macs : (String[]) BluetoothLeService.deviceMap.keySet().toArray();
            BluetoothSend.addLeafAndSend(new Music(2, BluetoothCommandConstant.REMOTE_MUSIC_SEND_DEVICE_VOLUME, new byte[]{(byte) volume}), commandType, realMacs);
        }
    }

    @Override
    public BluetoothVar getBluetoothVarByMAC(String mac) {
        if (TextUtils.isEmpty(mac) || BluetoothLeService.deviceMap == null || BluetoothLeService.deviceMap.size() == 0)
            return null;
        AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
        if (appsCommDevice == null || appsCommDevice.bluetoothVar == null) {
            return null;
        }
        return appsCommDevice.bluetoothVar;
    }

    @Override
    public void clearSendCommand(String... macs) {
        if (macs == null || macs.length == 0) return;
        for (String mac : macs) {
            AppsCommDevice appsCommDevice = BluetoothLeService.deviceMap.get(mac);
            if (appsCommDevice != null && appsCommDevice.bluetoothSend != null) {
                appsCommDevice.bluetoothSend.clear();
            }
        }
    }
}
