package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.csn.ems.MainActivity;
import com.csn.ems.R;
import com.csn.ems.callback.MenuItemSelectedCallback;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class TimeClockFragment extends Fragment {
    Fragment newFragment = null;
    int selectedItem;
String TAG="TimeClockFragment";
    private MenuItemSelectedCallback menuItemSelectedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);
        if (getContext() instanceof MenuItemSelectedCallback) {
            Log.d(TAG, "onCreateView: Instance matched");
            menuItemSelectedCallback = (MenuItemSelectedCallback) getContext();
        } else {
            Log.d(TAG, "onCreateView: Not instance");
        }

        if (savedInstanceState == null) {
            try {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if (SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename) != null && !SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename).equals("Manager")) {
                    transaction.replace(R.id.fragment_container, new CheckinCheckoutFragment());
                    selectedItem = R.id.action_checkin;
                }else {
                    transaction.replace(R.id.fragment_container, new TimeSheetFragment());
                    selectedItem = R.id.action_timesheet;
                }

//                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();



                if (menuItemSelectedCallback != null) {
                    menuItemSelectedCallback.selectedItem(null, selectedItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuItemSelectedCallback.selectedItem(item, item.getItemId());
        selectedItem = item.getItemId();

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_checkin:
                // item.getTitle().equals()

                newFragment = new CheckinCheckoutFragment();




                break;
            case R.id.action_timesheet:


                newFragment = new TimeSheetFragment();


                break;
            case R.id.action_approvedtimesheet:


                newFragment = new ApprovedTimesheetFragment();


                break;


        }

        try {


            // FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);

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


}
