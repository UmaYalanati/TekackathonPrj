package com.csn.ems.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    Button mTextView;
    DatePickerDialog mDatePickerDialog;

    public DatePickerFragment() {
    }

    public DatePickerFragment(Button textview) {
        mTextView = textview;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog fff;

        fff = new DatePickerDialog(getActivity(), this, year, month,
                day);
        // Create a new instance of DatePickerDialog and return it
        return fff;
    }

    @SuppressLint("NewApi")
    public void onDateSet(DatePicker view, int year, int month, int day) {
        view.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        String str_year = String.valueOf(year);
        String date_str = "";


        if (new StringBuilder().append(month + 1).length() >= 2 && new StringBuilder().append(day).length() < 2) {
//mm dd yy
            if (new StringBuilder().append(day).length() >= 2) {
                mTextView.setText(new StringBuilder()
                        .append(month + 1).append("-").append(day).append("-").append(str_year).toString());

            } else {


                mTextView.setText(new StringBuilder()
                        .append(0)
                        .append(month + 1).append("-").append(day).append("-").append(str_year)
                        .toString());
            }
        } else {
            if (new StringBuilder().append(day).length() >= 2) {
                if (new StringBuilder().append(month + 1).length() >= 2){
                    mTextView.setText(new StringBuilder()
                            .append(month + 1).append("-")
                            .append(day).append("-")
                            .append(str_year)
                            .toString());
                }else{
                    mTextView.setText(new StringBuilder()
                            .append(0).append(month + 1).append("-")
                            .append(day).append("-")
                            .append(str_year)
                            .toString());
                }


            } else {


                mTextView.setText(new StringBuilder()
                        .append(0).append(month + 1).append("-")
                        .append(0).append(day).append("-")
                        .append(str_year).toString());
            }


        }
    }
}

