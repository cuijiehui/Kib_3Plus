package cn.appscomm.db.mode;

import org.litepal.crud.DataSupport;

/**
 * 睡眠数据模型（包含所有未上传的和已上传的）
 * 一笔睡眠数据为一条记录，所以一天可能有多笔数据
 * 已上传的睡眠flag可以为服务器给予的ID或其他
 * 未上传的睡眠flag为-1
 * 睡眠类型：0x00：睡着， 0x01：浅睡， 0x02：醒着，0x03：准备入睡，0x10（16）：进入睡眠模式；0x11（17）：退出睡眠模式
 */
public class SleepL28TDB extends DataSupport {

    private int id;
    private int uId;
    private int total;                                                                              // 总睡眠
    private int deep;                                                                               // 深睡
    private int light;                                                                              // 浅睡
    private int awake;                                                                              // 清醒
    private int sleep;                                                                              // 睡眠总时间(浅睡+深睡)
    private int awakeTime;                                                                          // 清醒次数
    private String detail;                                                                          // 一笔睡眠的详情，如：2016-07-28 07:15:17&BEGIN,2016-07-28 07:16:17&DEEP,2016-07-28 07:17:17&END,
    private String date;                                                                            // 日期，如：2016-08-08
    private int flag;                                                                               // -1：未上传 >0：已上传

    public SleepL28TDB(int uId, int total, int deep, int light, int awake, int sleep, int awakeTime, String detail, String date, int flag) {
        this.uId = uId;
        this.total = total;
        this.deep = deep;
        this.light = light;
        this.awake = awake;
        this.sleep = sleep;
        this.awakeTime = awakeTime;
        this.detail = detail;
        this.date = date;
        this.flag = flag;
    }

    public SleepL28TDB() {
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(int awakeTime) {
        this.awakeTime = awakeTime;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
