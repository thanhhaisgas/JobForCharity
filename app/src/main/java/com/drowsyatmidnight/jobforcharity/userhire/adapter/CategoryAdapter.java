package com.drowsyatmidnight.jobforcharity.userhire.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.JobsCategory;
import com.drowsyatmidnight.jobforcharity.userhire.holder.CategoryHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by haint on 25/07/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    private Context context;
    private Activity activity;
    private View rootView;
    private LayoutInflater layoutInflater;
    private List<String> categoryName;
    private List<String> categoryColor;
    private int [] mResourceImg = {
            R.drawable.icon_category_education,
            R.drawable.icon_category_home,
            R.drawable.icon_category_tourguide,
            R.drawable.icon_category_delivery,
            R.drawable.icon_category_bikeshare,
            R.drawable.icon_category_fix,
            R.drawable.icon_category_other
    };

    public CategoryAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categoryName = Arrays.asList(context.getResources().getStringArray(R.array.listCategoryName));
        categoryColor = Arrays.asList(context.getResources().getStringArray(R.array.list_color_category));
    }


    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, final int position) {
        holder.imgCategory.setImageResource(mResourceImg[position]);
        holder.imgCategory.setBackgroundColor(Color.parseColor(categoryColor.get(position)));
        holder.txtNameCategory.setText(categoryName.get(position));
        DataFirebase.getCountCategory(categoryName.get(position), holder.txtJobCountCategory);
        holder.cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goJobCategory = new Intent(context, JobsCategory.class);
                goJobCategory.putExtra("NameCategory", categoryName.get(position));
                goJobCategory.putExtra("idImgCategory", mResourceImg[position]);
                goJobCategory.putExtra("categoryColor", categoryColor.get(position));
                goJobCategory.putExtra("position", position);
                context.startActivity(goJobCategory);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryName.size();
    }
}
