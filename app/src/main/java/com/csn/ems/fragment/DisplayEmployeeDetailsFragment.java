package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.LoginActivity;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class DisplayEmployeeDetailsFragment extends Fragment {

   String TAG="EmployeeDetailsFragment";
    EmployeeDetails employeeDetails = new EmployeeDetails();
    Button btnlogout;
    TextView tvemployeefullname, tvid, tvusername, tvmobile, tvaddress, tvemail, tvposition;
    TextView tvgender, tvcity, tvstate, tvpincode, tvbusinessarea, tvsubbusiness, tvlocation, tvjoiningdate;
TextView tvhrsperday,tvdob;
    public static DisplayEmployeeDetailsFragment newInstance() {
        return new DisplayEmployeeDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeedetails, container, false);

        tvemployeefullname = (TextView) view.findViewById(R.id.tvemployeefullname);
        tvid = (TextView) view.findViewById(R.id.tvid);
        tvusername = (TextView) view.findViewById(R.id.tvusername);
        tvmobile = (TextView) view.findViewById(R.id.tvmobile);
        tvaddress = (TextView) view.findViewById(R.id.tvaddress);
        tvemail = (TextView) view.findViewById(R.id.tvemail);
        tvposition = (TextView) view.findViewById(R.id.tvposition);
        tvgender = (TextView) view.findViewById(R.id.tvgender);
        tvcity = (TextView) view.findViewById(R.id.tvcity);
        tvstate = (TextView) view.findViewById(R.id.tvstate);
        tvpincode = (TextView) view.findViewById(R.id.tvpincode);
        tvbusinessarea = (TextView) view.findViewById(R.id.tvbusinessarea);
        tvsubbusiness = (TextView) view.findViewById(R.id.tvsubbusiness);
        tvlocation = (TextView) view.findViewById(R.id.tvlocation);
        tvjoiningdate = (TextView) view.findViewById(R.id.tvjoiningdate);
        tvhrsperday = (TextView) view.findViewById(R.id.tvhrsperday);
                tvdob = (TextView) view.findViewById(R.id.tvdob);
        btnlogout= (Button) view.findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtils
                        .getInstance(getActivity())
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.employeeId,
                                "").commitSplash();

                Intent intent_homescreen = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_homescreen);
                getActivity().finish();
            }
        });
        displaydetails();

        return view;
    }
void displaydetails(){
    final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
    int empid=Integer.parseInt(SharedPreferenceUtils
            .getInstance(getActivity())
            .getSplashCacheItem(
                    EmsConstants.employeeId).toString().trim());

    if (SharedPreferenceUtils
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
    }else {
        empid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim());
    }
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
                Log.i(TAG, "onResponse: Fetched " + employeeDetails + " PropertyTypes.");
                setEmployeeDetails();
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
    void setEmployeeDetails(){
        // ed_Name,ed_NickName,ed_email,ed_username,ed_mobilenor,ed_homenor,ed_address,ed_city,ed_state,ed_zipcode
        tvemployeefullname.setText(employeeDetails.getEmployeeName());
        tvusername.setText(employeeDetails.getEmployeeCode());
        tvemail.setText(employeeDetails.getEmailId());
        tvmobile.setText(employeeDetails.getContactNumber());
        tvaddress.setText(employeeDetails.getAddress1()+employeeDetails.getAddress2());

        tvcity.setText(employeeDetails.getCity());
        tvstate.setText(employeeDetails.getStateName());
        tvpincode.setText(String.valueOf(employeeDetails.getPostalCode()));
        tvdob.setText(employeeDetails.getdOB());
        tvgender.setText(employeeDetails.getGender());
        tvjoiningdate.setText(employeeDetails.getJoiningDate());
        tvposition.setText(employeeDetails.getPositionName());
        tvbusinessarea.setText(employeeDetails.getBusinessAreaName());
        tvsubbusiness.setText(employeeDetails.getSubBusinessAreaName());
        tvhrsperday.setText(employeeDetails.getHoursPerDay());

    }
}
