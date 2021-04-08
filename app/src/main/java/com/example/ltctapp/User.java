package com.example.ltctapp;

public class User {
    public String userName;
    public String userID;
    public String emailID;

    public boolean readAccess;
    public boolean adminMode;

    public User(String userName, String emailID,String userID, boolean readAccess, boolean adminMode){
        this.userName = userName;
        this.userID = userID;
        this.readAccess = readAccess;
        this.adminMode = adminMode;
        this.emailID = emailID;


    }
    public User(){}

}
