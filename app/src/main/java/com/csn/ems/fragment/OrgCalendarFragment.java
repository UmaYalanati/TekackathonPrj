package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.ScheduleTime;
import com.csn.ems.services.ServiceGenerator;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 18-10-2016.
 */

public class OrgCalendarFragment extends Fragment {
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    String toDate;
    TextView tvdatee;
String TAG="OrgCalendarFragment";
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
        tvdatee=(TextView)view.findViewById(R.id.tvdatee);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        caldroidFragment = new CaldroidFragment();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        toDate = newDateFormat.format(c.getTime());
        tvdatee.setText(toDate);
        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getActivity(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getActivity(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getActivity(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getActivity(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        dialogCaldroidFragment = new CaldroidFragment();
        dialogCaldroidFragment.setCaldroidListener(listener);
        return view;
    }
    
/*    void displayDatedetails(){
        
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
                        inTakeMasterDetails = response.body();
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
        
    }*/
}
