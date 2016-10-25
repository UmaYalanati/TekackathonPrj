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

public class TimeClockFragment extends Fragment {
    Fragment newFragment = null;
    MenuItem pinneditem, pinneditem1, pinneditem2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);

        if (savedInstanceState == null) {
            try {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, new CheckinCheckoutFragment());
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
        // getActivity().invalidateOptionsMenu();
        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_checkin:
                // item.getTitle().equals()
                changeIcon(item, R.drawable.ic_time_clock_h);
                pinneditem = item;
                newFragment = new CheckinCheckoutFragment();

                if (pinneditem2 != null)
                    pinneditem2.setIcon(R.drawable.ic_timesheetapproval);

                if (pinneditem1 != null)
                    pinneditem1.setIcon(R.drawable.ic_time_sheet);


                break;
            case R.id.action_timesheet:
                changeIcon(item, R.drawable.ic_time_sheet_h);

                newFragment = new TimeSheetFragment();
                //   getActivity().invalidateOptionsMenu();
                pinneditem1 = item;

                if (pinneditem != null)
                    pinneditem.setIcon(R.drawable.ic_time_clock);

                if (pinneditem2 != null)
                    pinneditem2.setIcon(R.drawable.ic_timesheetapproval);

                break;
            case R.id.action_approvedtimesheet:
                changeIcon(item, R.drawable.ic_timesheetapproval_h);

                newFragment = new ApprovedTimesheet();
                pinneditem2 = item;


                if (pinneditem != null)
                    pinneditem.setIcon(R.drawable.ic_time_clock);

                if (pinneditem1 != null)
                    pinneditem1.setIcon(R.drawable.ic_time_sheet);


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
        setRetainInstance(true);
    }

    public void changeIcon(final MenuItem item, final int drawableResourceId) {
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
