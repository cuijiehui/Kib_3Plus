package com.example.administrator.kib_3plus.http.mode;

/**
 * Created by cui on 2017/7/4.
 */

public class AddMenberJsonMode  {
//    map.put("familyId",familyID+"");
//    map.put("name",add_name_et.getText().toString().trim());
//    map.put("gender",add_gender_tv.getText().toString().trim());
//    map.put("age",add_age_tv.getText().toString().trim());
//    map.put("height",add_height_tv.getText().toString().trim());
//    map.put("weight",add_weigh_tv.getText().toString().trim());
//    map.put("brithday",add_birthday_tv.getText().toString().trim());
//    map.put("favorite","red");
//    map.put("url","");

    private String familyId;
    private String name;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String brithday;
    private String favorite;
    private String url;


    public AddMenberJsonMode(String familyId, String name, String gender, String age, String height, String weight, String brithday, String favorite, String url) {

        this.familyId = familyId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.brithday = brithday;
        this.favorite = favorite;
        this.url = url;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
