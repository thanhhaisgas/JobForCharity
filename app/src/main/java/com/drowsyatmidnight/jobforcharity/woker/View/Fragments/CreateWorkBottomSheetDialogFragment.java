package com.drowsyatmidnight.jobforcharity.woker.View.Fragments;

/**
 * Created by davidtran on 7/29/17.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
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
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.woker.Presenter.CreateWorkPresenter;
import com.drowsyatmidnight.jobforcharity.woker.View.Acitivities.CreateWorkActivity;
import com.drowsyatmidnight.jobforcharity.woker.View.Adapters.DatetimeAdapter;
import com.drowsyatmidnight.jobforcharity.woker.View.Utils.Communicator;
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
import java.util.List;

public class CreateWorkBottomSheetDialogFragment extends BottomSheetDialogFragment {

    Communicator comm = null;
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
    
   
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        //noinspection RestrictedApi
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.activity_create_job, null);
        dialog.setContentView(inflatedView);

        btnPostJob = (Button) inflatedView.findViewById(R.id.btnPostJob);
        txtJobDetail = (EditText) inflatedView.findViewById(R.id.txtJobDetail);
        txtJobName = (EditText) inflatedView.findViewById(R.id.txtInputJobName);
        txtInputDate = (EditText) inflatedView.findViewById(R.id.txtInputDate);
        txtInputBeginTime = (EditText) inflatedView.findViewById(R.id.txtInputBeginTime);
        txtInputEndTime = (EditText) inflatedView.findViewById(R.id.txtInputEndTime);

        lvDatetime = (ListView) inflatedView.findViewById(R.id.lvDatetime);
        btnAddDateTime = (ImageButton) inflatedView.findViewById(R.id.btnAddDateTime);


        spCategories = (Spinner) inflatedView.findViewById(R.id.spinnerCategories);

        mDatetimeList = new ArrayList<>();

        initPresenter();
        setupAdapter();
        setupListener();
        
        
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);
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


                                                        /*String category = (String) dsp.child("category").getValue();
                                                        String workName = (String) dsp.child("workName").getValue();
                                                        String description = (String) dsp.child("description").getValue();

                                                        String Date = (String) dsp.child(key).child("date").getValue();
                                                        String mEndTime = (String) dsp.child(key).child("endTime").getValue();
                                                        String mBeginTime = (String) dsp.child(key).child("beginTime").getValue();
                                                        String mHirerUID = (String) dsp.child(key).child("hirerUID").getValue();
                                                        String mStatus = (String) dsp.child(key).child("status").getValue();

                                                        ShiftWork_Model shiftWork_model = new ShiftWork_Model();
                                                        shiftWork_model.setDateTimeID(DateTimeID);
                                                        shiftWork_model.setDate(Date);
                                                        shiftWork_model.setHirerUID(mHirerUID);
                                                        shiftWork_model.setBeginTime(mBeginTime);
                                                        shiftWork_model.setEndTime(mEndTime);
                                                        shiftWork_model.setStatus(mStatus);
                                                        comm = (Communicator) getActivity();
                                                        comm.onDateTimeChanged(new Work(category,workName,description),shiftWork_model);*/
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
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.listCategoryName,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(mAdapter);


        //Setup datetime listview
        mDatetimeAdapter = new DatetimeAdapter(mDatetimeList,getContext());
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

                mDatetime.setStatus("available");
                mDatetime.setDate(Date);
                mDatetime.setBeginTime(BeginTime);
                mDatetime.setEndTime(EndTime);
                mDatetime.setHirerUID("");

                mDatetimeList.add(mDatetime);
                // Sort datetimelist base on date
                Collections.sort(mDatetimeList, new Comparator<Work.Datetime>() {
                    @Override
                    public int compare(Work.Datetime o1, Work.Datetime o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
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

        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
        new DatePickerDialog(getContext(), mDateSetListener, Calendar.getInstance()
                .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();

  /*      mDatePickerDialog.setTitle("Select Date");
        mDatePickerDialog.show();*/
    }
}