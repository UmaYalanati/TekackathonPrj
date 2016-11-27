package com.csn.ems.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csn.ems.ChildEmployeeDetailsActivity;
import com.csn.ems.R;
import com.csn.ems.callback.ItemClickListener;
import com.csn.ems.model.ChildEmployees;
import com.csn.ems.model.Login;
import com.csn.ems.recyclerviewadapter.ChildEmployeeAdapter;
import com.csn.ems.sharedpreference.LoginComplexPreferences;

import java.util.List;

import static com.csn.ems.emsconstants.EmsConstants.child_Employeeid;

/**
 * Created by uyalanat on 27-11-2016.
 */

public class ChildEmployeeList extends Fragment implements ItemClickListener {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String TAG = "LeavesStatusFragment";

    Context context;
    RecyclerView child_employee_recycler_view;

    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;


    public static ChildEmployeeList newInstance() {
        return new ChildEmployeeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.childemployesslist, container, false);

        context = getActivity();


        child_employee_recycler_view = (RecyclerView) view.findViewById(R.id.child_employee_recycler_view);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        child_employee_recycler_view.setLayoutManager(recylerViewLayoutManager);
        LoginComplexPreferences loginComplexPreferences = LoginComplexPreferences.getComplexPreferences(getActivity(), "object_prefs", 0);
        final Login currentUser = loginComplexPreferences.getObject("object_value", Login.class);
        updateRecyclerViewForClients(currentUser.getChildEmployees());
        //child_employee_recycler_view.setO
        return view;
    }

    @Override
    public void onClick(View view, int position) {
        LoginComplexPreferences loginComplexPreferences = LoginComplexPreferences.getComplexPreferences(getActivity(), "object_prefs", 0);
        final Login currentUser = loginComplexPreferences.getObject("object_value", Login.class);
        child_Employeeid=currentUser.getChildEmployees().get(position).getEmployeeId();
        Intent i=new Intent(getActivity(), ChildEmployeeDetailsActivity.class);
        startActivity(i);
    }
    private void updateRecyclerViewForClients(List<ChildEmployees> childEmployeeList) {

        ChildEmployeeAdapter adapter = new ChildEmployeeAdapter(context, childEmployeeList);
        child_employee_recycler_view.setAdapter(adapter);


        adapter.setClickListener(this);
    }

}


