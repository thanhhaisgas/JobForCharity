// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FmReviews_ViewBinding implements Unbinder {
  private FmReviews target;

  @UiThread
  public FmReviews_ViewBinding(FmReviews target, View source) {
    this.target = target;

    target.lvJobInProgress = Utils.findRequiredViewAsType(source, R.id.lvJobInProgress, "field 'lvJobInProgress'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FmReviews target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lvJobInProgress = null;
  }
}
