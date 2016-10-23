package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.csn.ems.R;

import static com.csn.ems.R.id.btnendtime;
import static com.csn.ems.R.id.btnstarttime;

/**
 * Created by uyalanat on 23-10-2016.
 */

public class UpcomingTimeOffFragment extends Fragment implements View.OnClickListener{
    public static UpcomingTimeOffFragment newInstance() {
        return new UpcomingTimeOffFragment();
    }
    Button btnstarttime,btnendtime;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcomingtimeoff, container, false);
        btnstarttime= (Button) view. findViewById(R.id.btnstarttime);
        btnendtime= (Button)view. findViewById(R.id.btnendtime);
        btnstarttime.setOnClickListener(this);
        btnendtime.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstarttime:
                DatePickerFragment starttimeFragment = new DatePickerFragment(btnstarttime);

                starttimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.btnendtime:
                DatePickerFragment endtimeFragment = new DatePickerFragment(btnendtime);

                endtimeFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;

        }
    }
}
