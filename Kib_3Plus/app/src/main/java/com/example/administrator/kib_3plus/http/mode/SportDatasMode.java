package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/7/17.
 */

public class SportDatasMode {
    /**
     * {
     "familyId”: "1",
     "childrenId":"1",
     "watchId":"123456789",
     customer":"Lite.JR",
     "values":[{"startTime":"2017-06-22 10:11:12","endTime":"2017-06-22 10:13:12","steps":"1234","calorie":"123"},
     {"startTime":"2017-06-22 11:15:12","endTime":"2017-06-22 11:18:12","steps":"6452","calorie":"1234"}]
     }

     */
    private int familyId;
    private int childrenId;
    private int watchId;
    private String customer;
    private value values;
    public static class value{
        private String startTime;
        private String endTime;
        private String steps;
        private String calorie;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getSteps() {
            return steps;
        }

        public void setSteps(String steps) {
            this.steps = steps;
        }

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }
    }

    public SportDatasMode(int familyId, int childrenId, int watchId, String customer, value values) {
        this.familyId = familyId;
        this.childrenId = childrenId;
        this.watchId = watchId;
        this.customer = customer;
        this.values = values;
    }

    @Override
    public String toString() {
        return "SportDatasMode{" +
                "familyId=" + familyId +
                ", childrenId=" + childrenId +
                ", watchId=" + watchId +
                ", customer='" + customer + '\'' +
                ", values=" + values +
                '}';
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(int childrenId) {
        this.childrenId = childrenId;
    }

    public int getWatchId() {
        return watchId;
    }

    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public value getValues() {
        return values;
    }

    public void setValues(value values) {
        this.values = values;
    }
}
