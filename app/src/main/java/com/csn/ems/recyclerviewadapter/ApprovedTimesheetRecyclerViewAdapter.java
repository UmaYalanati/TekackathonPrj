package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;

/**
 * Created by uyalanat on 24-10-2016.
 */

public class ApprovedTimesheetRecyclerViewAdapter  extends RecyclerView.Adapter<ApprovedTimesheetRecyclerViewAdapter.ViewHolder>{

    String[] SubjectValues;
    Context context;
    View view1;
    ApprovedTimesheetRecyclerViewAdapter.ViewHolder viewHolder1;
    TextView textView,tvapprovalstatus;

    public ApprovedTimesheetRecyclerViewAdapter(Context context1, String[] SubjectValues1){

        SubjectValues = SubjectValues1;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView tvapprovalstatus;
        public ViewHolder(View v){

            super(v);
            tvapprovalstatus= (TextView)v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView)v.findViewById(R.id.tvdate);
            //tvapprovalstatus.setVisibility(View.GONE);
        }
    }

    @Override
    public ApprovedTimesheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.child_timesheet,parent,false);

        viewHolder1 = new ApprovedTimesheetRecyclerViewAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ApprovedTimesheetRecyclerViewAdapter.ViewHolder holder, int position){

        holder.textView.setText(SubjectValues[position]);

    }

    @Override
    public int getItemCount(){

        return SubjectValues.length;
    }
}

