package com.csn.ems.recyclerviewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csn.ems.EMSApplication;
import com.csn.ems.R;
import com.csn.ems.model.BreakDetails;
import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.model.LeaveStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by uyalanat on 30-10-2016.
 */

public class LeaveTypeAdapter extends BaseAdapter {

    private Context context;
    //	private ArrayList<BreakDetails> al;
    private Resources resources;
    private Typeface medium;
    private Typeface regular;

   // EMSApplication.InTakeMasterDetails
   List<LeaveStatus> leaveTypes;

    public LeaveTypeAdapter(Context context, List<LeaveStatus> leaveTypes)
    {
        this.context				 = context;
        this.leaveTypes				 = leaveTypes;

    }

    @Override
    public int getCount()
    {
        return	leaveTypes.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(context).inflate(R.layout.child_leavetype, null);
        TextView tvleavetype=(TextView)convertView.findViewById(R.id.tvleavetype);
        tvleavetype.setText(leaveTypes.get(position).getName());

        return convertView;
    }
}

