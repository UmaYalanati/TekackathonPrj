package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.BreakDetails;
import com.csn.ems.model.InsertBreakIn;
import com.csn.ems.model.InsertClockIn;
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

/**
 * Created by uyalanat on 22-10-2016.
 */

public class CheckinCheckoutFragment extends Fragment implements View.OnClickListener , LocationListener {

    String TAG="CheckoutFragment";

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    ListView listView_breakDetails;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;

    TextView tvcurrentdate, tvcurrenttime,tvtotalahrs,tvcheckoutcurrentdate;
    Button btncheckin, btncheckout, btncontinueshift;
    ImageButton imgbtnbreak;
    LinearLayout layout_checkout, layout_checkin, ll_breaktime, ll_startbreak;
      double lat,lng;
    InsertClockIn insertClockIn=new InsertClockIn();
    InsertBreakIn insertBreakIn=new InsertBreakIn();
    String formattedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.checkinfragment, container, false);
        listView_breakDetails= (ListView) view.findViewById(R.id.listView_breakDetails);
        tvcurrentdate = (TextView) view.findViewById(R.id.tvcurrentdate);
        tvcheckoutcurrentdate= (TextView) view.findViewById(R.id.tvcheckoutcurrentdate);
        tvcurrenttime = (TextView) view.findViewById(R.id.tvcurrenttime);
        tvtotalahrs= (TextView) view.findViewById(R.id.tvtotalahrs);
        btncheckin = (Button) view.findViewById(R.id.btncheckin);
        btncheckout = (Button) view.findViewById(R.id.btncheckout);
        btncontinueshift = (Button) view.findViewById(R.id.btncontinueshift);
        imgbtnbreak = (ImageButton) view.findViewById(R.id.imgbtnbreak);
        layout_checkout = (LinearLayout) view.findViewById(R.id.layout_checkout);
        layout_checkin = (LinearLayout) view.findViewById(R.id.layout_checkin);
        ll_breaktime = (LinearLayout) view.findViewById(R.id.ll_breaktime);
        ll_startbreak = (LinearLayout) view.findViewById(R.id.ll_startbreak);
        btncheckin.setOnClickListener(this);
        btncheckout.setOnClickListener(this);
        imgbtnbreak.setOnClickListener(this);
        btncontinueshift.setOnClickListener(this);

        try {
            String str_MyDate;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
             formattedDate = newDateFormat.format(c.getTime());


            Date MyDate = newDateFormat.parse(formattedDate);
            newDateFormat.applyPattern("MMM d, yyyy");
            str_MyDate = newDateFormat.format(MyDate);
            tvcurrentdate.setText(str_MyDate);
            tvcheckoutcurrentdate.setText(str_MyDate);



        } catch (ParseException e) {

        }

        Thread myThread = null;

        Runnable runnable = new CheckinCheckoutFragment.CountDownRunner();
        myThread = new Thread(runnable);
        myThread.start();

        locationManager = (LocationManager)getActivity(). getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION","PERMISSION_NOT_GRANTED");
        }


        return view;
    }
    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
    
    
    public void  updateCheckin(){
        insertClockIn.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        insertClockIn.setWorkingDate(formattedDate);
        insertClockIn.setCheckInLattitude(lat);
        insertClockIn.setCheckInLongitude(lng);
        insertClockIn.setCheckIn(tvcurrenttime.getText().toString().trim());

    }
    public void  updateCheckOut(){
        insertClockIn.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        insertClockIn.setWorkingDate(formattedDate);
        insertClockIn.setCheckOutLattitude(lat);
        insertClockIn.setCheckOutLongitude(lng);
        insertClockIn.setCheckOut(tvtotalahrs.getText().toString().trim());

    }

    public void  updateBreakin(){
        insertClockIn.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        insertClockIn.setWorkingDate(formattedDate);
        insertClockIn.setCheckInLattitude(lat);
        insertClockIn.setCheckInLongitude(lng);
        insertClockIn.setCheckIn(tvcurrenttime.getText().toString().trim());

    }
    public void checkIn(boolean checkin){
        {

            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);

            EMSService service = ServiceGenerator.createService();
            Call<InsertClockIn> insertClockInCall;
            if (checkin) {
                updateCheckin();
                insertClockInCall = service.insertClockIn(insertClockIn);
            }
            else
            {
                updateCheckOut();
                insertClockInCall = service.updateClockOut(insertClockIn);
            }
            insertClockInCall.enqueue(new Callback<InsertClockIn>() {
                @Override
                public void onResponse(Call<InsertClockIn> call, Response<InsertClockIn> response) {
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

                        InsertClockIn emp = response.body();
                        if (emp != null) {
                            Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("InsertClockIn Uploaded Successfully!")
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
                                    .setTitle("InsertClockIn Creation Failed!")
                                    .setMessage("We are unable to save your InsertClockInin our database this time.\n\n" +
                                            "Please try validating your parameters once or Try again later.")
                                    .setPositiveButton(R.string.ok, null)
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<InsertClockIn> call, Throwable t) {
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
    public void breakIn(boolean breakin){
        {

            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);

            EMSService service = ServiceGenerator.createService();
            Call<InsertBreakIn> insertClockInCall;
            if (breakin) {
                updateCheckin();
                insertClockInCall = service.InsertBreakIn(insertBreakIn);
            }
            else
            {
                updateCheckOut();
                insertClockInCall = service.updateBreakOut(insertBreakIn);
            }
            insertClockInCall.enqueue(new Callback<InsertBreakIn>() {
                @Override
                public void onResponse(Call<InsertBreakIn> call, Response<InsertBreakIn> response) {
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

                        InsertBreakIn emp = response.body();
                        if (emp != null) {
                            Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("InsertClockIn Uploaded Successfully!")
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
                                    .setTitle("InsertClockIn Creation Failed!")
                                    .setMessage("We are unable to save your InsertClockInin our database this time.\n\n" +
                                            "Please try validating your parameters once or Try again later.")
                                    .setPositiveButton(R.string.ok, null)
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<InsertBreakIn> call, Throwable t) {
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
    public void getbreaktimes(){
        {
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
            int empid=Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim());
            Call<BreakDetails> listCall = ServiceGenerator.createService().getBreakDetails(empid);

            listCall.enqueue(new Callback<BreakDetails>() {
                @Override
                public void onResponse(Call<BreakDetails> call, Response<BreakDetails> response) {
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
                        setBreakDetails();
                    }
                }

                @Override
                public void onFailure(Call<BreakDetails> call, Throwable t) {
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
    public void doWork() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String am_pm = null;

                    Calendar cal = Calendar.getInstance();

                    int millisecond = cal.get(Calendar.MILLISECOND);
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    //12 hour format
                    int hour = cal.get(Calendar.HOUR);

                    Calendar now = Calendar.getInstance();
                    int a = now.get(Calendar.AM_PM);
                    if (a == Calendar.AM)
                        am_pm = "AM";
                    else
                        am_pm = "PM";
                    String curTime = String.valueOf(hour) + ":" + String.valueOf(minute) + " " + am_pm;

                    tvcurrenttime.setText(curTime);
                    tvtotalahrs.setText(curTime);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncheckin:
                layout_checkin.setVisibility(View.GONE);
                layout_checkout.setVisibility(View.VISIBLE);
                checkIn(true);
                break;
            case R.id.btncheckout:
                layout_checkin.setVisibility(View.VISIBLE);
                layout_checkout.setVisibility(View.GONE);
                checkIn(false);
                break;
            case R.id.btncontinueshift:
                ll_startbreak.setVisibility(View.VISIBLE);
                ll_breaktime.setVisibility(View.GONE);

                break;
            case R.id.imgbtnbreak:
                ll_startbreak.setVisibility(View.GONE);
                ll_breaktime.setVisibility(View.VISIBLE);

                break;
        }
    }

    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }


}

