package org;

public class AdoptItem {

    String title;
    String dogType;
    String dogName;
    String gender;
    String dogPrice;
    String dogImg;
    String location;
    String adopt_id;


    String getTitle(){
        return this.title;
    }

    String getDogType(){
        return this.dogType;
    }

    String getDogName(){
        return this.dogName;
    }

    String getGender(){
        return this.gender;
    }

    String getDogPrice(){
        return this.dogPrice;
    }

    String getDogImg(){
        return this.dogImg;
    }

    String getLocation(){
        return this.location;
    }

    String getAdopt_id(){
        return this.adopt_id;
    }

    AdoptItem(String adopt_id, String title, String dogType, String dogName, String gender, String dogPrice, String location, String dogImg){

        this.adopt_id=adopt_id;
        this.title=title;
        this.dogType=dogType;
        this.dogName=dogName;
        this.gender=gender;
        this.dogPrice=dogPrice;
        this.dogImg=dogImg;
        this.location=location;
    }

}
