package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.EMSApplication;
import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.CreateLeaveRequest;
import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.model.UpcomingEvents;
import com.csn.ems.recyclerviewadapter.LeaveTypeAdapter;
import com.csn.ems.recyclerviewadapter.UpcomingEventsAdapter;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.ParseException;
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

public class UpcomingTimeOffFragment extends Fragment implements View.OnClickListener {
    String TAG = "UpcomingTimeOffFragment";
    ListView listView_upcomingholidayslist;

    public static UpcomingTimeOffFragment newInstance() {
        return new UpcomingTimeOffFragment();
    }

    String toDate;
    CreateLeaveRequest createLeaveRequest = new CreateLeaveRequest();
    Button btnstarttime, btnendtime, btnsubmitrequest;
    EditText ed_comments;
    InTakeMasterDetails inTakeMasterDetails = new InTakeMasterDetails();
    Spinner spinner_leavetype;
    TextView tvtittle;
    int leaveTypeId, leav_postion = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcomingtimeoff, container, false);
        listView_upcomingholidayslist = (ListView) view.findViewById(R.id.listView_upcomingholidayslist);
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        btnsubmitrequest = (Button) view.findViewById(R.id.btnsubmitrequest);
        ed_comments = (EditText) view.findViewById(R.id.ed_comments);
        tvtittle = (TextView) view.findViewById(R.id.tvtittle);
        spinner_leavetype = (Spinner) view.findViewById(R.id.spinner_leavetype);

        LeaveTypeAdapter adapter = new LeaveTypeAdapter(getActivity(), EMSApplication.inTakeMasterDetails.getLeaveTypes());
        spinner_leavetype.setAdapter(adapter);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        String fromDate = newDateFormat.format(calendar.getTime());
        upcomingDetails();
        btnendtime.setText(fromDate);
        btnstarttime.setText(toDate);

        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        btnsubmitrequest.setOnClickListener(this);


        spinner_leavetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int selectedPosition = arg2; //Here is your selected position
                leav_postion = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment starttimeFragment = new DatePickerFragment(btnstarttime, btnstarttime.getText().toString(), true);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.btnendtime:

                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime, btnstarttime.getText().toString().trim(), true);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");

                break;
            case R.id.btnsubmitrequest:
                createLeave();
                break;
        }
    }

    void setData() {
        leaveTypeId = EMSApplication.inTakeMasterDetails.getLeaveTypes().get(leav_postion).getId();
        createLeaveRequest.setLeaveTypeId(leaveTypeId);
        // createLeaveRequest.setLeaveleaveTypeId);
        createLeaveRequest.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        try {
            String str_MyDate;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            //SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = newDateFormat.format(c.getTime());


            Date MyDate = newDateFormat.parse(formattedDate);
            //newDateFormat.applyPattern("yyyy-MM-dd");
            str_MyDate = newDateFormat.format(MyDate);
            createLeaveRequest.setAppliedDate(str_MyDate);
        } catch (ParseException e) {

        }
        createLeaveRequest.setDateFrom(btnstarttime.getText().toString().trim());
        createLeaveRequest.setDateTo(btnendtime.getText().toString().trim());
        createLeaveRequest.setLeaveId(0);
        createLeaveRequest.setComments(ed_comments.getText().toString().trim());
        createLeaveRequest.setAssignedTo(null);
        createLeaveRequest.setActionDate(null);
        createLeaveRequest.setLeaveStatusId(4);

    }

    void createLeave() {
        setData();
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<CreateLeaveRequest> createLeaveRequestcall = service.createLeaveRequest(createLeaveRequest);
        createLeaveRequestcall.enqueue(new Callback<CreateLeaveRequest>() {
            @Override
            public void onResponse(Call<CreateLeaveRequest> call, Response<CreateLeaveRequest> response) {
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


                    CreateLeaveRequest emp = response.body();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    toDate = newDateFormat.format(c.getTime());

                    Calendar calendar = Calendar.getInstance(); // this would default to now
                    calendar.add(Calendar.DAY_OF_MONTH, +1);
                    String fromDate = newDateFormat.format(calendar.getTime());
                    upcomingDetails();
                    btnendtime.setText(fromDate);
                    btnstarttime.setText(toDate);
                    ed_comments.setText("");
                    spinner_leavetype.setSelection(0);
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE

                    CreateLeaveRequest emp = response.body();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    toDate = newDateFormat.format(c.getTime());

                    Calendar calendar = Calendar.getInstance(); // this would default to now
                    calendar.add(Calendar.DAY_OF_MONTH, +1);
                    String fromDate = newDateFormat.format(calendar.getTime());
                    upcomingDetails();
                    btnendtime.setText(fromDate);
                    btnstarttime.setText(toDate);
                    ed_comments.setText("");
                    spinner_leavetype.setSelection(0);
                    if (emp != null) {
                        Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        new AlertDialog.Builder(getContext())
                                .setTitle("Your leave has been created Successfully!")
                                .setMessage("")
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //loadPage(1);
                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //  clear_Fields();
                                        // loadPage(1);
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("CreateLeaveRequest Creation Failed!")
                                .setMessage("We are unable to save your CreateLeaveRequestin our database this time.\n\n" +
                                        "Please try validating your parameters once or Try again later.")
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateLeaveRequest> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
                } else {
                }
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();

    }

    public void upcomingDetails() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
        int empid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim());
           /* int timesheetid=Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.timesheetId).toString().trim());*/
        int timesheetid = 1;
        Call<List<UpcomingEvents>> listCall = ServiceGenerator.createService().getUpcomingEvents(toDate);

        listCall.enqueue(new Callback<List<UpcomingEvents>>() {
            @Override
            public void onResponse(Call<List<UpcomingEvents>> call, Response<List<UpcomingEvents>> response) {
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
                    //  breakDetails = response.body();

                    List<UpcomingEvents> breakDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + breakDetails + " PropertyTypes.");

                    setUpcomingEvents(breakDetails);
                }
            }

            @Override
            public void onFailure(Call<List<UpcomingEvents>> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }

    public void setUpcomingEvents(List<UpcomingEvents> breakDetails) {
        if (breakDetails.size() > 0) {
            tvtittle.setVisibility(View.VISIBLE);
        }
        UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(getActivity(), breakDetails);
        listView_upcomingholidayslist.setAdapter(adapter);

    }
}
