package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/6/29.
 */

public class LoginMode extends CallBackBaseMode {
    /**
     * SJ={"result":"0","message":"The success of the operation","servertime":"1498730100"
     * ,"data":{"id":34834,"userId":34607,"nickName":"1212","password":"e10adc3949ba59abbe56e057f20f883e"
     * ,"mail":"1212@qq.com","telphone":"","registerTime":"2017-04-11 09:33:10"
     * ,"isActive":1,"account":null,"view":null,"watchId":null,"userName":"1212"
     * ,"birthDate":"2017-04-11","countryId":0,"provinceId":0,"cityId":0,"areaId":0
     * ,"gender":0,"isBind":0,"height":"36.0","heightUnit":1,"weight":70.0,"weightUnit":1
     * ,"imgUrl":"null","sleepTarget":"8","advancedSettring":{"sid":null,"personId":34607,"watchId":null
     * ,"timeMode":"1","lengthUnit":"0","weightUnit":"0"},"kronoz_token":null,"customer":null}}
     */
    /**
     * String id = json.getString("id");
     String userId = json.getString("userId");
     String password = json.getString("password");
     String nickName = json.getString("nickName");
     String mail = json.getString("mail");
     String userName = json.getString("userName");
     String birthDate = json.getString("birthDate");
     String gender = json.getString("gender");
     String isBind = json.getString("isBind");
     String watchId = json.getString("watchId");
     String height = json.getString("height");
     String weight = json.getString("weight");
     String imgUrl = json.getString("imgUrl");  //头像
     int countryCode = json.getInt("countryId");
     */
    LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public class LoginData{
        int id;
        int userId;
        String nickName;
        String password;
        String mail;
        String userName;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "LoginData{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", nickName='" + nickName + '\'' +
                    ", password='" + password + '\'' +
                    ", mail='" + mail + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginMode{" +
                "data=" + data +
                '}';
    }
}
