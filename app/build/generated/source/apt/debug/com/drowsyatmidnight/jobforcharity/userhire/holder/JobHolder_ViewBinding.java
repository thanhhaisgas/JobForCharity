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

public class JobHolder_ViewBinding implements Unbinder {
  private JobHolder target;

  @UiThread
  public JobHolder_ViewBinding(JobHolder target, View source) {
    this.target = target;

    target.imgProfileJob = Utils.findRequiredViewAsType(source, R.id.imgProfileJob, "field 'imgProfileJob'", ImageView.class);
    target.txtJobName = Utils.findRequiredViewAsType(source, R.id.txtJobName, "field 'txtJobName'", TextView.class);
    target.txtJobExpirationDate = Utils.findRequiredViewAsType(source, R.id.txtJobExpirationDate, "field 'txtJobExpirationDate'", TextView.class);
    target.cardViewJobCategory = Utils.findRequiredViewAsType(source, R.id.cardViewJobCategory, "field 'cardViewJobCategory'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    JobHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgProfileJob = null;
    target.txtJobName = null;
    target.txtJobExpirationDate = null;
    target.cardViewJobCategory = null;
  }
}
