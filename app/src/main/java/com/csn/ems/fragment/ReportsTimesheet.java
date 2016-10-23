package com.csn.ems.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.csn.ems.R;
import com.csn.ems.RecyclerViewAdapter.ReportTimesheetRecyclerViewAdapter;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportsTimesheet extends Fragment {

    public static ReportsTimesheet newInstance() {
        return new ReportsTimesheet();
    }
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

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
        final View view = inflater.inflate(R.layout.reporttimesheet, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout)view. findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView)view. findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new ReportTimesheetRecyclerViewAdapter(context, subjects);

        recyclerView.setAdapter(recyclerViewAdapter);



        return view;
    }

}
