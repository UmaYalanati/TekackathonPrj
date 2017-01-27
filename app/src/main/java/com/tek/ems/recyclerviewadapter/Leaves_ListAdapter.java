package com.tek.ems.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tek.ems.R;
import com.tek.ems.model.LeaveDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by uyalanat on 01-11-2016.
 */

public class Leaves_ListAdapter extends BaseAdapter {

    private Context context;
    //	private ArrayList<BreakDetails> al;
    private Resources resources;
    private Typeface medium;
    private Typeface regular;
    private List<LeaveDetails> leaveDetails;

    public Leaves_ListAdapter(Context context, List<LeaveDetails> leaveDetails) {
        this.context = context;
        this.leaveDetails = leaveDetails;

    }

    @Override
    public int getCount() {
        return leaveDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.child_leaves, null);
        TextView tvleavedate = (TextView) convertView.findViewById(R.id.tvleavedate);
        TextView tvnumofleaves = (TextView) convertView.findViewById(R.id.tvnumofleaves);
        TextView textView_leavestatus = (TextView) convertView.findViewById(R.id.tvleavestatus);
        if (leaveDetails.get(position).getLeaveType() != null)
            textView_leavestatus.setText(leaveDetails.get(position).getLeaveType());


        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (leaveDetails.get(position).getLeaveDate() != null) {
            tvleavedate.setText(leaveDetails.get(position).getLeaveDate());
        }
        if (leaveDetails.get(position).getDateFrom() != null && leaveDetails.get(position).getDateTo() != null) {
            try {
                Date date1 = myFormat.parse(leaveDetails.get(position).getDateFrom());
                Date date2 = myFormat.parse(leaveDetails.get(position).getDateTo());
                long diff = date2.getTime() - date1.getTime();

                tvnumofleaves.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "Day(s)"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}

