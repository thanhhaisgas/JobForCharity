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

public class CategoryHolder_ViewBinding implements Unbinder {
  private CategoryHolder target;

  @UiThread
  public CategoryHolder_ViewBinding(CategoryHolder target, View source) {
    this.target = target;

    target.imgCategory = Utils.findRequiredViewAsType(source, R.id.imgCategory, "field 'imgCategory'", ImageView.class);
    target.txtNameCategory = Utils.findRequiredViewAsType(source, R.id.txtNameCategory, "field 'txtNameCategory'", TextView.class);
    target.txtJobCountCategory = Utils.findRequiredViewAsType(source, R.id.txtJobCountCategory, "field 'txtJobCountCategory'", TextView.class);
    target.cardViewCategory = Utils.findRequiredViewAsType(source, R.id.cardViewCategory, "field 'cardViewCategory'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgCategory = null;
    target.txtNameCategory = null;
    target.txtJobCountCategory = null;
    target.cardViewCategory = null;
  }
}
