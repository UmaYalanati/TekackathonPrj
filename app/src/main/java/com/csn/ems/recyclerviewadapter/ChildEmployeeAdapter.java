package com.csn.ems.recyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;
import com.csn.ems.callback.ItemClickListener;
import com.csn.ems.model.ChildEmployees;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by uyalanat on 27-11-2016.
 */

public class ChildEmployeeAdapter extends RecyclerView.Adapter<ChildEmployeeAdapter.ViewHolder> {

    // String[] SubjectValues;
    Context context;
    View view1;
    ChildEmployeeAdapter.ViewHolder viewHolder1;
    TextView textView;
    List<ChildEmployees> childEmployeeList;
    private ItemClickListener clickListener;
    public ChildEmployeeAdapter(Context context1, List<ChildEmployees> childEmployeeList) {

        this.childEmployeeList = childEmployeeList;
        context = context1;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;
        public de.hdodenhof.circleimageview.CircleImageView circleImageView;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.tvEmployee_name);
            circleImageView = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.img_profilepic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public ChildEmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.customchildemployee, parent, false);

        viewHolder1 = new ChildEmployeeAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ChildEmployeeAdapter.ViewHolder holder, int position) {

        holder.textView.setText(childEmployeeList.get(position).getEmployeeName());
        Picasso.with(context)
                .load("http://" + childEmployeeList.get(position).getEmployeeName()).into(holder.circleImageView);
       // holder.itemView.setOnClickListener(this);
    /*    holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"umaaaaaaaaaa",Toast.LENGTH_LONG).show();;
               // onClickSubject.onNext(element);
            }
        });*/
    }

    @Override
    public int getItemCount() {

        return childEmployeeList.size();
    }
}


