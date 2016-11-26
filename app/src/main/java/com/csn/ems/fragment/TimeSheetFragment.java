package com.csn.ems.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.EMSApplication;
import com.csn.ems.R;
import com.csn.ems.callback.OnCustomEventListener;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.Login;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.recyclerviewadapter.ChildEmployeesAdapter;
import com.csn.ems.recyclerviewadapter.LeaveTypeAdapter;
import com.csn.ems.recyclerviewadapter.TimesheetRecyclerViewAdapter;
import com.csn.ems.services.ServiceGenerator;
import com.csn.ems.sharedpreference.LoginComplexPreferences;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.EMSApplication.inTakeMasterDetails;


/**
 * Created by uyalanat on 22-10-2016.
 */

public class TimeSheetFragment extends Fragment implements View.OnClickListener,OnCustomEventListener {
    public static TimeSheetFragment newInstance() {
        return new TimeSheetFragment();
    }
    //OnCustomEventListener onCustomEventListener;

String TAG="TimeSheetFragment";
    Button btnstarttime, btnendtime;
    String[] SPINNERLIST = {"Select", "Approved", "Unapproved"};
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    AppCompatSpinner spinner_listofsheet,spinner_listofemployees;
int selectedItemposition=0,selectedEmployeeposition=0;
TextView tvapprovalstatus;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeetimesheet, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        btnstarttime = (Button) view.findViewById(R.id.btnstarttime);
        btnendtime = (Button) view.findViewById(R.id.btnendtime);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        tvapprovalstatus= (TextView) view.findViewById(R.id.tvapprovalstatus);
       // tvapprovalstatus.setVisibility(View.GONE);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

     //   recyclerViewAdapter = new TimesheetRecyclerViewAdapter(context, subjects);

        recyclerView.setAdapter(recyclerViewAdapter);
        spinner_listofsheet = (AppCompatSpinner) view.findViewById(R.id.spinner_listofsheet);
        spinner_listofemployees = (AppCompatSpinner) view.findViewById(R.id.spinner_listofemployees);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

       // spinner_listofsheet.setddapter(arrayAdapter);
       // LeaveStatus
        if (inTakeMasterDetails.getLeaveStatus()!=null&& inTakeMasterDetails.getLeaveStatus().size()>0) {
            LeaveTypeAdapter adapter = new LeaveTypeAdapter(getActivity(), inTakeMasterDetails.getLeaveStatus());
            spinner_listofsheet.setAdapter(adapter);
        }
        LoginComplexPreferences loginComplexPreferences = LoginComplexPreferences.getComplexPreferences(getActivity(), "object_prefs", 0);
       final Login currentUser = loginComplexPreferences.getObject("object_value", Login.class);
         ;
        if (currentUser.getChildEmployees()!=null&& currentUser.getChildEmployees().size()>0) {
            ChildEmployeesAdapter childEmployeesAdapter = new ChildEmployeesAdapter(getActivity(), currentUser.getChildEmployees());
            spinner_listofemployees.setAdapter(childEmployeesAdapter);
        }

        /////Uma
        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

      //  SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String toDate = newDateFormat.format(c.getTime());

        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        String fromDate = newDateFormat.format(calendar.getTime());

        btnendtime.setText(toDate);
        btnstarttime.setText(fromDate);

