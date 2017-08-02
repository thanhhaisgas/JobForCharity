package com.drowsyatmidnight.jobforcharity.woker.Models.Entity;

import java.util.List;

/**
 * Created by davidtran on 8/1/17.
 */

public class GroupedDateTimeWork {
    String date;
    List<ShiftWork_Model> mShiftWorkModels;

    public GroupedDateTimeWork(String date, List<ShiftWork_Model> shiftWorkModels) {
        this.date = date;
        mShiftWorkModels = shiftWorkModels;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ShiftWork_Model> getShiftWorkModels() {
        return mShiftWorkModels;
    }

    public void setShiftWorkModels(List<ShiftWork_Model> shiftWorkModels) {
        mShiftWorkModels = shiftWorkModels;
    }
}
