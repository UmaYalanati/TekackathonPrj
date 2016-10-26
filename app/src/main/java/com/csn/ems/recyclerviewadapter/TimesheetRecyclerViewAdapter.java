package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;

/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimesheetRecyclerViewAdapter extends RecyclerView.Adapter<TimesheetRecyclerViewAdapter.ViewHolder> {

    String[] SubjectValues;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView, tvapprovalstatus;

    public TimesheetRecyclerViewAdapter(Context context1, String[] SubjectValues1) {

        SubjectValues = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView tvapprovalstatus;

        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (TextView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvapprovalstatus.setVisibility(View.GONE);
        }
    }

    @Override
    public TimesheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.child_timesheet, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(SubjectValues[position]);

    }

    @Override
    public int getItemCount() {

        return SubjectValues.length;
    }
}