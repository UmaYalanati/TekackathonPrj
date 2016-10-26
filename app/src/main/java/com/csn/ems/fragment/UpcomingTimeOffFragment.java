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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.model.CreateLeaveRequest;
import com.csn.ems.model.CreateLeaveRequest;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.btnendtime;
import static com.csn.ems.R.id.btnstarttime;
import static com.csn.ems.R.id.tvcurrentdate;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class UpcomingTimeOffFragment extends Fragment implements View.OnClickListener {
  String  TAG="UpcomingTimeOffFragment";
    public static UpcomingTimeOffFragment newInstance() {
        return new UpcomingTimeOffFragment();
    }
CreateLeaveRequest createLeaveRequest=new CreateLeaveRequest();
    Button btnstarttime, btnendtime,btnsubmitrequest;
EditText ed_comments;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcomingtimeoff, container, false);
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        btnsubmitrequest = (Button) view.findViewById(R.id.btnsubmitrequest);
        ed_comments= (EditText) view.findViewById(R.id.ed_comments);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String fromDate = newDateFormat.format(calendar.getTime());

        btnendtime.setText(toDate);
        btnstarttime.setText(fromDate);

        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        btnsubmitrequest.setOnClickListener(this);
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
                break;
            case R.id.btnsubmitrequest:
                createLeave();
                break;
        }
    }

    void setData(){
       // "LeaveTypeId":1,"Comments":"HealthProblem","AssignedTo":null,"ActionDate":null,"LeaveStatusId":4}
        createLeaveRequest.setLeaveId(2);
        createLeaveRequest.setEmployeeId(2);
        try {
            String str_MyDate;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = newDateFormat.format(c.getTime());


            Date MyDate = newDateFormat.parse(formattedDate);
            //newDateFormat.applyPattern("yyyy-MM-dd");
            str_MyDate = newDateFormat.format(MyDate);
            createLeaveRequest.setAppliedDate(str_MyDate);
        } catch (ParseException e) {

        }
        createLeaveRequest.setDateFrom(btnstarttime.getText().toString().trim());
        createLeaveRequest.setDateTo(btnendtime.getText().toString().trim());
        createLeaveRequest.setLeaveId(1);
        createLeaveRequest.setComments(ed_comments.getText().toString().trim());
        createLeaveRequest.setAssignedTo(null);
        createLeaveRequest.setActionDate(null);
        createLeaveRequest.setLeaveStatusId(4);

    }
    void createLeave(){
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
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE

                    CreateLeaveRequest emp = response.body();
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
}
