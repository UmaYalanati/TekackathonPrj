package com.csn.ems.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.csn.ems.R;
import com.csn.ems.activity.LoginActivity;
import com.csn.ems.recyclerviewadapter.DashboardRecyclerViewAdapter;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class DashBoardFragment extends Fragment {

    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    String[] subjects =
            {
                    "Hi Uma",
                    "Time Clock",
                    "Schedule"
            };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sample, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new DashboardRecyclerViewAdapter(getActivity(), subjects);

        recyclerView.setAdapter(recyclerViewAdapter);


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_signout:

                Intent intent_homescreen = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_homescreen);
                getActivity().finish();
                ;
                break;
        }


        return true;
        //  return super.onOptionsItemSelected(item);
    }
}
