package com.drowsyatmidnight.jobforcharity.hirer.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;

/**
 * Created by davidtran on 7/24/17.
 */

public class FinishedNeededWorksFragment extends Fragment {
    public static FinishedNeededWorksFragment newInstance(){
        FinishedNeededWorksFragment fragment = new FinishedNeededWorksFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString(SEARCH_KEYWORD,searchKeyWord);
        fragment.setArguments(bundle);*/

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished_neededworks, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
