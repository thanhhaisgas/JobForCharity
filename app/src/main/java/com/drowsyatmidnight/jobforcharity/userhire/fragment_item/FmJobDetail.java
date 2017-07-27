package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;

import java.io.Serializable;

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
        detailNameJob.setText(getArguments().getString("nameWork"));
        detailDescriptionJob.setText(getArguments().getString("Description"));
    }
}
