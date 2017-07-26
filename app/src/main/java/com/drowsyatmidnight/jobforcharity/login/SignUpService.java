package com.drowsyatmidnight.jobforcharity.login;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.drowsyatmidnight.jobforcharity.model.User_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by haint on 23/07/2017.
 */

public class SignUpService {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Activity activity;

    public SignUpService(Activity activity) {
        this.activity = activity;
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public interface RegisterListener {
        void registerSuccess();
        void registerFailure(String message);
    }

    public void registerAccount(String email, final String passWord, final String FName, final String LName, final String mobilePhone, final RegisterListener listener){
        auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final FirebaseUser user = task.getResult().getUser();
                    if(user!=null){
                        //send email
                        user.sendEmailVerification().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //save user info
                                    User_model userModel = new User_model();
                                    userModel.setUid(user.getUid());
                                    userModel.setFName(FName);
                                    userModel.setLName(LName);
                                    userModel.setMobilePhone(mobilePhone);
                                    userModel.setEmail(user.getEmail());
                                    userModel.setPassWord(passWord);
                                    createAccountInDatabase(userModel, new RegisterListener() {
                                        @Override
                                        public void registerSuccess() {
                                            auth.signOut();
                                            listener.registerSuccess();
                                        }
                                        @Override
                                        public void registerFailure(String message) {
                                            listener.registerFailure(message);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    //push user info to rdb
    public void createAccountInDatabase(User_model user_model, final RegisterListener listener){
        databaseReference.child("USERS")
                .child(user_model.getUid())
                .setValue(user_model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.registerSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.registerFailure(e.getMessage());
            }
        });
    }
}
