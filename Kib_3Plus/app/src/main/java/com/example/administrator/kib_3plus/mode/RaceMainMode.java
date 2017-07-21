package com.example.administrator.kib_3plus.mode;

import static android.R.attr.name;

/**
 * Created by cui on 2017/7/21.
 */

public class RaceMainMode {

    private int uId;
    private String name;
    private String iconUrl;
    private boolean isClick;

    public RaceMainMode(int uId, String name, String iconUrl, boolean isClick) {
        this.uId = uId;
        this.name = name;
        this.iconUrl = iconUrl;
        this.isClick = isClick;
    }


    @Override
    public String toString() {
        return "RaceMainMode{" +
                "uId=" + uId +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", isClick=" + isClick +
                '}';
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
