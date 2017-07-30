package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.KeyValueFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.AdapterDateTimes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haint on 26/07/2017.
 */

public class FmJobDetail extends Fragment {

    private View rootView;
    @BindView(R.id.detailNameJob)
    TextView detailNameJob;
    @BindView(R.id.detailDescriptionJob)
    TextView detailDescriptionJob;
    @BindView(R.id.lvJobDetailDateTime)
    ExpandableListView lvJobDetailDateTime;
    @BindView(R.id.btnRentJob)
    Button btnRentJob;
    @BindView(R.id.scroll1)
    NestedScrollView scroll1;
    @BindView(R.id.btnCancelJob)
    Button btnCancelJob;
    private AdapterDateTimes adapterDateTimes;
    private List<ShiftWork_Model> shiftWork_models;
    private String JobID;

    public static FmJobDetail newInstance(String nameWork, String Description, Serializable shiftWork_models, String JobID, String CategoryName, String ViewType, String workerID) {
        FmJobDetail fmJobDetail = new FmJobDetail();

        Bundle args = new Bundle();
        args.putString("nameWork", nameWork);
        args.putString("Description", Description);
        args.putSerializable("shiftWorks", shiftWork_models);
        args.putString("JobID", JobID);
        args.putString("category", CategoryName);
        args.putString("view_type", ViewType);
        args.putString("workerID", workerID);
        fmJobDetail.setArguments(args);

        return fmJobDetail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.job_detail_jobinfo, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpView();
        addEvents();
    }

    private void addEvents() {
        btnRentJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapterDateTimes.listSelected.size()==0){
                    Toast.makeText(rootView.getContext(),"Please, select at least one schedule", Toast.LENGTH_SHORT).show();
                }else {
                    if (btnRentJob.getText().toString().toUpperCase().compareTo(KeyValueFirebase.RENT.toUpperCase())==0){
                        DataFirebase.updateRentJob(adapterDateTimes.listSelected, KeyValueFirebase.UID, JobID, KeyValueFirebase.RENT);
                    }
                    if (btnRentJob.getText().toString().toUpperCase().compareTo(KeyValueFirebase.DONE.toUpperCase())==0){
                        DataFirebase.updateRentJob(adapterDateTimes.listSelected, KeyValueFirebase.UID, JobID, KeyValueFirebase.DONE);
                        showDialogReview();
                    }
                }
            }
        });
        btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapterDateTimes.listSelected.size()==0){
                    Toast.makeText(rootView.getContext(),"Please, select at least one schedule", Toast.LENGTH_SHORT).show();
                }else {
                    DataFirebase.updateRentJob(adapterDateTimes.listSelected, KeyValueFirebase.UID, JobID, KeyValueFirebase.CANCEL);
                }
            }
        });
    }

    private void showDialogReview() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(rootView.getContext());
        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        View dialogView = inflater.inflate(R.layout.review_dialog, null);
        dialogBuilder.setView(dialogView);
        ImageView imgProfileReview = (ImageView) dialogView.findViewById(R.id.imgProfileReview);
        imgProfileReview.setImageResource(R.drawable.test);
        final RatingBar rateBarReview = (RatingBar) dialogView.findViewById(R.id.rateBarReview);
        final EditText edCommentReviews = (EditText) dialogView.findViewById(R.id.edCommentReviews);
        dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataFirebase.pushReviews(rateBarReview.getNumStars(), edCommentReviews.getText().toString(), JobID, KeyValueFirebase.UID, getArguments().getString("workerID"));
            }
        });
        dialogBuilder.setTitle(R.string.review);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void setUpView() {
        if (getArguments().getString("view_type").compareTo(KeyValueFirebase.VIEW_JOBDETAILS)!=0){
            btnCancelJob.setVisibility(View.VISIBLE);
            btnRentJob.setText(getResources().getString(R.string.done));
        }
        detailNameJob.setText(getArguments().getString("nameWork"));
        detailDescriptionJob.setText(getArguments().getString("Description"));
        JobID = getArguments().getString("JobID");
        setUpTimes();
        setHeightLV();
    }

    private void setHeightLV() {
        for (int i = 0; i < adapterDateTimes.getGroupCount(); i++)
            lvJobDetailDateTime.expandGroup(i);
        setListViewHeight(lvJobDetailDateTime);
        lvJobDetailDateTime.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });
    }

    private void setListViewHeight(ExpandableListView listView) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupView = listAdapter.getGroupView(i, true, null, listView);
            groupView.measure(0, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupView.getMeasuredHeight();

            if (listView.isGroupExpanded(i)){
                for(int j = 0; j < listAdapter.getChildrenCount(i); j++){
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(0, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setUpTimes() {
        shiftWork_models = new ArrayList<>();
        shiftWork_models.addAll((Collection<? extends ShiftWork_Model>) getArguments().getSerializable("shiftWorks"));
        HashMap<String, List<ShiftWork_Model>> timeJobs = new HashMap<String, List<ShiftWork_Model>>();
        timeJobs = DataFirebase.dateTimes(shiftWork_models);

        List<String> date = new ArrayList<>(timeJobs.keySet());
        adapterDateTimes = new AdapterDateTimes(rootView.getContext(), date, timeJobs, getArguments().getString("view_type"));
        lvJobDetailDateTime.setAdapter(adapterDateTimes);
        lvJobDetailDateTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
