package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.GoogleMaps;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.model.TimeSheetDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimesheetRecyclerViewAdapter extends RecyclerView.Adapter<TimesheetRecyclerViewAdapter.ViewHolder> {


    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView, tvapprovalstatus;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText(timesheetDetails.get(position).getWorkingDate());

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
        String intime = "";

        String ottime = "";

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd hh:mm a");
        Date dt = null;
        Date dt_out = null;

        if (timesheetDetails.get(position).getCheckIn() != null) {
            holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckIn());
        }
        if (timesheetDetails.get(position).getCheckOut() != null) {
            holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckOut());
        }

 /*       try {
            if (timesheetDetails.get(position).getCheckIn() != null) {

                if (timesheetDetails.get(position).getCheckIn().contains("AM")
                        || timesheetDetails.get(position).getCheckIn().contains("PM")) {
                    holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckIn());
                }else {
                    dt = sdf.parse(timesheetDetails.get(position).getCheckIn());
                    System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
                    intime = sdfs.format(dt);
                    holder.tvcheckintime.setText(intime);
                }
            }
            if (timesheetDetails.get(position).getCheckOut() != null) {
                if (timesheetDetails.get(position).getCheckOut().contains("AM")
                        || timesheetDetails.get(position).getCheckOut().contains("PM")) {
                    holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckOut());
                } else {
                    dt_out = sdf.parse(timesheetDetails.get(position).getCheckOut());
                    ottime = sdfs.format(dt_out);
                    holder.tvcheckouttime.setText(ottime);
                }


            }


        } catch (ParseException e) {

            e.printStackTrace();
        }*/
        try {
            if (timesheetDetails.get(position).getCheckIn() != null && timesheetDetails.get(position).getCheckOut() != null) {
                //   long secs = (dt.getTime() - dt.getTime()) / 1000;
                //   int hours = secs / 3600;
             /*   dt = sdfs.parse(timesheetDetails.get(position).getCheckIn());
                dt_out = sdfs.parse(timesheetDetails.get(position).getCheckOut());
                final int MILLI_TO_HOUR = 1000 * 60 * 60;
                int hours = (int) (dt.getTime() - dt_out.getTime()) / MILLI_TO_HOUR;
                //
                //  Period p = new Period(dt, dt_out);
                long diff = dt.getTime() - dt_out.getTime();
                long diffHours = diff / (60 * 60 * 1000) % 24;*/


                String str_intime = timesheetDetails.get(position).getCheckIn();
                String[] splited = str_intime.split("\\s+");

                String str_ottime = timesheetDetails.get(position).getCheckOut();
                String[] splited_out = str_intime.split("\\s+");
                SimpleDateFormat formate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                str_intime = "01/15/2012 " + splited[0] + ":10";
                str_ottime = "01/15/2012 " + splited_out[0] + ":10";
                Date d1 = null;
                Date d2 = null;
                d1 = format.parse(str_intime);
                d2 = format.parse(str_ottime);
                long diff = d2.getTime() - d1.getTime();


                long diffHours = diff / (60 * 60 * 1000) % 24;
                holder.tvtotalhrs.setText(String.valueOf(diffHours) + "hrs");
                // int hours = p.getHours();
            }
        } catch (ParseException e) {

        }

    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}
