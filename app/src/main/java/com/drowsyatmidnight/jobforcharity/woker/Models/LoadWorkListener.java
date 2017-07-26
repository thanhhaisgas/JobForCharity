package com.drowsyatmidnight.jobforcharity.woker.Models;

import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public interface LoadWorkListener {
    public void onLoadWorkSucess(List<Work> workList);
    public void onLoadWorkFailure(String message);
}
