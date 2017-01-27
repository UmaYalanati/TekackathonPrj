package com.tek.ems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.tek.ems.activity.LoginActivity;


/**
 * Created by uyalanat on 29-05-2016.
 */
public class SplashActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 2000; // time to display the splash screen in ms
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.AppTheme);
      //  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
      //  getActionBar().hide();
        super.onCreate(savedInstanceState);
       // getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);

      //  getSupportActionBar().hide();
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {


                    startHomeScreen();
                    finish();
                }
            };
        };
        splashTread.start();
    }
    protected void startHomeScreen() {
        Intent i =new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
