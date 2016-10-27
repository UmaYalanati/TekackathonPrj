package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.recyclerviewadapter.TimesheetRecyclerViewAdapter;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimeSheetFragment extends Fragment implements View.OnClickListener {
    public static TimeSheetFragment newInstance() {
        return new TimeSheetFragment();
    }
String TAG="TimeSheetFragment";
    Button btnstarttime, btnendtime;
    String[] SPINNERLIST = {"Select", "Approved", "Unapproved"};
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    AppCompatSpinner spinner_listofsheet;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeetimesheet, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

     //   recyclerViewAdapter = new TimesheetRecyclerViewAdapter(context, subjects);

        recyclerView.setAdapter(recyclerViewAdapter);
        spinner_listofsheet = (AppCompatSpinner) view.findViewById(R.id.spinner_listofsheet);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

        spinner_listofsheet.setAdapter(arrayAdapter);
        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String fromDate = newDateFormat.format(calendar.getTime());

        btnendtime.setText(toDate);
        btnstarttime.setText(fromDate);

      //  spinner_listofsheet.setoni

        spinner_listofsheet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Select")){
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),"ALL");
                }else{
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),spinner_listofsheet.getSelectedItem().toString());
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),"ALL");



        return view;
    }

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
               // Date date2 = sdf.parse(getTodaysDate());
               // Date date1 = sdf.parse(SharedPreferenceUtils.getInstance(context).getSettingsCacheItem(WatscoConstants.RATEUS_DATE).toString());
                if (spinner_listofsheet.getSelectedItem().toString().equals("Select")){
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),"ALL");
                }else{
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),spinner_listofsheet.getSelectedItem().toString());
                }

                break;

        }
    }

    void getlistofleaves(int employeeId,String startdate,String enddaate,String status) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<List<TimeSheetDetails>> listCall = ServiceGenerator.createService().getTimeSheetDetails(employeeId, startdate, enddaate, status);


        listCall.enqueue(new Callback<List<TimeSheetDetails>>() {
            @Override
            public void onResponse(Call<List<TimeSheetDetails>> call, Response<List<TimeSheetDetails>> response) {
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
                    List<TimeSheetDetails> timesheetDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + timesheetDetails.size() + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
                   updateRecyclerViewForClients(timesheetDetails);
                }
            }

            @Override
            public void onFailure(Call<List<TimeSheetDetails>> call, Throwable t) {
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

    private void updateRecyclerViewForClients(List<TimeSheetDetails> timesheetDetails) {

        TimesheetRecyclerViewAdapter adapter = new TimesheetRecyclerViewAdapter(context, timesheetDetails);

        recyclerView.setAdapter(adapter);
        //  }

    }
}
