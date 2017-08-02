package com.drowsyatmidnight.jobforcharity.woker.View.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.GroupedDateTimeWork;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Job_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.View.Utils.CommDateTimeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davidtran on 8/1/17.
 */

public class DetailDateTimeAdapter extends BaseExpandableListAdapter {
    private ImageButton btnDeleteTime;
    public List<ShiftWork_Model> listSelected = new ArrayList<>();
    CommDateTimeAdapter mCommDateTimeAdapter= null;
    TextView dateHeader;
    TextView beginTimeItem;
    TextView endTimeItem;
    TextView salaryItem;
    TextView statusItem;
    Context mContext;
Activity mActivity;
    Job_Model mJobModel;
    Map<String, List<ShiftWork_Model>> map = new LinkedHashMap<String, List<ShiftWork_Model>>();
    List<GroupedDateTimeWork> groupedDateTimeWorks =  new ArrayList<>();
    public DetailDateTimeAdapter(Context context, List<GroupedDateTimeWork> groupedDateTimeWorks, Job_Model job_model, Activity activity) {
        this.groupedDateTimeWorks = groupedDateTimeWorks;
        this.mContext = context;
        this.mJobModel = job_model;
        this.mActivity = activity;
    }

    @Override
    public int getGroupCount() {
        return groupedDateTimeWorks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

       /* String value = example.get(name).toString();
        return map.keySet(groupPosition).;*/
       return groupedDateTimeWorks.get(groupPosition).getShiftWorkModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupedDateTimeWorks.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupedDateTimeWorks.get(groupPosition).getShiftWorkModels();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        /*ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);*/


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.  LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_detailwork_date, null);
        }
        dateHeader = (TextView) convertView.findViewById(R.id.txtJobDetailDate);
        dateHeader.setText(groupedDateTimeWorks.get(groupPosition).getDate());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.  LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_detailwork_times, null);
        }

        beginTimeItem = (TextView) convertView.findViewById(R.id.txtJobDetailTimeStart);
        endTimeItem = (TextView) convertView.findViewById(R.id.txtJobDetailTimeEnd);
        salaryItem = (TextView) convertView.findViewById(R.id.txtJobDetailSalary);
        statusItem = (TextView) convertView.findViewById(R.id.datetimeStatus);

        beginTimeItem.setText(groupedDateTimeWorks
                .get(groupPosition).getShiftWorkModels().get(childPosition).getBeginTime());
        endTimeItem.setText(groupedDateTimeWorks
                .get(groupPosition).getShiftWorkModels().get(childPosition).getEndTime());
        salaryItem.setText(groupedDateTimeWorks
                .get(groupPosition).getShiftWorkModels().get(childPosition).getSalary());
        statusItem.setText(groupedDateTimeWorks
                .get(groupPosition).getShiftWorkModels().get(childPosition).getStatus());

        btnDeleteTime = (ImageButton) convertView.findViewById(R.id.btnDeleteTime);


        String DateTimeID = groupedDateTimeWorks.get(groupPosition).getShiftWorkModels().get(childPosition).getDateTimeID();
        deleteTimeListener(DateTimeID, mJobModel, btnDeleteTime);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



    private void deleteTimeListener(final String datetimeid , final Job_Model jobModel, ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("JOBS").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String mWorkerUID = (String) dsp.child("workerUID").getValue();
                            String mWorkName = (String) dsp.child("workName").getValue();
                            String mDescription = (String) dsp.child("description").getValue();
                            if(jobModel.getWokerUID().compareTo(mWorkerUID)==0
                                    && jobModel.getDescription().compareTo(mDescription)==0){

                                dsp.child("DateTimes").child(datetimeid).getRef()
                                        .child("deletedStatus").setValue("true");
                                break;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
      //notifyDataSetChanged();
        mCommDateTimeAdapter = (CommDateTimeAdapter) mActivity;
        mCommDateTimeAdapter.onDateTimeDeletedSuccess();
    }
}
