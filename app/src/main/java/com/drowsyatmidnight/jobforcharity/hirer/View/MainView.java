package com.drowsyatmidnight.jobforcharity.hirer.View;

import com.drowsyatmidnight.jobforcharity.hirer.Models.Entity.Work;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public interface MainView {
    void displayWorkList(List<Work> workList);
    void getCreatedWork(Work work);
}
