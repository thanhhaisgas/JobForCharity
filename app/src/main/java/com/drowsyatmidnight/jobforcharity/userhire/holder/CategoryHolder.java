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
 * Created by haint on 25/07/2017.
 */

public class CategoryHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgCategory)
    public ImageView imgCategory;
    @BindView(R.id.txtNameCategory)
    public TextView txtNameCategory;
    @BindView(R.id.txtJobCountCategory)
    public TextView txtJobCountCategory;
    @BindView(R.id.cardViewCategory)
    public CardView cardViewCategory;
    public CategoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
