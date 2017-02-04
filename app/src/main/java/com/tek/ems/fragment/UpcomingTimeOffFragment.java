package com.tek.ems.fragment;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tek.ems.R;
import com.tek.ems.emsconstants.EmsConstants;
import com.tek.ems.emsconstants.SharedPreferenceUtils;
import com.tek.ems.model.CreateLeaveRequest;
import com.tek.ems.model.EmployeeDetails;
import com.tek.ems.recyclerviewadapter.LeaveTypeAdapter;
import com.tek.ems.services.EMSService;
import com.tek.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class UpcomingTimeOffFragment extends Fragment implements View.OnClickListener {
    String TAG = "UpcomingTimeOffFragment";


    public static UpcomingTimeOffFragment newInstance() {
        return new UpcomingTimeOffFragment();
    }

    String toDate;
    CreateLeaveRequest createLeaveRequest = new CreateLeaveRequest();
    Button btnstarttime, btnendtime, btnsubmitrequest;
    EditText ed_comments;
    Spinner spinner_leavetype;
    TextView tvtittle;
    EmployeeDetails employeeDetails = new EmployeeDetails();
    String leaveType = "NORMAL";
    TextView tvCasualLeaves, tveEarnedLeaves, tvSickLeaves, tveCompOffs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcomingtimeoff, container, false);

        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        btnsubmitrequest = (Button) view.findViewById(R.id.btnsubmitrequest);
        ed_comments = (EditText) view.findViewById(R.id.ed_comments);
        tvtittle = (TextView) view.findViewById(R.id.tvtittle);
        spinner_leavetype = (Spinner) view.findViewById(R.id.spinner_leavetype);
        tvCasualLeaves = (TextView) view.findViewById(R.id.tvCasualLeaves);
        tveEarnedLeaves = (TextView) view.findViewById(R.id.tveEarnedLeaves);
        tvSickLeaves = (TextView) view.findViewById(R.id.tvSickLeaves);
        tveCompOffs = (TextView) view.findViewById(R.id.tveCompOffs);
        final List<String> list = new ArrayList<String>();
        list.add("NORMAL");
        list.add("SICK");
        list.add("COMPOFF");
        list.add("OPTIONAL");
        LeaveTypeAdapter adapter = new LeaveTypeAdapter(getActivity(), list);
        spinner_leavetype.setAdapter(adapter);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                //leav_postion = arg2;
                leaveType = list.get(arg2);
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

    void createLeave() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);
        int empid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim());
        int reportingManagerId = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.reportingManagerId).toString().trim());
        EMSService service = ServiceGenerator.createService();
        Call<CreateLeaveRequest> createLeaveRequestcall = service.createLeaveRequest(empid, "Mobile", "NORMAL", ed_comments.getText().toString().trim(), btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim(),reportingManagerId);
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
                        if (response.code() == 400) {
                            Toast.makeText(getContext(), "You have already created Leave for this Day", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }


                    CreateLeaveRequest emp = response.body();
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());

                    // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyy-MM-dd");
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

                    // SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyy-MM-dd");
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

      /*  if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.rolename) != null && SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.rolename).equals("Manager")) {
            empid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.childEmployeeId).toString().trim());
        } else {
            empid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim());
        }*/

        empid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim());
        Call<EmployeeDetails> listCall = ServiceGenerator.createService().getEmployeeById(empid);

        listCall.enqueue(new Callback<EmployeeDetails>() {
            @Override
            public void onResponse(Call<EmployeeDetails> call, Response<EmployeeDetails> response) {
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
                    employeeDetails = response.body();
                    tvCasualLeaves.setText("CasualLeaves" + " : " + String.valueOf(employeeDetails.getCasualLeaves()));
                    tveEarnedLeaves.setText("EarnedLeaves" + " : " + String.valueOf(employeeDetails.getEarnedLeaves()));
                    tvSickLeaves.setText("SickLeaves" + " : " + String.valueOf(employeeDetails.getSickLeaves()));
                    tveCompOffs.setText("CompOffs" + " : " + String.valueOf(employeeDetails.getCompOffs()));
                    Log.i(TAG, "onResponse: Fetched " + employeeDetails + " PropertyTypes.");

                }
            }

            @Override
            public void onFailure(Call<EmployeeDetails> call, Throwable t) {
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


}
