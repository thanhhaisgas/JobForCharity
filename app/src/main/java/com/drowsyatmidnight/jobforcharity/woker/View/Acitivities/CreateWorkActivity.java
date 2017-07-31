package com.drowsyatmidnight.jobforcharity.woker.View.Acitivities;


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
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.woker.Presenter.CreateWorkPresenter;
import com.drowsyatmidnight.jobforcharity.woker.View.Adapters.DatetimeAdapter;
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.drowsyatmidnight.jobforcharity.woker.View.Utils.CommDateTimeAdapter;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by davidtran on 7/16/17.
 */

public class CreateWorkActivity extends AppCompatActivity  {

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
    ImageButton btnDeleteDateTime;
    Calendar mCalendar = Calendar.getInstance();
    DatetimeAdapter mDatetimeAdapter;
    Work mWork = new Work();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        btnDeleteDateTime = (ImageButton) findViewById(R.id.btnDeleteDateTime);

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


    }
    private void initPresenter(){
        mCreateWorkPresenter = new CreateWorkPresenter();
    }



    private void createWork(){
        //String category = spCategories.getSelectedItem().toString();
        final String name = txtJobName.getText().toString();
        final String description = txtJobDetail.getText().toString();
        final String Category = spCategories.getSelectedItem().toString();
        String UID = "";
        try {
            UID = Authority.sFirebaseAuth.getCurrentUser().getUid();
        }catch (Exception e){
            Log.d("An","Failed to get uid when create job");
        }
        mWork.setCategory(Category);
        mWork.setWorkerUID(UID);
        mWork.setDescription(description);
        /*mWork.setDatetimes(mDatetimeList);*/
        mWork.setWorkName(name);
        mWork.setDescription(description);

        createWorkInFirebase(mWork,mDatetimeList);
    }
    public void createWorkInFirebase(final Work work, List<Work.Datetime> mDateTimeList) {
        final List<Work.Datetime> DateTimeList = mDateTimeList;
        FirebaseAuth mAuth = Authority.sFirebaseAuth;
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DataSnapshot dataSnapshotChild;

        // add list work
        final String key = mFirebaseDatabaseReference.push().getKey();
        mFirebaseDatabaseReference.child("JOBS")
                .child(key).setValue(work)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Update job count to category
                        mFirebaseDatabaseReference.child("CATEGORIES")
                                .child(work.getCategory()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String strCount = String.valueOf(dataSnapshot.child("count").getValue());
                                int count = Integer.parseInt(strCount);
                                count++;
                                dataSnapshot.getRef().child("count").setValue(count+++"");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        Log.d("an", "create work successfully");
                        mFirebaseDatabaseReference.child("JOBS")
                                .child(key).child("JobID").setValue(key);

                        // add list datetime to work
                        for (Work.Datetime datetime:DateTimeList) {
                            mFirebaseDatabaseReference.child("JOBS").child(key)
                                    .child("DateTimes").push().setValue(datetime)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //put datetimeid into itself
                                            mFirebaseDatabaseReference.child("JOBS").child(key)
                                                    .child("DateTimes").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                                                        String DateTimeID = dsp.getKey();
                                                        mFirebaseDatabaseReference.child("JOBS").child(key)
                                                                .child("DateTimes").child(DateTimeID)
                                                                .child("DateTimeID").setValue(DateTimeID);


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

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



    private void setupAdapter(){
        /*Setup categories adapter*/
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.listCategoryName,
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


        btnDeleteDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("an: delete clicked","true");
                for (int i:mDatetimeAdapter.indexSelected)
                mDatetimeList.remove(i);
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


        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar myCalendar = Calendar.getInstance();
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtInputDate.setText(year+"/"+monthOfYear+"/"+dayOfMonth);

            }

        };
        new DatePickerDialog(CreateWorkActivity.this, mDateSetListener, Calendar.getInstance()
                .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();

  /*      mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.show();*/
    }


    /*@Override
    public void onSelectDateTime(final List<Integer> selectedIndex) {
        btnDeleteDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatetimeList.remove(selectedIndex);
                mDatetimeAdapter.notifyDataSetChanged();
            }
        });
    }*/
}
