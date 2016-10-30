package com.csn.ems;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.csn.ems.model.InTakeMasterDetails;
import com.csn.ems.services.ServiceGenerator;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uyalanat on 28-10-2016.
 */

public class EMSApplication extends Application {

    private static Context context;
  public static  InTakeMasterDetails inTakeMasterDetails = new InTakeMasterDetails();
String TAG="EMSApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        EMSApplication.context = getApplicationContext();
        JodaTimeAndroid.init(this);
        displaydetails();
    }

    void displaydetails() {
        //final ProgressDialog loading = ProgressDialog.show(context, "Fetching Data", "Please wait...", false, false);

        Call<InTakeMasterDetails> listCall = ServiceGenerator.createService().getInTakeMasterDetails();

        listCall.enqueue(new Callback<InTakeMasterDetails>() {
            @Override
            public void onResponse(Call<InTakeMasterDetails> call, Response<InTakeMasterDetails> response) {
               /* if (loading.isShowing()) {
                    loading.dismiss();
                }*/

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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
                /*if (loading.isShowing()) {
                    loading.dismiss();
                }*/
                Toast.makeText(context, "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
       /* loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();*/
    }


}
