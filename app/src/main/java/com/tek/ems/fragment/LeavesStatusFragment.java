package com.tek.ems.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tek.ems.R;
import com.tek.ems.emsconstants.EmsConstants;
import com.tek.ems.emsconstants.SharedPreferenceUtils;
import com.tek.ems.model.LeaveDetails;
import com.tek.ems.recyclerviewadapter.ListofLivesRecyclerViewAdapter;
import com.tek.ems.services.ServiceGenerator;

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

public class LeavesStatusFragment extends Fragment implements View.OnClickListener {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String TAG = "LeavesStatusFragment";
    //  LeaveDetails leavedetails=new LeaveDetails();
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    Button btnstarttime, btnendtime;


    public static LeavesStatusFragment newInstance() {
        return new LeavesStatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.leaveslist, container, false);

        context = getActivity();
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        //SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String fromDate = newDateFormat.format(calendar.getTime());

        btnendtime.setText(toDate);
        btnstarttime.setText(fromDate);

        getlistofleaves(btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim());

        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);


        return view;
    }

    void getlistofleaves(String startdate,String enddaate) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<List<LeaveDetails>> listCall = ServiceGenerator.createService().getLeaveDetails(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()), startdate, enddaate);


        listCall.enqueue(new Callback<List<LeaveDetails>>() {
            @Override
            public void onResponse(Call<List<LeaveDetails>> call, Response<List<LeaveDetails>> response) {
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
                    List<LeaveDetails> leaveDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + leaveDetails.size() + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
                    updateRecyclerViewForClients(leaveDetails);
                }
            }

            @Override
            public void onFailure(Call<List<LeaveDetails>> call, Throwable t) {
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

    private void updateRecyclerViewForClients(List<LeaveDetails> leaveDetails) {
        //   Log.d(TAG, "updateRecyclerViewForClients() called for " + leaveDetails.size() + " Clients.");

        // if (recyclerViewAdapter != null) {
        ListofLivesRecyclerViewAdapter adapter = new ListofLivesRecyclerViewAdapter(context, leaveDetails);

        recyclerView.setAdapter(adapter);
        //  }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment starttimeFragment = new DatePickerFragment(btnstarttime,btnstarttime.getText().toString(),false);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.btnendtime:
                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime,btnstarttime.getText().toString(),false);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                getlistofleaves(btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim());
                break;

        }
    }
}
