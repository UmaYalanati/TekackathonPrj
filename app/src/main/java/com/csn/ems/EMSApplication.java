package com.csn.ems;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by uyalanat on 28-10-2016.
 */

public class EMSApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
