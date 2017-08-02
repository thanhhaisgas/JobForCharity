package com.drowsyatmidnight.jobforcharity.woker.Models.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haint on 27/07/2017.
 */

public class Job_Model implements Parcelable {
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

    public Job_Model(List<ShiftWork_Model> dateTimes, String category, String description, String wokerUID, String workName) {
        DateTimes = dateTimes;
        Category = category;
        Description = description;
        WokerUID = wokerUID;
        WorkName = workName;
    }
    public Job_Model(List<ShiftWork_Model> dateTimes, String category, String description, String wokerUID, String workName,String JobID) {
        DateTimes = dateTimes;
        Category = category;
        Description = description;
        WokerUID = wokerUID;
        WorkName = workName;
    }

    private List<ShiftWork_Model> DateTimes;
    private String Category;
    private String Description;
    private String WokerUID;
    private String WorkName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.DateTimes);
        dest.writeString(this.Category);
        dest.writeString(this.Description);
        dest.writeString(this.WokerUID);
        dest.writeString(this.WorkName);
    }

    protected Job_Model(Parcel in) {
        this.DateTimes = new ArrayList<ShiftWork_Model>();
        in.readList(this.DateTimes, ShiftWork_Model.class.getClassLoader());
        this.Category = in.readString();
        this.Description = in.readString();
        this.WokerUID = in.readString();
        this.WorkName = in.readString();
    }

    public static final Parcelable.Creator<Job_Model> CREATOR = new Parcelable.Creator<Job_Model>() {
        @Override
        public Job_Model createFromParcel(Parcel source) {
            return new Job_Model(source);
        }

        @Override
        public Job_Model[] newArray(int size) {
            return new Job_Model[size];
        }
    };
}
