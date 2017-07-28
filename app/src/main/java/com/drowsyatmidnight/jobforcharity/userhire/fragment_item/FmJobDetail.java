package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.AdapterDateTimes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    private AdapterDateTimes adapterDateTimes;
    private List<ShiftWork_Model> shiftWork_models;

    public static FmJobDetail newInstance(String nameWork, String Description, Serializable shiftWork_models) {
        FmJobDetail fmJobDetail = new FmJobDetail();

        Bundle args = new Bundle();
        args.putString("nameWork", nameWork);
        args.putString("Description", Description);
        args.putSerializable("shiftWorks", shiftWork_models);
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
        lvJobDetailDateTime.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });
    }

    private void setUpView() {
        detailNameJob.setText(getArguments().getString("nameWork"));
        detailDescriptionJob.setText(getArguments().getString("Description"));
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
        List<ShiftWork_Model> timeBeginEnd = new ArrayList<>();
        Collections.sort(shiftWork_models, new Comparator<ShiftWork_Model>() {
            @Override
            public int compare(ShiftWork_Model o1, ShiftWork_Model o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        if (shiftWork_models.size()<=1){
            timeBeginEnd.add(shiftWork_models.get(0));
            timeJobs.put(shiftWork_models.get(0).getDate(),timeBeginEnd);
        }else {
            for(int i=0; i<shiftWork_models.size(); i++){
                if((i+1)<shiftWork_models.size()){
                    if(shiftWork_models.get(i).getDate().compareTo(shiftWork_models.get(i+1).getDate())==0){
                        timeBeginEnd.add(shiftWork_models.get(i));
                    }else {
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(),timeBeginEnd);
                        timeBeginEnd = new ArrayList<>();
                    }
                }else {
                    if(shiftWork_models.get(i).getDate().compareTo(shiftWork_models.get(i-1).getDate())==0){
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(),timeBeginEnd);
                    }else {
                        timeBeginEnd.add(shiftWork_models.get(i));
                        timeJobs.put(shiftWork_models.get(i).getDate(),timeBeginEnd);
                    }
                }
            }
        }

        List<String> date = new ArrayList<>(timeJobs.keySet());
        adapterDateTimes = new AdapterDateTimes(rootView.getContext(), date, timeJobs);
        lvJobDetailDateTime.setAdapter(adapterDateTimes);
    }
}
