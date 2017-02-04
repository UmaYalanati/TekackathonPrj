package com.tek.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tek.ems.R;
import com.tek.ems.model.TimeSheetDetails;

import java.util.List;

/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimesheetRecyclerViewAdapter extends RecyclerView.Adapter<TimesheetRecyclerViewAdapter.ViewHolder> {


    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;
    List<TimeSheetDetails> timesheetDetails;

    public TimesheetRecyclerViewAdapter(Context context1, List<TimeSheetDetails> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;
        public ImageView tvapprovalstatus;
        ImageView imageButton, imageButton2;

        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (ImageView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvcheckintime = (TextView) v.findViewById(R.id.tvcheckintime);
            tvtotalhrs = (TextView) v.findViewById(R.id.tvtotalhrs);
            tvcheckouttime = (TextView) v.findViewById(R.id.tvcheckouttime);
            imageButton = (ImageView) v.findViewById(R.id.imageButton);
            imageButton2 = (ImageView) v.findViewById(R.id.imageButton2);
            //tvapprovalstatus.setVisibility(View.GONE);
        }
    }

    @Override
    public TimesheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.aaaaa, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (timesheetDetails.get(position).getInsertDate() != null) {
            holder.textView.setText(timesheetDetails.get(position).getInsertDate());
        }
        if (timesheetDetails.get(position).getStatus() != null) {
            if (timesheetDetails.get(position).getStatus().equals("Approved")) {
                // tvapprovalstatus.setBackgroundResource(R.drawable.approvedicon);
                //setting image resource
                holder.tvapprovalstatus.setImageResource(R.drawable.approvedicon);
            } else if (timesheetDetails.get(position).getStatus().equals("Pending")) {
                //  tvapprovalstatus.setBackgroundResource(R.drawable.bluedot);
                holder.tvapprovalstatus.setImageResource(R.drawable.bluedot);
            } else {
                // tvapprovalstatus.setBackgroundResource(R.drawable.reddot);
                holder.tvapprovalstatus.setImageResource(R.drawable.reddot);
            }
        }


        if (timesheetDetails.get(position).getCheckinTime() != null) {

            holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckinTime());

        }
        if (timesheetDetails.get(position).getCheckoutTime() != null) {
            holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckoutTime());
        }


        if (timesheetDetails.get(position).getWorkingHours() != null) {
            holder.tvtotalhrs.setText(timesheetDetails.get(position).getWorkingHours() + "hrs");
        }


    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}
