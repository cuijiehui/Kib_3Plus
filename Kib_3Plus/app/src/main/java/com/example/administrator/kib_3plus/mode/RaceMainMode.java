package com.example.administrator.kib_3plus.mode;

/**
 * Created by cui on 2017/7/21.
 */

public class RaceMainMode {

    private int uId;
    private String name;
    private int icon;
    private boolean isIcon;
    private String iconUrl;
    private int favorite;
    private boolean isClick;

    public RaceMainMode(int uId, String name, int icon, boolean isIcon, String iconUrl, int favorite, boolean isClick) {
        this.uId = uId;
        this.name = name;
        this.icon = icon;
        this.isIcon = isIcon;
        this.iconUrl = iconUrl;
        this.favorite = favorite;
        this.isClick = isClick;
    }

    @Override
    public String toString() {
        return "RaceMainMode{" +
                "uId=" + uId +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", isIcon=" + isIcon +
                ", iconUrl='" + iconUrl + '\'' +
                ", favorite=" + favorite +
                ", isClick=" + isClick +
                '}';
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isIcon() {
        return isIcon;
    }

    public void setIcon(boolean icon) {
        isIcon = icon;
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
