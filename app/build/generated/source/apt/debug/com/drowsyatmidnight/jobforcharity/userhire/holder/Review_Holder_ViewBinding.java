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

public class Review_Holder_ViewBinding implements Unbinder {
  private Review_Holder target;

  @UiThread
  public Review_Holder_ViewBinding(Review_Holder target, View source) {
    this.target = target;

    target.imgProfileItemReview = Utils.findRequiredViewAsType(source, R.id.imgProfileItemReview, "field 'imgProfileItemReview'", ImageView.class);
    target.txtNameUserReview = Utils.findRequiredViewAsType(source, R.id.txtNameUserReview, "field 'txtNameUserReview'", TextView.class);
    target.txtContentReview = Utils.findRequiredViewAsType(source, R.id.txtContentReview, "field 'txtContentReview'", TextView.class);
    target.cardViewReview = Utils.findRequiredViewAsType(source, R.id.cardViewReview, "field 'cardViewReview'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Review_Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgProfileItemReview = null;
    target.txtNameUserReview = null;
    target.txtContentReview = null;
    target.cardViewReview = null;
  }
}
