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
import com.tek.ems.emsconstants.EmsConstants;
import com.tek.ems.emsconstants.SharedPreferenceUtils;

import java.lang.reflect.Field;

/**
 * Created by uyalanat on 18-10-2016.
 */

public class EmployeeDetailsFragment extends Fragment {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final String LOGIN_TYPE = "LOGIN_TYPE";

    public static EmployeeDetailsFragment newInstance(String loginType) {

        Bundle args = new Bundle();
        args.putString(LOGIN_TYPE, loginType);
        EmployeeDetailsFragment fragment = new EmployeeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MenuItemSelectedCallback menuItemSelectedCallback;

    int selectedItem;

    private static final String TAG = "EmployeeDetailsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);
        Bundle arguments = getArguments();

        String loginType = arguments.getString(LOGIN_TYPE);
        Log.d(TAG, "onCreateView: loginType: " + loginType);

        if (getContext() instanceof MenuItemSelectedCallback) {
            Log.d(TAG, "onCreateView: Instance matched");
            menuItemSelectedCallback = (MenuItemSelectedCallback) getContext();
        } else {
            Log.d(TAG, "onCreateView: Not instance");
        }

        if (savedInstanceState == null) {
            try {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename) != null && SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename).equals("Manager")) {
                    if (loginType.equals("Settings")) {
                        transaction.replace(R.id.fragment_container, new ManagerDetails());
                    } else {
                        transaction.replace(R.id.fragment_container, new ChildEmployeeList());
                    }


                } else {
                    transaction.replace(R.id.fragment_container, new DisplayEmployeeDetailsFragment());

                }


                transaction.commit();
                selectedItem = R.id.action_employeedetails;

                if (menuItemSelectedCallback != null) {
                    menuItemSelectedCallback.selectedItem(null, selectedItem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuItemSelectedCallback.selectedItem(item, item.getItemId());
        // toggle nav drawer on selecting action bar app icon/title
        Fragment newFragment = null;
        selectedItem = item.getItemId();
        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_employeedetails:

                //  newFragment = new DisplayEmployeeDetailsFragment();
                if (SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename) != null && SharedPreferenceUtils
                        .getInstance(getActivity())
                        .getSplashCacheItem(
                                EmsConstants.rolename).equals("Manager")) {
                    //newFragment.replace(R.id.fragment_container, new ChildEmployeeList());
                    newFragment = new ChildEmployeeList();

                } else {
                    newFragment = new DisplayEmployeeDetailsFragment();

                }
                break;
            case R.id.action_editdetails:

                newFragment = new EditEmployeeDetailsFragment();

                break;
            case R.id.action_changepassword:

                newFragment = new ChangePasswordFragment();

                break;
        }

        try {
            // FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, newFragment);

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
