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

import java.util.List;

public class BreaklistAdapter extends BaseAdapter {

    private Context context;
    //	private ArrayList<BreakDetails> al;
    private Resources resources;
    private Typeface medium;
    private Typeface regular;
    private List<BreakDetails> breakDetails;

    public BreaklistAdapter(Context context, List<BreakDetails> breakDetails) {
        this.context = context;
        this.breakDetails = breakDetails;

    }

    @Override
    public int getCount() {
        return breakDetails.size();
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
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_break, null);
        TextView tvbreaktime = (TextView) convertView.findViewById(R.id.tvbreaktime);
TextView tvbreakdesc= (TextView) convertView.findViewById(R.id.tvbreakdesc);

        if (breakDetails.get(position).getBreakIn() != null && breakDetails.get(position).getBreakOut() != null) {
            tvbreaktime.setText("Break(" + breakDetails.get(position).getBreakIn() + "---" + breakDetails.get(position).getBreakOut() + ")");
        }
        if (breakDetails.get(position).getBreakIn() == null && breakDetails.get(position).getBreakOut() != null) {
            tvbreaktime.setText("Break(" + "0.0" + "---" + breakDetails.get(position).getBreakOut() + ")");
        }
        if (breakDetails.get(position).getBreakOut() == null && breakDetails.get(position).getBreakIn() != null) {
            tvbreaktime.setText("Break(" + breakDetails.get(position).getBreakIn() + "---" + "0.0" + ")");
        }
if (breakDetails.get(position).getComments()!=null)
{
    tvbreakdesc.setVisibility(View.VISIBLE);
    tvbreakdesc.setText(breakDetails.get(position).getComments());
}else {
    tvbreakdesc.setVisibility(View.GONE);
}
        return convertView;
    }
}
