package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csn.ems.R;
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
    public TimesheetRecyclerViewAdapter(Context context1,   List<TimeSheetDetails> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,tvcheckintime,tvcheckouttime,tvtotalhrs;
        public ImageView tvapprovalstatus;

        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (ImageView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvcheckintime= (TextView) v.findViewById(R.id.tvcheckintime);
            tvtotalhrs= (TextView) v.findViewById(R.id.tvtotalhrs);
            tvcheckouttime= (TextView) v.findViewById(R.id.tvcheckouttime);
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

        holder.textView.setText(timesheetDetails.get(position).getWorkingDate());
        String intime="";

        String ottime="";

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt=null;
        Date dt_out=null;
        try {
            if (timesheetDetails.get(position).getCheckIn() != null)
            {
                dt = sdf.parse(timesheetDetails.get(position).getCheckIn());


            }
            if (timesheetDetails.get(position).getCheckOut() != null)
            {

                dt_out = sdf.parse(timesheetDetails.get(position).getCheckOut());

            }
            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            intime=sdfs.format(dt);
            ottime=sdfs.format(dt_out);


            holder.tvcheckintime.setText(intime);
            holder.tvcheckouttime.setText(ottime);

            if (timesheetDetails.get(position).getCheckIn() != null&&timesheetDetails.get(position).getCheckOut() != null){
             //   long secs = (dt.getTime() - dt.getTime()) / 1000;
             //   int hours = secs / 3600;
                final int MILLI_TO_HOUR = 1000 * 60 * 60;
                int hours= (int) (dt.getTime() - dt_out.getTime()) / MILLI_TO_HOUR;
               //  Period p = new Period(dt, dt_out);

              //  holder.tvtotalhrs.setText(String.valueOf(holder)+"hrs");
               // int hours = p.getHours();
            }


        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }
}
