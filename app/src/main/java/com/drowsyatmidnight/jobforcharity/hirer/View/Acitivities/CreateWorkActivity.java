package com.drowsyatmidnight.jobforcharity.hirer.View.Acitivities;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.hirer.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.hirer.Presenter.CreateWorkPresenter;
import com.drowsyatmidnight.jobforcharity.hirer.View.Adapters.DatetimeAdapter;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by davidtran on 7/16/17.
 */

public class CreateWorkActivity extends AppCompatActivity {

    CreateWorkPresenter mCreateWorkPresenter;
    List<Work.Datetime> mDatetimeList;
    ListView lvDatetime;

    EditText txtInputDate;
    EditText txtInputBeginTime;
    EditText txtInputEndTime;

    Button btnPostJob;
    Spinner spCategories;

    EditText txtJobName;
    EditText txtJobDetail;

    ImageButton btnAddDateTime;
    Calendar mCalendar = Calendar.getInstance();
    DatetimeAdapter mDatetimeAdapter;
    Work mWork = new Work();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        btnPostJob = (Button) findViewById(R.id.btnPostJob);
        txtJobDetail = (EditText) findViewById(R.id.txtJobDetail);
        txtJobName = (EditText) findViewById(R.id.txtInputJobName);
        txtInputDate = (EditText) findViewById(R.id.txtInputDate);
        txtInputBeginTime = (EditText) findViewById(R.id.txtInputBeginTime);
        txtInputEndTime = (EditText) findViewById(R.id.txtInputEndTime);

        lvDatetime = (ListView) findViewById(R.id.lvDatetime);
        btnAddDateTime = (ImageButton) findViewById(R.id.btnAddDateTime);


        spCategories = (Spinner) findViewById(R.id.spinnerCategories);

        mDatetimeList = new ArrayList<>();

        initPresenter();
        setupAdapter();
        setupListener();

        getUserFromFirebase();
    }
    private void initPresenter(){
        mCreateWorkPresenter = new CreateWorkPresenter();
    }



    private void createWork(){
        //String category = spCategories.getSelectedItem().toString();
        String name = txtJobName.getText().toString();
        String description = txtJobDetail.getText().toString();
        mWork.setCategoryID("123");
        mWork.setHirerUID("UID");
        mWork.setDescription(description);
        /*mWork.setDatetimes(mDatetimeList);*/
        mWork.setWorkName(name);
        mWork.setDescription(description);

        createWorkInFirebase(mWork,mDatetimeList);
    }
    public void createWorkInFirebase(Work work, List<Work.Datetime> mDateTimeList) {
        final List<Work.Datetime> DateTimeList = mDateTimeList;
        FirebaseAuth mAuth = Authority.sFirebaseAuth;
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final String UID = user.getUid();
        DataSnapshot dataSnapshotChild;


        // add list datetime to jobs
        final String key = mFirebaseDatabaseReference.push().getKey();
        mFirebaseDatabaseReference.child("JOBS")
                .child(key).setValue(work)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //TO DO SOMTHING WHEN COMPLETE
                        Log.d("an", "create work successfully");

                        for (Work.Datetime datetime:DateTimeList) {


                            //mFirebaseDatabaseReference.child("Jobs").child("DateTimes").push();
                            mFirebaseDatabaseReference.child("JOBS").child(key)
                                    .child("DateTimes").push().setValue(datetime)
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TO DO SOMTHING WHEN FAILURE
                        Log.d("an", "create work unsuccessfully");
                    }
                });

        // TO DO SOMETHING TO CREATE DATA IN FIREBASE
        //
        //
    }
    public void getUserFromFirebase() {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("USERS");
        UserRef.keepSynced(true);
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    Log.d("an:user uid",dataSnapshotChild.getKey());

                }

                // Check your arraylist size and pass to list view like

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void setupAdapter(){
        /*Setup categories adapter*/
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_categories,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(mAdapter);

        //Setup datetime listview
      mDatetimeAdapter = new DatetimeAdapter(mDatetimeList,this);
        lvDatetime.setAdapter(mDatetimeAdapter);

    }
    private void setupListener() {

        txtInputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClicked(v, txtInputDate);

            }
        });
        txtInputBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeClicked(v, txtInputBeginTime);
            }
        });
        txtInputEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeClicked(v, txtInputEndTime);
            }
        });

        btnAddDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = txtInputDate.getText().toString();
                String EndTime = txtInputEndTime.getText().toString();
                String BeginTime = txtInputBeginTime.getText().toString();
                Work.Datetime mDatetime = new Work.Datetime();
                mDatetime.setDate(Date);
                mDatetime.setBeginTime(BeginTime);
                mDatetime.setEndTime(EndTime);
                mDatetimeList.add(mDatetime);
                mDatetimeAdapter.notifyDataSetChanged();



            }
        });
        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWork();
            }
        });

    }

    public void onTimeClicked(View v, final EditText editText) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editText.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void onDateClicked(View v, final EditText editText) {

        final int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);

        DatePickerDialog mDatePickerDialog;

        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setText(year + "/" + month + "/" + day);
            }
        }, year, month, day);
        mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.show();
    }
}
