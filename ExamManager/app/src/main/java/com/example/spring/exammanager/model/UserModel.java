package com.example.spring.exammanager.model;

/**
 * 用户信息
 */
public class UserModel {

    public UserModel() {

    }

    public UserModel(String userName, String passwrod,int userType) {
        this.userName = userName;
        this.passwrod = passwrod;
        this.userType=userType;
    }

    public String userName;
    public String passwrod;
    public int userType;
    public String realName;
    public int sex;
    public String identityNumber;
    public String userJson;
}
