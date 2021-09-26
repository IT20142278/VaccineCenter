package com.example.vaccinecenter.model;

public class ApplyVaccine {

    private String applyID;
    private String nic;
    private String does;
    private String address;
    private String district;
    private String gramaDivision;
    private String age;
    private String occupation;
    private String gender;

    public ApplyVaccine() {
    }

    public ApplyVaccine(String applyID, String nic, String does, String address, String district, String gramaDivision, String age, String occupation, String gender) {
        this.applyID = applyID;
        this.nic = nic;
        this.does = does;
        this.address = address;
        this.district = district;
        this.gramaDivision = gramaDivision;
        this.age = age;
        this.occupation = occupation;
        this.gender = gender;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDoes() {
        return does;
    }

    public void setDoes(String does) {
        this.does = does;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGramaDivision() {
        return gramaDivision;
    }

    public void setGramaDivision(String gramaDivision) {
        this.gramaDivision = gramaDivision;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
