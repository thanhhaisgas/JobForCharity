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
 * Created by haint on 30/07/2017.
 */

public class Review_Holder extends RecyclerView.ViewHolder{
    @BindView(R.id.imgProfileItemReview)
    public ImageView imgProfileItemReview;
    @BindView(R.id.txtNameUserReview)
    public TextView txtNameUserReview;
    @BindView(R.id.txtContentReview)
    public TextView txtContentReview;
    @BindView(R.id.cardViewReview)
    public CardView cardViewReview;
    public Review_Holder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
