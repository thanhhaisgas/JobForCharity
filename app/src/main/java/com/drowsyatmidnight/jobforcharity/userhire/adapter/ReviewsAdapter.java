package com.drowsyatmidnight.jobforcharity.userhire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.Review_Model;
import com.drowsyatmidnight.jobforcharity.userhire.holder.Review_Holder;

import java.util.List;

/**
 * Created by haint on 30/07/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<Review_Holder> {
    private Context context;
    private View rootView;
    private LayoutInflater layoutInflater;
    private List<Review_Model> review_models;

    public ReviewsAdapter(Context context, List<Review_Model> review_models) {
        this.context = context;
        this.review_models = review_models;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Review_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_review, parent, false);
        return new Review_Holder(rootView);
    }

    @Override
    public void onBindViewHolder(Review_Holder holder, final int position) {
        holder.txtNameUserReview.setText(review_models.get(position).getlName()+" "+review_models.get(position).getfName());
        holder.txtContentReview.setText(review_models.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return review_models.size();
    }
}
