package com.example.vaccinecenter.model;

public class Profile {

    private String userId;

    private String Email;

    private String UserName;

    private String phoneNo;

    private String address;

    public Profile() {
    }

    public Profile(String userId, String email, String userName, String phoneNo, String address) {
        this.userId = userId;
        Email = email;
        UserName = userName;
        this.phoneNo = phoneNo;
        this.address = address;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
