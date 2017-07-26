package com.drowsyatmidnight.jobforcharity.woker.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public class UserInterator {
    private LoadWorkListener mLoadWorkListener;
    private CreateWorkListener mCreateWorkListener;

    public UserInterator(CreateWorkListener createWorkListener) {
        mCreateWorkListener = createWorkListener;
    }

    public UserInterator(LoadWorkListener loadWorkListener) {
        mLoadWorkListener = loadWorkListener;

    }

    public void loadWorkFromFirebase() {
        List<Work> mWorkList = new ArrayList<>();
        //TO DO SOMETHING TO GET DATA FROM FIREBASE
        //
        //
        if (mWorkList != null) {
            mLoadWorkListener.onLoadWorkSucess(mWorkList);
        } else {
            mLoadWorkListener.onLoadWorkFailure("Failed to load Data from Firebase");
        }
    }


    public void createWorkInFirebase(Work work, List<Work.Datetime> mDateTimeList) {
        FirebaseAuth mAuth = Authority.sFirebaseAuth;
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final String UID = user.getUid();

        // add list datetime to jobs

            mFirebaseDatabaseReference.child("Jobs")
                    .push().setValue(work)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //TO DO SOMTHING WHEN COMPLETE
                            Log.d("an", "create work successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //TO DO SOMTHING WHEN FAILURE
                            Log.d("an", "create work unsuccessfully");
                        }
                    });


        for (Work.Datetime datetime:mDateTimeList) {
            String key = mFirebaseDatabaseReference.getKey();
            mFirebaseDatabaseReference.child("Jobs").child("DateTimes").child(key)
                    .push().setValue(datetime)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //TO DO SOMTHING WHEN COMPLETE
                            Log.d("an", "create datetime successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //TO DO SOMTHING WHEN FAILURE
                            Log.d("an", "create datetime unsuccessfully");
                        }
                    });

        }
        // TO DO SOMETHING TO CREATE DATA IN FIREBASE
        //
        //
    }
}
