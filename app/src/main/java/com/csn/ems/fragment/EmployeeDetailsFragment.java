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
 * Created by uyalanat on 18-10-2016.
 */

public class EmployeeDetailsFragment extends Fragment {
    Fragment fragOne;
    Class fragmentClass = null;
    String tag = null;

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
                transaction.replace(R.id.fragment_container, new DisplayEmployeeDetailsFragment());
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
        Fragment newFragment = null;
        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_employeedetails:

                changeIcon(item, R.drawable.ic_employee_h);

                newFragment = new DisplayEmployeeDetailsFragment();
                pinneditem = item;
                if (pinneditem2 != null)
                    pinneditem2.setIcon(R.drawable.ic_password);

                if (pinneditem1 != null)
                    pinneditem1.setIcon(R.drawable.ic_employee_edit);


                break;
            case R.id.action_editdetails:
                changeIcon(item, R.drawable.ic_employee_edit_h);

                newFragment = new EditEmployeeDetailsFragment();
                pinneditem1 = item;
                if (pinneditem2 != null)
                    pinneditem2.setIcon(R.drawable.ic_password);

                if (pinneditem != null)
                    pinneditem.setIcon(R.drawable.ic_employee);

                break;
            case R.id.action_changepassword:
                changeIcon(item, R.drawable.ic_password_h);

                newFragment = new ChangePasswordFragment();
                pinneditem2 = item;
                if (pinneditem1 != null)
                    pinneditem1.setIcon(R.drawable.ic_employee_edit);

                if (pinneditem != null)
                    pinneditem.setIcon(R.drawable.ic_employee);


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
