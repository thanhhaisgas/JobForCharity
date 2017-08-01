package com.drowsyatmidnight.jobforcharity.Utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import io.github.yavski.fabspeeddial.FabSpeedDial;

/**
 * Created by haint on 01/08/2017.
 */

public class NestScrollViewBehavior extends CoordinatorLayout.Behavior<FabSpeedDial> {
    public NestScrollViewBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FabSpeedDial child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout,
                               final FabSpeedDial child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,dxUnconsumed, dyUnconsumed);
        Log.d("dyConsumed", String.valueOf(dyConsumed));
        Log.d("dyUnconsumed", String.valueOf(dyUnconsumed));
        if (dyConsumed > 0 || dyConsumed < 0) {
            child.hide();
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FabSpeedDial child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        child.show();
    }
}
