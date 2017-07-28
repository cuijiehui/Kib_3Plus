package com.example.administrator.kib_3plus.mode;

/**
 * Created by cui on 2017/7/5.
 */

public class BandSettingsMode {
    private int uId;
    private String name;
    private int icon;
    private boolean isIcon;
    private int favorite;
    private String url;
    private boolean isCache;
    private String mac;

    public BandSettingsMode(int uId, String name, int icon, boolean isIcon, int favorite, String url, boolean isCache, String mac) {
        this.uId = uId;
        this.name = name;
        this.icon = icon;
        this.isIcon = isIcon;
        this.favorite = favorite;
        this.url = url;
        this.isCache = isCache;
        this.mac = mac;
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

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }
}
