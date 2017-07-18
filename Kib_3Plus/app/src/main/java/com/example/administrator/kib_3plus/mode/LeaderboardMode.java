package com.example.administrator.kib_3plus.mode;

/**
 * Created by cui on 2017/7/5.
 */

public class LeaderboardMode  {
    int uId;
    private   String name;
    private int step;
    private long time;
    private String url;
    private boolean isCache;//如果使用的是本地的话就直接使用本地不是就去网络加载

    public LeaderboardMode(int uId, String name, int step, long time, String url, boolean isCache) {
        this.uId = uId;
        this.name = name;
        this.step = step;
        this.time = time;
        this.url = url;
        this.isCache = isCache;
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
