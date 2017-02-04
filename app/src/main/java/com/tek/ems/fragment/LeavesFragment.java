package com.tek.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tek.ems.R;
import com.tek.ems.callback.MenuItemSelectedCallback;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class LeavesFragment extends Fragment {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String TAG = "LeavesFragment";
    Fragment newFragment = null;
    int selectedItem;

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

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, new UpcomingTimeOffFragment());
//                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

                selectedItem = R.id.action_upcomingtimeoff;

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
        // toggle nav drawer on selecting action bar app icon/title
        menuItemSelectedCallback.selectedItem(item, item.getItemId());
        selectedItem = item.getItemId();
        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_leaaveinformation:

             /*   changeIcon(item, R.drawable.ic_leave_information_h);
                if (pinMenuItem != null)
                    pinMenuItem.setIcon(R.drawable.ic_leave);


                pinMenuItem = item;*/

                newFragment = ListofLeavesFragment.newInstance();


                break;
            case R.id.action_upcomingtimeoff:
              /*  changeIcon(item, R.drawable.ic_leave_h);
                if (pinMenuItem != null)
                    pinMenuItem.setIcon(R.drawable.ic_leave_information);


                pinMenuItem = item;*/

                newFragment = UpcomingTimeOffFragment.newInstance();


                break;
            case R.id.action_reportsheeetsecond:

                newFragment = ReportsTimesheetFragment.newInstance();

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
        //  return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
