// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class JobsCategory_ViewBinding implements Unbinder {
  private JobsCategory target;

  @UiThread
  public JobsCategory_ViewBinding(JobsCategory target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public JobsCategory_ViewBinding(JobsCategory target, View source) {
    this.target = target;

    target.lvJobsCategory = Utils.findRequiredViewAsType(source, R.id.lvJobsCategory, "field 'lvJobsCategory'", RecyclerView.class);
    target.txtJobNameCategory = Utils.findRequiredViewAsType(source, R.id.txtJobNameCategory, "field 'txtJobNameCategory'", TextView.class);
    target.txtJobCountCategory = Utils.findRequiredViewAsType(source, R.id.txtJobCountCategory, "field 'txtJobCountCategory'", TextView.class);
    target.imgJobCategory = Utils.findRequiredViewAsType(source, R.id.imgJobCategory, "field 'imgJobCategory'", ImageView.class);
    target.lnJobCatagory = Utils.findRequiredViewAsType(source, R.id.lnJobCatagory, "field 'lnJobCatagory'", LinearLayout.class);
    target.toolbarJobsCategory = Utils.findRequiredViewAsType(source, R.id.toolbarJobsCategory, "field 'toolbarJobsCategory'", Toolbar.class);
    target.bgActivityJobsCategory = Utils.findRequiredViewAsType(source, R.id.bgActivityJobsCategory, "field 'bgActivityJobsCategory'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    JobsCategory target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lvJobsCategory = null;
    target.txtJobNameCategory = null;
    target.txtJobCountCategory = null;
    target.imgJobCategory = null;
    target.lnJobCatagory = null;
    target.toolbarJobsCategory = null;
    target.bgActivityJobsCategory = null;
  }
}
