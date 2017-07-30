package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haint on 30/07/2017.
 */

public class FmJobInHistory extends Fragment {
    @BindView(R.id.lvJobInProgress)
    RecyclerView lvJobInProgress;

    private List<Job_Model> job_models;

    private View rootView;

    @Override
    public void onResume() {
        super.onResume();
        setUpView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.job_in_progress, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    private void setUpView() {
        job_models = new ArrayList<>();
        DataFirebase.getJobInHistory(job_models, lvJobInProgress, rootView.getContext());
    }
}
