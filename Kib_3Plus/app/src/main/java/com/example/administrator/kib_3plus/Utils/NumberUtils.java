package com.example.administrator.kib_3plus.Utils;

import android.content.Context;

import com.example.administrator.kib_3plus.R;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.lang.Integer.parseInt;

/**
 * Created by cui on 2017/6/19.
 */

public enum NumberUtils {
    INSTANCE;

    private Context mContext;
    public void init(Context context){
        mContext=context;
    }
    private  String hexStr = "0123456789ABCDEF";
    private  NumberUtils numberUtils;

    public int dip2px( float dipValue) {
        if(mContext!=null){

            final float scale = mContext.getResources().getDisplayMetrics().density;
            LogUtils.i("scale="+scale);
            return (int) (dipValue * scale + 0.5f);
        }else {
            return 0;
        }
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public  int px2dip( float pxValue) {
        if(mContext!=null) {
            final float scale = mContext.getResources().getDisplayMetrics().density;
            LogUtils.i("scale="+scale);
            LogUtils.i("pxValue / scale + 0.5f="+pxValue / scale + 0.5f);
            LogUtils.i("int="+(int)(pxValue / scale + 0.5f));
            return (int) (pxValue / scale + 0.5f);
        }else{
            return 0;

        }
    }
    public int getFavorite(String favorite){
        int intFavorite=-1;
        if(favorite.equals(mContext.getString(R.string.like_orange))){
            intFavorite=R.color.like_orange;
        } else if(favorite.equals(mContext.getString(R.string.like_blue))){
            intFavorite=R.color.like_blue;

        }else if(favorite.equals(mContext.getString(R.string.like_violet))){
            intFavorite=R.color.like_violet;

        }else if(favorite.equals(mContext.getString(R.string.like_mazarine))){
            intFavorite=R.color.like_mazarine;

        }else if(favorite.equals(mContext.getString(R.string.like_green))){
            intFavorite=R.color.like_green;

        }else if(favorite.equals(mContext.getString(R.string.like_yellow))){
            intFavorite=R.color.like_yellow;

        }else if(favorite.equals(mContext.getString(R.string.like_red))){
            intFavorite=R.color.like_red;
        }
        return intFavorite;
    }
    /**
     * @param date
     * @return 返回当前年String格式：yyyy
     */
    public  String getCurYear(Date date) {

        return formatDate2(new Date()).substring(0, 4);
    }

    /**
     * @param date
     * @return 返回String格式：yyyy-MM-dd
     */
    public  String formatDate2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
    /**
     * 获得当前年份所在的月份
     *
     * @param date
     * @return MM
     */
    public  String getCurMonthOfYear(Date date) {
        return formatDate2(new Date()).substring(5, 7);
    }
    /**
     * 获得当前月份所在的天
     *
     * @param date
     * @return dd
     */
    public  String getCurDayOfMonth(Date date) {
        return formatDate2(new Date()).substring(8, 10);
    }
    //1尺=12 寸、1寸=2.54cm
    public  double ftToCm(String ftNum){
       int cm= Integer.parseInt(ftNum.split("'")[0])*12;
        int indexS=ftNum.indexOf("'");
        int indexN=ftNum.indexOf("\"");
        LogUtils.i("indexS="+indexS+"--indexN="+indexN+"--cm="+cm);
        cm=Integer.parseInt(ftNum.substring(indexS+1,indexN))+cm;
        LogUtils.i("cm="+cm);
        return cm*2.54;
    }
    public  String ftToCmLeft(String ftNum){
        int cm= Integer.parseInt(ftNum.split("'")[0])*12;
        int indexS=ftNum.indexOf("'");
        int indexN=ftNum.indexOf("\"");
        LogUtils.i("indexS="+indexS+"--indexN="+indexN+"--cm="+cm);
        cm=Integer.parseInt(ftNum.substring(indexS+1,indexN))+cm;
        LogUtils.i("cm="+cm);
        String leftCm=((int)(cm*2.54))+"";
        return leftCm;
    }
    public  String ftToCmRight(String ftNum){
        int cm= Integer.parseInt(ftNum.split("'")[0])*12;
        int indexS=ftNum.indexOf("'");
        int indexN=ftNum.indexOf("\"");
        LogUtils.i("indexS="+indexS+"--indexN="+indexN+"--cm="+cm);
        cm=Integer.parseInt(ftNum.substring(indexS+1,indexN))+cm;
        LogUtils.i("cm="+cm);
        String rightCm=(cm*2.54)+"";
        int index=rightCm.indexOf(".");
        rightCm= rightCm.substring(index,index+1);
        return rightCm;
    }
    public  String cmToFt(String cmNum){
        double cm=new Double(cmNum);
        int ft= (int)(double)(cm * 0.3937008);
        int a1 = ft/12;
        int a2 = ft%12;
        String ftt=a1 + "'" + a2 + "\" ";
        return ftt;
    }
    public  String cmToFtRight(String cmNum){
        double cm=new Double(cmNum);

        int ft= (int)(double)(cm * 0.3937008);
        int a2 = ft%12;
        String ftt= a2 + "\" ";
        return ftt;
    }
    public  String cmToFtLeft(String cmNum){
        double cm=new Double(cmNum);

        int ft= (int)(double)(cm * 0.3937008);
        int a1 = ft/12;
        String ftt=a1 + "'" ;
        return ftt;
    }

    public  double lbsToKg(String lbsNum){
        double lbss=new Double(lbsNum);
        double lbs= (double)(lbss * 0.4532);
        return lbs;
    }
    public  String lbsToKgLeft(String lbsNum){
        double lbss=new Double(lbsNum);
        double lbs= (double)(lbss * 0.4532);
        int index=(lbs+"").indexOf(".");
        String leftLbs=(lbs+"").substring(0,index);
        return leftLbs;
    }
    public  String lbsToKgRight(String lbsNum){
        double lbss=new Double(lbsNum);
        double lbs= (double)(lbss * 0.4532);
        int index=(lbs+"").indexOf(".");
        String leftLbs=(lbs+"").substring(index,index+1);
        return leftLbs;
    }

    public  double kgToLbs(String kgNum){
        double kgg=new Double(kgNum);
        double kg= (int)(double)(kgg * 2.2065);
        return kg;
    }
    public  String kgToLbsLeft(String kgNum){
        double kgg=new Double(kgNum);
        double kg= (int)(double)(kgg * 2.2065);
        int index=(kg+"").indexOf(".");
        String leftKg=(kg+"").substring(0,index);
        return leftKg;
    }
    public  String kgToLbsRight(String kgNum){
        double kgg=new Double(kgNum);
        double kg= (int)(double)(kgg * 2.2065);
        int index=(kg+"").indexOf(".");
        String rightKg=(kg+"").substring(index,index+1);
        return rightKg;
    }

    /* MD5进行密码加密*/
    public  String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
    // int 类型转byte[] 2进制数组
    public  byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
    /**
     * int转换到byte[]
     *
     * @param integer  需要转换的int
     * @param byteSize byte[]数组的大小
     * @return 转换后的byte[]
     */
    public  byte[] intToByteArray(final int integer, int byteSize) {
        byte[] bytes = new byte[byteSize];
        for (int i = 0; i < byteSize; i++) {
            bytes[i] = (byte) ((integer >> (8 * i)) & 0xFF);
        }
        return bytes;
    }
    /**
     * @param bytes
     * @return 将二进制转换为十六进制字符输出
     * new byte[]{0b01111111}-->"7F" ;  new byte[]{0x2F}-->"2F"
     */
    public  String binaryToHexString(byte[] bytes) {

        String result = "";
        if (bytes == null) {
            return result;
        }
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex + " ";
        }
        return result;
    }
}
