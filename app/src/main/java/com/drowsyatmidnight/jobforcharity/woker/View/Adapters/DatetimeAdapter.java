package com.drowsyatmidnight.jobforcharity.woker.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.woker.Models.Entity.Work;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtran on 7/25/17.
 */

public class DatetimeAdapter extends BaseAdapter {
    List<Work.Datetime> mDatetimeList = new ArrayList<>();
    Context mContext;
    public DatetimeAdapter(List<Work.Datetime> datetimeList,Context context) {
    this.mDatetimeList = datetimeList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatetimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatetimeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class ViewHolder {
        TextView tvDate;
        TextView tvBeginTime;
        TextView tvEndTime;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_datetime, parent, false);

            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDateWork);
            //viewHolder.tvBeginTime= (TextView) convertView.findViewById(R.id.tvBeginTimeWork);
            //viewHolder.tvEndTime = (TextView) convertView.findViewById(R.id.tvEndTimeWork);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.tvDate.setText(mDatetimeList.get(position).getDate()+"  " + mDatetimeList.get(position).getBeginTime()
                +"  " + mDatetimeList.get(position).getBeginTime());

        //viewHolder.tvBeginTime.setText(mDatetimeList.get(position).getBeginTime());

        //viewHolder.tvEndTime.setText(mDatetimeList.get(position).getEndTime());

        // Return the completed view to render on screen
        return convertView;
    }
}
