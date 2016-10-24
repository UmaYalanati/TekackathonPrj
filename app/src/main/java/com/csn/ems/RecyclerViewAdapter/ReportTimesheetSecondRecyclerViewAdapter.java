package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportTimesheetSecondRecyclerViewAdapter extends RecyclerView.Adapter<ReportTimesheetSecondRecyclerViewAdapter.ViewHolder> {

    String[] SubjectValues;
    Context context;
    View view1;
    ReportTimesheetSecondRecyclerViewAdapter.ViewHolder viewHolder1;
    TextView textView;

    public ReportTimesheetSecondRecyclerViewAdapter(Context context1, String[] SubjectValues1) {

        SubjectValues = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.tvmonth);
        }
    }

    @Override
    public ReportTimesheetSecondRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.childtimesheet, parent, false);

        viewHolder1 = new ReportTimesheetSecondRecyclerViewAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ReportTimesheetSecondRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.textView.setText(SubjectValues[position]);
    }

    @Override
    public int getItemCount() {

        return SubjectValues.length;
    }
}


