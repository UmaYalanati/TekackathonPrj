package com.csn.ems.recyclerviewadapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.TimeSheetDetails;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 24-10-2016.
 */

public class ApprovedTimesheetRecyclerViewAdapter extends RecyclerView.Adapter<ApprovedTimesheetRecyclerViewAdapter.ViewHolder> {

    private AlertDialog allocationDialog;
    TimeSheetDetails timeSheetDetails = new TimeSheetDetails();
    Context context;
    View view1;
    ApprovedTimesheetRecyclerViewAdapter.ViewHolder viewHolder1;
    public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;
    ImageView tvapprovalstatus;
    List<TimeSheetDetails> timesheetDetails;

    public ApprovedTimesheetRecyclerViewAdapter(Context context1, List<TimeSheetDetails> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        this.context = context1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;
        public ImageView tvapprovalstatus;

        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (ImageView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvcheckintime = (TextView) v.findViewById(R.id.tvcheckintime);
            tvcheckouttime = (TextView) v.findViewById(R.id.tvcheckouttime);
            tvtotalhrs = (TextView) v.findViewById(R.id.tvtotalhrs);
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
                        Alertview(timesheetDetails.get(pos).getTimeSheetId(), timesheetDetails.get(pos).getEmployeeId(), timesheetDetails.get(pos).getWorkingDate(), timesheetDetails.get(pos).getCheckIn(), timesheetDetails.get(pos).getCheckOut(), timesheetDetails.get(pos).getCheckInLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getCheckOutLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getAssignedTo(), timesheetDetails.get(pos).getApprovalType(), timesheetDetails.get(pos).getNote(), timesheetDetails.get(pos).getStatus());
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
    public void onBindViewHolder(ApprovedTimesheetRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.textView.setText(timesheetDetails.get(position).getWorkingDate());
/*if (timesheetDetails.get(position).getStatus()!=null) {
    if (timesheetDetails.get(position).getStatus().equals("Approved"))
        tvapprovalstatus.setBackgroundResource(R.drawable.bluedot);
    else {
        tvapprovalstatus.setBackgroundResource(R.drawable.reddot);
    }
}*/


        String intime = "";

        String ottime = "";

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt = null;
        Date dt_out = null;
        try {
            if (timesheetDetails.get(position).getCheckIn() != null) {
                dt = sdf.parse(timesheetDetails.get(position).getCheckIn());
                System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
                intime = sdfs.format(dt);
                holder.tvcheckintime.setText(intime);

            }
            if (timesheetDetails.get(position).getCheckOut() != null) {

                dt_out = sdf.parse(timesheetDetails.get(position).getCheckOut());
                ottime = sdfs.format(dt_out);
                holder.tvcheckouttime.setText(ottime);
            }


            if (timesheetDetails.get(position).getCheckIn() != null && timesheetDetails.get(position).getCheckOut() != null) {
                //   long secs = (dt.getTime() - dt.getTime()) / 1000;
                //   int hours = secs / 3600;
                final int MILLI_TO_HOUR = 1000 * 60 * 60;
                int hours = (int) (dt.getTime() - dt_out.getTime()) / MILLI_TO_HOUR;
                //  Period p = new Period(dt, dt_out);

                long diff = dt.getTime() - dt_out.getTime();
                long diffHours = diff / (60 * 60 * 1000) % 24;
                holder.tvtotalhrs.setText(String.valueOf(diffHours) + "hrs");

                // int hours = p.getHours();
            }


        } catch (ParseException e) {

            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return timesheetDetails.size();
    }

    public void Alertview(final int timesheetid, final int EmployeeId, final String WorkingDate, final String CheckIn, final String CheckOut, final double CheckInLattitude, final double checkInLongitude, final double checkOutLattitude, final double checkOutLongitude, final String AssignedTo, final String ApprovalType, final String Note, final String status) {
        {


            //  View dialogView = context.inflater.inflate(R.layout.custom_approveleave, null);

            View dialogView = LayoutInflater.from(context).inflate(
                    R.layout.custom_approveleave, null);

            final TextInputEditText allocationDateTextField = (TextInputEditText) dialogView.findViewById(R.id.ed_comments);
            //  final RadioGroup active_inactiveRadioButtonGroup = (RadioGroup) dialogView.findViewById(R.id.radioGroup);


            allocationDialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle("Time Sheet Approval ")
                    .setCancelable(false)
                    .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, status);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, status);
                            //Log.i(TAG, "onClick: Clearing " + operation + " Allocation Details");
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
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, status);
                        }
                    });

                    rejectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, ApprovalType, Note, status);
                            allocationDialog.dismiss();
                        }
                    });
                }
            });
            allocationDialog.show();


        }
    }

    public void updateemployeedetails(int timesheetid, int EmployeeId, String WorkingDate, String CheckIn, String CheckOut, double CheckInLattitude, double checkInLongitude, double checkOutLattitude, double checkOutLongitude, String AssignedTo, String ApprovalType, String Note, String status) {
        timeSheetDetails.setTimeSheetId(timesheetid);
        timeSheetDetails.setEmployeeId(EmployeeId);
        timeSheetDetails.setWorkingDate(WorkingDate);
        timeSheetDetails.setCheckIn(CheckIn);
        timeSheetDetails.setCheckOut(CheckOut);
        timeSheetDetails.setCheckInLattitude(CheckInLattitude);
        timeSheetDetails.setCheckInLongitude(checkInLongitude);
        timeSheetDetails.setCheckOutLattitude(checkOutLattitude);
        timeSheetDetails.setCheckOutLongitude(checkOutLongitude);
        timeSheetDetails.setAssignedTo(AssignedTo);
        timeSheetDetails.setApprovalType(ApprovalType);
        timeSheetDetails.setNote(Note);
        timeSheetDetails.setStatus(status);
        uploadDetails();

    }

    void uploadDetails() {
        //updateemployeedetails();
        final ProgressDialog loading = ProgressDialog.show(context, "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<TimeSheetDetails> timeSheetDetailsCall = service.updateTimeCardApproval(timeSheetDetails);
        timeSheetDetailsCall.enqueue(new Callback<TimeSheetDetails>() {
            @Override
            public void onResponse(Call<TimeSheetDetails> call, Response<TimeSheetDetails> response) {
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

                    TimeSheetDetails emp = response.body();
                    if (emp != null) {
                        // Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        new AlertDialog.Builder(context)
                                .setTitle("Approved Successfully!")
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
            public void onFailure(Call<TimeSheetDetails> call, Throwable t) {
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
