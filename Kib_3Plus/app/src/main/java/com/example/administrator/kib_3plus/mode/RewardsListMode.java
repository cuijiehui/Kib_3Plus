package com.example.administrator.kib_3plus.mode;

/**
 * Created by cui on 2017/7/15.
 */

public class RewardsListMode {
    private String name;
    private int choresId;
    private String date;
    private int icon;
    private int gold;//奖励的金币

    public RewardsListMode(String name, int choresId, String date, int icon, int gold) {
        this.name = name;
        this.choresId = choresId;
        this.date = date;
        this.icon = icon;
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "ChoresListMode{" +
                "name='" + name + '\'' +
                ", choresId=" + choresId +
                ", date='" + date + '\'' +
                ", icon=" + icon +
                ", gold=" + gold +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
