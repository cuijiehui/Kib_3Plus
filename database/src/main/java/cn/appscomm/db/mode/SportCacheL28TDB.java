package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * 运动数据缓存模型：
 * 从设备上获取到的运动数据，直接存储到该模型
 * 需要上传运动数据到服务器时，则上传该数据库，上传成功后清空
 * 每个成员变量都是按照蓝牙协议来设计
 */
public class SportCacheL28TDB extends DataSupport {
    private int id;
    private int uId;
    private int step;                                                                               // 步数
    private int calories;                                                                           // 卡路里
    private long timeStamp;                                                                         // 时间戳

    public SportCacheL28TDB(int step, int calories, long timeStamp) {
        this.step = step;
        this.calories = calories;
        this.timeStamp = timeStamp;
    }

    public SportCacheL28TDB(int uId, int step, int calories, long timeStamp) {
        this.uId = uId;
        this.step = step;
        this.calories = calories;
        this.timeStamp = timeStamp;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "SportCacheDB{" +
                "id=" + id +
                ", step=" + step +
                ", calories=" + calories +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
