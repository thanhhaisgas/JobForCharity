package com.drowsyatmidnight.jobforcharity.woker.View.Adapters;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by haint on 27/07/2017.
 */

public class AdapterDateTimes extends BaseExpandableListAdapter {
    private Job_Model mJobModel;
    private ImageButton btnDeleteTime;
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<ShiftWork_Model>> expandableListDetail;
    public List<ShiftWork_Model> listSelected = new ArrayList<>();
    private String view_type;

    public AdapterDateTimes(Context context,Job_Model job_model, List<String> expandableListTitle,
                            HashMap<String, List<ShiftWork_Model>> expandableListDetail, String view_type) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.view_type = view_type;
        mJobModel = job_model;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ShiftWork_Model expandedListText = (ShiftWork_Model) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.  LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_detailwork_times, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.txtJobDetailTimeStart);
        expandedListTextView.setText(expandedListText.getBeginTime());
        TextView expandedListTextView2 = (TextView) convertView
                .findViewById(R.id.txtJobDetailTimeEnd);
        expandedListTextView2.setText(expandedListText.getEndTime());
        TextView txtJobDetailSalary = (TextView) convertView
                .findViewById(R.id.txtJobDetailSalary);
        txtJobDetailSalary.setText(expandedListText.getSalary());
        btnDeleteTime = (ImageButton) convertView.findViewById(R.id.btnDeleteTime);
        selectTime(convertView, listPosition, expandedListPosition);

        String mDateTimeID = this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition).getDateTimeID();
        deleteTimeListener(mDateTimeID,mJobModel,btnDeleteTime);


        return convertView;
    }

    private void selectTime(final View convertView, final int listPosition, final int expandedListPosition) {
        final CardView bg = (CardView) convertView.findViewById(R.id.cardViewJobDateTime);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color=0;
                int check = -5317;
                Drawable d = bg.getBackground();
                if (d instanceof ColorDrawable)
                    color = ((ColorDrawable) d).getColor();
                if(color==check){
                    bg.setBackgroundColor(Color.TRANSPARENT);
                }else {
                    bg.setBackgroundColor(convertView.getResources().getColor(R.color.yellow));
                }
                ShiftWork_Model dataSelected = (ShiftWork_Model) getChild(listPosition, expandedListPosition);
                storeData(dataSelected);
            }
        });
    }

    private void storeData(ShiftWork_Model dataSelected) {
        String newTime = dataSelected.getBeginTime()+dataSelected.getEndTime();
        int pos = 0;
        boolean check = false;
        for (int i=0; i<listSelected.size(); i++){
            String oldTime = listSelected.get(i).getBeginTime()+listSelected.get(i).getEndTime();
            if (newTime.compareTo(oldTime)==0){
                check = true;
                pos = i;
            }
        }
        if (check == true){
            listSelected.remove(pos);
        }else {
            listSelected.add(dataSelected);
        }
    }


    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_detailjob_date, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.txtJobDetailDate);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
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
    }

}