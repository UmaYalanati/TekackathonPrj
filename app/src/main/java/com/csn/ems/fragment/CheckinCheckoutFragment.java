package com.csn.ems.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.csn.ems.recyclerviewadapter.BreaklistAdapter;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

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
 * Created by uyalanat on 22-10-2016.
 */

public class CheckinCheckoutFragment extends Fragment implements View.OnClickListener, LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    static final int  MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=2;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    String TAG = "CheckoutFragment";
    BreakDetails breakDetails = new BreakDetails();
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    ListView listView_breakDetails;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    TextView tvcheckintime, tvcurrentdate, tvcurrenttime, tvbreaktime, tvtotalahrs, tvcheckoutcurrentdate;
    Button btncheckin, btncheckout, btncontinueshift;
    ImageButton imgbtnbreak;
    EditText edcomments;
    LinearLayout layout_checkout, layout_checkin, ll_breaktime, ll_startbreak;
    double lat, lng;
    InsertClockIn insertClockIn = new InsertClockIn();
    InsertBreakIn insertBreakIn = new InsertBreakIn();
    String formattedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.checkinfragment, container, false);
        edcomments = (EditText) view.findViewById(R.id.edcomments);
        listView_breakDetails = (ListView) view.findViewById(R.id.listView_breakDetails);
        tvcheckintime = (TextView) view.findViewById(R.id.tvcheckintime);
        tvcurrentdate = (TextView) view.findViewById(R.id.tvcurrentdate);
        tvcheckoutcurrentdate = (TextView) view.findViewById(R.id.tvcheckoutcurrentdate);
        tvcurrenttime = (TextView) view.findViewById(R.id.tvcurrenttime);
        tvbreaktime = (TextView) view.findViewById(R.id.tvbreaktime);
        tvtotalahrs = (TextView) view.findViewById(R.id.tvtotalahrs);
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

        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Activity.LOCATION_SERVICE);


        Location location = null;

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          /*  ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );*/
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        try {
            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            buildAlertMessageNoGps();
            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
        }
        boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled(
            buildAlertMessageNoGps();
        } else {
            // this.canGetLocation = true;
            if (isNetworkEnabled) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            onLocationChanged(location);

                        }
                    }
                } catch (SecurityException e) {
                    Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
                }
            } else if (isGPSEnabled) {
                // if GPS Enabled get lat/long using GPS Services
                if (location == null) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                onLocationChanged(location);
                            }
                        }
                    } catch (SecurityException e) {
                        Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
                    }
                }
            }
        }

        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.checkinTime) != null && !SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.checkinTime).toString().trim().isEmpty()) {
            tvcheckintime.setText(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.checkinTime).toString().trim());
        }
        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.breakinTime) != null && !SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.breakinTime).toString().trim().isEmpty()) {
            tvbreaktime.setText(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.breakinTime).toString().trim());
        }

        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.checkinTime) != null && !SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.checkinTime).toString().trim().isEmpty()) {
            getbreaktimes();
            layout_checkin.setVisibility(View.GONE);
            layout_checkout.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


    public void updateCheckin() {
      /*  int timesheetid=Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.timesheetId).toString().trim());
        insertClockIn.setTimeSheetId(timesheetid);*/
        insertClockIn.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        insertClockIn.setWorkingDate(formattedDate);
        insertClockIn.setCheckInLattitude(lat);
        insertClockIn.setCheckInLongitude(lng);
        insertClockIn.setCheckIn(tvcurrenttime.getText().toString().trim());

    }

    public void updateCheckOut() {
        int timesheetid=Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.timesheetId).toString().trim());
        insertClockIn.setTimeSheetId(timesheetid);
        insertClockIn.setEmployeeId(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        insertClockIn.setWorkingDate(formattedDate);
        insertClockIn.setCheckOutLattitude(lat);
        insertClockIn.setCheckOutLongitude(lng);
        insertClockIn.setCheckOut(tvtotalahrs.getText().toString().trim());

    }

    public void updatebreakout() {
        int timesheetid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.timesheetId).toString().trim());
        int breakinid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.breakinid).toString().trim());
        insertBreakIn.setTimeSheetId(timesheetid);
        insertBreakIn.setBreakId(breakinid);
        insertBreakIn.setComments(edcomments.getText().toString());
        insertBreakIn.setBreakOutOutLattitude(lat);
        insertBreakIn.setBreakOutLongitude(lng);
        insertBreakIn.setBreakOut(tvcurrenttime.getText().toString().trim());
    }

    public void updateBreakin() {
        int timesheetid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.timesheetId).toString().trim());
        insertBreakIn.setTimeSheetId(timesheetid);

        insertBreakIn.setComments(edcomments.getText().toString());
        insertBreakIn.setBreakInLattitude(lat);
        insertBreakIn.setBreakInLongitude(lng);
        insertBreakIn.setBreakIn(tvbreaktime.getText().toString().trim());
    }

    public void checkIn(boolean checkin) {
        {

            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Please wait...", false, false);

            EMSService service = ServiceGenerator.createService();
            Call<InsertClockIn> insertClockInCall;
            if (checkin) {
                updateCheckin();
                insertClockInCall = service.insertClockIn(insertClockIn);
            } else {
                updateCheckOut();
                insertClockInCall = service.updateClockOut(insertClockIn);

                SharedPreferenceUtils
                        .getInstance(getActivity())
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.checkinTime,
                                "").commitSplash();
                SharedPreferenceUtils
                        .getInstance(getActivity())
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.timesheetId,
                                "").commitSplash();

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

                            SharedPreferenceUtils
                                    .getInstance(getActivity())
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.timesheetId,
                                            String.valueOf(emp.getTimeSheetId())).commitSplash();
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

    public void breakIn(boolean breakin) {
        {

            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Please wait...", false, false);

            EMSService service = ServiceGenerator.createService();
            Call<InsertBreakIn> insertClockInCall;
            if (breakin) {
                updateBreakin();
                insertClockInCall = service.InsertBreakIn(insertBreakIn);
            } else {
                updatebreakout();
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
                            SharedPreferenceUtils
                                    .getInstance(getActivity())
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.breakinid,
                                            String.valueOf(emp.getBreakId())).commitSplash();
                            edcomments.setText("");
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

    public void getbreaktimes() {
        {
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
            int empid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim());
            int timesheetid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.timesheetId).toString().trim());
            //  int timesheetid=1;
            Call<List<BreakDetails>> listCall = ServiceGenerator.createService().getBreakDetails(timesheetid, empid);

            listCall.enqueue(new Callback<List<BreakDetails>>() {
                @Override
                public void onResponse(Call<List<BreakDetails>> call, Response<List<BreakDetails>> response) {
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

                        List<BreakDetails> breakDetails = response.body();
                        Log.i(TAG, "onResponse: Fetched " + breakDetails + " PropertyTypes.");
                        setBreakDetails(breakDetails);
                    }
                }

                @Override
                public void onFailure(Call<List<BreakDetails>> call, Throwable t) {
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

    void setBreakDetails(List<BreakDetails> breakDetails) {
        BreaklistAdapter adapter = new BreaklistAdapter(getActivity(), breakDetails);
        listView_breakDetails.setAdapter(adapter);
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
                    tvbreaktime.setText(curTime);
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


                SharedPreferenceUtils
                        .getInstance(getActivity())
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.checkinTime,
                                tvcurrenttime.getText().toString().trim()).commitSplash();
                tvcheckintime.setText(tvcurrenttime.getText().toString().trim());
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

                breakIn(false);
                if (SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.timesheetId) != null && !SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.timesheetId).toString().trim().isEmpty()) {
                    getbreaktimes();
                    layout_checkin.setVisibility(View.GONE);
                    layout_checkout.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.imgbtnbreak:
                breakIn(true);
                SharedPreferenceUtils
                        .getInstance(getActivity())
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.breakinTime,
                                tvbreaktime.getText().toString().trim()).commitSplash();
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

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the task you need to do.
                 //   displayLocationSettingsRequest(getActivity(),requestCode);
                } else {

                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public static void displayLocationSettingsRequest(final Context context, final int REQUEST_CHECK_SETTINGS) {


        Log.d("Check Utility", "displayLocationSettingsRequest() called with: context = [" + context + "], REQUEST_CHECK_SETTINGS = [" + REQUEST_CHECK_SETTINGS + "]");
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.e("Check Utility", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("Check Utility", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("Check Utility", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("Check Utility", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}

