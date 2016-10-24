package com.csn.ems;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.csn.ems.activity.LoginActivity;
import com.csn.ems.fragment.DashBoardFragment;
import com.csn.ems.fragment.EmployeeDetailsFragment;
import com.csn.ems.fragment.LeavesFragment;
import com.csn.ems.fragment.OrgCalendarFragment;
import com.csn.ems.fragment.ReportsFragment;
import com.csn.ems.fragment.TimeClockFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int currentSelectedItem;

    Fragment fragment;
    Class fragmentClass = null;
    String tag = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        fragmentClass = DashBoardFragment.class;
        tag = "Dashboard";
        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment, tag)
                        .addToBackStack(tag)
//                        .addToBackStack(String.valueOf(previousItemChecked))
                        .commit();

                // Set action bar title
                setTitle(tag);

                invalidateOptionsMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onBackPressed() {
        //Back stack u r not handling,
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //based on fragment, update menu here. Got it? Shall i show ? t
        //There must be a easy way to do this. but this will work 100% u r doing hide i am asking item click
         if (currentSelectedItem == R.id.nav_dashboard) {
            getMenuInflater().inflate(R.menu.dashboardmenu, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        }else  if (currentSelectedItem == R.id.nav_employee) {
             getMenuInflater().inflate(R.menu.main, menu);
            /* MenuItem item = menu.findItem(R.id.action_some);
             item.setVisible(false);*/
         } else if (currentSelectedItem == R.id.nav_timeclock) {
            getMenuInflater().inflate(R.menu.timeclock, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        }else if (currentSelectedItem == R.id.nav_leave) {
            getMenuInflater().inflate(R.menu.leaves, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        }else if (currentSelectedItem == R.id.nav_reports) {
            getMenuInflater().inflate(R.menu.report, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        }else if (currentSelectedItem == R.id.nav_settings) {
             getMenuInflater().inflate(R.menu.main, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
         }else {
            getMenuInflater().inflate(R.menu.dashboardmenu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_signout:

                Intent intent_homescreen=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_homescreen);
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        int id = item.getItemId();
        currentSelectedItem = id;

        if (id == R.id.nav_dashboard) {

            fragmentClass = DashBoardFragment.class;
            tag = "Dashboard";
        } else if (id == R.id.nav_employee) {

            fragmentClass = EmployeeDetailsFragment.class;
            tag = "Employee";

        } else if (id == R.id.nav_timeclock) {
            fragmentClass = TimeClockFragment.class;
            tag = "Time Lock";
        } else if (id == R.id.nav_orgcalendar) {
            fragmentClass = OrgCalendarFragment.class;
            tag = "Employee";
        } else if (id == R.id.nav_leave) {
            fragmentClass = LeavesFragment.class;
            tag = "Leave";
        } else if (id == R.id.nav_reports) {
            fragmentClass = ReportsFragment.class;
            tag = "Report";
        } else if (id == R.id.nav_settings) {
            fragmentClass = EmployeeDetailsFragment.class;
            tag = "Settings";
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment, tag)
                        .addToBackStack(tag)
//                        .addToBackStack(String.valueOf(previousItemChecked))
                        .commit();
                // Highlight the selected item has been done by NavigationView
                item.setChecked(true);
                // Set action bar title
                setTitle(item.getTitle());
                setTitleColor(ContextCompat.getColor(this, R.color.colorAccent));
              //  getActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=\"red\">" + getString(R.string.app_name) + "</font>"));
                invalidateOptionsMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        drawerLayout.closeDrawers();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
