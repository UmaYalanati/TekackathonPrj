package com.tek.ems.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tek.ems.R;
import com.tek.ems.activity.GoogleMaps;
import com.tek.ems.emsconstants.EmsConstants;
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
        if (timesheetDetails.get(position).getWorkingDate() != null) {
            holder.textView.setText(timesheetDetails.get(position).getWorkingDate());
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
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(timesheetDetails.get(position).getCheckInLattitude()).equals("0.0")) {
                    Toast.makeText(context, "Location not Available", Toast.LENGTH_SHORT).show();
                } else {
                    EmsConstants.latitude = timesheetDetails.get(position).getCheckInLattitude();
                    EmsConstants.longitude = timesheetDetails.get(position).getCheckInLongitude();
                    Intent i = new Intent(context, GoogleMaps.class);
                    context.startActivity(i);
                }
            }
        });
        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(timesheetDetails.get(position).getCheckInLattitude()).equals("0.0")) {
                    Toast.makeText(context, "Location not Available", Toast.LENGTH_SHORT).show();
                } else {
                    EmsConstants.latitude = timesheetDetails.get(position).getCheckOutLattitude();
                    EmsConstants.longitude = timesheetDetails.get(position).getCheckOutLongitude();
                    Intent i = new Intent(context, GoogleMaps.class);
                    context.startActivity(i);
                }
            }
        });


        if (timesheetDetails.get(position).getCheckIn() != null) {

            holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckIn());
            if (timesheetDetails.get(position).getCheckIn().trim().isEmpty()) {
                holder.tvcheckintime.setText("0:00 am");
            } else {
                holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckIn());
            }
        }
        if (timesheetDetails.get(position).getCheckOut() != null) {
            if (timesheetDetails.get(position).getCheckOut().trim().isEmpty()) {
                holder.tvcheckouttime.setText("0:00 am");
            } else {
                holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckOut());
            }


        }


        if (timesheetDetails.get(position).getCalculatedLength() != null) {
            holder.tvtotalhrs.setText(timesheetDetails.get(position).getCalculatedLength() + "hrs");
            // int hours = p.getHours();
        }


    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}
