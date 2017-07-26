package com.drowsyatmidnight.jobforcharity.hirer.Presenter;

import com.drowsyatmidnight.jobforcharity.hirer.Models.CreateWorkListener;
import com.drowsyatmidnight.jobforcharity.hirer.Models.Entity.Work;
import com.drowsyatmidnight.jobforcharity.hirer.Models.LoadWorkListener;
import com.drowsyatmidnight.jobforcharity.hirer.Models.UserInterator;
import com.drowsyatmidnight.jobforcharity.hirer.View.MainView;

import java.util.List;

/**
 * Created by davidtran on 7/24/17.
 */

public class CreateWorkPresenter  {
    private UserInterator mUserInterator;
    private MainView mMainView;

    public CreateWorkPresenter(MainView mainView) {
        mMainView = mainView;
       /* mUserInterator = new UserInterator(this);*/
    }

    public CreateWorkPresenter() {
       /* mUserInterator = new UserInterator(this);*/
    }

    public void createWorkData(Work work, List<Work.Datetime> datetimeList){
        mUserInterator.createWorkInFirebase(work,datetimeList);
    }


    /*@Override
    public void onCreateWorkSuccess(Work work) {

    }

    @Override
    public void onCreateWorkFailure(String message) {

    }*/
}

