package cn.appscomm.server.mode.L28T;

/**
 * Created by cui on 2017/6/28.
 */

public class LoginL28T {
    //paramsList=[account=123@123.com, password=e10adc3949ba59abbe56e057f20f883e, encryptMode=1]
    public String account;
    public String password;
    public String encryptMode="1";

    public LoginL28T(String password, String account) {
        this.password = password;
        this.account = account;
    }
}
