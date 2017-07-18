package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * Created by cui on 2017/7/6.
 */

public class BandSettingDB extends DataSupport {
    private int id;
    private String mac;
    private boolean sync; //默认为false
    private String units;//默认为1 1.英制 0.公制
    private String vibration;//默认为standard
    private String tiem;//默认时间格式。12.    24
    private boolean sleep;//默认为false
    private int bedTime;//默认 645 分钟
    private int awakeTime;//默认 360 分钟
    private boolean inactivity;//默认为false
    private int interval;//90分钟
    private int start;//510分钟
    private int end;//465
    private int steps;//100
    private String frequency;//0000000

    public BandSettingDB(String mac, boolean sync, String units, String vibration, String tiem, boolean sleep, int bedTime, int awakeTime, boolean inactivity, int interval, int start, int end, int steps, String frequency) {

        this.mac = mac;
        this.sync = sync;
        this.units = units;
        this.vibration = vibration;
        this.tiem = tiem;
        this.sleep = sleep;
        this.bedTime = bedTime;
        this.awakeTime = awakeTime;
        this.inactivity = inactivity;
        this.interval = interval;
        this.start = start;
        this.end = end;
        this.steps = steps;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getVibration() {
        return vibration;
    }

    public void setVibration(String vibration) {
        this.vibration = vibration;
    }

    public String getTiem() {
        return tiem;
    }

    public void setTiem(String tiem) {
        this.tiem = tiem;
    }

    public boolean isSleep() {
        return sleep;
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    public int getBedTime() {
        return bedTime;
    }

    public void setBedTime(int bedTime) {
        this.bedTime = bedTime;
    }

    public int getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(int awakeTime) {
        this.awakeTime = awakeTime;
    }

    public boolean isInactivity() {
        return inactivity;
    }

    public void setInactivity(boolean inactivity) {
        this.inactivity = inactivity;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
