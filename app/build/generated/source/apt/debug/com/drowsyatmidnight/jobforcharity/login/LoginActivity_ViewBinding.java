// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.txtEmail = Utils.findRequiredViewAsType(source, R.id.txtEmail, "field 'txtEmail'", EditText.class);
    target.txtPass = Utils.findRequiredViewAsType(source, R.id.txtPass, "field 'txtPass'", EditText.class);
    target.btnSignIn = Utils.findRequiredViewAsType(source, R.id.btnSignIn, "field 'btnSignIn'", Button.class);
    target.txtSignUp = Utils.findRequiredViewAsType(source, R.id.txtSignUp, "field 'txtSignUp'", TextView.class);
    target.viewLogin = Utils.findRequiredViewAsType(source, R.id.viewLogin, "field 'viewLogin'", LinearLayout.class);
    target.viewEmployer = Utils.findRequiredViewAsType(source, R.id.employer, "field 'viewEmployer'", LinearLayout.class);
    target.viewEmployee = Utils.findRequiredViewAsType(source, R.id.employee, "field 'viewEmployee'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtEmail = null;
    target.txtPass = null;
    target.btnSignIn = null;
    target.txtSignUp = null;
    target.viewLogin = null;
    target.viewEmployer = null;
    target.viewEmployee = null;
  }
}
