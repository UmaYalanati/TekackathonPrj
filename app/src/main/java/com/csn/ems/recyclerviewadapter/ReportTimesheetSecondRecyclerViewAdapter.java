package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;
import com.csn.ems.model.TimeSheetReport;

import java.util.List;

import static com.csn.ems.R.id.tvawd;
import static com.csn.ems.R.id.tvleaves;
import static com.csn.ems.R.id.tvlop;
import static com.csn.ems.R.id.tvpwd;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportTimesheetSecondRecyclerViewAdapter extends RecyclerView.Adapter<ReportTimesheetSecondRecyclerViewAdapter.ViewHolder> {

    List<TimeSheetReport> timesheetDetails;
    Context context;
    View view1;
    ReportTimesheetSecondRecyclerViewAdapter.ViewHolder viewHolder1;
    TextView textView;

    public ReportTimesheetSecondRecyclerViewAdapter(Context context1, List<TimeSheetReport> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,tvawd,tvpwd,tvlop,tvleaves,tvtgs,tvtds,tvtna;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.tvmonth);
            tvawd= (TextView) v.findViewById(R.id.tvawd);
                    tvpwd= (TextView) v.findViewById(R.id.tvpwd);
                    tvlop= (TextView) v.findViewById(R.id.tvlop);
                    tvleaves= (TextView) v.findViewById(R.id.tvleaves);
            tvtgs= (TextView) v.findViewById(R.id.tvtgs);
                    tvtds= (TextView) v.findViewById(R.id.tvtds);
                    tvtna= (TextView) v.findViewById(R.id.tvtna);
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

        holder.textView.setText(timesheetDetails.get(position).getMonth());
        holder.tvawd.setText(timesheetDetails.get(position).getaWD());
                holder.tvpwd.setText(timesheetDetails.get(position).getpWD());
                holder.tvlop.setText(timesheetDetails.get(position).getlOP());
                holder.tvleaves.setText(timesheetDetails.get(position).getLeaves());
        holder.tvtgs.setText(timesheetDetails.get(position).gettGS());
                holder.tvtds.setText(timesheetDetails.get(position).gettDS());
                holder.tvtna.setText(timesheetDetails.get(position).gettNA());
    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}


