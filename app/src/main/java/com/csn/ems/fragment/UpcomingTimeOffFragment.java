package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csn.ems.R;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class UpcomingTimeOffFragment extends Fragment {
    public static UpcomingTimeOffFragment newInstance() {
        return new UpcomingTimeOffFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcomingtimeoff, container, false);

        return view;
    }
}
