package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by cui on 2017/7/1.
 */

public class ChildInfoDB extends DataSupport {

    private int id;
    private int uId;
    private int familyId;
    private String name;
    private  String gender ;
    private int age;
    private String height;
    private String weight;
    private String brithday;
    private String favorite;
    private String iconUrl;
    private int icon;
    private boolean isIcon;
    private String mac;
    private int goldCount;
    public ChildInfoDB() {
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }


    public ChildInfoDB(int uId, int familyId, String name, String gender, int age, String height, String weight
            , String brithday, String favorite, String iconUrl, int icon, boolean isIcon, String mac, int goldCount) {
        this.uId = uId;
        this.familyId = familyId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.brithday = brithday;
        this.favorite = favorite;
        this.iconUrl = iconUrl;
        this.icon = icon;
        this.isIcon = isIcon;
        this.mac = mac;
        this.goldCount = goldCount;
    }
    @Override
    public String toString() {
        return "ChildInfoDB{" +
                "id=" + id +
                ", uId=" + uId +
                ", familyId=" + familyId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", brithday='" + brithday + '\'' +
                ", favorite='" + favorite + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", icon=" + icon +
                ", isIcon=" + isIcon +
                ", mac='" + mac + '\'' +
                ", goldCount=" + goldCount +
                '}';
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

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
