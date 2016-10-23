package com.csn.ems.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csn.ems.R;
import com.csn.ems.recyclerviewadapter.ApprovedTimesheetRecyclerViewAdapter;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ApprovedTimesheet extends Fragment {

    String[] SPINNERLIST = {"Select", "Approved", "Unapproved"};
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    AppCompatSpinner spinner_listofsheet;
    TextView tvtittle;
    String[] subjects =
            {
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016",
                    "Oct 10,2016"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeetimesheet, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout)view. findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView)view. findViewById(R.id.my_recycler_view);

        tvtittle=(TextView)view.findViewById(R.id.tvtittle);

        tvtittle.setText("Time Card Approval");
        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new ApprovedTimesheetRecyclerViewAdapter(context, subjects);

        recyclerView.setAdapter(recyclerViewAdapter);
        spinner_listofsheet=(AppCompatSpinner)view.findViewById(R.id.spinner_listofsheet);

        spinner_listofsheet.setVisibility(View.GONE);


        return view;
    }

}

