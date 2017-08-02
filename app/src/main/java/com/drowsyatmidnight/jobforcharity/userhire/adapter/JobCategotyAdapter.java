package com.drowsyatmidnight.jobforcharity.userhire.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Job_Model;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.userhire.JobDetail;
import com.drowsyatmidnight.jobforcharity.userhire.holder.JobHolder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haint on 27/07/2017.
 */

public class JobCategotyAdapter extends RecyclerView.Adapter<JobHolder> {
    private Context context;
    private View rootView;
    private LayoutInflater layoutInflater;
    private List<Job_Model> job_models;

    public JobCategotyAdapter(Context context, List<Job_Model> job_models) {
        this.context = context;
        this.job_models = job_models;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_job, parent, false);
        return new JobHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobHolder holder, final int position) {
        holder.txtJobName.setText(job_models.get(position).getWorkName());
        holder.txtJobExpirationDate.setText(" "+ExpirationDate());
        holder.cardViewJobCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDetail = new Intent(context, JobDetail.class);
                goDetail.putExtra("workName", job_models.get(position).getWorkName());
                goDetail.putExtra("description", job_models.get(position).getDescription());
                goDetail.putExtra("shiftWorks", (Serializable) job_models.get(position).getDateTimes());
                context.startActivity(goDetail);
            }
        });
    }

    private String ExpirationDate(){
        String temp="";
        temp = job_models.get(0).getDateTimes().get(0).getDate();
        for(Job_Model m : job_models){
            for (ShiftWork_Model s : m.getDateTimes()){
                if(s.getDate().compareTo(temp) > 0){
                    temp = s.getDate();
                }
            }
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        return job_models.size();
    }
}
