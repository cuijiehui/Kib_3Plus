package cn.appscomm.bluetooth.mode;

/**
 * Created by cui on 2017/6/29.
 */

public class SportBTL28T {
    public int index;
    public int step;                                                                                // 步数
    public int calories;                                                                            // 卡路里
    public long timeStamp;                                                                          // 时间戳

    public SportBTL28T(int index, int step, int calories, long timeStamp) {
        this.index = index;
        this.step = step;
        this.calories = calories;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "SportBTL28T{" +
                "index=" + index +
                ", step=" + step +
                ", calories=" + calories +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
