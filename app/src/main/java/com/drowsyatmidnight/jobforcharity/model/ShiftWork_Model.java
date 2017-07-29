package com.drowsyatmidnight.jobforcharity.model;

import java.io.Serializable;

/**
 * Created by haint on 27/07/2017.
 */

public class ShiftWork_Model implements Serializable {
    public String getDateTimeID() {
        return DateTimeID;
    }

    public void setDateTimeID(String dateTimeID) {
        DateTimeID = dateTimeID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getHirerUID() {
        return hirerUID;
    }

    public void setHirerUID(String hirerUID) {
        this.hirerUID = hirerUID;
    }

    private String DateTimeID;
    private String Date;
    private String BeginTime;
    private String EndTime;
    private String Status;
    private String hirerUID;


}
