package com.drowsyatmidnight.jobforcharity.woker.View.Utils;

import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;

/**
 * Created by davidtran on 7/29/17.
 */

public interface Communicator {
    void onDateTimeChanged(Work work, ShiftWork_Model shiftWork_model);
}
