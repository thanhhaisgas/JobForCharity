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
import com.drowsyatmidnight.jobforcharity.userhire.adapter.JobInProGressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haint on 29/07/2017.
 */

public class FmJobInProgress extends Fragment {

    @BindView(R.id.lvJobInProgress)
    RecyclerView lvJobInProgress;

    private JobInProGressAdapter jobInProGressAdapter;
    List<Job_Model> job_models;

    private View rootView;

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
        setUpView();
    }

    private void setUpView() {
        job_models = new ArrayList<>();
        DataFirebase.getJobInProgress(job_models, lvJobInProgress, rootView.getContext());
    }
}
