package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.ed_Name;
import static com.csn.ems.R.id.ed_NickName;



/**
 * Created by uyalanat on 20-10-2016.
 */

public class EditEmployeeDetailsFragment extends Fragment {

    EmployeeDetails employeeDetails = new EmployeeDetails();
String TAG="EditEmployee";
    Button btnupdateemployee;
    TextInputEditText ed_gender;
    TextInputEditText ed_Name,ed_NickName,ed_email,ed_dob,ed_mobilenor,ed_homenor,ed_address,ed_address2,ed_city,ed_state,ed_zipcode;

    TextInputEditText ed_joiningDate,ed_positionName,ed_businessAreaName,ed_subBusinessAreaName,ed_hoursPerDay;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_profile, container, false);

        ed_gender=(TextInputEditText) view.findViewById(R.id.ed_gender);
        btnupdateemployee=(Button) view.findViewById(R.id.btnupdateemployee);
                ed_Name=(TextInputEditText) view.findViewById(R.id.ed_Name);
                ed_NickName=(TextInputEditText) view.findViewById(R.id.ed_NickName);
        ed_address2=(TextInputEditText) view.findViewById(R.id.ed_address2);
                ed_email=(TextInputEditText) view.findViewById(R.id.ed_email);
        ed_dob=(TextInputEditText) view.findViewById(R.id.ed_dob);
                ed_mobilenor=(TextInputEditText) view.findViewById(R.id.ed_mobilenor);
                ed_homenor=(TextInputEditText) view.findViewById(R.id.ed_homenor);
                ed_address=(TextInputEditText) view.findViewById(R.id.ed_address);
                ed_city=(TextInputEditText) view.findViewById(R.id.ed_city);
                ed_state=(TextInputEditText) view.findViewById(R.id.ed_state);
                ed_zipcode=(TextInputEditText) view.findViewById(R.id.ed_zipcode);

        ed_joiningDate=(TextInputEditText) view.findViewById(R.id.ed_joiningDate);
                ed_positionName=(TextInputEditText) view.findViewById(R.id.ed_positionName);
                ed_businessAreaName=(TextInputEditText) view.findViewById(R.id.ed_businessAreaName);
                ed_subBusinessAreaName=(TextInputEditText) view.findViewById(R.id.ed_subBusinessAreaName);
                ed_hoursPerDay=(TextInputEditText) view.findViewById(R.id.ed_hoursPerDay);





        loadConsolidatedData();


        btnupdateemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDetails();
            }
        });
        return view;
    }

    public void loadConsolidatedData() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
        int empid=Integer.parseInt(SharedPreferenceUtils
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
    public void setEmployeeDetails() {
        // ed_Name,ed_NickName,ed_email,ed_username,ed_mobilenor,ed_homenor,ed_address,ed_city,ed_state,ed_zipcode
        ed_Name.setText(employeeDetails.getEmployeeName());
        ed_email.setText(employeeDetails.getEmailId());
        ed_mobilenor.setText(employeeDetails.getContactNumber());
        ed_address.setText(employeeDetails.getAddress1());
        ed_address2.setText(employeeDetails.getAddress2());
        ed_city.setText(employeeDetails.getCity());
        ed_state.setText(employeeDetails.getStateName());
        ed_zipcode.setText(String.valueOf(employeeDetails.getPostalCode()));
        ed_dob.setText(employeeDetails.getdOB());
        ed_gender.setText(employeeDetails.getGender());
        ed_joiningDate.setText(employeeDetails.getJoiningDate());
                ed_positionName.setText(employeeDetails.getPositionName());
                ed_businessAreaName.setText(employeeDetails.getBusinessAreaName());
                ed_subBusinessAreaName.setText(employeeDetails.getSubBusinessAreaName());
                ed_hoursPerDay.setText(employeeDetails.getHoursPerDay());

    }
    public void updateemployeedetails() {
       // ed_Name,ed_NickName,ed_email,ed_username,ed_mobilenor,ed_homenor,ed_address,ed_city,ed_state,ed_zipcode
        employeeDetails.setEmployeeName(ed_Name.getText().toString().trim());
        employeeDetails.setEmailId(ed_email.getText().toString().trim());
        employeeDetails.setContactNumber(ed_mobilenor.getText().toString().trim());
        employeeDetails.setAddress1(ed_address.getText().toString().trim());
        employeeDetails.setAddress2(ed_address2.getText().toString().trim());
        employeeDetails.setdOB(ed_dob.getText().toString().trim());
        employeeDetails.setCity(ed_city.getText().toString().trim());
        employeeDetails.setStateName(ed_state.getText().toString().trim());
        employeeDetails.setGender(ed_gender.getText().toString().trim());
        if (!ed_zipcode.getText().toString().trim().isEmpty())
        employeeDetails.setPostalCode(Integer.parseInt(ed_zipcode.getText().toString().trim()));


        employeeDetails.setJoiningDate(ed_joiningDate.getText().toString().trim());
        employeeDetails.setPositionName(ed_positionName.getText().toString().trim());
        employeeDetails.setBusinessAreaName(ed_businessAreaName.getText().toString().trim());
        employeeDetails.setSubBusinessAreaName(ed_subBusinessAreaName.getText().toString().trim());
        employeeDetails.setHoursPerDay(ed_hoursPerDay.getText().toString().trim());



    }

    void uploadDetails(){
        updateemployeedetails();
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
            Call<EmployeeDetails> employeeDetailsCall = service.updateEmployee(employeeDetails);
        employeeDetailsCall.enqueue(new Callback<EmployeeDetails>() {
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

                        EmployeeDetails emp = response.body();
                        if (emp != null) {
                            Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("EmployeeDetails Uploaded Successfully!")
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
                                    .setTitle("EmployeeDetails Creation Failed!")
                                    .setMessage("We are unable to save your EmployeeDetailsin our database this time.\n\n" +
                                            "Please try validating your parameters once or Try again later.")
                                    .setPositiveButton(R.string.ok, null)
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EmployeeDetails> call, Throwable t) {
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
