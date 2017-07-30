// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    Home_UserHire target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchViewHome = null;
  }
}
