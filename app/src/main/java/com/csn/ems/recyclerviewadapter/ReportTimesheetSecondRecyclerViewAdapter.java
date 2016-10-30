package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.csn.ems.R;
import com.csn.ems.model.TimeSheetReport;

import java.util.List;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportTimesheetSecondRecyclerViewAdapter extends RecyclerView.Adapter<ReportTimesheetSecondRecyclerViewAdapter.ViewHolder> {

    List<TimeSheetReport> timesheetDetails;
    Context context;
    View view1;
    ReportTimesheetSecondRecyclerViewAdapter.ViewHolder viewHolder1;
    TextView textView;
    public boolean check;
    public ReportTimesheetSecondRecyclerViewAdapter(Context context1, List<TimeSheetReport> timesheetDetails,boolean check) {

        this.timesheetDetails = timesheetDetails;
        this.check=check;
        this.context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, tvawd, tvpwd, tvlop, tvleaves, tvtgs, tvtds, tvtna;
public CheckBox checkbox;
        public ViewHolder(View v) {

            super(v);
            checkbox= (CheckBox) v.findViewById(R.id.checkbox);
            textView = (TextView) v.findViewById(R.id.tvmonth);
            tvawd = (TextView) v.findViewById(R.id.tvawd);
            tvpwd = (TextView) v.findViewById(R.id.tvpwd);
            tvlop = (TextView) v.findViewById(R.id.tvlop);
            tvleaves = (TextView) v.findViewById(R.id.tvleaves);
            tvtgs = (TextView) v.findViewById(R.id.tvtgs);
            tvtds = (TextView) v.findViewById(R.id.tvtds);
            tvtna = (TextView) v.findViewById(R.id.tvtna);


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

        if (check){
            holder.checkbox.setChecked(true);
        }else {
            holder.checkbox.setChecked(false);
        }

        holder.textView.setText(timesheetDetails.get(position).getMonth());
        holder.tvawd.setText(String.valueOf(timesheetDetails.get(position).getaWD()));
        holder.tvpwd.setText(String.valueOf(timesheetDetails.get(position).getpWD()));
        holder.tvlop.setText(String.valueOf(timesheetDetails.get(position).getlOP()));
        holder.tvleaves.setText(String.valueOf(timesheetDetails.get(position).getLeaves()));
        holder.tvtgs.setText(String.valueOf(timesheetDetails.get(position).gettGS()));
        holder.tvtds.setText(String.valueOf(timesheetDetails.get(position).gettDS()));
        holder.tvtna.setText(String.valueOf(timesheetDetails.get(position).gettNA()));
    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}


