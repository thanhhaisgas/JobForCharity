package com.drowsyatmidnight.jobforcharity.woker.Models.Entity;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */


public class Work {
    private String WorkName;
    private String Category;
    private String Description;
    private String WorkerUID;

    public Work(){

    }
    public Work(String category, String workName, String description) {
        WorkName = workName;
        Category = category;
        Description = description;

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



    public String getWorkName() {
        return WorkName;
    }

    public void setWorkName(String workName) {
        WorkName = workName;
    }



    public String getWorkerUID() {
        return WorkerUID;
    }

    public void setWorkerUID(String workerUID) {
        WorkerUID = workerUID;
    }






   /* public List<Datetime> getDatetimes() {
        return Datetimes;
    }

    public void setDatetimes(List<Datetime> datetimes) {
        Datetimes = datetimes;
    }*/




    public static class Datetime {

        public String getHirerUID() {
            return HirerUID;
        }

        public void setHirerUID(String hirerUID) {
            HirerUID = hirerUID;
        }


        private String HirerUID;
        private String Status;
        private String Date;
        private String BeginTime;
        private String EndTime;

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
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
