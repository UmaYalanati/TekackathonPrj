package com.tek.ems.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tek.ems.R;
import com.tek.ems.emsconstants.EmsConstants;
import com.tek.ems.emsconstants.SharedPreferenceUtils;
import com.tek.ems.model.ScheduleTime;
import com.tek.ems.services.ServiceGenerator;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tek.ems.EMSApplication.inTakeMasterDetails;

/**
 * Created by uyalanat on 18-10-2016.
 */

public class OrgCalendarFragment extends Fragment {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
  //  formatter.applyPattern("MMM d, yyyy");
    String toDate;
    TextView tvdatee,tvendTime,tvholiday,tvweekOff,tvstartTime;
    ScheduleTime scheduleTime=new ScheduleTime();

    String TAG = "OrgCalendarFragment";
     CaldroidListener listener;

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);

        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        tvdatee = (TextView) view.findViewById(R.id.tvdatee);

        tvendTime= (TextView) view.findViewById(R.id.tvendTime);
                tvholiday= (TextView) view.findViewById(R.id.tvholiday);
                tvweekOff= (TextView) view.findViewById(R.id.tvweekOff);
                tvstartTime= (TextView) view.findViewById(R.id.tvstartTime);
        caldroidFragment = new CaldroidFragment();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

     final   SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        toDate = newDateFormat.format(c.getTime());
        tvdatee.setText(toDate);
        displayDatedetails();
        // Setup listener
        listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
              /*  Toast.makeText(getActivity(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
                toDate=newDateFormat.format(date);
                displayDatedetails();
                if (caldroidFragment!=null) {
                    caldroidFragment.refreshView();
                }
                ColorDrawable green = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                caldroidFragment = new CaldroidFragment();

                // Attach to the activity
                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                t.replace(R.id.calendar1, caldroidFragment);
                t.commit();
                caldroidFragment.setBackgroundDrawableForDate(green, date);
                caldroidFragment.refreshView();
                caldroidFragment.setCaldroidListener(listener);
            }

            @Override
            public void onChangeMonth(int month, int year) {
              /*  String text = "month: " + month + " year: " + year;
                Toast.makeText(getActivity(), text,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                /*Toast.makeText(getActivity(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                   /* Toast.makeText(getActivity(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

        };

        //  dialogCaldroidFragment = new CaldroidFragment();
        // dialogCaldroidFragment.setCaldroidListener(listener);
        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
        return view;
    }
    
  void displayDatedetails(){
        
            final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

            Call<ScheduleTime> listCall = ServiceGenerator.createService().getScheduleTime(Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim()),toDate);

            listCall.enqueue(new Callback<ScheduleTime>() {
                @Override
                public void onResponse(Call<ScheduleTime> call, Response<ScheduleTime> response) {
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
                        scheduleTime = response.body();
                        tvdatee.setText(toDate);
                        if (scheduleTime.getStartTime()!=null){
                            tvstartTime.setText("Start Time :"+scheduleTime.getStartTime());
                        }
                        if (scheduleTime.getEndTime()!=null){
                            tvendTime.setText("End Time :"+scheduleTime.getEndTime());
                        }

                        if (scheduleTime.getHoliday()!=null){
                            tvendTime.setText(scheduleTime.getHoliday());
                        }

                        if (scheduleTime.getWeekOff()!=null){
                            tvendTime.setText(scheduleTime.getWeekOff());
                        }
                        Log.i(TAG, "onResponse: Fetched " + inTakeMasterDetails + " PropertyTypes.");
                        // setScheduleTime();
                    }
                }

                @Override
                public void onFailure(Call<ScheduleTime> call, Throwable t) {
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
