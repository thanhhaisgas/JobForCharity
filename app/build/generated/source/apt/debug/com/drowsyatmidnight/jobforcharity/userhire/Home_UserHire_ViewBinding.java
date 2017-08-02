// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import com.lapism.searchview.SearchView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Home_UserHire_ViewBinding implements Unbinder {
  private Home_UserHire target;

  @UiThread
  public Home_UserHire_ViewBinding(Home_UserHire target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Home_UserHire_ViewBinding(Home_UserHire target, View source) {
    this.target = target;

    target.searchViewHome = Utils.findRequiredViewAsType(source, R.id.searchViewHome, "field 'searchViewHome'", SearchView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.drawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer'", DrawerLayout.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navigationView'", NavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Home_UserHire target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchViewHome = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
  }
}
