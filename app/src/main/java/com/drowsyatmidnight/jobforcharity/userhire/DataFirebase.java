package com.drowsyatmidnight.jobforcharity.userhire;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.model.Category_Model;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.Review_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobCategotyAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobInHistoryApdapter;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobInProGressAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.ReviewsAdapter;
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

    public static void getUserInfo(String UID, final TextView txtTenDetail, final TextView txtPhoneNumDetail, final TextView txtEmailDetail, final RatingBar ratingBar, final TextView txtCountRate){
        databaseReference.child("USERS").child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_Model user_model = dataSnapshot.getValue(User_Model.class);
                txtTenDetail.setText(user_model.getLName()+" "+user_model.getFName());
                txtPhoneNumDetail.setText(user_model.getMobilePhone());
                txtEmailDetail.setText(user_model.getEmail());
                ratingBar.setRating(Float.parseFloat(user_model.getRate()));
                txtCountRate.setText(user_model.getCountRate()+" reviews");
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

    public static void getJobsCategory(final String categoryName, final RecyclerView lvJobsCategory, final Context context, final Activity activity){
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
                            if (shiftWork_model.getStatus().compareTo(KeyValueFirebase.AVAILABLE)==0&&shiftWork_model.getDeletedStatus().compareTo("true")!=0) {
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
                JobCategotyAdapter jobCategotyAdapter = new JobCategotyAdapter(context, listJob, activity);
                lvJobsCategory.setAdapter(jobCategotyAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                lvJobsCategory.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void updateRentJob(final List<ShiftWork_Model> data, final String hirerUID, String JobID, final String status){
        databaseReference.child("JOBS").child(JobID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot date : dataSnapshot.child("DateTimes").getChildren()) {
                    for (ShiftWork_Model m : data) {
                        if (m.getDateTimeID().compareTo(date.getKey()) == 0 && status.compareTo(KeyValueFirebase.RENT)==0) {
                            date.getRef().child("hirerUID").setValue(hirerUID);
                            date.getRef().child("status").setValue("unavailable");
                        }
                        if (m.getDateTimeID().compareTo(date.getKey()) == 0 && status.compareTo(KeyValueFirebase.CANCEL)==0) {
                            date.getRef().child("hirerUID").setValue("");
                            date.getRef().child("status").setValue("available");
                        }
                        if (m.getDateTimeID().compareTo(date.getKey()) == 0 && status.compareTo(KeyValueFirebase.DONE)==0) {
                            date.getRef().child("status").setValue("completed");
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

    public static void getJobInHistory(final List<Job_Model> job_models, final RecyclerView lvJobInProgress, final Context context) {
        databaseReference.child("JOBS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShiftWork_Model> listDate = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    for (DataSnapshot date : dsp.child("DateTimes").getChildren()){
                        ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                        if ((shiftWork_model.getStatus().compareTo(KeyValueFirebase.JOB_COMPLETED)==0)&&shiftWork_model.getHirerUID().compareTo(KeyValueFirebase.UID)==0) {
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
                JobInHistoryApdapter jobInHistoryApdapter = new JobInHistoryApdapter(context, job_models);
                lvJobInProgress.setAdapter(jobInHistoryApdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                lvJobInProgress.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void pushReviews(final int numStars, String text, String jobID, final String uid, final String workerID) {
        Review_Model review_model = new Review_Model(uid,text,jobID,"","");
        final String key = databaseReference.push().getKey();
        databaseReference.child("REVIEWS").child(workerID).child(key).setValue(review_model);
        databaseReference.child("USERS").child(workerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String countRate = (String) dataSnapshot.child("countRate").getValue();
                String pointStar = (String) dataSnapshot.child("rate").getValue();
                dataSnapshot.getRef().child("countRate").setValue(String.valueOf(Integer.parseInt(countRate)+1));
                dataSnapshot.getRef().child("rate").setValue(String.valueOf(Integer.parseInt(pointStar)+numStars));
                databaseReference.child("USERS").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String fname = (String) dataSnapshot.child("fname").getValue();
                        String lname = (String) dataSnapshot.child("lname").getValue();
                        databaseReference.child("REVIEWS").child(workerID).child(key).child("fName").setValue(fname);
                        databaseReference.child("REVIEWS").child(workerID).child(key).child("lName").setValue(lname);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getReviews(final RecyclerView lvJobInProgress, final Context context, String workerUID) {
        databaseReference.child("REVIEWS").child(workerUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Review_Model> review_models = new ArrayList<>();
                for (DataSnapshot reviewModel : dataSnapshot.getChildren()){
                    String comment = (String) reviewModel.child("comment").getValue();
                    if(comment.trim().length()!=0){
                        String hirerUID = (String) reviewModel.child("hirerUID").getValue();
                        String jobID = (String) reviewModel.child("jobID").getValue();
                        String lName = (String) reviewModel.child("lName").getValue();
                        String fName = (String) reviewModel.child("fName").getValue();
                        Review_Model review_model = new Review_Model(hirerUID,comment,jobID,lName,fName);
                        review_models.add(review_model);
                    }
                }
                if (review_models.size()>0){
                    Log.d("test", String.valueOf(review_models.size()));
                    ReviewsAdapter adapter = new ReviewsAdapter(context, review_models);
                    lvJobInProgress.setAdapter(adapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    lvJobInProgress.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void searchJobOrderCategory(final String categoryName, final RecyclerView lvJobsCategory, final Context context, final Activity activity, final String query) {
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
                            if (workName.contains(query)){
                                listJob.add(new Job_Model(listDate,category,description,workerUID,workName,JobID));
                            }
                        }
                        listDate = new ArrayList<>();
                    }
                }
                JobCategotyAdapter jobCategotyAdapter = new JobCategotyAdapter(context, listJob, activity);
                lvJobsCategory.setAdapter(jobCategotyAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                lvJobsCategory.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
