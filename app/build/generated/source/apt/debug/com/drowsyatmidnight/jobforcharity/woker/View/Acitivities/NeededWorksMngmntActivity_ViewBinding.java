// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.woker.View.Acitivities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NeededWorksMngmntActivity_ViewBinding implements Unbinder {
  private NeededWorksMngmntActivity target;

  @UiThread
  public NeededWorksMngmntActivity_ViewBinding(NeededWorksMngmntActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NeededWorksMngmntActivity_ViewBinding(NeededWorksMngmntActivity target, View source) {
    this.target = target;

    target.fab_compose = Utils.findRequiredViewAsType(source, R.id.fab_compose, "field 'fab_compose'", FloatingActionButton.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayoutHirer, "field 'tabLayout'", TabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpagerHirer, "field 'viewPager'", ViewPager.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbarHirer, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NeededWorksMngmntActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fab_compose = null;
    target.tabLayout = null;
    target.viewPager = null;
    target.toolbar = null;
  }
}
