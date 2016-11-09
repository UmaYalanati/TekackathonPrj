package com.csn.ems.recyclerviewadapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.GoogleMaps;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.model.TimeSheetDetailsApprove;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.tvapprovalstatus;

/**
 * Created by uyalanat on 24-10-2016.
 */

public class ApprovedTimesheetRecyclerViewAdapter extends RecyclerView.Adapter<ApprovedTimesheetRecyclerViewAdapter.ViewHolder> {

    private AlertDialog allocationDialog;
    TimeSheetDetailsApprove timeSheetDetails1 = new TimeSheetDetailsApprove();
    Context context;
    View view1;
    String radiovalue;
    String approvalType = "";

    ApprovedTimesheetRecyclerViewAdapter.ViewHolder viewHolder1;
    public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;

    List<TimeSheetDetails> timesheetDetails;
String note="";
    public ApprovedTimesheetRecyclerViewAdapter(Context context1, List<TimeSheetDetails> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        this.context = context1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;

        ImageView imageButton, imageButton2;
        ImageView tvapprovalstatus;
        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (ImageView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvcheckintime = (TextView) v.findViewById(R.id.tvcheckintime);
            tvcheckouttime = (TextView) v.findViewById(R.id.tvcheckouttime);
            tvtotalhrs = (TextView) v.findViewById(R.id.tvtotalhrs);
            imageButton = (ImageView) v.findViewById(R.id.imageButton);
            imageButton2 = (ImageView) v.findViewById(R.id.imageButton2);
            //      tvapprovalstatus.setVisibility(View.GONE);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (SharedPreferenceUtils
                            .getInstance(context)
                            .getSplashCacheItem(
                                    EmsConstants.rolename) != null && SharedPreferenceUtils
                            .getInstance(context)
                            .getSplashCacheItem(
                                    EmsConstants.rolename).equals("Manager")) {
                        Alertview(timesheetDetails.get(pos).getTimeSheetId(), Integer.parseInt(SharedPreferenceUtils
                                .getInstance(context)
                                .getSplashCacheItem(
                                        EmsConstants.childEmployeeId).toString()), timesheetDetails.get(pos).getWorkingDate(), timesheetDetails.get(pos).getCheckIn(), timesheetDetails.get(pos).getCheckOut(), timesheetDetails.get(pos).getCheckInLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getCheckOutLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getAssignedTo(), timesheetDetails.get(pos).getApprovalType(), timesheetDetails.get(pos).getNote(), timesheetDetails.get(pos).getStatus());
                    }
                }
            });
        }
    }

    @Override
    public ApprovedTimesheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.child_timesheet, parent, false);

        viewHolder1 = new ApprovedTimesheetRecyclerViewAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ApprovedTimesheetRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.textView.setText(timesheetDetails.get(position).getWorkingDate());
        if (timesheetDetails.get(position).getStatus() != null) {
            if (timesheetDetails.get(position).getStatus().equals("Approved")) {
                // tvapprovalstatus.setBackgroundResource(R.drawable.approvedicon);
                //setting image resource
                holder.tvapprovalstatus.setImageResource(R.drawable.approvedicon);
            } else if (timesheetDetails.get(position).getStatus().equals("Pending")) {
                //  tvapprovalstatus.setBackgroundResource(R.drawable.bluedot);
                holder.tvapprovalstatus.setImageResource(R.drawable.bluedot);
            } else {
                // tvapprovalstatus.setBackgroundResource(R.drawable.reddot);
                holder.tvapprovalstatus.setImageResource(R.drawable.reddot);
            }
        }
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(timesheetDetails.get(position).getCheckInLattitude()).equals("0.0")) {
                    Toast.makeText(context, "Location not Available", Toast.LENGTH_SHORT).show();
                } else {
                    EmsConstants.latitude = timesheetDetails.get(position).getCheckInLattitude();
                    EmsConstants.longitude = timesheetDetails.get(position).getCheckInLongitude();
                    Intent i = new Intent(context, GoogleMaps.class);
                    context.startActivity(i);
                }
            }
        });
        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(timesheetDetails.get(position).getCheckInLattitude()).equals("0.0")) {
                    Toast.makeText(context, "Location not Available", Toast.LENGTH_SHORT).show();
                } else {
                    EmsConstants.latitude = timesheetDetails.get(position).getCheckOutLattitude();
                    EmsConstants.longitude = timesheetDetails.get(position).getCheckOutLongitude();
                    Intent i = new Intent(context, GoogleMaps.class);
                    context.startActivity(i);
                }
            }
        });


        if (timesheetDetails.get(position).getCheckIn() != null) {

            holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckIn());

        }
        if (timesheetDetails.get(position).getCheckOut() != null) {

              /*  dt_out = sdf.parse(timesheetDetails.get(position).getCheckOut());
                ottime = sdfs.format(dt_out);*/
            holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckOut());
        }


        if (timesheetDetails.get(position).getCalculatedLength() != null) {

            holder.tvtotalhrs.setText(timesheetDetails.get(position).getCalculatedLength() + "hrs");

            // int hours = p.getHours();
        }


    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }

    public void Alertview(final int timesheetid, final int EmployeeId, final String WorkingDate, final String CheckIn, final String CheckOut, final double CheckInLattitude, final double checkInLongitude, final double checkOutLattitude, final double checkOutLongitude, final String AssignedTo, final String ApprovalType, final String Note, final String status) {
        {

            String approvalval = "FullDay";
            //  View dialogView = context.inflater.inflate(R.layout.custom_approveleave, null);
            SharedPreferenceUtils
                    .getInstance(context)
                    .editSplash()
                    .addSplashCacheItem(EmsConstants.approvalval,
                            approvalval).commitSplash();
            final View dialogView = LayoutInflater.from(context).inflate(
                    R.layout.custom_approveleave, null);

            final TextInputEditText allocationDateTextField = (TextInputEditText) dialogView.findViewById(R.id.ed_comments);
            note = allocationDateTextField.getText().toString().trim();
            final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);
            note=allocationDateTextField.getText().toString().trim();
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radioButton1:
                            radiovalue = ((RadioButton) dialogView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                            // approvalval=radiovalue;
                            SharedPreferenceUtils
                                    .getInstance(context)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.approvalval,
                                            radiovalue).commitSplash();
                            break;

                        case R.id.radioButton2:
                            radiovalue = ((RadioButton) dialogView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                            // approvalval=radiovalue;
                            SharedPreferenceUtils
                                    .getInstance(context)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.approvalval,
                                            radiovalue).commitSplash();
                            break;


                    }


                }
            });
            allocationDialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle("Time Sheet Approval ")
                    .setCancelable(false)
                    .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, SharedPreferenceUtils
                                    .getInstance(context)
                                    .getSplashCacheItem(
                                            EmsConstants.approvalval).toString().trim(), Note, radiovalue);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        /*    updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, SharedPreferenceUtils
                                    .getInstance(context)
                                    .getSplashCacheItem(
                                            EmsConstants.approvalval).toString().trim(), Note, radiovalue);*/
                            //Log.i(TAG, "onClick: Clearing " + operation + " Allocation Details");
                   if (allocationDialog!=null){
                       allocationDialog.dismiss();
                   }
                        }
                    })
                    .setNegativeButton("Reject", null)
                    .create();

            allocationDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button allocateButton = allocationDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    Button clearButton = allocationDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                    Button rejectButton = allocationDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                    allocateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isFocusRequested = false;
                            String allocatedDate = allocationDateTextField.getText().toString();
                            /// updateemployeedetails();
                            allocationDialog.dismiss();
                            approvalType = "Approved";
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, radiovalue);
                        }
                    });

                    rejectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            approvalType = "Rejected";
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, radiovalue);
                            allocationDialog.dismiss();
                        }
                    });
                }
            });
            allocationDialog.show();


        }
    }

    public void updateemployeedetails(int timesheetid, int EmployeeId, String WorkingDate, String CheckIn, String CheckOut, double CheckInLattitude, double checkInLongitude, double checkOutLattitude, double checkOutLongitude, String AssignedTo, String ApprovalType, String Note, String status) {
        timeSheetDetails1.setTimeSheetId(timesheetid);
        timeSheetDetails1.setEmployeeId(EmployeeId);
        timeSheetDetails1.setWorkingDate(WorkingDate);
        timeSheetDetails1.setCheckIn(CheckIn);
        timeSheetDetails1.setCheckOut(CheckOut);
        timeSheetDetails1.setCheckInLattitude(CheckInLattitude);
        timeSheetDetails1.setCheckInLongitude(checkInLongitude);
        timeSheetDetails1.setCheckOutLattitude(checkOutLattitude);
        timeSheetDetails1.setCheckOutLongitude(checkOutLongitude);
        timeSheetDetails1.setAssignedTo(Integer.parseInt(SharedPreferenceUtils
                .getInstance(context)
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim()));
        if (approvalType.equals("Rejected")){
            timeSheetDetails1.setApprovalType("LOP");
        }else {
            timeSheetDetails1.setApprovalType(radiovalue);
        }

        timeSheetDetails1.setNote(note);
        timeSheetDetails1.setStatus(approvalType);
        uploadDetails();

    }

    void uploadDetails() {
        //updateemployeedetails();
        final ProgressDialog loading = ProgressDialog.show(context, "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<TimeSheetDetailsApprove> timeSheetDetailsCall = service.updateTimeCardApproval(timeSheetDetails1);
        timeSheetDetailsCall.enqueue(new Callback<TimeSheetDetailsApprove>() {
            @Override
            public void onResponse(Call<TimeSheetDetailsApprove> call, Response<TimeSheetDetailsApprove> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        // Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE
                    notifyDataSetChanged();
                    TimeSheetDetailsApprove emp = response.body();
                    if (emp != null) {
                        // Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        new AlertDialog.Builder(context)
                                .setTitle("Successfully Done!")
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
                        new AlertDialog.Builder(context)
                                .setTitle("TimeSheetDetails Creation Failed!")
                                .setMessage("We are unable to save your TimeSheetDetailsin our database this time.\n\n" +
                                        "Please try validating your parameters once or Try again later.")
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TimeSheetDetailsApprove> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(context, "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    // Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
                } else {
                }
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();

    }
}
