package com.example.administrator.kib_3plus.http.mode;

import java.io.Serializable;

/**
 * Created by cui on 2017/7/1.
 */

public class  AddMenberMode  extends CallBackBaseMode implements Serializable{
    /**
     *


     stringToList={"result":0
     ,"message":"Add children successful"
     ,"servertime":1499139624
     ,"data":{"id":7
     ,"familyId":4
     ,"name":"Cbjc"
     ,"gender":1
     ,"age":1
     ,"height":"5'6\""
     ,"weight":"88.0"
     ,"brithday":"7 / 4 / 2017"
     ,"favorite":"red"
     ,"url":""
     ,"createTime":"2017-07-04 03:40:24"
     ,"updateTime":null}}
     */
    MenberData data;

    public MenberData getData() {
        return data;
    }

    public void setData(MenberData data) {
        this.data = data;
    }

    public static class MenberData implements Serializable{
        int id;
        int familyId;
        String name;
        String gender;
        int age;
        String height;
        String weight;
        String brithday;
        String favorite;
        String url;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MenberData{" +
                    "id=" + id +
                    ", familyId=" + familyId +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", age=" + age +
                    ", height='" + height + '\'' +
                    ", weight='" + weight + '\'' +
                    ", brithday='" + brithday + '\'' +
                    ", favorite='" + favorite + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AddMenberMode{" +
                "data=" + data.toString() +
                '}';
    }
}
