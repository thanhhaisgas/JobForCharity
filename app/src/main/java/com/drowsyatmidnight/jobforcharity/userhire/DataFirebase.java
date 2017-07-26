package com.drowsyatmidnight.jobforcharity.userhire;

import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.model.Category_model;
import com.drowsyatmidnight.jobforcharity.model.User_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by haint on 26/07/2017.
 */

public class DataFirebase {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static void getUserInfo(final TextView txtTenDetail, final TextView txtPhoneNumDetail, final TextView txtEmailDetail){
        databaseReference.child("USERS").child(KeyValueFirebase.UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_model user_model = dataSnapshot.getValue(User_model.class);
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
                        Category_model category_model = new Category_model(categoryName[i], "0");
                        databaseReference.child("CATEGORIES").child(categoryName[i]).setValue(category_model);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
