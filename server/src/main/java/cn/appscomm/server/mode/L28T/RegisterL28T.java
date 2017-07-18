package cn.appscomm.server.mode.L28T;

import cn.appscomm.server.constant.Urls;
import cn.appscomm.server.mode.base.BaseRequest;

/**
 * Created by Administrator on 2017/3/1.
 */
public class RegisterL28T {
    public static final int ACCOUNT_TYPE_EMAIL = 111;
    public static final int ACCOUNT_TYPE_PHONE = 222;

    public String userName;
    public String email;
    public String password;
    public int gender;
    public String birthDay;
    public String height;
    public String weight;
    public String heightUnit;
    public String weightUnit;
    public String countryCode="0";
    public String imgUrl;
    public String encryptMode="1";

//    paramsList=[
// userName=rrr, email=123@123.com, password=e10adc3949ba59abbe56e057f20f883e, gender=0,
// birthDay=2017-6-28, height=36, weight=70, heightUnit=1, weightUnit=1, countryCode=0, imgUrl=, encryptMode=1]

//{"birthDay":"2013-01-04","countryCode":"0","email":"258@qq.com","encryptMode":"1","gender":1,"height":"167.6"
// ,"heightUnit":"1","imgUrl":"","password":"e10adc3949ba59abbe56e057f20f883e","userName":"66553","weight":"39.8","weightUnit":"1"}
    public RegisterL28T(String userName, String email, String password, int gender, String birthDay
            , String height, String weight, String heightUnit, String weightUnit, String imgUrl) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDay = birthDay;
        this.height = height;
        this.weight = weight;
        this.heightUnit = heightUnit;
        this.weightUnit = weightUnit;
        this.imgUrl = imgUrl;
    }
}
