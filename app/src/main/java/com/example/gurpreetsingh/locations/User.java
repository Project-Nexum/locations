package com.example.gurpreetsingh.locations;

public class User {


    public String name;
    public String address;
    public double latitude;
    public double longitude;

    public User(){

    }
    public User(String name , String address , double latitude , double longitude){
        this.name = name;
        this.address=address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
