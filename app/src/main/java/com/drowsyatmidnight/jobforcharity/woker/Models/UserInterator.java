package com.drowsyatmidnight.jobforcharity.woker.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
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
        List<List<Work.Datetime>> mListListDateTime = new ArrayList<>();

        //TO DO SOMETHING TO GET DATA FROM FIREBASE
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("JOBS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String category = (String) dsp.child("category").getValue();
                    List<ShiftWork_Model> listDate = new ArrayList<>();
                    List<Job_Model> listJob = new ArrayList<>();
                    listDate.clear();
                    for (DataSnapshot date : dsp.child("DateTimes").getChildren()) {
                        ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                        listDate.add(shiftWork_model);
                    }

                    String workName = (String) dsp.child("workName").getValue();
                    String hirerUID = (String) dsp.child("hirerUID").getValue();
                    String description = (String) dsp.child("description").getValue();
                    listJob.add(new Job_Model(listDate, category, description, hirerUID, workName));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void createWorkInFirebase(Work work, List<Work.Datetime> mDateTimeList) {
        FirebaseAuth mAuth = Authority.sFirebaseAuth;
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final String UID = user.getUid();

        // add list datetime to jobs

            mFirebaseDatabaseReference.child("JOBS")
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
