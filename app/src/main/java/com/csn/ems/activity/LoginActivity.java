package com.csn.ems.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.csn.ems.MainActivity;
import com.csn.ems.R;


/**
 * Created by uyalanat on 18-10-2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";

    private Button bt_submit, bt_clear;


    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_clear  = (Button) findViewById(R.id.btn_clear);
        bt_submit= (Button) findViewById(R.id.btn_submit);

        userIdEditText = (TextInputEditText) findViewById(R.id.userId);
        passwordEditText = (TextInputEditText) findViewById(R.id.password);

        bt_submit.setOnClickListener(this);
        bt_clear.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (userIdEditText.getText().toString().isEmpty()) {
                    userIdEditText.setError("Please enter UserName to Login!");
                    userIdEditText.requestFocus();
                }else if (passwordEditText.getText().toString().isEmpty()) {
                    this.passwordEditText.setError("Please enter Password to Login!");

                    passwordEditText.requestFocus();
                }else{
                    Intent intent_homescreen=new Intent(com.csn.ems.com.csn.ems.activity.LoginActivity.this, MainActivity.class);
                    startActivity(intent_homescreen);
                }




                break;

            default:
                break;
        }

    }





}