      //  spinner_listofsheet.setoni
        spinner_listofemployees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // String selectedItem = parent.getItemAtPosition(position).toString();
                selectedEmployeeposition=position;
                getlistofleaves(currentUser.getChildEmployees().get(position).getEmployeeId(),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(), inTakeMasterDetails.getLeaveStatus().get(position).getName());

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinner_listofsheet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               // String selectedItem = parent.getItemAtPosition(position).toString();
                selectedItemposition=position;
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(), inTakeMasterDetails.getLeaveStatus().get(position).getName());

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(), inTakeMasterDetails.getLeaveStatus().get(selectedItemposition).getName());



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment2 starttimeFragment = new DatePickerFragment2(btnstarttime,btnstarttime.getText().toString(),false);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");


                 /*   getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),EMSApplication.inTakeMasterDetails.getLeaveStatus().get(selectedItemposition).getName());*/


                break;
            case R.id.btnendtime:
                DatePickerFragment2 endtimeFragment = new DatePickerFragment2(btnendtime,btnstarttime.getText().toString(),false);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
               // Date date2 = sdf.parse(getTodaysDate());
               // Date date1 = sdf.parse(SharedPreferenceUtils.getInstance(context).getSettingsCacheItem(WatscoConstants.RATEUS_DATE).toString());



                break;

        }
    }

    void getlistofleaves(int employeeId,String startdate,String enddaate,String status) {

        int empid=employeeId;

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
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<List<TimeSheetDetails>> listCall = ServiceGenerator.createService().getTimeSheetDetails(empid, startdate, enddaate, status);


        listCall.enqueue(new Callback<List<TimeSheetDetails>>() {
            @Override
            public void onResponse(Call<List<TimeSheetDetails>> call, Response<List<TimeSheetDetails>> response) {
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
                    List<TimeSheetDetails> timesheetDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + timesheetDetails.size() + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
                   updateRecyclerViewForClients(timesheetDetails);
                }
            }

            @Override
            public void onFailure(Call<List<TimeSheetDetails>> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                //    Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }

    private void updateRecyclerViewForClients(List<TimeSheetDetails> timesheetDetails) {

        TimesheetRecyclerViewAdapter adapter = new TimesheetRecyclerViewAdapter(context, timesheetDetails);

        recyclerView.setAdapter(adapter);
        //  }

    }

    @Override
    public void onEvent(String buttenview) {
        if (buttenview!=null) {
            getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim()), btnstarttime.getText().toString().trim(), btnendtime.getText().toString().trim(), EMSApplication.inTakeMasterDetails.getLeaveStatus().get(selectedItemposition).getName());
        }
        }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment2 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        String date;
        Button mTextView;
        DatePickerDialog mDatePickerDialog;
        boolean isdateset;
        public DatePickerFragment2() {
        }

        public DatePickerFragment2(Button textview,String date,boolean isdateset) {
            this.mTextView = textview;
            this.date= date;
            this.isdateset=isdateset;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog fff;
            Date d=null;
            int year1=year,month1=month,day1=day;
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                d = sdf.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                year1 = cal.get(Calendar.YEAR);
                month1 = cal.get(Calendar.MONTH);
                day1 = cal.get(Calendar.DAY_OF_MONTH);


            }catch (ParseException e){

            }
            fff = new DatePickerDialog(getActivity(), this, year, month,
                    day);
            if (isdateset){
                fff.getDatePicker().setMinDate(d.getTime());
            }

            // Create a new instance of DatePickerDialog and return it
            return fff;
        }

        @SuppressLint("NewApi")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            view.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

            String str_year = String.valueOf(year);
            String date_str = "";
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date d = sdf.parse(date);
                view.setMinDate(d.getTime());
            }catch (ParseException e){

            }


            if (new StringBuilder().append(month + 1).length() >= 2 && new StringBuilder().append(day).length() < 2) {
//mm dd yy
                if (new StringBuilder().append(day).length() >= 2) {
                    mTextView.setText(new StringBuilder()
                            .append(month + 1).append("/").append(day).append("/").append(str_year).toString());

                } else {

                    mTextView.setText(new StringBuilder()

                            .append(month + 1).append("/").append(0).append(day).append("/").append(str_year)
                            .toString());
                }
            } else {
                if (new StringBuilder().append(day).length() >= 2) {
                    if (new StringBuilder().append(month + 1).length() >= 2){
                        mTextView.setText(new StringBuilder()
                                .append(month + 1).append("/")
                                .append(day).append("/")
                                .append(str_year)
                                .toString());
                    }else{
                        mTextView.setText(new StringBuilder()
                                .append(0).append(month + 1).append("/")
                                .append(day).append("/")
                                .append(str_year)
                                .toString());
                    }


                } else {


                    mTextView.setText(new StringBuilder()
                            .append(0).append(month + 1).append("/")
                            .append(0).append(day).append("/")
                            .append(str_year).toString());
                }


            }
            getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(), inTakeMasterDetails.getLeaveStatus().get(selectedItemposition).getName());

        }
    }
  /*  onCustomEventListener.setCustomEventListener(new OnCustomEventListener(){
        public void onEvent(){
            //do whatever you want to do when the event is performed.

        }
    });*/

}
