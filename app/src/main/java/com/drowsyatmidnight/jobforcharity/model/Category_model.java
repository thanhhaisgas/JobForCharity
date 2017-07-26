package com.drowsyatmidnight.jobforcharity.model;

/**
 * Created by haint on 26/07/2017.
 */

public class Category_model {
    private String Name;
    private String Count;

    public Category_model(String name, String count) {
        Name = name;
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
