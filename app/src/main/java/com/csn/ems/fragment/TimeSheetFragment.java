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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.csn.ems.R;
import com.csn.ems.recyclerviewadapter.TimesheetRecyclerViewAdapter;


/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimeSheetFragment extends Fragment implements View.OnClickListener{
    public static TimeSheetFragment newInstance() {
        return new TimeSheetFragment();
    }
    Button btnstarttime,btnendtime;
    String[] SPINNERLIST = {"Select", "Approved", "Unapproved"};
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    AppCompatSpinner spinner_listofsheet;
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
        btnstarttime= (Button) view. findViewById(R.id.btnstarttime);
                btnendtime= (Button)view. findViewById(R.id.btnendtime);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new TimesheetRecyclerViewAdapter(context, subjects);

        recyclerView.setAdapter(recyclerViewAdapter);
        spinner_listofsheet=(AppCompatSpinner)view.findViewById(R.id.spinner_listofsheet);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

        spinner_listofsheet.setAdapter(arrayAdapter);
        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        return view;
    }
/*    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        setDate(c.getTime().getTime());
    }
    int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
            | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH
            | DateUtils.FORMAT_ABBREV_WEEKDAY;
    String dateString = DateUtils.formatDateTime(getActivity(),millisecond, flags);
    textDate.setText(dateString);
}*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment starttimeFragment = new DatePickerFragment(btnstarttime);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.btnendtime:
                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;

        }
    }
}
