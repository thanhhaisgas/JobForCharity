package com.drowsyatmidnight.jobforcharity.userhire.fragment_item;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.CategoryAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.WhatNewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by haint on 24/07/2017.
 */

public class FmCategory extends Fragment {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.lvCategory)
    RecyclerView lvCategory;

    private View view;
    private WhatNewPagerAdapter whatNewPagerAdapter;
    private int [] mResource = {
            R.drawable.test1,
            R.drawable.test2,
            R.drawable.test3,
            R.drawable.test4
    };
    private int CurrentNew = 0;
    private final int NumNews = 4;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.job_category, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUpViewWhatNew();
        setUpCategoryList();
    }

    private void setUpCategoryList() {
        categoryAdapter = new CategoryAdapter(view.getContext());
        lvCategory.setAdapter(categoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        lvCategory.setLayoutManager(layoutManager);
    }

    private void setUpViewWhatNew() {
        whatNewPagerAdapter = new WhatNewPagerAdapter(view.getContext(), mResource);
        pager.setAdapter(whatNewPagerAdapter);
        indicator.setViewPager(pager);
        whatNewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        autoChangeNew();
    }

    private void autoChangeNew() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CurrentNew = pager.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final Handler handler = new Handler();
        final Runnable changing = new Runnable() {
            @Override
            public void run() {
                if(CurrentNew == NumNews){
                    CurrentNew = 0;
                }
                pager.setCurrentItem(CurrentNew++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(changing);
            }
        },500,3000);
    }
}
