package com.drowsyatmidnight.jobforcharity.hirer;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.drowsyatmidnight.jobforcharity.R;

import java.util.Calendar;

/**
 * Created by davidtran on 7/16/17.
 */

public class CreateJobActivity extends Activity {

    EditText txtInputDate;
    EditText txtInputBeginTime;
    EditText txtInputEndTime;
    Button btnPostJob;
    Spinner spCategories;
    Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        txtInputDate = (EditText) findViewById(R.id.txtInputDate);
        txtInputBeginTime = (EditText) findViewById(R.id.txtInputBeginTime);
        txtInputEndTime = (EditText) findViewById(R.id.txtInputEndTime);
        spCategories = (Spinner) findViewById(R.id.spinnerCategories);

        setupAdapter();
        setupListener();
    }

    private void setupAdapter(){
        /*Setup categories adapter*/
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_categories,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(mAdapter);


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
