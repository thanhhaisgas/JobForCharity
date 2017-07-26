package com.drowsyatmidnight.jobforcharity.hirer.Presenter;

import com.drowsyatmidnight.jobforcharity.hirer.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.hirer.Models.LoadWorkListener;
import com.drowsyatmidnight.jobforcharity.hirer.Models.UserInterator;
import com.drowsyatmidnight.jobforcharity.hirer.View.MainView;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public class LoadWorkPresenter implements LoadWorkListener {
    private UserInterator mUserInterator;
    private MainView mMainView;

    public LoadWorkPresenter(MainView mainView) {
        mMainView = mainView;
        mUserInterator = new UserInterator(this);
    }

    public void loadWorkData(){
        mUserInterator.loadWorkFromFirebase();
    }

    @Override
    public void onLoadWorkSucess(List<Work> workList) {
        mMainView.displayWorkList(workList);
    }

    @Override
    public void onLoadWorkFailure(String message) {

    }
}
