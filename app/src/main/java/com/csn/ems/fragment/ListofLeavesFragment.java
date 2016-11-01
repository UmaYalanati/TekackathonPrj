package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.LeaveCategorylist;
import com.csn.ems.recyclerviewadapter.Leaves_ListAdapter;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 01-11-2016.
 */

public class ListofLeavesFragment extends Fragment implements View.OnClickListener {
    String TAG = "LeavesStatusFragment";
    //  LeaveCategorylist leavedetails=new LeaveCategorylist();
    Context context;
   // RecyclerView recyclerView;
    //RelativeLayout relativeLayout;
  //  RecyclerView.Adapter recyclerViewAdapter;
   // RecyclerView.LayoutManager recylerViewLayoutManager;
    Button btnstarttime, btnendtime;

    ListView listView_pending, listView_approved, listView_rejected;
TextView tvRejected,tvapproved,tvPending;

    public static ListofLeavesFragment newInstance() {
        return new ListofLeavesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_leaves, container, false);

        context = getActivity();
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        //relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);
        listView_pending = (ListView) view.findViewById(R.id.listView_pending);
        listView_approved = (ListView) view.findViewById(R.id.listView_approved);
        listView_rejected = (ListView) view.findViewById(R.id.listView_rejected);
        tvRejected= (TextView) view.findViewById(R.id.tvRejected);
                tvapproved= (TextView) view.findViewById(R.id.tvapproved);
                tvPending= (TextView) view.findViewById(R.id.tvPending);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        //SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
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

    void getlistofleaves(String startdate, String enddaate) {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<LeaveCategorylist> listCall = ServiceGenerator.createService().getLeaveCategorylist(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()), startdate, "11/5/2016");


        listCall.enqueue(new Callback<LeaveCategorylist>() {
            @Override
            public void onResponse(Call<LeaveCategorylist> call, Response<LeaveCategorylist> response) {
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
                    LeaveCategorylist leaveDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + leaveDetails + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
                    updateRecyclerViewForClients(leaveDetails);
                }
            }

            @Override
            public void onFailure(Call<LeaveCategorylist> call, Throwable t) {
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

    private void updateRecyclerViewForClients(LeaveCategorylist leaveDetails) {
        //   Log.d(TAG, "updateRecyclerViewForClients() called for " + leaveDetails.size() + " Clients.");

       // ,,
       if (leaveDetails.getPending()!=null&&leaveDetails.getPending().size()>0) {
           Leaves_ListAdapter adapter = new Leaves_ListAdapter(context, leaveDetails.getPending());

           listView_pending.setAdapter(adapter);
           tvPending.setVisibility(View.VISIBLE);
       }
        if (leaveDetails.getApproved()!=null&&leaveDetails.getApproved().size()>0) {
            Leaves_ListAdapter adapter1 = new Leaves_ListAdapter(context, leaveDetails.getApproved());

            listView_approved.setAdapter(adapter1);
            tvapproved.setVisibility(View.VISIBLE);
        }

        if (leaveDetails.getRejected()!=null&&leaveDetails.getRejected().size()>0) {
            Leaves_ListAdapter adapter3 = new Leaves_ListAdapter(context, leaveDetails.getRejected());

            listView_rejected.setAdapter(adapter3);
            tvRejected.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment starttimeFragment = new DatePickerFragment(btnstarttime, btnstarttime.getText().toString(), false);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");
               if (!btnendtime.getText().toString().trim().isEmpty()){
                   getlistofleaves(btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim());
               }
                break;
            case R.id.btnendtime:
                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime, btnstarttime.getText().toString(), false);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                getlistofleaves(btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim());
                break;

        }
    }
}

