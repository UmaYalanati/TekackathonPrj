package com.csn.ems.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csn.ems.R;

/**
 * Created by uyalanat on 20-10-2016.
 */

public class DisplayEmployeeDetailsFragment extends Fragment {

    TextView tvemployeefullname,tvid,tvusername,tvmobile,tvaddress,tvemail,tvposition;
    public static DisplayEmployeeDetailsFragment newInstance() {
        return new DisplayEmployeeDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_employeedetails, container, false);

        tvemployeefullname=(TextView)view.findViewById(R.id.tvemployeefullname);
                tvid=(TextView)view.findViewById(R.id.tvid);
                        tvusername=(TextView)view.findViewById(R.id.tvusername);
                tvmobile=(TextView)view.findViewById(R.id.tvmobile);
                tvaddress=(TextView)view.findViewById(R.id.tvaddress);
                tvemail=(TextView)view.findViewById(R.id.tvemail);
                tvposition=(TextView)view.findViewById(R.id.tvposition);

        return view;
    }

}
