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

import com.csn.ems.R;
import com.csn.ems.model.BreakDetails;
import com.csn.ems.model.UpcomingEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.csn.ems.R.id.tvbreaktime;

/**
 * Created by uyalanat on 29-10-2016.
 */

public class UpcomingEventsAdapter extends BaseAdapter {

    private Context context;
    //	private ArrayList<BreakDetails> al;
    private Resources resources;
    private Typeface medium;
    private Typeface regular;
    private List<UpcomingEvents> breakDetails;

    public UpcomingEventsAdapter(Context context, 	List<UpcomingEvents> breakDetails)
    {
        this.context				 = context;
        this.breakDetails				 = breakDetails;

    }

    @Override
    public int getCount()
    {
        return	breakDetails.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.child_upcomingholidays, null);
        TextView tvholidaydate=(TextView)convertView.findViewById(R.id.tvholidaydate);
TextView tvholidayname=(TextView)convertView.findViewById(R.id.tvholidayname);
            if (breakDetails.get(position).getHolidayDate() != null)
                tvholidaydate.setText(breakDetails.get(position).getHolidayDate());


        if (breakDetails.get(position).getHolidayName() != null)
            tvholidayname.setText(breakDetails.get(position).getHolidayName());
        return convertView;
    }
}

