package com.example.vaccinecenter.model;

public class AddCenters {

    private String centerId;
    private String centerName;
    private String centerDoctor;
    private String centerHours;
    private String centerAddress;
    private String centerPhoneNo;

    public AddCenters() {
    }

    public AddCenters(String centerId, String centerName, String centerDoctor, String centerHours, String centerAddress, String centerPhoneNo) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.centerDoctor = centerDoctor;
        this.centerHours = centerHours;
        this.centerAddress = centerAddress;
        this.centerPhoneNo = centerPhoneNo;
    }

        public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterDoctor() {
        return centerDoctor;
    }

    public void setCenterDoctor(String centerDoctor) {
        this.centerDoctor = centerDoctor;
    }

    public String getCenterHours() {
        return centerHours;
    }

    public void setCenterHours(String centerHours) {
        this.centerHours = centerHours;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterPhoneNo() {
        return centerPhoneNo;
    }

    public void setCenterPhoneNo(String centerPhoneNo) {
        this.centerPhoneNo = centerPhoneNo;
    }
}
