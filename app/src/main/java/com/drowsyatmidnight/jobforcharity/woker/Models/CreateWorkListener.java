package com.drowsyatmidnight.jobforcharity.woker.Models;

import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;

/**
 * Created by davidtran on 7/24/17.
 */

public interface CreateWorkListener {
    public void onCreateWorkSuccess(Work work);
    public void onCreateWorkFailure(String message);
}
