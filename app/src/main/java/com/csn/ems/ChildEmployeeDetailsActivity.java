package com.csn.ems;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 27-11-2016.
 */

public class ChildEmployeeDetailsActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String TAG="EmployeeDetailsFragment";
    EmployeeDetails employeeDetails = new EmployeeDetails();
    Button btnlogout;
    TextView tvemployeefullname, tvid, tvusername, tvmobile, tvaddress, tvemail, tvposition;
    TextView tvgender, tvcity, tvstate, tvpincode, tvbusinessarea, tvsubbusiness, tvlocation, tvjoiningdate;
    TextView tvhrsperday,tvdob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_employeedetails);
        tvemployeefullname = (TextView) findViewById(R.id.tvemployeefullname);
        tvid = (TextView) findViewById(R.id.tvid);
        tvusername = (TextView) findViewById(R.id.tvusername);
        tvmobile = (TextView) findViewById(R.id.tvmobile);
        tvaddress = (TextView) findViewById(R.id.tvaddress);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvposition = (TextView) findViewById(R.id.tvposition);
        tvgender = (TextView) findViewById(R.id.tvgender);
        tvcity = (TextView) findViewById(R.id.tvcity);
        tvstate = (TextView) findViewById(R.id.tvstate);
        tvpincode = (TextView) findViewById(R.id.tvpincode);
        tvbusinessarea = (TextView) findViewById(R.id.tvbusinessarea);
        tvsubbusiness = (TextView) findViewById(R.id.tvsubbusiness);
        tvlocation = (TextView) findViewById(R.id.tvlocation);
        tvjoiningdate = (TextView) findViewById(R.id.tvjoiningdate);
        tvhrsperday = (TextView) findViewById(R.id.tvhrsperday);
        tvdob = (TextView) findViewById(R.id.tvdob);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setVisibility(View.GONE);
        displaydetails();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    void displaydetails(){
        final ProgressDialog loading = ProgressDialog.show(ChildEmployeeDetailsActivity.this, "Fetching Data", "Please wait...", false, false);
        int empid=EmsConstants.child_Employeeid;

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
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Error connecting with Web Services...\n" +
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
