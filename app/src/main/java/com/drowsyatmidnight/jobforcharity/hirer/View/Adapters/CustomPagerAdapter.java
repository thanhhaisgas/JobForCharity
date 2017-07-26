package com.drowsyatmidnight.jobforcharity.hirer.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by davidtran on 7/5/17.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return fragmentList.get(0);
            case 1: return fragmentList.get(1);
            case 2: return fragmentList.get(2);
            case 3: return  fragmentList.get(3);
            default: return  new Fragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
;