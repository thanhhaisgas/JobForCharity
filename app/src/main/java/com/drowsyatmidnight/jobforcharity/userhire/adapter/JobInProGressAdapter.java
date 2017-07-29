package com.drowsyatmidnight.jobforcharity.userhire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.Job_Model;
import com.drowsyatmidnight.jobforcharity.model.ShiftWork_Model;
import com.drowsyatmidnight.jobforcharity.userhire.DataFirebase;
import com.drowsyatmidnight.jobforcharity.userhire.holder.JobInProgressHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haint on 29/07/2017.
 */

public class JobInProGressAdapter extends RecyclerView.Adapter<JobInProgressHolder> {
    private Context context;
    private View rootView;
    private LayoutInflater layoutInflater;
    private List<Job_Model> job_models;

    public JobInProGressAdapter(Context context, List<Job_Model> job_models) {
        this.context = context;
        this.job_models = job_models;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public JobInProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = layoutInflater.inflate(R.layout.item_job_in_progress, parent, false);
        return new JobInProgressHolder(rootView);
    }

    @Override
    public void onBindViewHolder(JobInProgressHolder holder, int position) {
        holder.txtJobNameInProgress.setText(job_models.get(position).getWorkName());
        HashMap<String, List<ShiftWork_Model>> dateTime;
        dateTime = DataFirebase.dateTimes(job_models.get(position).getDateTimes());
        String tempDate = "";
        for (Map.Entry m : dateTime.entrySet()){
            List<ShiftWork_Model> tempTime = (List<ShiftWork_Model>) m.getValue();
            tempDate += m.getKey()+"\n";
            for (ShiftWork_Model s : tempTime){
                tempDate += "\t"+s.getBeginTime()+" - "+s.getEndTime()+"\n";
            }
        }
        holder.txtJobDateInProgress.setText(tempDate);
    }

    @Override
    public int getItemCount() {
        return job_models.size();
    }
}
