// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class JobInProgressHolder_ViewBinding implements Unbinder {
  private JobInProgressHolder target;

  @UiThread
  public JobInProgressHolder_ViewBinding(JobInProgressHolder target, View source) {
    this.target = target;

    target.imgProfileJobInProgress = Utils.findRequiredViewAsType(source, R.id.imgProfileJobInProgress, "field 'imgProfileJobInProgress'", ImageView.class);
    target.txtJobNameInProgress = Utils.findRequiredViewAsType(source, R.id.txtJobNameInProgress, "field 'txtJobNameInProgress'", TextView.class);
    target.txtJobDateInProgress = Utils.findRequiredViewAsType(source, R.id.txtJobDateInProgress, "field 'txtJobDateInProgress'", TextView.class);
    target.cardViewJobInProgress = Utils.findRequiredViewAsType(source, R.id.cardViewJobInProgress, "field 'cardViewJobInProgress'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    JobInProgressHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgProfileJobInProgress = null;
    target.txtJobNameInProgress = null;
    target.txtJobDateInProgress = null;
    target.cardViewJobInProgress = null;
  }
}
