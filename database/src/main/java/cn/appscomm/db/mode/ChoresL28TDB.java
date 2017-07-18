package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

import static android.R.attr.name;

/**
 * Created by cui on 2017/7/3.
 */

public class ChoresL28TDB extends DataSupport {
    private int id;
    private int uId;//用户id
    private String name;//任务名
    private int choresId;//任务id。用于在任务列表中识别
    private int gold;//奖励的金币
    private int icon;//图片 资源id
    private String date;//1111111日期
    private boolean isFinish;//是否完成

    public ChoresL28TDB(int uId, String name, int choresId, int gold, int icon, String date, boolean isFinish) {
        this.uId = uId;
        this.name = name;
        this.choresId = choresId;
        this.gold = gold;
        this.icon = icon;
        this.date = date;
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        return "ChoresL28TDB{" +
                "id=" + id +
                ", uId=" + uId +
                ", name='" + name + '\'' +
                ", choresId=" + choresId +
                ", gold=" + gold +
                ", icon=" + icon +
                ", date='" + date + '\'' +
                ", isFinish=" + isFinish +
                '}';
    }

    public int getChoresId() {
        return choresId;
    }

    public void setChoresId(int choresId) {
        this.choresId = choresId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
