package com.tek.ems;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.tek.ems.model.InTakeMasterDetails;
import com.tek.ems.services.ServiceGenerator;

import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica_Neue_Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        EMSApplication.context = getApplicationContext();
        JodaTimeAndroid.init(this);
        //displaydetails();
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
