package com.drowsyatmidnight.jobforcharity.model;

/**
 * Created by haint on 30/07/2017.
 */

public class Review_Model {
    private String hirerUID;
    private String comment;
    private String jobID;
    private String lName;
    private String fName;

    public Review_Model(String hirerUID, String comment, String jobID, String lName, String fName) {
        this.hirerUID = hirerUID;
        this.comment = comment;
        this.jobID = jobID;
        this.lName = lName;
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getHirerUID() {
        return hirerUID;
    }

    public void setHirerUID(String hirerUID) {
        this.hirerUID = hirerUID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
