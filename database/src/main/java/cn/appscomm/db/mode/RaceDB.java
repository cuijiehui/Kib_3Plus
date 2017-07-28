package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * Created by cui on 2017/7/24.
 */

public class RaceDB extends DataSupport {
    private int id ;
    private int uId;
    private int step;
    private int goal;
    private int icon;
    private int bgIcon;
    private boolean isIcon;
    private int favorite;
    private String url;


    public RaceDB(int uId, int step, int goal, int icon, int bgIcon, boolean isIcon, int favorite, String url) {
        this.uId = uId;
        this.step = step;
        this.goal = goal;
        this.icon = icon;
        this.bgIcon = bgIcon;
        this.isIcon = isIcon;
        this.favorite = favorite;
        this.url = url;
    }

    @Override
    public String toString() {
        return "RaceDB{" +
                "id=" + id +
                ", uId=" + uId +
                ", step=" + step +
                ", goal=" + goal +
                ", icon=" + icon +
                ", bgIcon=" + bgIcon +
                ", isIcon=" + isIcon +
                ", favorite=" + favorite +
                ", url='" + url + '\'' +
                '}';
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getBgIcon() {
        return bgIcon;
    }

    public void setBgIcon(int bgIcon) {
        this.bgIcon = bgIcon;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
