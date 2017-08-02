package com.drowsyatmidnight.jobforcharity.userhire.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by haint on 29/07/2017.
 */

public class JobInProgressHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgProfileJobInProgress)
    public ImageView imgProfileJobInProgress;
    @BindView(R.id.txtJobNameInProgress)
    public TextView txtJobNameInProgress;
    @BindView(R.id.txtJobDateInProgress)
    public TextView txtJobDateInProgress;
    @BindView(R.id.cardViewJobInProgress)
    public CardView cardViewJobInProgress;
    public JobInProgressHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
