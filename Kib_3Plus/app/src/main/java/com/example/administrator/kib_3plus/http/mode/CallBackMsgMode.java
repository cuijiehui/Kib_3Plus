package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/6/29.
 */

public class CallBackMsgMode extends CallBackBaseMode {
//    {"result":"9999","message":"program exception","servertime":"1498717522","data":{}}
    Datas data;
    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }

    public class Datas{
        int id;
        int userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "Datas{" +
                    "id=" + id +
                    ", userId=" + userId +
                    '}';
        }
    }


}
