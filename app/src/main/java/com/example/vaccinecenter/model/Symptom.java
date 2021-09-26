package com.example.vaccinecenter.model;

public class Symptom {

    private String symptomId;
    private String symptomName;
    private String sDescription;
    private String sRarity;
    private String sEffectedHours;
    private String sPercentage;
    private String sUrl;

    public Symptom() {
    }

    public Symptom(String symptomId, String symptomName, String sDescription, String sRarity, String sEffectedHours, String sPercentage, String sUrl) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
        this.sDescription = sDescription;
        this.sRarity = sRarity;
        this.sEffectedHours = sEffectedHours;
        this.sPercentage = sPercentage;
        this.sUrl = sUrl;
    }



    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsRarity() {
        return sRarity;
    }

    public void setsRarity(String sRarity) {
        this.sRarity = sRarity;
    }

    public String getsEffectedHours() {
        return sEffectedHours;
    }

    public void setsEffectedHours(String sEffectedHours) {
        this.sEffectedHours = sEffectedHours;
    }

    public String getsPercentage() {
        return sPercentage;
    }

    public void setsPercentage(String sPercentage) {
        this.sPercentage = sPercentage;
    }

    public String getsUrl() { return sUrl; }

    public void setsUrl(String sUrl) { this.sUrl = sUrl; }
}


