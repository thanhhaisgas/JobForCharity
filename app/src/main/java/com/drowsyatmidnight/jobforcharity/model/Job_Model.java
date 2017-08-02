package com.drowsyatmidnight.jobforcharity.model;

import java.util.List;

/**
 * Created by haint on 27/07/2017.
 */

public class Job_Model {
    public List<ShiftWork_Model> getDateTimes() {
        return DateTimes;
    }

    public void setDateTimes(List<ShiftWork_Model> dateTimes) {
        DateTimes = dateTimes;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getWokerUID() {
        return WokerUID;
    }

    public void setWokerUID(String wokerUID) {
        WokerUID = wokerUID;
    }

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public Job_Model(List<ShiftWork_Model> dateTimes, String category, String description, String wokerUID, String workName, String jobID) {
        DateTimes = dateTimes;
        Category = category;
        Description = description;
        WokerUID = wokerUID;
        WorkName = workName;
        JobID = jobID;
    }

    private List<ShiftWork_Model> DateTimes;
    private String Category;
    private String Description;
    private String WokerUID;
    private String WorkName;
    private String JobID;

}
