package com.drowsyatmidnight.jobforcharity.hirer.Models;

import com.drowsyatmidnight.jobforcharity.hirer.Models.Entity.Work;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public interface CreateWorkListener {
    public void onCreateWorkSuccess(Work work);
    public void onCreateWorkFailure(String message);
}
