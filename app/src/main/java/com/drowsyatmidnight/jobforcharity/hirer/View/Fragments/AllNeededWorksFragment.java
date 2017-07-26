package com.drowsyatmidnight.jobforcharity.hirer.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drowsyatmidnight.jobforcharity.R;

import butterknife.ButterKnife;

/**
 * Created by davidtran on 7/24/17.
 */

public class AllNeededWorksFragment extends Fragment {

    public static AllNeededWorksFragment newInstance(){
        AllNeededWorksFragment fragment = new AllNeededWorksFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString(SEARCH_KEYWORD,searchKeyWord);
        fragment.setArguments(bundle);*/

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_neededworks, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}
