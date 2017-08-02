package com.drowsyatmidnight.jobforcharity.woker.View.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.GroupedDateTimeWork;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Job_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.woker.View.Adapters.DetailDateTimeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by haint on 26/07/2017.
 */

public class DetailMyWorkFragment extends Fragment{

    public interface SomeEvent{
        void isDateTimeScrolled(boolean isScroled);
    }

    private SomeEvent someEvent;

    @BindView(R.id.scrollViewDetail)
    NestedScrollView mScrollView;

    @BindView(R.id.detailNameJob)
    TextView detailNameJob;
    @BindView(R.id.detailDescriptionJob)
    TextView detailDescriptionJob;

    @BindView(R.id.lvWorkDetailDateTime)
    ExpandableListView lvWorkDetailDateTime;

    Map<String,List<ShiftWork_Model>> GroupedDateTime = new HashMap<>();
    private Job_Model mJobModel;
    private DetailDateTimeAdapter mDetailDateTimeAdapter;

    private List<GroupedDateTimeWork> mGroupedDateTimeWorkList = new ArrayList<>();


    public static DetailMyWorkFragment newInstance(Job_Model jobModel) {
        DetailMyWorkFragment fmJobDetail = new DetailMyWorkFragment();

        Bundle args = new Bundle();
        args.putString("nameWork", jobModel.getWorkName());
        args.putString("Description", jobModel.getDescription());
        args.putSerializable("shiftWorks", (Serializable) jobModel.getDateTimes());
        args.putParcelable("job_model",jobModel);
        args.putString("category", jobModel.getCategory());

        args.putString("workerID", jobModel.getWokerUID());
        fmJobDetail.setArguments(args);

        return fmJobDetail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobModel = getArguments().getParcelable("job_model");

        mGroupedDateTimeWorkList = getGroupedDateTimeList();
        //List<GroupedDateTimeWork> m = getGroupedDateTimeList();
        for (GroupedDateTimeWork g: getGroupedDateTimeList()) {
            Log.d("Group:",g.getDate());
            for (ShiftWork_Model h:g.getShiftWorkModels()){
                Log.d("item:",h.getBeginTime() + h.getEndTime());
            }

        }
    }
    private void setupAdapter(){
        mDetailDateTimeAdapter = new DetailDateTimeAdapter(getContext(),mGroupedDateTimeWorkList,mJobModel,getActivity());
        lvWorkDetailDateTime.setAdapter(mDetailDateTimeAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.job_detail_jobinfo_worker, container, false);
        ButterKnife.bind(this,rootView);

        setupAdapter();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpView();





    }
    private List<GroupedDateTimeWork> getGroupedDateTimeList(){
        List<GroupedDateTimeWork> mGroupedDateTimeWorks = new ArrayList<>();
        //Map<String,List<ShiftWork_Model>> map = new HashMap<>();
        int i = 0;
        while (i<mJobModel.getDateTimes().size()){

            Log.d("An count group ","+1");
            String date = mJobModel.getDateTimes().get(i).getDate();

            GroupedDateTimeWork mGroupedDateTimeWork;

            List<ShiftWork_Model> mShiftWorkModelList = new ArrayList<>();
            mShiftWorkModelList.add(mJobModel.getDateTimes().get(i));
            mJobModel.getDateTimes().remove(i);
            for(int j=0;j<mJobModel.getDateTimes().size();j++){
                if(mJobModel.getDateTimes().get(j).getDate().equals(date)){
                   mShiftWorkModelList.add(mJobModel.getDateTimes().get(j));
                    mJobModel.getDateTimes().remove(j);

                    i=-1;
                }
            }
            mGroupedDateTimeWork = new GroupedDateTimeWork(date,mShiftWorkModelList);
            mGroupedDateTimeWorks.add(mGroupedDateTimeWork);
            //map.put(date,mShiftWorkModelList);
            //groupedDateTimes.add(map);
            i++;
        }
        return mGroupedDateTimeWorks;

        /*for (Map<String, List<ShiftWork_Model>> map:groupedDateTimes){
            String group = map.keySet().toString();
            Log.d("group:",group);
            Log.d("item",map.get(group).toString());
        }*/
    }


    private void setUpView() {

        detailNameJob.setText(getArguments().getString("nameWork"));
        detailDescriptionJob.setText(getArguments().getString("Description"));
        lvWorkDetailDateTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });





        setHeightLV();
    }

    private void setHeightLV() {
        for (int i = 0; i < mDetailDateTimeAdapter.getGroupCount(); i++)
            lvWorkDetailDateTime.expandGroup(i);
        setListViewHeight(lvWorkDetailDateTime);
        lvWorkDetailDateTime.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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


}