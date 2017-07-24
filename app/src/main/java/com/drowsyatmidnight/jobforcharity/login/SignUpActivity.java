package com.drowsyatmidnight.jobforcharity.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.Utils.ValidChecking;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.txtFName)
    EditText txtFName;
    @BindView(R.id.txtLName)
    EditText txtLName;
    @BindView(R.id.txtEmailSup)
    EditText txtEmailSup;
    @BindView(R.id.txtPhoneNum)
    EditText txtPhoneNum;
    @BindView(R.id.txtPassSup)
    EditText txtPassSUp;
    @BindView(R.id.txtRePass)
    EditText txtRePass;
    @BindView(R.id.ckVerify)
    CheckBox ckVerify;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String FName, LName, EMail, PhoneNum, Pass;
    private SignUpService signUpService;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        toolbar();
        addEvents();
    }

    private void toolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void addEvents() {
        signUpService = new SignUpService(this);
        btnSignUp.setEnabled(false);
        ckVerify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnSignUp.setEnabled(true);
                }else {
                    btnSignUp.setEnabled(false);
                }
                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doSignUp();
                    }
                });
            }
        });
    }

    private void doSignUp() {
        if(checkInputData()){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            signUpService.registerAccount(EMail, Pass, FName, LName, PhoneNum, new SignUpService.RegisterListener() {
                @Override
                public void registerSuccess() {
                    progressDialog.dismiss();
                    txtFName.setText("");
                    txtLName.setText("");
                    txtEmailSup.setText("");
                    txtPassSUp.setText("");
                    txtRePass.setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Verify email");
                    builder.setMessage("Please verify your email address to complete sign up");
                    builder.create().show();
                }

                @Override
                public void registerFailure(String message) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean checkInputData(){
        if(ValidChecking.isEmpty(txtFName)&&ValidChecking.isEmpty(txtLName)&&ValidChecking.isEmpty(txtEmailSup)&&ValidChecking.isEmpty(txtPassSUp)&&ValidChecking.isEmpty(txtRePass)){
            FName=txtFName.getText().toString().trim();
            LName=txtLName.getText().toString().trim();
            PhoneNum=txtPhoneNum.getText().toString().trim();
            EMail=txtEmailSup.getText().toString().trim();
            if (!ValidChecking.isEmailValid(EMail)){
                txtEmailSup.requestFocus();
                txtEmailSup.setError("Email is not valid");
                return false;
            }else {
                if(txtPassSUp.getText().toString().length()<6){
                    txtPassSUp.requestFocus();
                    txtPassSUp.setError("Your password must be longer than 6 characters");
                    return false;
                }else {
                    if(txtPassSUp.getText().toString().compareTo(txtRePass.getText().toString())==0){
                        Pass=txtPassSUp.getText().toString().trim();
                    }else{
                        txtPassSUp.requestFocus();
                        txtPassSUp.setError("Password is not match");
                        return false;
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }
}
