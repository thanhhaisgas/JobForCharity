package com.drowsyatmidnight.jobforcharity.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by haint on 23/07/2017.
 */

public class LoginService {

    private FirebaseAuth auth;

    public LoginService() {
        auth = FirebaseAuth.getInstance();
    }

    public interface LoginListener{
        void loginSuccess();
        void loginFailure(String message);
    }

    public void loginAcountEmail(String mail, String pass, final LoginListener loginListener){
        auth.signInWithEmailAndPassword(mail,pass).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginListener.loginFailure(e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                loginListener.loginSuccess();
                Authority.sFirebaseAuth = auth;
            }
        });
    }
}
