package com.csn.ems.recyclerviewadapter;

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

import java.util.List;

import static com.csn.ems.R.id.tvapprovalstatus;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class ReportTimesheetRecyclerViewAdapter extends RecyclerView.Adapter<ReportTimesheetRecyclerViewAdapter.ViewHolder> {

    private AlertDialog allocationDialog;
    TimeSheetDetails timeSheetDetails = new TimeSheetDetails();
    Context context;
    View view1;
    ReportTimesheetRecyclerViewAdapter.ViewHolder viewHolder1;
    public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;
 //   ImageView tvapprovalstatus;
    List<TimeSheetDetails> timesheetDetails;

    public ReportTimesheetRecyclerViewAdapter(Context context1, List<TimeSheetDetails> timesheetDetails) {

        this.timesheetDetails = timesheetDetails;
        this.context = context1;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, tvcheckintime, tvcheckouttime, tvtotalhrs;
        public ImageView tvapprovalstatus;
        public ImageView imageButton, imageButton2;

        public ViewHolder(View v) {

            super(v);
            tvapprovalstatus = (ImageView) v.findViewById(R.id.tvapprovalstatus);
            textView = (TextView) v.findViewById(R.id.tvdate);
            tvcheckintime = (TextView) v.findViewById(R.id.tvcheckintime);
            tvcheckouttime = (TextView) v.findViewById(R.id.tvcheckouttime);
            tvtotalhrs = (TextView) v.findViewById(R.id.tvtotalhrs);
            //      tvapprovalstatus.setVisibility(View.GONE);
            imageButton = (ImageView) v.findViewById(R.id.imageButton);
            imageButton2 = (ImageView) v.findViewById(R.id.imageButton2);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                  /*  if (SharedPreferenceUtils
                            .getInstance(context)
                            .getSplashCacheItem(
                                    EmsConstants.rolename) != null && SharedPreferenceUtils
                            .getInstance(context)
                            .getSplashCacheItem(
                                    EmsConstants.rolename).equals("Manager")) {
                        Alertview(timesheetDetails.get(pos).getTimeSheetId(), timesheetDetails.get(pos).getEmployeeId(), timesheetDetails.get(pos).getWorkingDate(), timesheetDetails.get(pos).getCheckIn(), timesheetDetails.get(pos).getCheckOut(), timesheetDetails.get(pos).getCheckInLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getCheckOutLattitude(), timesheetDetails.get(pos).getCheckOutLongitude(), timesheetDetails.get(pos).getAssignedTo(), timesheetDetails.get(pos).getApprovalType(), timesheetDetails.get(pos).getNote(), timesheetDetails.get(pos).getStatus());
                    }*/
                }
            });
        }
    }

    @Override
    public ReportTimesheetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.aaaaa, parent, false);

        viewHolder1 = new ReportTimesheetRecyclerViewAdapter.ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ReportTimesheetRecyclerViewAdapter.ViewHolder holder, final int position) {

        holder.textView.setText(timesheetDetails.get(position).getWorkingDate());

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

        if (timesheetDetails.get(position).getCheckIn() != null) {
            holder.tvcheckintime.setText(timesheetDetails.get(position).getCheckIn());

        }
        if (timesheetDetails.get(position).getCheckOut() != null) {

            holder.tvcheckouttime.setText(timesheetDetails.get(position).getCheckOut());
        }


        if (timesheetDetails.get(position).getCalculatedLength() != null) {

            holder.tvtotalhrs.setText(timesheetDetails.get(position).getCalculatedLength() + "hrs");

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
            //  final RadioGroup active_inactiveRadioButtonGroup = (RadioGroup) dialogView.findViewById(R.id.radioGroup);
            final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radioButton1:
                            String radiovalue = ((RadioButton) dialogView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                            // approvalval=radiovalue;
                            SharedPreferenceUtils
                                    .getInstance(context)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.approvalval,
                                            radiovalue).commitSplash();
                            break;

                        case R.id.radioButton2:
                            String radiovalue1 = ((RadioButton) dialogView.findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                            // approvalval=radiovalue;
                            SharedPreferenceUtils
                                    .getInstance(context)
                                    .editSplash()
                                    .addSplashCacheItem(EmsConstants.approvalval,
                                            radiovalue1).commitSplash();
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
                                            EmsConstants.approvalval).toString().trim(), Note, status);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateemployeedetails(timesheetid, EmployeeId, WorkingDate, CheckIn, CheckOut, CheckInLattitude, checkInLongitude, checkOutLattitude, checkOutLongitude, AssignedTo, SharedPreferenceUtils
                                    .getInstance(context)
                                    .getSplashCacheItem(
                                            EmsConstants.approvalval).toString().trim(), Note, status);
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
        //  uploadDetails();

    }


}
