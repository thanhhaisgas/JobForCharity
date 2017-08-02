package com.drowsyatmidnight.jobforcharity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.Utils.ValidChecking;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.Home_UserHire;
import com.drowsyatmidnight.jobforcharity.userhire.KeyValueFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPass)
    EditText txtPass;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.txtSignUp)
    TextView txtSignUp;
    @BindView(R.id.viewLogin)
    LinearLayout viewLogin;
    @BindView(R.id.employer)
    LinearLayout viewEmployer;
    @BindView(R.id.employee)
    LinearLayout viewEmployee;

    private String EMail, Pass;

    private LoginService loginService;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        addEvents();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    private void addEvents() {
        checkVerifyEmail();
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcSignUp();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignIn();
            }
        });
    }

    private void checkVerifyEmail() {
        loginService = new LoginService();
        auth=FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //check verify of user
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    if(user.isEmailVerified()){
                        //EmailVerified
                    }else {
                        auth.signOut();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Verify email");
                        builder.setMessage("Please verify your email address to complete sign up");
                        builder.create().show();
                    }
                }
            }
        };
    }

    private void doSignIn() {
        if(checkInputData()){
            final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            sweetAlertDialog.setTitleText("Please wait...");
            sweetAlertDialog.setCancelText(getString(R.string.cancel));
            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    viewLogin.setVisibility(View.VISIBLE);
                    viewEmployer.setVisibility(View.GONE);
                    viewEmployee.setVisibility(View.GONE);
                    sweetAlertDialog.dismiss();
                }
            });
            sweetAlertDialog.show();

            viewLogin.setVisibility(View.GONE);
            loginService.loginAcountEmail(EMail, Pass, new LoginService.LoginListener() {
                @Override
                public void loginSuccess() {
                    sweetAlertDialog.dismiss();
                    KeyValueFirebase.UID = user.getUid();
                    DataFirebase.createCategories(getResources().getStringArray(R.array.listCategoryName));
                    viewEmployer.setVisibility(View.VISIBLE);
                    viewEmployee.setVisibility(View.VISIBLE);
                    viewEmployer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent goHomeUserHire = new Intent(LoginActivity.this, Home_UserHire.class);
                            startActivity(goHomeUserHire);
                        }
                    });

                }

                @Override
                public void loginFailure(String message) {
                    sweetAlertDialog.dismiss();
                    viewLogin.setVisibility(View.VISIBLE);
                    viewEmployer.setVisibility(View.GONE);
                    viewEmployee.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void AcSignUp() {
        Intent signup = new Intent(this, SignUpActivity.class);
        startActivity(signup);
    }

    private boolean checkInputData(){
        if(ValidChecking.isEmpty(txtEmail)&&ValidChecking.isEmpty(txtPass)){
            EMail=txtEmail.getText().toString().trim();
            Pass=txtPass.getText().toString().trim();
            if (!ValidChecking.isEmailValid(EMail)){
                txtEmail.requestFocus();
                txtEmail.setError("Email is not valid");
                return false;
            }else {
                if(txtPass.getText().toString().length()<6){
                    txtPass.requestFocus();
                    txtPass.setError("Your password must be shorter than 6 characters");
                    return false;
                }
            }
            return true;
        }else {
            return false;
        }
    }

}
