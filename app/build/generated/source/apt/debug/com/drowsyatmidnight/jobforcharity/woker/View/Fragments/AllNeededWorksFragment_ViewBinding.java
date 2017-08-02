// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.woker.View.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ExpandableListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllNeededWorksFragment_ViewBinding implements Unbinder {
  private AllNeededWorksFragment target;

  @UiThread
  public AllNeededWorksFragment_ViewBinding(AllNeededWorksFragment target, View source) {
    this.target = target;

    target.mExpandableListView = Utils.findRequiredViewAsType(source, R.id.exListJob, "field 'mExpandableListView'", ExpandableListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllNeededWorksFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mExpandableListView = null;
  }
}
