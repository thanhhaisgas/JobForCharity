// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.relex.circleindicator.CircleIndicator;

public class FmCategory_ViewBinding implements Unbinder {
  private FmCategory target;

  @UiThread
  public FmCategory_ViewBinding(FmCategory target, View source) {
    this.target = target;

    target.pager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'pager'", ViewPager.class);
    target.indicator = Utils.findRequiredViewAsType(source, R.id.indicator, "field 'indicator'", CircleIndicator.class);
    target.lvCategory = Utils.findRequiredViewAsType(source, R.id.lvCategory, "field 'lvCategory'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FmCategory target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pager = null;
    target.indicator = null;
    target.lvCategory = null;
  }
}
