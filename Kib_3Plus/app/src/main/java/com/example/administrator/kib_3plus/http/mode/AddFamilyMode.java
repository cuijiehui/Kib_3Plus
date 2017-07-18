package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/6/29.
 */

public class AddFamilyMode extends  CallBackBaseMode {

    AddFamilyData data;

    public AddFamilyData getData() {
        return data;
    }

    public void setData(AddFamilyData data) {
        this.data = data;
    }

    /**
     * {
     "result":"0",
     "message":"Add family successful",
     "data":{
     "id":2
     "name":"test"
     "personMail":"test@qq.com"
     "createTime":"2017-06-22 10:10:10"
     "updateTime":""
     }
     result":"0",
     "message":"Get family successful",
     "data":{
     "id":2,
     "name":"test",
     "personMail":"test@qq.com",
     "createTime":"2017-06-22 10:10:10",
     "updateTime":""
     }

     */
    public class AddFamilyData{
        int id;
        String name;
        String personMail;
        String  createTime;
        String  updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersonMail() {
            return personMail;
        }

        public void setPersonMail(String personMail) {
            this.personMail = personMail;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
