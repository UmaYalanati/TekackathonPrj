package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.csn.ems.R;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.model.LeaveDetails;
import com.csn.ems.recyclerviewadapter.ListofLivesRecyclerViewAdapter;
import com.csn.ems.services.ServiceGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class LeavesStatusFragment extends Fragment {
String TAG="LeavesStatusFragment";
  //  LeaveDetails leavedetails=new LeaveDetails();
    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;



    public static LeavesStatusFragment newInstance() {
        return new LeavesStatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.leaveslist, container, false);

        context = getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);


        getlistofleaves();

        return view;
    }
void getlistofleaves(){
    final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);

    Call<List<LeaveDetails>> listCall= ServiceGenerator.createService().getLeaveDetails(2,"10/25/2016","10/28/2016",0);



    listCall.enqueue(new Callback<List<LeaveDetails>>() {
        @Override
        public void onResponse(Call<List<LeaveDetails>> call, Response<List<LeaveDetails>> response) {
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
                List<LeaveDetails> leaveDetails = response.body();
                Log.i(TAG, "onResponse: Fetched " + leaveDetails.size() + " clients.");
//                    for (Client client : clients) {
//                        Log.i(TAG, "onResponse: Client: "+client);
//                    }
               updateRecyclerViewForClients(leaveDetails);
            }
        }

        @Override
        public void onFailure(Call<List<LeaveDetails>> call, Throwable t) {
            if (loading.isShowing()) {
                loading.dismiss();
            }
            Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                    "Please try again after some time.", Toast.LENGTH_SHORT).show();
        //    Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
        }
    });
    loading.setCancelable(false);
    loading.setIndeterminate(true);
    loading.show();
}
    private void updateRecyclerViewForClients(List<LeaveDetails> leaveDetails) {
     //   Log.d(TAG, "updateRecyclerViewForClients() called for " + leaveDetails.size() + " Clients.");

       // if (recyclerViewAdapter != null) {
            ListofLivesRecyclerViewAdapter    adapter = new ListofLivesRecyclerViewAdapter(context, leaveDetails);

            recyclerView.setAdapter(adapter);
      //  }

      /*  RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            ViewClientAdapter viewClientAdapter = (ViewClientAdapter) adapter;
            viewClientAdapter.removeAllItems();
        }

        ListofLivesRecyclerViewAdapter adapter = (ListofLivesRecyclerViewAdapter) viewClientRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateClients(clients);
        }*/
    }
}
