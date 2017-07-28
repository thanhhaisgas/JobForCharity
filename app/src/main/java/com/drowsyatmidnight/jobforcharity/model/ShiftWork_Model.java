package com.drowsyatmidnight.jobforcharity.model;

import java.io.Serializable;

/**
 * Created by haint on 27/07/2017.
 */

public class ShiftWork_Model implements Serializable {
    private String Date;
    private String beginTime;
    private String endTime;
    private String status;
    private String uidHirer;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
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

    @Override
    public String toString() {
        return "ShiftWork_Model{" +
                "Date='" + Date + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                ", uidHirer='" + uidHirer + '\'' +
                '}';
    }
}
