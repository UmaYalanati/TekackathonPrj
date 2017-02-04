package com.tek.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tek.ems.R;
import com.tek.ems.model.LeaveDetails;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ListofLivesRecyclerViewAdapter extends RecyclerView.Adapter<ListofLivesRecyclerViewAdapter.ViewHolder> {

    // String[] SubjectValues;
    Context context;
    View view1;
    ListofLivesRecyclerViewAdapter.ViewHolder viewHolder1;
    TextView textView;
    private List<LeaveDetails> leaveDetails;

    public ListofLivesRecyclerViewAdapter(Context context1, List<LeaveDetails> leaveDetails) {

        this.leaveDetails = leaveDetails;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_leavestatus, tvleavedate, tvnumofleaves;

        public ViewHolder(View v) {

            super(v);
            tvleavedate = (TextView) v.findViewById(R.id.tvleavedate);
            tvnumofleaves = (TextView) v.findViewById(R.id.tvnumofleaves);
            textView_leavestatus = (TextView) v.findViewById(R.id.tvleavestatus);
        }
    }

    @Override
    public ListofLivesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.child_leaves, parent, false);

        viewHolder1 = new ListofLivesRecyclerViewAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ListofLivesRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.textView_leavestatus.setText(leaveDetails.get(position).getStatus());


        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (leaveDetails.get(position).getStartDate() != null) {
            holder.tvleavedate.setText(leaveDetails.get(position).getStartDate()+"to"+leaveDetails.get(position).getEndDate());
        }
        if (leaveDetails.get(position).getStartDate() != null && leaveDetails.get(position).getEndDate() != null) {
            /*try {
                Date date1 = myFormat.parse(leaveDetails.get(position).getStartDate());
                Date date2 = myFormat.parse(leaveDetails.get(position).getEndDate());
                long diff = date2.getTime() - date1.getTime();*/

                holder.tvnumofleaves.setText(leaveDetails.get(position).getNoOfDays() + "Day(s)");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public int getItemCount() {

        return leaveDetails.size();
    }
}
