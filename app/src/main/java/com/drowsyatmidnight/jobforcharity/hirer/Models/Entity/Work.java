package com.drowsyatmidnight.jobforcharity.hirer.Models.Entity;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */


public class Work {
    private String WorkID;
    private String WorkName;
    private String HirerUID;
    private String CategoryID;
    /*List<Datetime> Datetimes;*/
    private String Description;


    /*public List<Datetime> getDatetimes() {
        return Datetimes;
    }

    public void setDatetimes(List<Datetime> datetimes) {
        Datetimes = datetimes;
    }*/



    public String getWorkID() {
        return WorkID;
    }

    public void setWorkID(String workID) {
        WorkID = workID;
    }

    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }

    public String getHirerUID() {
        return HirerUID;
    }

    public void setHirerUID(String hirerUID) {
        HirerUID = hirerUID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public static class Datetime{

        private String Date;
        private String BeginTime;
        private String EndTime;

        public void setDate(String date) {
            Date = date;
        }

        public void setBeginTime(String beginTime) {
            BeginTime = beginTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getDate() {
            return Date;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public String getEndTime() {
            return EndTime;
        }




    }

}
