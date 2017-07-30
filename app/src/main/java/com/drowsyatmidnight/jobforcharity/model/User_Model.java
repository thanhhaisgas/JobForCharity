package com.drowsyatmidnight.jobforcharity.model;

/**
 * Created by haint on 23/07/2017.
 */

public class User_Model {
    private String FName;
    private String LName;
    private String mobilePhone;
    private String Email;
    private String Uid;
    private String passWord;
    private String rate;
    private String countRate;

    public String getCountRate() {
        return countRate;
    }

    public void setCountRate(String countRate) {
        this.countRate = countRate;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
