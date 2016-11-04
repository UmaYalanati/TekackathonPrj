package com.csn.ems.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by uyalanat on 23-10-2016.
 */
@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String date;
    Button mTextView;
    DatePickerDialog mDatePickerDialog;
boolean isdateset;
    public DatePickerFragment() {
    }

    public DatePickerFragment(Button textview,String date,boolean isdateset) {
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
    }
}

