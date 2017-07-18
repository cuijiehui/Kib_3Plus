package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * Created by cui on 2017/7/3.
 */

public class RewardsL28TDB extends DataSupport {
    private int id;
    private int uId;//用户id
    private String name;//奖励名
    private int choresId;//任务id。用于在任务列表中识别
    private int gold;//金币消耗
    private int icon;//
    private String date;//1111111
    private boolean isRedeem;//是否兑换

    public RewardsL28TDB(int uId, String name, int choresId, int gold, int icon, String date, boolean isRedeem) {
        this.uId = uId;
        this.name = name;
        this.choresId = choresId;
        this.gold = gold;
        this.icon = icon;
        this.date = date;
        this.isRedeem = isRedeem;
    }

    @Override
    public String toString() {
        return "RewardsL28TDB{" +
                "id=" + id +
                ", uId=" + uId +
                ", name='" + name + '\'' +
                ", choresId=" + choresId +
                ", gold=" + gold +
                ", icon=" + icon +
                ", date='" + date + '\'' +
                ", isRedeem=" + isRedeem +
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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



    public boolean isRedeem() {
        return isRedeem;
    }

    public void setRedeem(boolean redeem) {
        isRedeem = redeem;
    }
}
