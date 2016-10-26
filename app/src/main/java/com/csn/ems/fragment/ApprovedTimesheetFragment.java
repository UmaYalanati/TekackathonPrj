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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.recyclerviewadapter.ApprovedTimesheetRecyclerViewAdapter;
import com.csn.ems.recyclerviewadapter.TimesheetRecyclerViewAdapter;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ApprovedTimesheetFragment extends Fragment {

    String[] SPINNERLIST = {"Select", "Approved", "Unapproved"};
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    ApprovedTimesheetRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    AppCompatSpinner spinner_listofsheet;
    TextView tvtittle;
Button btnstarttime,btnendtime;
String TAG="TimesheetFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeetimesheet, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        tvtittle = (TextView) view.findViewById(R.id.tvtittle);
        btnstarttime= (Button) view.findViewById(R.id.btnstarttime);
                btnendtime= (Button) view.findViewById(R.id.btnendtime);
        tvtittle.setText("Time Card Approval");
        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

      //  recyclerViewAdapter = new ApprovedTimesheetRecyclerViewAdapter(context, subjects);


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String fromDate = newDateFormat.format(calendar.getTime());

        btnendtime.setText(toDate);
        btnstarttime.setText(fromDate);

        getlistofleaves(1,btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),"Pending");

       // recyclerView.setAdapter(recyclerViewAdapter);
        spinner_listofsheet = (AppCompatSpinner) view.findViewById(R.id.spinner_listofsheet);

        spinner_listofsheet.setVisibility(View.GONE);



        return view;
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

        recyclerViewAdapter = new ApprovedTimesheetRecyclerViewAdapter(getActivity(), timesheetDetails);

        recyclerView.setAdapter(recyclerViewAdapter);
        //  }

    }
}

