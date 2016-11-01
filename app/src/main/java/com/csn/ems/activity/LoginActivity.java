package com.csn.ems.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.csn.ems.MainActivity;
import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.Login;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.emsconstants.EmsConstants.empId;


/**
 * Created by uyalanat on 18-10-2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    public static String userName;
    private Button bt_submit, bt_clear;

    Login loginset = new Login();
    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getSupportActionBar().hide();
        //  getActionBar().hide();
        if (SharedPreferenceUtils
                .getInstance(LoginActivity.this)
                .getSplashCacheItem(
                        EmsConstants.employeeId) != null && !SharedPreferenceUtils
                .getInstance(LoginActivity.this)
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim().isEmpty()) {
            Intent intent_homescreen = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent_homescreen);
        } else {
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.loginscreen);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            bt_clear = (Button) findViewById(R.id.btn_clear);
            bt_submit = (Button) findViewById(R.id.btn_submit);

            userIdEditText = (TextInputEditText) findViewById(R.id.userId);
            passwordEditText = (TextInputEditText) findViewById(R.id.password);

            bt_submit.setOnClickListener(this);
            bt_clear.setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (userIdEditText.getText().toString().isEmpty()) {
                    userIdEditText.setError("Please enter UserName to Login!");
                    userIdEditText.requestFocus();
                } else if (passwordEditText.getText().toString().isEmpty()) {
                    this.passwordEditText.setError("Please enter Password to Login!");

                    passwordEditText.requestFocus();
                } else {
                    try {
                        byte[] bytesOfMessage = passwordEditText.getText().toString().trim().getBytes("UTF-8");
                        try {

                            MessageDigest md = MessageDigest.getInstance("MD5");
                            byte[] thedigest = md.digest(bytesOfMessage);
                        } catch (NoSuchAlgorithmException e) {

                        }
                    } catch (UnsupportedEncodingException e) {

                    }

                    uploadDetails(userIdEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                  /*  Intent intent_homescreen = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent_homescreen);*/
                }


                break;

            default:
                break;
        }

    }

    void uploadDetails(final String username_n, String password) {

        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<Login> employeeDetailsCall = service.getLogin(username_n, password);
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
                        if (response.code() == 400) {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Sorry")
                                    .setMessage("LogIn Failed")
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            userName = username_n;


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


                            // response.errorBody().toString()


                        } else {
                            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE
//if (response.body().)
                    final Login emp = response.body();
                    if (emp != null) {
                        empId = emp.getEmployeeId();
                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.username,
                                        username_n).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.employeeId,
                                        String.valueOf(emp.getEmployeeId())).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.employeename,
                                        String.valueOf(emp.getEmployeeName())).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.emaailid,
                                        String.valueOf(emp.getEmailId())).commitSplash();
                        if (emp.getRoleName()!=null) {
                            SharedPreferenceUtils
                                    .getInstance(LoginActivity.this)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.rolename,
                                            emp.getRoleName()
                                    ).commitSplash();
                        }
                        if (emp.getChildEmployeeId()!=0) {
                            SharedPreferenceUtils
                                    .getInstance(LoginActivity.this)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.childEmployeeId,
                                            String.valueOf(emp.getChildEmployeeId())).commitSplash();
                        }
                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.photoPath,
                                        String.valueOf(emp.getPhotoPath())).commitSplash();
                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.organizationame,
                                        String.valueOf(emp.getOrganizationName())).commitSplash();


                        Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        userName = username_n;

                        Intent intent_homescreen = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_homescreen);
                      /*  new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Logged Successfully!")
                                .setMessage("")
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {



                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //  clear_Fields();
                                        // loadPage(1);
                                    }
                                })
                                .show();*/
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
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
                Toast.makeText(getApplicationContext(), "Error connecting with Web Services...\n" +
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

