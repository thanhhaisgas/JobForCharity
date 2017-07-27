package com.drowsyatmidnight.jobforcharity.userhire;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.model.Category_Model;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobCategotyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haint on 26/07/2017.
 */

public class DataFirebase {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static void getUserInfo(final TextView txtTenDetail, final TextView txtPhoneNumDetail, final TextView txtEmailDetail){
        databaseReference.child("USERS").child(KeyValueFirebase.UID).addValueEventListener(new ValueEventListener() {
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
                listDate.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    String category = (String) dsp.child("category").getValue();
                    if (category.compareTo(categoryName)==0){
                        for (DataSnapshot date : dsp.child("DateTimes").getChildren()){
                            ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                            listDate.add(shiftWork_model);
                        }
                        String workName = (String) dsp.child("workName").getValue();
                        String hirerUID = (String) dsp.child("hirerUID").getValue();
                        String description = (String) dsp.child("description").getValue();
                        listJob.add(new Job_Model(listDate,category,description,hirerUID,workName));
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

}
