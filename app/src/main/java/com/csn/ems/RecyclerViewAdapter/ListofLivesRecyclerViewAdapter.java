package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;
import com.csn.ems.model.LeaveDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        public TextView textView_leavestatus,tvleavedate,tvnumofleaves;

        public ViewHolder(View v) {

            super(v);
            tvleavedate= (TextView) v.findViewById(R.id.tvleavedate);
            tvnumofleaves= (TextView) v.findViewById(R.id.tvnumofleaves);
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

        holder.textView_leavestatus.setText(leaveDetails.get(position).getLeaveStatus());


        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] parts = leaveDetails.get(position).getDateFrom().split("T");
        String[] partss = leaveDetails.get(position).getDateTo().split("T");
        String inputString1 = parts[0];
        String inputString2 = partss[0];
        System.out.println ("Days: " + parts[0]);
        holder.tvleavedate.setText(parts[0]+" To "+partss[0]);
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();

      holder.tvnumofleaves.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+"Day(s)"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {

        return leaveDetails.size();
    }
}
