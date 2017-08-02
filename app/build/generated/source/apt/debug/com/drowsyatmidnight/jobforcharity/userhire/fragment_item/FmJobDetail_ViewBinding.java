// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FmJobDetail_ViewBinding implements Unbinder {
  private FmJobDetail target;

  @UiThread
  public FmJobDetail_ViewBinding(FmJobDetail target, View source) {
    this.target = target;

    target.detailNameJob = Utils.findRequiredViewAsType(source, R.id.detailNameJob, "field 'detailNameJob'", TextView.class);
    target.detailDescriptionJob = Utils.findRequiredViewAsType(source, R.id.detailDescriptionJob, "field 'detailDescriptionJob'", TextView.class);
    target.lvJobDetailDateTime = Utils.findRequiredViewAsType(source, R.id.lvJobDetailDateTime, "field 'lvJobDetailDateTime'", ExpandableListView.class);
    target.btnRentJob = Utils.findRequiredViewAsType(source, R.id.btnRentJob, "field 'btnRentJob'", Button.class);
    target.scroll1 = Utils.findRequiredViewAsType(source, R.id.scroll1, "field 'scroll1'", NestedScrollView.class);
    target.btnCancelJob = Utils.findRequiredViewAsType(source, R.id.btnCancelJob, "field 'btnCancelJob'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FmJobDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.detailNameJob = null;
    target.detailDescriptionJob = null;
    target.lvJobDetailDateTime = null;
    target.btnRentJob = null;
    target.scroll1 = null;
    target.btnCancelJob = null;
  }
}
