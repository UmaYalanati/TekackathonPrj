package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.LoginActivity;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.services.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.btnendtime;
import static com.csn.ems.R.id.btnstarttime;
import static com.csn.ems.R.id.spinner_listofsheet;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class DashBoardFragment extends Fragment implements View.OnClickListener{
String TAG="DashBoardFragment";
   CardView card_view;
    ImageView imgprofilepic;
    TextView tvemployeename,tvcompanyname,tvcheckintime,tvschedule,tvnorofdays,tvmonth,tvday;
FrameLayout fragment_container;
    InTakeMasterDetails inTakeMasterDetails=new InTakeMasterDetails();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dashboardscreen, container, false);
        card_view=(CardView) view.findViewById(R.id.card_view);
                imgprofilepic=(ImageView) view.findViewById(R.id.imgprofilepic);
                tvemployeename=(TextView)view.findViewById(R.id.tvemployeename);
                tvcompanyname=(TextView)view.findViewById(R.id.tvcompanyname);
                tvcheckintime=(TextView)view.findViewById(R.id.tvcheckintime);
                tvschedule=(TextView)view.findViewById(R.id.tvschedule);
                tvnorofdays=(TextView)view.findViewById(R.id.tvnorofdays);
                tvmonth=(TextView)view.findViewById(R.id.tvmonth);
                tvday=(TextView)view.findViewById(R.id.tvday);
        fragment_container=(FrameLayout) view.findViewById(R.id.fragment_container);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        SimpleDateFormat day_date = new SimpleDateFormat("dd");
        String month_name = month_date.format(cal.getTime());
        String day_name = day_date.format(cal.getTime());
        tvmonth.setText(month_name);
        tvday.setText(day_name);
        card_view.setOnClickListener(this);
if (SharedPreferenceUtils
        .getInstance(getActivity())
        .getSplashCacheItem(
                EmsConstants.employeename)!=null)
        tvemployeename.setText("Hi "+SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeename).toString().trim());

        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.checkintime)!=null)
            tvschedule.setText(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.checkintime).toString().trim());

        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.photoPath)!=null) {
            //  Bitmap bmp=null;
            //  image_employee = (ImageView)navigationView. findViewById(R.id.imageView_employee);
            ///   image_employee.setImageBitmap(getBitmapFromURL(employeeDetails.getPhotoPath()));
            Picasso.with(getActivity())
                   .load("http://"+SharedPreferenceUtils
                           .getInstance(getActivity())
                           .getSplashCacheItem(
                                   EmsConstants.photoPath).toString().trim()).into(imgprofilepic);
            //   (ImageView) navigationView.findViewById(R.id.imageView_employee).s(getBitmapFromURL("http://"+employeeDetails.getPhotoPath()));
        }else{
            imgprofilepic.setBackgroundResource(R.drawable.ic_dashboard_profile_pic);
        }



        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_signout:

                Intent intent_homescreen = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_homescreen);
                getActivity().finish();

                break;
        }


        return true;
        //  return super.onOptionsItemSelected(item);
    }
    void displaydetails(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<InTakeMasterDetails> listCall = ServiceGenerator.createService().getInTakeMasterDetails(inTakeMasterDetails);

        listCall.enqueue(new Callback<InTakeMasterDetails>() {
            @Override
            public void onResponse(Call<InTakeMasterDetails> call, Response<InTakeMasterDetails> response) {
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
                   // setInTakeMasterDetails();
                }
            }

            @Override
            public void onFailure(Call<InTakeMasterDetails> call, Throwable t) {
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

    @Override
    public void onClick(View v) {
        Fragment newFragment = null;
        switch (v.getId()) {
            case R.id.card_view:
              //  Toast.makeText(getActivity(),"uma",1000).show();
                try {
                    fragment_container.setVisibility(View.VISIBLE);
                    newFragment=  EmployeeDetailsFragment.newInstance();;
                    // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                    transaction.replace(R.id.fragment_container, newFragment);

// Commit the transaction
                    transaction.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
           /* case btnendtime:
                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                if (spinner_listofsheet.getSelectedItem().toString().equals("Select")){
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),"ALL");
                }else{
                    getlistofleaves(Integer.parseInt(SharedPreferenceUtils
                            .getInstance(getActivity())
                            .getSplashCacheItem(
                                    EmsConstants.employeeId).toString().trim()),btnstarttime.getText().toString().trim(),btnendtime.getText().toString().trim(),spinner_listofsheet.getSelectedItem().toString());
                }

                break;*/

        }
    }
}
