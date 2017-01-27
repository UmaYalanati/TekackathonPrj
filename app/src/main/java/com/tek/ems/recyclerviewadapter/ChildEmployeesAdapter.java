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
import com.tek.ems.model.ChildEmployees;

import java.util.List;

/**
 * Created by uyalanat on 21-11-2016.
 */

public class ChildEmployeesAdapter extends BaseAdapter {

    private Context context;
    //	private ArrayList<BreakDetails> al;
    private Resources resources;
    private Typeface medium;
    private Typeface regular;

    // EMSApplication.InTakeMasterDetails
    List<ChildEmployees> leaveTypes;

    public ChildEmployeesAdapter(Context context, List<ChildEmployees> leaveTypes)
    {
        this.context				 = context;
        this.leaveTypes				 = leaveTypes;

    }

    @Override
    public int getCount()
    {
        if (leaveTypes.size()>0)
            return	leaveTypes.size();
        else
            return 0;
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
        tvleavetype.setText(leaveTypes.get(position).getEmployeeName());

        return convertView;
    }
}


