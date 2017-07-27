package com.drowsyatmidnight.jobforcharity.model;

import java.io.Serializable;

/**
 * Created by haint on 27/07/2017.
 */

public class ShiftWork_Model implements Serializable {
    private String Date;
    private String biginTime;
    private String endTime;
    private String status;
    private String uidHirer;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBiginTime() {
        return biginTime;
    }

    public void setBiginTime(String biginTime) {
        this.biginTime = biginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUidHirer() {
        return uidHirer;
    }

    public void setUidHirer(String uidHirer) {
        this.uidHirer = uidHirer;
    }
}
