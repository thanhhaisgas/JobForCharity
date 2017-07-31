package com.drowsyatmidnight.jobforcharity.woker.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.woker.Models.LoadWorkListener;
import com.drowsyatmidnight.jobforcharity.woker.View.Adapters.WorkAdapter;
import com.drowsyatmidnight.jobforcharity.woker.View.Utils.Communicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 7/24/17.
 */

public class AllNeededWorksFragment extends Fragment implements LoadWorkListener {
    int countRun = 0;
    String workViewMode="";
    int mViewMode = 2;

    final int ALL_WORK_VIEW_MODE = 2;
    final int FEATURE_WORK_VIEW_MODE = 1;
    final int PROGRESSING_WORK_VIEW_MODE = 0;
    final int HISTORY_VIEW_MODE = -1;

    @BindView(R.id.exListJob)
    ExpandableListView mExpandableListView;

    BaseExpandableListAdapter adapter;
    List<Work> mWorkList = new ArrayList<>();
    static List<Job_Model> testlist = new ArrayList<>();
    private Communicator comm = null;
    List<List<Work.Datetime>> mListListDateTime = new ArrayList<>();

    public static AllNeededWorksFragment newInstance(int viewMode){
        AllNeededWorksFragment fragment = new AllNeededWorksFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("view_mode_key",viewMode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_neededworks, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        loadWorkFromFirebase();

        mExpandableListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        /*
        adapter = new WorkAdapter(getContext(),mWorkList);
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        */
    }

    private void setupAdapter(List<Job_Model> listJob){
        adapter = new WorkAdapter(listJob,getContext());
        //adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        //recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mExpandableListView.setAdapter(adapter);
    }


    @Override
    public void onLoadWorkSucess(List<Work> workList) {

    }

    @Override
    public void onLoadWorkFailure(String message) {
        mWorkList = new ArrayList<>();
        adapter.notifyDataSetChanged();

        Log.d("An","ms:"+message);
    }


    public void loadWorkFromFirebase() {
        final List<Work> mWorkList = new ArrayList<>();
        List<List<Work.Datetime>> mListListDateTime = new ArrayList<>();

        //listJob.clear();
        //TO DO SOMETHING TO GET DATA FROM FIREBASE
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("JOBS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Job_Model> listJob =new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String mWorkerUID = (String) dsp.child("workerUID").getValue();
                    String mWorkName = (String) dsp.child("workName").getValue();

                    if (mWorkerUID.equals(Authority.sFirebaseAuth.getCurrentUser().getUid())) {
                        List<ShiftWork_Model> listDate = new ArrayList<>();
                        // List<Job_Model> listJob = new ArrayList<>();
                        listDate.clear();
                        for (DataSnapshot date : dsp.child("DateTimes").getChildren()) {
                            ShiftWork_Model shiftWork_model = date.getValue(ShiftWork_Model.class);
                            listDate.add(shiftWork_model);

                        }
                        String category = (String) dsp.child("category").getValue();
                        String workName = (String) dsp.child("workName").getValue();

                        String description = (String) dsp.child("description").getValue();
                        listJob.add(new Job_Model(listDate, category, description, mWorkerUID, workName));

                        // mWorkList = listJob;Log

                    }
                }
                countRun++;
                    if(!listJob.isEmpty()) {


                }
                //testlist = listJob;
                if(getArguments().getInt("view_mode_key") == ALL_WORK_VIEW_MODE) {

                    bindListWorkOnAllWorkMode(listJob);
                }
                else if(getArguments().getInt("view_mode_key") == FEATURE_WORK_VIEW_MODE){

                    bindListWorkOnFeatureWorkMode(listJob);
                }
                else if(getArguments().getInt("view_mode_key") == PROGRESSING_WORK_VIEW_MODE){

                    bindListWorkOnProgressingWorkMode(listJob);
                }
                else if(getArguments().getInt("view_mode_key") == HISTORY_VIEW_MODE){

                    bindListWorkOnHistoryWorkMode(listJob);
                }
                WorkListGetting(listJob,countRun);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private List<Job_Model> WorkListGetting(List<Job_Model>worklist,int count){
        Toast.makeText(getContext(),"count:"+count,Toast.LENGTH_SHORT).show();
        return worklist;
    }
    private void bindListWorkOnAllWorkMode(List<Job_Model> workList){
        setupAdapter(workList);
    }
    private void bindListWorkOnFeatureWorkMode(List<Job_Model> workList){

        List<Job_Model> featureWorkList = new ArrayList<>();

        final Calendar c = Calendar.getInstance();
        int yyyy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        String sCurrentDate = yyyy+"/"+mm+"/"+dd;
        boolean compare = false;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = null;
        Date gotDate = null;
        try {
            currentDate = (Date) formatter.parse(sCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<workList.size();i++){
      //  for (Job_Model work: workList) {
            for (int j = 0;j<workList.get(i).getDateTimes().size();j++){
          //  for(ShiftWork_Model shiftwork:work.getDateTimes()){
                try {
                    gotDate = (Date) formatter.parse(workList.get(i).getDateTimes().get(j).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(gotDate.compareTo(currentDate)==-1) {
                    Log.d("An:Sgotdate",workList.get(i).getDateTimes().get(j).getDate());
                    Log.d("An:gotdate",gotDate.toString());
                    Log.d("An:Scurrdate",sCurrentDate);
                    Log.d("An:currdate",currentDate.toString());
                    workList.get(i).getDateTimes().remove(j);

                }
                if(workList.get(i).getDateTimes().isEmpty()) workList.remove(i);

            }
        }
        setupAdapter(workList);
    }
    private void bindListWorkOnProgressingWorkMode(List<Job_Model> workList){
        List<Job_Model> featureWorkList = new ArrayList<>();

        final Calendar c = Calendar.getInstance();
        int yyyy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        String sCurrentDate = yyyy+"/"+mm+"/"+dd;
        boolean compare = false;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = null;
        Date gotDate = null;
        try {
            currentDate = (Date) formatter.parse(sCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<workList.size();i++){
            //  for (Job_Model work: workList) {
            for (int j = 0;j<workList.get(i).getDateTimes().size();j++){
                //  for(ShiftWork_Model shiftwork:work.getDateTimes()){
                try {
                    gotDate = (Date) formatter.parse(workList.get(i).getDateTimes().get(j).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(gotDate.compareTo(currentDate)!=0) {
                    Log.d("An:Sgotdate",workList.get(i).getDateTimes().get(j).getDate());
                    Log.d("An:gotdate",gotDate.toString());
                    Log.d("An:Scurrdate",sCurrentDate);
                    Log.d("An:currdate",currentDate.toString());
                    workList.get(i).getDateTimes().remove(j);

                }
                if(workList.get(i).getDateTimes().isEmpty()) workList.remove(i);
            }
        }
        setupAdapter(workList);
    }
    private void bindListWorkOnHistoryWorkMode(List<Job_Model> workList){

        final Calendar c = Calendar.getInstance();
        int yyyy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        String sCurrentDate = yyyy+"/"+mm+"/"+dd;
        boolean compare = false;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = null;
        Date gotDate = null;
        try {
            currentDate = (Date) formatter.parse(sCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<workList.size();i++){
            //  for (Job_Model work: workList) {
            for (int j = 0;j<workList.get(i).getDateTimes().size();j++){
                //  for(ShiftWork_Model shiftwork:work.getDateTimes()){
                try {
                    gotDate = (Date) formatter.parse(workList.get(i).getDateTimes().get(j).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(gotDate.compareTo(currentDate)==1) {
                    Log.d("An:Sgotdate",workList.get(i).getDateTimes().get(j).getDate());
                    Log.d("An:gotdate",gotDate.toString());
                    Log.d("An:Scurrdate",sCurrentDate);
                    Log.d("An:currdate",currentDate.toString());
                    workList.get(i).getDateTimes().remove(j);

                }
                if(workList.get(i).getDateTimes().isEmpty()) workList.remove(i);
            }
        }



        setupAdapter(workList);

    }
}
