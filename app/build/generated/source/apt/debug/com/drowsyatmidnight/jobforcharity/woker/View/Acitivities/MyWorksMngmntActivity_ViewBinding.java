// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.woker.View.Acitivities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyWorksMngmntActivity_ViewBinding implements Unbinder {
  private MyWorksMngmntActivity target;

  @UiThread
  public MyWorksMngmntActivity_ViewBinding(MyWorksMngmntActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyWorksMngmntActivity_ViewBinding(MyWorksMngmntActivity target, View source) {
    this.target = target;

    target.navViewWorker = Utils.findRequiredViewAsType(source, R.id.nav_view_worker, "field 'navViewWorker'", NavigationView.class);
    target.tvViewMode = Utils.findRequiredViewAsType(source, R.id.tvViewMode, "field 'tvViewMode'", TextView.class);
    target.imgWorker = Utils.findRequiredViewAsType(source, R.id.imgWorker, "field 'imgWorker'", CircleImageView.class);
    target.myword_container = Utils.findRequiredViewAsType(source, R.id.mywork_container, "field 'myword_container'", FrameLayout.class);
    target.fab_addJob = Utils.findRequiredViewAsType(source, R.id.fab_addJob, "field 'fab_addJob'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyWorksMngmntActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.navViewWorker = null;
    target.tvViewMode = null;
    target.imgWorker = null;
    target.myword_container = null;
    target.fab_addJob = null;
  }
}
