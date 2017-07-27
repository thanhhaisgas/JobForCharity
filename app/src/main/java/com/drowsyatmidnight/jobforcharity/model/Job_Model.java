package com.drowsyatmidnight.jobforcharity.model;

import java.util.List;

/**
 * Created by haint on 27/07/2017.
 */

public class Job_Model {
    private List<ShiftWork_Model> DateTimes;
    private String category;
    private String discription;
    private String hirerUID;
    private String workName;

    public Job_Model(List<ShiftWork_Model> dateTimes, String category, String discription, String hirerUID, String workName) {
        DateTimes = dateTimes;
        this.category = category;
        this.discription = discription;
        this.hirerUID = hirerUID;
        this.workName = workName;
    }

    public List<ShiftWork_Model> getDateTimes() {
        return DateTimes;
    }

    public void setDateTimes(List<ShiftWork_Model> dateTimes) {
        DateTimes = dateTimes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getHirerUID() {
        return hirerUID;
    }

    public void setHirerUID(String hirerUID) {
        this.hirerUID = hirerUID;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}
