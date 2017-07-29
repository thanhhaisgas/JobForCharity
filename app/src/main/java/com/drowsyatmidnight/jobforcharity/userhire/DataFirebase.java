package com.drowsyatmidnight.jobforcharity.userhire;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.model.Category_Model;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobCategotyAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobInProGressAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by haint on 26/07/2017.
 */

public class DataFirebase {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static void getUserInfo(String UID, final TextView txtTenDetail, final TextView txtPhoneNumDetail, final TextView txtEmailDetail){
        databaseReference.child("USERS").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Model user_model = dataSnapshot.getValue(User_Model.class);
                txtTenDetail.setText(user_model.getLName()+" "+user_model.getFName());
                txtPhoneNumDetail.setText(user_model.getMobilePhone());
                txtEmailDetail.setText(user_model.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void createCategories(final String[] categoryName){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild("CATEGORIES")){
                    for (int i = 0; i<categoryName.length; i++){
                        Category_Model category_model = new Category_Model(categoryName[i], "0");
                        databaseReference.child("CATEGORIES").child(categoryName[i]).setValue(category_model);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getJobsCategory(final String categoryName, final RecyclerView lvJobsCategory, final Context context){
        databaseReference.child("JOBS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShiftWork_Model> listDate = new ArrayList<>();
                List<Job_Model> listJob = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    String category = (String) dsp.child("category").getValue();
                    if (category.compareTo(categoryName)==0){
                        for (DataSnapshot date : dsp.child("DateTimes").getChildren()){
                            ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                            if (shiftWork_model.getStatus().compareTo(KeyValueFirebase.AVAILABLE)==0) {
                                listDate.add(shiftWork_model);
                            }
                        }
                        if (listDate.size()>0){
                            String workName = (String) dsp.child("workName").getValue();
                            String workerUID = (String) dsp.child("workerUID").getValue();
                            String description = (String) dsp.child("description").getValue();
                            String JobID = (String) dsp.child("JobID").getValue();
                            listJob.add(new Job_Model(listDate,category,description,workerUID,workName,JobID));
                        }
                        listDate = new ArrayList<>();
                    }
                }
                JobCategotyAdapter jobCategotyAdapter = new JobCategotyAdapter(context, listJob);
                lvJobsCategory.setAdapter(jobCategotyAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                lvJobsCategory.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void updateRentJob(final List<ShiftWork_Model> data, final String hirerUID, String JobID, final String category){
        databaseReference.child("JOBS").child(JobID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot date : dataSnapshot.child("DateTimes").getChildren()) {
                    for (ShiftWork_Model m : data) {
                        if (m.getDateTimeID().compareTo(date.getKey()) == 0) {
                            Log.d("afbefore", m.getStatus());
                            date.getRef().child("hirerUID").setValue(hirerUID);
                            date.getRef().child("status").setValue("unavailable");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static HashMap<String, List<ShiftWork_Model>> dateTimes(List<ShiftWork_Model> shiftWork_models) {
        HashMap<String, List<ShiftWork_Model>> timeJobs = new HashMap<String, List<ShiftWork_Model>>();
        List<ShiftWork_Model> timeBeginEnd = new ArrayList<>();
        Collections.sort(shiftWork_models, new Comparator<ShiftWork_Model>() {
            @Override
            public int compare(ShiftWork_Model o1, ShiftWork_Model o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        if (shiftWork_models.size() <= 1) {
            timeBeginEnd.add(shiftWork_models.get(0));
            timeJobs.put(shiftWork_models.get(0).getDate(), timeBeginEnd);
        } else {
            for (int i = 0; i < shiftWork_models.size(); i++) {
                if ((i + 1) < shiftWork_models.size()) {
                    if (shiftWork_models.get(i).getDate().compareTo(shiftWork_models.get(i + 1).getDate()) == 0) {
                        timeBeginEnd.add(shiftWork_models.get(i));
                    } else {
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(), timeBeginEnd);
                        timeBeginEnd = new ArrayList<>();
                    }
                } else {
                    if (shiftWork_models.get(i).getDate().compareTo(shiftWork_models.get(i - 1).getDate()) == 0) {
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(), timeBeginEnd);
                    } else {
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(), timeBeginEnd);
                    }
                }
            }
        }
        return timeJobs;
    }

    public static void getJobInProgress(final List<Job_Model> job_models, final RecyclerView lvJobInProgress, final Context context) {
        databaseReference.child("JOBS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShiftWork_Model> listDate = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    for (DataSnapshot date : dsp.child("DateTimes").getChildren()){
                        ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                        if (shiftWork_model.getStatus().compareTo(KeyValueFirebase.UNAVAILABLE)==0&&shiftWork_model.getHirerUID().compareTo(KeyValueFirebase.UID)==0) {
                            listDate.add(shiftWork_model);
                        }
                    }
                    if (listDate.size()>0){
                        String category = (String) dsp.child("category").getValue();
                        String workName = (String) dsp.child("workName").getValue();
                        String workerUID = (String) dsp.child("workerUID").getValue();
                        String description = (String) dsp.child("description").getValue();
                        String JobID = (String) dsp.child("JobID").getValue();
                        job_models.add(new Job_Model(listDate,category,description,workerUID,workName,JobID));
                    }
                    listDate = new ArrayList<>();
                }
                JobInProGressAdapter jobInProGressAdapter = new JobInProGressAdapter(context, job_models);
                lvJobInProgress.setAdapter(jobInProGressAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                lvJobInProgress.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getCountCategory(String s, final TextView txtCount) {
        databaseReference.child("CATEGORIES").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtCount.setText(" "+dataSnapshot.child("count").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
