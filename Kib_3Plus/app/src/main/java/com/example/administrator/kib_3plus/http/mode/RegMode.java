package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/6/29.
 */

public class RegMode  {
    String userName;
    String email;
    String password;
    String gender;
    String birthDay;
    String height;
    String weight;
    String heightUnit;
    String weightUnit;
    String countryCode;
    String imgUrl;
    String encryptMode;

    public RegMode(String userName, String email, String password, String gender, String birthDay, String height, String weight, String heightUnit, String weightUnit, String countryCode, String imgUrl, String encryptMode) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDay = birthDay;
        this.height = height;
        this.weight = weight;
        this.heightUnit = heightUnit;
        this.weightUnit = weightUnit;
        this.countryCode = countryCode;
        this.imgUrl = imgUrl;
        this.encryptMode = encryptMode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEncryptMode() {
        return encryptMode;
    }

    public void setEncryptMode(String encryptMode) {
        this.encryptMode = encryptMode;
    }
}
