package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.TimeSheetReport;
import com.csn.ems.recyclerviewadapter.ApprovedTimesheetRecyclerViewAdapter;
import com.csn.ems.recyclerviewadapter.ReportTimesheetSecondRecyclerViewAdapter;
import com.csn.ems.services.ServiceGenerator;

import org.joda.time.LocalDate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportsTimesheetSecond extends Fragment {

    String TAG="ReportsTimesheetSecond";

    public static ReportsTimesheetSecond newInstance() {
        return new ReportsTimesheetSecond();
    }

    public static final int JANUARY = 1;

    public static final int DECEMBER = 12;
CheckBox checkbox_select;
    public static final int FIRST_OF_THE_MONTH = 1;
    static int month;
    String[] str = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    LinearLayout ll_next, ll_back;
    TextView tvmonthname;

    RecyclerView recyclerView;

    ReportTimesheetSecondRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    List<TimeSheetReport> timesheetDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.reporttimesheetsecond, container, false);
        tvmonthname = (TextView) view.findViewById(R.id.tvmonthname);
        ll_next = (LinearLayout) view.findViewById(R.id.ll_next);
        ll_back = (LinearLayout) view.findViewById(R.id.ll_back);
        checkbox_select= (CheckBox) view.findViewById(R.id.checkbox_select);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recylerViewLayoutManager);




        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);
        getLastDayOfMonth(month + 1, year);

        System.out.println(new SimpleDateFormat("MMMM").format(cal.getTime()));
        tvmonthname.setText(new SimpleDateFormat("MMMM").format(cal.getTime()));

        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (month < 11) {
                    // month++;
                    month=month+1;
                    String monthString;

                    monthString = str[month+1 - 1];
                    tvmonthname.setText(monthString);

                } else {

                    month = 0;
                    String monthString;

                    monthString = str[month+1 - 1];
                    tvmonthname.setText(monthString);
                }


                getLastDayOfMonth(month+1,year);
            }

        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month >= 0) {
                  //  month--;
                    month=month-1;
                }
                getLastDayOfMonth(month + 1, year);

                String monthString;
                if (month < str.length) {
                    monthString = str[month+1 - 1];
                    tvmonthname.setText(monthString);
                } else {
                    monthString = "Invalid month";
                }
                // 
                // getLastDayOfMonth(month+1,year);
            }

        });

        checkbox_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()){
                    if (isChecked){
                        updateRecyclerViewForClients(timesheetDetails,true);
                    }else{
                        updateRecyclerViewForClients(timesheetDetails,false);
                    }
                }
            }
        });

        return view;
    }

    void getlistofleaves(int employeeId, String startdate) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<List<TimeSheetReport>> listCall = ServiceGenerator.createService().getTimeSheetReport(employeeId, startdate);


        listCall.enqueue(new Callback<List<TimeSheetReport>>() {
            @Override
            public void onResponse(Call<List<TimeSheetReport>> call, Response<List<TimeSheetReport>> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE
                    timesheetDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + timesheetDetails.size() + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
                    updateRecyclerViewForClients(timesheetDetails,false);
                }
            }

            @Override
            public void onFailure(Call<List<TimeSheetReport>> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                //    Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }

    private void updateRecyclerViewForClients(List<TimeSheetReport> timesheetDetails,boolean check) {

        recyclerViewAdapter = new ReportTimesheetSecondRecyclerViewAdapter(getActivity(), timesheetDetails,check);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        //  }

    }

    public void getLastDayOfMonth(final int month, final int year) {
        int lastDay = 0;
        int firstDay = 0;

        if ((month >= JANUARY) && (month <= DECEMBER)) {
            LocalDate aDate = new LocalDate(year, month, FIRST_OF_THE_MONTH);

            lastDay = aDate.dayOfMonth().getMaximumValue();
            firstDay = aDate.dayOfMonth().getMinimumValue();
        }
if (SharedPreferenceUtils
        .getInstance(getActivity())
        .getSplashCacheItem(
                EmsConstants.childEmployeeId)!=null&&!SharedPreferenceUtils
        .getInstance(getActivity())
        .getSplashCacheItem(
                EmsConstants.childEmployeeId).toString().trim().isEmpty()){
    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
            .getInstance(getActivity())
            .getSplashCacheItem(
                    EmsConstants.childEmployeeId).toString().trim()), String.valueOf(month)+"/"+String.valueOf(firstDay)+"/"+String.valueOf(year));
                }else{
    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
            .getInstance(getActivity())
            .getSplashCacheItem(
                    EmsConstants.employeeId).toString().trim()), String.valueOf(month)+"/"+String.valueOf(firstDay)+"/"+String.valueOf(year));
                }

    }
}

