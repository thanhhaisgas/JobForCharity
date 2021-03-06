package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.JobDetail;
import com.drowsyatmidnight.jobforcharity.userhire.KeyValueFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.AdapterDateTimes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by haint on 26/07/2017.
 */

public class FmJobDetail extends Fragment{

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
                        showWarningDialogRent(getString(R.string.Dialog_title_warning), getString(R.string.Dialog_content_rent_warning),
                                getString(R.string.Dialog_title_rent_success), getString(R.string.Dialog_content_rent_success), KeyValueFirebase.RENT);
                        //DataFirebase.updateCountCategory(KeyValueFirebase.RENT, getArguments().getString("category"), getArguments().getString("JobID"));
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
                    showWarningDialogRent(getString(R.string.Dialog_title_warning), getString(R.string.Dialog_content_cancel_warning),
                            getString(R.string.Dialog_title_cancel_success), "", KeyValueFirebase.CANCEL);
                    //DataFirebase.updateCountCategory(KeyValueFirebase.CANCEL, getArguments().getString("category"), getArguments().getString("JobID"));
                }
            }
        });
        ((JobDetail)getActivity()).setOnMenuFabSelected(new JobDetail.OnMenuFabSelected() {
            @Override
            public void onSelected(int i) {
                if (i==1 || i==2){
                    btnRentJob.performClick();
                }
                if (i == 3){
                    btnCancelJob.performClick();
                }
            }
        });
    }

    private void showWarningDialogRent(String title_warning, String content_warning, final String title_success, final String content_success, final String buttonClick){
        new SweetAlertDialog(rootView.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title_warning)
                .setContentText(content_warning)
                .setConfirmText(getString(R.string.ok))
                .setCancelText(getString(R.string.cancel))
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (buttonClick.compareTo(KeyValueFirebase.RENT)==0){
                            DataFirebase.updateRentJob(adapterDateTimes.listSelected, KeyValueFirebase.UID, JobID, KeyValueFirebase.RENT);
                        }
                        if (buttonClick.compareTo(KeyValueFirebase.CANCEL)==0){
                            DataFirebase.updateRentJob(adapterDateTimes.listSelected, KeyValueFirebase.UID, JobID, KeyValueFirebase.CANCEL);
                        }
                        sweetAlertDialog
                                .setTitleText(title_success)
                                .setContentText(content_success)
                                .showCancelButton(false)
                                .setConfirmText(getString(R.string.ok))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        getActivity().onBackPressed();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }).show();
    }

    private void showDialogReview() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(rootView.getContext());
        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        View dialogView = inflater.inflate(R.layout.review_dialog, null);
        dialogBuilder.setView(dialogView);
        ImageView imgProfileReview = (ImageView) dialogView.findViewById(R.id.imgProfileReview);
        imgProfileReview.setImageResource(R.drawable.test);
        final RatingBar rateBarReview = (RatingBar) dialogView.findViewById(R.id.rateBarReview);
        LayerDrawable stars = (LayerDrawable) rateBarReview.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        final EditText edCommentReviews = (EditText) dialogView.findViewById(R.id.edCommentReviews);
        final TextView txtNameReview = (TextView) dialogView.findViewById(R.id.txtNameReview);
        DataFirebase.getUserInfo(getArguments().getString("workerID"), new DataFirebase.OnGetDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        User_Model user_model = data.getValue(User_Model.class);
                        txtNameReview.setText(user_model.getLName()+" "+user_model.getFName());
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
        Button btnReviewCacel = (Button) dialogView.findViewById(R.id.btnReviewCacel);
        Button btnReviewSend = (Button) dialogView.findViewById(R.id.btnReviewSend);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnReviewCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showThankYou();
            }
        });
        btnReviewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFirebase.pushReviews(rateBarReview.getNumStars(), edCommentReviews.getText().toString(), JobID, KeyValueFirebase.UID, getArguments().getString("workerID"));
                showThankYou();
                alertDialog.dismiss();
            }
        });
    }

    private void showThankYou(){
        new SweetAlertDialog(rootView.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.thank_you))
                .setContentText(getString(R.string.Dialog_content_done_warning))
                .setConfirmText(getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        getActivity().onBackPressed();
                    }
                }).show();
    }

    private void setUpView() {
        if (getArguments().getString("view_type").compareTo(KeyValueFirebase.VIEW_JOBDETAILS)!=0){
            btnRentJob.setText(getResources().getString(R.string.done));
        }
        detailNameJob.setText(getArguments().getString("nameWork"));
        detailDescriptionJob.setText(getArguments().getString("Description"));
        JobID = getArguments().getString("JobID");
        setUpTimes();
        setHeightLV();
        lvJobDetailDateTime.bringToFront();
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
