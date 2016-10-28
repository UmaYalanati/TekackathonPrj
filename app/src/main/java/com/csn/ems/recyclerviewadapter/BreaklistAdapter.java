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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.csn.ems.R.id.tvbreaktime;

public class BreaklistAdapter extends BaseAdapter {

	private Context context;
//	private ArrayList<BreakDetails> al;
	private Resources resources;
	private Typeface medium;
	private Typeface regular;
	private	List<BreakDetails> breakDetails;
	
	public BreaklistAdapter(Context context, 	List<BreakDetails> breakDetails)
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
		convertView = LayoutInflater.from(context).inflate(R.layout.cell_break, null);
		TextView tvbreaktime=(TextView)convertView.findViewById(R.id.tvbreaktime);
		String intime="";

		String ottime="";

	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
	Date dt=null;
		Date dt_out=null;
	try {
		if (breakDetails.get(position).getBreakIn() != null)
		{
			dt = sdf.parse(breakDetails.get(position).getBreakIn());
			dt_out = sdf.parse(breakDetails.get(position).getBreakOut());

	}
		System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
		intime=sdfs.format(dt);
		ottime=sdfs.format(dt_out);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}



		tvbreaktime.setText("Break("+intime+"---"+ottime+")");
		return convertView;
	}
}
