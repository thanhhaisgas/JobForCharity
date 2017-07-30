// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class JobDetail_ViewBinding implements Unbinder {
  private JobDetail target;

  @UiThread
  public JobDetail_ViewBinding(JobDetail target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public JobDetail_ViewBinding(JobDetail target, View source) {
    this.target = target;

    target.tabs = Utils.findRequiredViewAsType(source, R.id.tabs, "field 'tabs'", TabLayout.class);
    target.vpJobDetail = Utils.findRequiredViewAsType(source, R.id.vpJobDetail, "field 'vpJobDetail'", ViewPager.class);
    target.toolbarJobDetail = Utils.findRequiredViewAsType(source, R.id.toolbarJobDetail, "field 'toolbarJobDetail'", Toolbar.class);
    target.imgBackground = Utils.findRequiredViewAsType(source, R.id.imgBackground, "field 'imgBackground'", ImageView.class);
    target.profile_image = Utils.findRequiredViewAsType(source, R.id.profile_image, "field 'profile_image'", ImageView.class);
    target.txtTenDetail = Utils.findRequiredViewAsType(source, R.id.txtTenDetail, "field 'txtTenDetail'", TextView.class);
    target.txtPhoneNumDetail = Utils.findRequiredViewAsType(source, R.id.txtPhoneNumDetail, "field 'txtPhoneNumDetail'", TextView.class);
    target.txtEmailDetail = Utils.findRequiredViewAsType(source, R.id.txtEmailDetail, "field 'txtEmailDetail'", TextView.class);
    target.rateBar = Utils.findRequiredViewAsType(source, R.id.rateBar, "field 'rateBar'", RatingBar.class);
    target.txtCountRate = Utils.findRequiredViewAsType(source, R.id.txtCountRate, "field 'txtCountRate'", TextView.class);
    target.appBarJobDetail = Utils.findRequiredViewAsType(source, R.id.appBarJobDetail, "field 'appBarJobDetail'", AppBarLayout.class);
    target.collapsingToolbarDetail = Utils.findRequiredViewAsType(source, R.id.collapsingToolbarDetail, "field 'collapsingToolbarDetail'", CollapsingToolbarLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    JobDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabs = null;
    target.vpJobDetail = null;
    target.toolbarJobDetail = null;
    target.imgBackground = null;
    target.profile_image = null;
    target.txtTenDetail = null;
    target.txtPhoneNumDetail = null;
    target.txtEmailDetail = null;
    target.rateBar = null;
    target.txtCountRate = null;
    target.appBarJobDetail = null;
    target.collapsingToolbarDetail = null;
  }
}
