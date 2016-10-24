package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.csn.ems.R;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class LeavesFragment extends Fragment {
    Fragment newFragment=null;
    MenuItem pinMenuItem;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.completeemployeedetails, container, false);
        if (savedInstanceState == null) {
            try {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
                transaction.replace(R.id.fragment_container, new UpcomingTimeOffFragment());
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }
   /* public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.leaves, menu);
        super.onCreateOptionsMenu(menu, menuInflater);

         pinMenuItem = menu.findItem(R.id.action_leaaveinformation);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_leaaveinformation:

                changeIcon(item,R.drawable.leaveinformation);
                if (pinMenuItem!=null)
                    pinMenuItem.setIcon(R.drawable.leave_black);


                pinMenuItem=item;

                newFragment =  LeavesStatusFragment.newInstance();



                break;
            case R.id.action_upcomingtimeoff:
                changeIcon(item,R.drawable.leave);
                if (pinMenuItem!=null)
                pinMenuItem.setIcon(R.drawable.leaveinformation_black);


                pinMenuItem=item;
              
                newFragment =  UpcomingTimeOffFragment.newInstance();


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
      //  return super.onOptionsItemSelected(item);
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
