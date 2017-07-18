package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by cui on 2017/7/3.
 */

public class SpoetL28TDB  extends DataSupport {
    private int id;
    private int uId;//用户id
    private String name;//
    private String mac;//
    private int activity;
    private int chores;
    private int sleep;
    private long sportTime;
    private String date;                                                                            // 日期，格式如：2016-08-08

    public SpoetL28TDB() {
    }

    public SpoetL28TDB(int uId, String name, String mac, int activity, int chores, int sleep, long sportTime, String date) {
        this.uId = uId;
        this.name = name;
        this.mac = mac;
        this.activity = activity;
        this.chores = chores;
        this.sleep = sleep;
        this.sportTime = sportTime;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SpoetL28TDB{" +
                "id=" + id +
                ", uId=" + uId +
                ", name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", activity=" + activity +
                ", chores=" + chores +
                ", sportTime=" + sportTime +
                ", date='" + date + '\'' +
                '}';
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
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

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getChores() {
        return chores;
    }

    public void setChores(int chores) {
        this.chores = chores;
    }

    public long getSportTime() {
        return sportTime;
    }

    public void setSportTime(long sportTime) {
        this.sportTime = sportTime;
    }
}
