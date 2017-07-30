package com.drowsyatmidnight.jobforcharity.woker.View.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.View.Fragments.DetailMyWorkFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends BaseExpandableListAdapter {


    List<Job_Model> mJobModelList;
    Context mContext;
    Activity mActivity;

    public WorkAdapter(List<Job_Model> jobModelList, Context context) {
        mJobModelList = jobModelList;

        mContext = context;
    }


    @Override
    public int getGroupCount() {
        return mJobModelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mJobModelList.get(groupPosition).getDateTimes().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mJobModelList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mJobModelList.get(groupPosition).getDateTimes().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 10 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = mJobModelList.get(groupPosition).getWorkName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_header_work, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.item_header_name);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        Button mImageButton = (Button) convertView.findViewById(R.id.btnDetailMyWork);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailMyWorkFragment fragment = new DetailMyWorkFragment();
                FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mywork_container, fragment);
                transaction.addToBackStack(null).commit();

            }
        });

        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String mDate = mJobModelList.get(groupPosition).getDateTimes().get(childPosition).getDate();
        final String beginTime = mJobModelList.get(groupPosition).getDateTimes().get(childPosition).getBeginTime();
        final String endTime = mJobModelList.get(groupPosition).getDateTimes().get(childPosition).getEndTime();
        final String status = mJobModelList.get(groupPosition).getDateTimes().get(childPosition).getStatus();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_datetime_work, null);
        }
        TextView txtDate = (TextView) convertView
                .findViewById(R.id.item_date);
        TextView txtBeginTime = (TextView) convertView
                .findViewById(R.id.item_begintime);
        TextView txtEndTime = (TextView) convertView
                .findViewById(R.id.item_endtime);
        TextView txtStatus = (TextView) convertView
                .findViewById(R.id.item_datetime_status);


        txtDate.setText(mDate);
        txtBeginTime.setText(beginTime);
        txtEndTime.setText(endTime);
        txtStatus.setText(status);



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
