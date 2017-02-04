package com.tek.ems.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tek.ems.MainActivity;
import com.tek.ems.R;
import com.tek.ems.emsconstants.EmsConstants;
import com.tek.ems.emsconstants.SharedPreferenceUtils;
import com.tek.ems.model.CustomLoginDetails;
import com.tek.ems.model.Login;
import com.tek.ems.services.EMSService;
import com.tek.ems.services.ServiceGenerator;
import com.tek.ems.sharedpreference.LoginComplexPreferences;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tek.ems.emsconstants.EmsConstants.empId;

/*import android.util.Base64;*/


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

    public static Login logindetails = new Login();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CustomLoginDetails customLoginDetails = new CustomLoginDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        // ...
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                userIdEditText.setText("");
                passwordEditText.setText("");
                break;
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
                  /*  byte[] encodedBytes = Base64.getEncoder().encode("Test".getBytes());
                    System.out.println("encodedBytes " + new String(encodedBytes));*/
                    byte[] data = new byte[0];
                    String base64 = null;
                    try {
                        data = passwordEditText.getText().toString().trim().getBytes("UTF-8");
                        base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //    String source = encodeString(passwordEditText.getText().toString().trim());
                    String source = md5(passwordEditText.getText().toString().trim());
                    uploadDetails(userIdEditText.getText().toString().trim(), source);

                }
                //mlalwani@teksystems.com
                /*SendMail sm = new SendMail(getApplicationContext(), "ujain@teksystems.com", "Time and Expense", "Please Approve the Time and expense",true);
                //Executing sendmail to send email
                sm.execute();*/


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
                    //   logindetails.setChildEmployees(emp.getChildEmployees());
                    LoginComplexPreferences loginComplexPreferences = LoginComplexPreferences.getComplexPreferences(getBaseContext(), "object_prefs", 0);
                    loginComplexPreferences.putObject("object_value", emp);
                    loginComplexPreferences.commit();
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
                                        String.valueOf(emp.getFirstName())).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.emaailid,
                                        emp.getEmailid()).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.organizationame,
                                        String.valueOf(emp.getCompanyName())).commitSplash();

                        SharedPreferenceUtils
                                .getInstance(LoginActivity.this)
                                .editSplash()
                                .addSplashCacheItem(EmsConstants.reportingManagerId,
                                        String.valueOf(emp.getReportingManagerId())).commitSplash();


                        Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        userName = username_n;
                        mFirebaseAuth.createUserWithEmailAndPassword(passwordEditText.getText().toString().trim() + "@teksystems.com", userIdEditText.getText().toString().trim() + "12345")
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("Signup Error", "onCancelled", task.getException());
                                        } else {
                                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                            String uid = user.getUid();
                                            firebaseAuthWithCustomLogin(userIdEditText.getText().toString().trim() + "@teksystems.com", userIdEditText.getText().toString().trim() + "12345");
                                            Log.d(TAG, "onComplete: uid: " + uid);

                                        }
                                    }
                                }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Exception..", "" + e);
                                if (e.getMessage().equals("The email address is already in use by another account.")) {
                                    firebaseAuthWithCustomLogin(passwordEditText.getText().toString().trim() + "@teksystems.com", userIdEditText.getText().toString().trim() + "12345");
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                     /*   Intent intent_homescreen = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_homescreen);*/

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

    private String encodeString(String s) {
        byte[] data = new byte[0];

        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

            return base64Encoded;

        }
    }

    private void firebaseAuthWithCustomLogin(String username, String password) {
        //  loginWithPassword();
        // Log.d(TAG, "firebaseAuthWithCustomLogin() called with: mCustomToken = [" + mCustomToken + "]");
        mFirebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());

                        } else {
                            String token = FirebaseInstanceId.getInstance().getToken();

                            Intent intent_homescreen = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent_homescreen);
                           /* android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                    Settings.Secure.ANDROID_ID);*/

                            // getLogin(edittext_username.getText().toString().trim(), edittext_password.getText().toString().trim(), token, android_id);
                        }

                        // ...
                    }
                }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("onFailure Message", "" + e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

