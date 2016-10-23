package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.csn.ems.R;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class ReportsFragment  extends Fragment {
    Fragment newFragment=null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);
        if (savedInstanceState == null) {
            try {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, new ReportsTimesheet());
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_reportsheeet:

                changeIcon(item,R.drawable.reportsheet);

                newFragment =  ReportsTimesheet.newInstance();



                break;
            case R.id.action_reportsheeetsecond:
                changeIcon(item,R.drawable.paysheet);

                newFragment =  ReportsTimesheetSecond.newInstance();


                break;



        }

        try {





            // FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
       // return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void changeIcon(final MenuItem item,final int drawableResourceId){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                if (item != null) {
                    item.setIcon(drawableResourceId);
                }
            }
        });
    }
}

