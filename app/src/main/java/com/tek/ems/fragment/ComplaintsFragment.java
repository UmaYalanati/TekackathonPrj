package com.tek.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tek.ems.R;
import com.tek.ems.callback.MenuItemSelectedCallback;

/**
 * Created by uyalanat on 04-02-2017.
 */

public class ComplaintsFragment  extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String TAG = "ComplaintsFragment";
    Fragment newFragment = null;
    int selectedItem;

    private MenuItemSelectedCallback menuItemSelectedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.complaints, container, false);



        return view;
    }





}


