// Generated code from Butter Knife. Do not modify!
package com.drowsyatmidnight.jobforcharity.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.drowsyatmidnight.jobforcharity.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.txtFName = Utils.findRequiredViewAsType(source, R.id.txtFName, "field 'txtFName'", EditText.class);
    target.txtLName = Utils.findRequiredViewAsType(source, R.id.txtLName, "field 'txtLName'", EditText.class);
    target.txtEmailSup = Utils.findRequiredViewAsType(source, R.id.txtEmailSup, "field 'txtEmailSup'", EditText.class);
    target.txtPhoneNum = Utils.findRequiredViewAsType(source, R.id.txtPhoneNum, "field 'txtPhoneNum'", EditText.class);
    target.txtPassSUp = Utils.findRequiredViewAsType(source, R.id.txtPassSup, "field 'txtPassSUp'", EditText.class);
    target.txtRePass = Utils.findRequiredViewAsType(source, R.id.txtRePass, "field 'txtRePass'", EditText.class);
    target.ckVerify = Utils.findRequiredViewAsType(source, R.id.ckVerify, "field 'ckVerify'", CheckBox.class);
    target.btnSignUp = Utils.findRequiredViewAsType(source, R.id.btnSignUp, "field 'btnSignUp'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtFName = null;
    target.txtLName = null;
    target.txtEmailSup = null;
    target.txtPhoneNum = null;
    target.txtPassSUp = null;
    target.txtRePass = null;
    target.ckVerify = null;
    target.btnSignUp = null;
    target.toolbar = null;
  }
}
