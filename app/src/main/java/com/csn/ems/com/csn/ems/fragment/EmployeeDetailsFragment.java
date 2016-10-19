package com.csn.ems.com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csn.ems.R;

/**
 * Created by uyalanat on 18-10-2016.
 */

public class EmployeeDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);

        return view;
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    *//*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );
        inflater.inflate( R.menu.main, menu );
    }*//*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment;
        Class fragmentClass = null;
        String tag = null;
        //noinspection SimplifiableIfStatement
       *//* if (id == R.id.action_settings) {
            return true;
        }*//*


        *//*if (id == R.id.nav_dashboard) {



        } else *//*if (id == R.id.action_settings) {


            Toast.makeText(getActivity(),"uma",1000).show();
                try {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    Fragment fragOne = new OrgCalendarFragment();
                    Bundle arguments = new Bundle();
                    arguments.putBoolean("shouldYouCreateAChildFragment", true);
                    fragOne.setArguments(arguments);
                    ft.add(R.id.fragment, fragOne);
                    ft.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            return true;

        }*//* else if (id == R.id.nav_timeclock) {

        } else if (id == R.id.nav_orgcalendar) {
            fragmentClass = OrgCalendarFragment.class;
            tag = "Employee";
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*//*



        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
    }*/
   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.main, menu);
       MenuItem item = menu.findItem(R.id.action_some);
       item.setVisible(false);
       super.onCreateOptionsMenu(menu, inflater);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:

                Toast.makeText(getActivity(),"uma",1000).show();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
