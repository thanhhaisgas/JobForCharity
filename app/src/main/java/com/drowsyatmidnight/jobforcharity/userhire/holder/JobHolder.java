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
 * Created by haint on 27/07/2017.
 */

public class JobHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgProfileJob)
    public ImageView imgProfileJob;
    @BindView(R.id.txtJobName)
    public TextView txtJobName;
    @BindView(R.id.txtJobExpirationDate)
    public TextView txtJobExpirationDate;
    @BindView(R.id.cardViewJobCategory)
    public CardView cardViewJobCategory;
    public JobHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
