package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.activity.LoginActivity;
import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class DashBoardFragment extends Fragment {
String TAG="DashBoardFragment";
   /* RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    String[] subjects =
            {
                    "Hi Uma",
                    "Time Clock",
                    "Schedule"
            };*/

    InTakeMasterDetails inTakeMasterDetails=new InTakeMasterDetails();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dashboardscreen, container, false);

      /*  recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewAdapter = new DashboardRecyclerViewAdapter(getActivity(), subjects);

        recyclerView.setAdapter(recyclerViewAdapter);*/

       // displaydetails();
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

                break;
        }


        return true;
        //  return super.onOptionsItemSelected(item);
    }
    void displaydetails(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

        Call<InTakeMasterDetails> listCall = ServiceGenerator.createService().getInTakeMasterDetails(inTakeMasterDetails);

        listCall.enqueue(new Callback<InTakeMasterDetails>() {
            @Override
            public void onResponse(Call<InTakeMasterDetails> call, Response<InTakeMasterDetails> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
                    //DO SUCCESS HANDLING HERE
                    inTakeMasterDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + inTakeMasterDetails + " PropertyTypes.");
                   // setInTakeMasterDetails();
                }
            }

            @Override
            public void onFailure(Call<InTakeMasterDetails> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }
}
