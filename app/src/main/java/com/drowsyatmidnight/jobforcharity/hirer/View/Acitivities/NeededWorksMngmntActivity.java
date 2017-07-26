package com.drowsyatmidnight.jobforcharity.hirer.View.Acitivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.hirer.View.Adapters.CustomPagerAdapter;
import com.drowsyatmidnight.jobforcharity.hirer.View.Fragments.AllNeededWorksFragment;
import com.drowsyatmidnight.jobforcharity.hirer.View.Fragments.ExpiredNeededWorksFragment;
import com.drowsyatmidnight.jobforcharity.hirer.View.Fragments.FinishedNeededWorksFragment;
import com.drowsyatmidnight.jobforcharity.hirer.View.Fragments.PerformingNeededWorksFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by davidtran on 7/23/17.
 */

public class NeededWorksMngmntActivity extends AppCompatActivity{
    @BindView(R.id.fab_compose)
    android.support.design.widget.FloatingActionButton fab_compose;
    @BindView(R.id.tabLayoutHirer)
    TabLayout tabLayout;
    @BindView(R.id.viewpagerHirer)
    ViewPager viewPager;
    @BindView(R.id.toolbarHirer)
    Toolbar toolbar;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    int icon[];

    Fragment mAllNeededWorksFragment = AllNeededWorksFragment.newInstance();
    Fragment mPerformingNeededWorksFragment = PerformingNeededWorksFragment.newInstance();
    Fragment mFinishedNeededWorksFragment = FinishedNeededWorksFragment.newInstance();
    Fragment mExpiredNeededWorksFragment = ExpiredNeededWorksFragment.newInstance();

    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neededjobs_mngmnt);

        icon = new int[]{
                R.drawable.ic_list_white_24dp,
                R.drawable.ic_playlist_play_white_24dp,
                R.drawable.ic_playlist_finished_white_24dp,
                R.drawable.ic_failed_white_24dp};

        setupTabLayout();
    }

    private void setupTabLayout(){
        fragmentArrayList.add(mAllNeededWorksFragment);
        fragmentArrayList.add(mPerformingNeededWorksFragment);
        fragmentArrayList.add(mFinishedNeededWorksFragment);
        fragmentArrayList.add(mExpiredNeededWorksFragment);

        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),fragmentArrayList);
        viewPager.setAdapter(customPagerAdapter);

        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < 4; i++) {
            tabLayout.getTabAt(i).setIcon(icon[i]);
        }
    }

}
