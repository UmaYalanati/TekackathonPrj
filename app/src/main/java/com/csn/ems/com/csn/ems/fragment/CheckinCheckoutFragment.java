package com.csn.ems.com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csn.ems.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by uyalanat on 22-10-2016.
 */

public class CheckinCheckoutFragment extends Fragment implements View.OnClickListener{
    TextView tvcurrentdate, tvcurrenttime;
    Button btncheckin,btncheckout,btncontinueshift;
    ImageButton imgbtnbreak;
    LinearLayout layout_checkout,layout_checkin,ll_breaktime,ll_startbreak;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.checkinfragment, container, false);
        tvcurrentdate = (TextView) view.findViewById(R.id.tvcurrentdate);
        tvcurrenttime = (TextView) view.findViewById(R.id.tvcurrenttime);
        btncheckin=(Button) view.findViewById(R.id.btncheckin);
        btncheckout=(Button) view.findViewById(R.id.btncheckout);
        btncontinueshift=(Button)view.findViewById(R.id.btncontinueshift);
        imgbtnbreak=(ImageButton)view.findViewById(R.id.imgbtnbreak);
        layout_checkout=(LinearLayout)view.findViewById(R.id.layout_checkout);
        layout_checkin=(LinearLayout)view.findViewById(R.id.layout_checkin);
        ll_breaktime=(LinearLayout)view.findViewById(R.id.ll_breaktime);
        ll_startbreak=(LinearLayout)view.findViewById(R.id.ll_startbreak);
        btncheckin.setOnClickListener(this);
        btncheckout.setOnClickListener(this);
        imgbtnbreak.setOnClickListener(this);
        btncontinueshift.setOnClickListener(this);

        try {
            String str_MyDate;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => "+c.getTime());

            SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = newDateFormat.format(c.getTime());


            Date MyDate = newDateFormat.parse(formattedDate);
            newDateFormat.applyPattern("MMM d, yyyy");
            str_MyDate = newDateFormat.format(MyDate);
            tvcurrentdate.setText(str_MyDate);
        }catch (ParseException e){

        }

        Thread myThread = null;

        Runnable runnable = new CheckinCheckoutFragment.CountDownRunner();
        myThread = new Thread(runnable);
        myThread.start();
        return view;
    }

    public void doWork() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String am_pm=null;

                    Calendar cal = Calendar.getInstance();

                    int millisecond = cal.get(Calendar.MILLISECOND);
                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    //12 hour format
                    int hour = cal.get(Calendar.HOUR);

                    Calendar now = Calendar.getInstance();
                    int a = now.get(Calendar.AM_PM);
                    if(a == Calendar.AM)
                        am_pm=   "AM";
                    else
                        am_pm=   "PM";
                    String curTime = String.valueOf(hour) + ":" + String.valueOf(minute) + " " + am_pm;

                    tvcurrenttime.setText(curTime);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncheckin:
                layout_checkin.setVisibility(View.GONE);
                layout_checkout.setVisibility(View.VISIBLE);
                break;
            case R.id.btncheckout:
                layout_checkin.setVisibility(View.VISIBLE);
                layout_checkout.setVisibility(View.GONE);
                break;
            case R.id.btncontinueshift:
                ll_startbreak.setVisibility(View.VISIBLE);
                ll_breaktime.setVisibility(View.GONE);
                break;
            case R.id.imgbtnbreak:
                ll_startbreak.setVisibility(View.GONE);
                ll_breaktime.setVisibility(View.VISIBLE);
                break;
        }
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }


}

