package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.LoginActivity;
import com.csn.ems.model.Login;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 21-10-2016.
 */

public class ChangePasswordFragment extends Fragment {
TextInputEditText confirmpassword,password;
    Button btnupdatepassword;
    String TAG="ChangePasswordFragment";
    Login login=new Login();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.password_change, container, false);
        confirmpassword=(TextInputEditText)view.findViewById(R.id.confirmpassword) ;
                password=(TextInputEditText)view.findViewById(R.id.password) ;
        btnupdatepassword=(Button) view.findViewById(R.id.btnupdatepassword) ;
        btnupdatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())){
                    Toast.makeText(getActivity(),"Password and Confirm passwords are not matching",Toast.LENGTH_SHORT).show();
                }else{
                    uploadDetails(LoginActivity.userName,password.getText().toString().trim());
                }
            }
        });
        return view;
    }
    void uploadDetails(String username,String password){

        final ProgressDialog loading = ProgressDialog.show(getActivity()
                , "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<Login> employeeDetailsCall = service.getLogin(username,password);
        employeeDetailsCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE

                    Login emp = response.body();
                    if (emp != null) {
                        Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Password Changed Successfully!")
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
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Login  Failed!")
                                .setMessage("We are unable to save your Loginin our database this time.\n\n" +
                                        "Please try validating your parameters once or Try again later.")
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getActivity(), "Error connecting with Web Services...\n" +
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
