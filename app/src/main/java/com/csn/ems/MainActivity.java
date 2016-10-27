package com.csn.ems;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.activity.LoginActivity;
import com.csn.ems.callback.MenuItemSelectedCallback;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.fragment.DashBoardFragment;
import com.csn.ems.fragment.EmployeeDetailsFragment;
import com.csn.ems.fragment.LeavesFragment;
import com.csn.ems.fragment.OrgCalendarFragment;
import com.csn.ems.fragment.ReportsFragment;
import com.csn.ems.fragment.TimeClockFragment;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.services.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.nav_dashboard;
import static java.lang.System.load;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuItemSelectedCallback {

    private static final String TAG = "MainActivity";
    EmployeeDetails employeeDetails = new EmployeeDetails();
    Fragment fragment;
    Class fragmentClass = null;
    String tag = null;
    TextView tvemployeename, tvemployeeemail;
    ImageView image_employee;
    int selectedMenuItem;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int currentSelectedItem;

    @Override
    protected void onResume() {

        super.onResume();
    }

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
        image_employee = (ImageView)navigationView. findViewById(R.id.imageView_employee);

        tvemployeename = (TextView)navigationView. findViewById(R.id.tvemployeename);
        tvemployeeemail = (TextView)navigationView. findViewById(R.id.tvemployeeemail);

        displaydetails();
        if (savedInstanceState == null) {
            fragmentClass = DashBoardFragment.class;
            tag = "Dashboard";
            try {
                fragment = (Fragment) fragmentClass.newInstance();

                // Insert the fragment by replacing any existing fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, fragment, tag)
                        .commit();

                // Set action bar title

                navigationView.setCheckedItem(nav_dashboard);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=#00BCD4>" + tag + "</font>"));
        }

        postInit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                final FragmentManager supportFragmentManager = getSupportFragmentManager();
                if (supportFragmentManager.getBackStackEntryCount() > 0) {
                    super.onBackPressed();
                } else {
                    if (!doubleBackToExitPressedOnce) {
                        this.doubleBackToExitPressedOnce = true;
                        Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                    } else {
                        try {
                            super.onBackPressed();
                            System.exit(0);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            super.onBackPressed();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //based on fragment, update menu here. Got it? Shall i show ? t
        //There must be a easy way to do this. but this will work 100% u r doing hide i am asking item click
        if (currentSelectedItem == nav_dashboard) {
            getMenuInflater().inflate(R.menu.dashboardmenu, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        } else if (currentSelectedItem == R.id.nav_employee) {
            getMenuInflater().inflate(R.menu.main, menu);
            /* MenuItem item = menu.findItem(R.id.action_some);
             item.setVisible(false);*/
        } else if (currentSelectedItem == R.id.nav_timeclock) {
            getMenuInflater().inflate(R.menu.timeclock, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        } else if (currentSelectedItem == R.id.nav_leave) {
            getMenuInflater().inflate(R.menu.leaves, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        } else if (currentSelectedItem == R.id.nav_reports) {
            getMenuInflater().inflate(R.menu.report, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        } else if (currentSelectedItem == R.id.nav_settings) {
            getMenuInflater().inflate(R.menu.main, menu);
            /*MenuItem item = menu.findItem(R.id.action_some);
            item.setVisible(false);*/
        } else {
            getMenuInflater().inflate(R.menu.dashboardmenu, menu);
        }

        for (int i = 0; i < menu.size(); i++) {
            final MenuItem menuItem = menu.getItem(i);
            final int itemId = menuItem.getItemId();
            final Drawable drawable = menuItem.getIcon();

            int selectedColor = R.color.black;
            if (itemId == selectedMenuItem) {
                selectedColor = R.color.colorAccent;
            }

            if (drawable != null) {
                drawable.mutate();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.setColorFilter(getResources().getColor(selectedColor, getTheme()), PorterDuff.Mode.SRC_ATOP);
                } else {
                    drawable.setColorFilter(getResources().getColor(selectedColor), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //    action_editdetails
            case R.id.action_signout:

                Intent intent_homescreen = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_homescreen);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void postInit() {
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        // Update your UI here.
                        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

                        if (backStackEntryCount > 0) {
                            final String pageName = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName();
                            if (pageName != null) {
                                getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=#00BCD4>" + pageName + "</font>"));
                                if (pageName.equals("Dashboard")) {
                                    navigationView.setCheckedItem(R.id.nav_dashboard);
                                    currentSelectedItem = R.id.nav_dashboard;
                                } else if (pageName.equals("Employee")) {
                                    navigationView.setCheckedItem(R.id.nav_employee);
                                    currentSelectedItem = R.id.nav_employee;
                                } else if (pageName.equals("Time Lock")) {
                                    navigationView.setCheckedItem(R.id.nav_timeclock);
                                    currentSelectedItem = R.id.nav_timeclock;
                                } else if (pageName.equals("Org Calendar")) {
                                    navigationView.setCheckedItem(R.id.nav_orgcalendar);
                                    currentSelectedItem = R.id.nav_orgcalendar;
                                } else if (pageName.equals("Leave")) {
                                    navigationView.setCheckedItem(R.id.nav_leave);
                                    currentSelectedItem = R.id.nav_leave;
                                } else if (pageName.equals("Report")) {
                                    navigationView.setCheckedItem(R.id.nav_reports);
                                    currentSelectedItem = R.id.nav_reports;
                                } else if (pageName.equals("Settings")) {
                                    navigationView.setCheckedItem(R.id.nav_settings);
                                    currentSelectedItem = R.id.nav_settings;
                                }
                            } else {
                                Log.d(TAG, "onBackStackChanged: Null page name");
                            }
                        } else {
                            getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=#00BCD4> Dashboard </font>"));
                            navigationView.setCheckedItem(R.id.nav_dashboard);
                            currentSelectedItem = R.id.nav_dashboard;
                        }

                        invalidateOptionsMenu();

                    }
                }

        );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        currentSelectedItem = id;
        if (id == nav_dashboard) {
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
            tag = "Org Calendar";
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

                //  SpannableString s = new SpannableString(item.getTitle());
                // s.setSpan(new ForegroundColorSpan(Color.GREEN), 0, item.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //  getSupportActionBar().setTitle(s);

                getSupportActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=#00BCD4>" + tag + "</font>"));
                // invalidateOptionsMenu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        drawerLayout.closeDrawers();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void selectedItem(MenuItem menuItem, int itemId) {
        if (menuItem != null) {
            selectedMenuItem = menuItem.getItemId();
        } else {
            selectedMenuItem = itemId;
        }
        invalidateOptionsMenu();
    }
    void displaydetails(){
        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please wait...", false, false);
int empid=Integer.parseInt(SharedPreferenceUtils
        .getInstance(getApplicationContext())
        .getSplashCacheItem(
                EmsConstants.employeeId).toString().trim());
        Call<EmployeeDetails> listCall = ServiceGenerator.createService().getEmployeeById(empid);

        listCall.enqueue(new Callback<EmployeeDetails>() {
            @Override
            public void onResponse(Call<EmployeeDetails> call, Response<EmployeeDetails> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
                    //DO SUCCESS HANDLING HERE
                    employeeDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + employeeDetails + " PropertyTypes.");
                    setEmployeeDetails(employeeDetails);
                }
            }

            @Override
            public void onFailure(Call<EmployeeDetails> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(MainActivity.this, "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }
    public void setEmployeeDetails(EmployeeDetails employeeDetails) {
        if (employeeDetails.getPhotoPath() != null) {
          //  Picasso.with(MainActivity.this)
            //        .load("http://"+employeeDetails.getPhotoPath()).into((ImageView) navigationView.findViewById(R.id.imageView_employee));
         //   (ImageView) navigationView.findViewById(R.id.imageView_employee).s(getBitmapFromURL("http://"+employeeDetails.getPhotoPath()));
        }
      /*  if (employeeDetails.getEmployeeName()!=null)
            tvemployeename.setText(employeeDetails.getEmployeeName());

        if (employeeDetails.getEmailId()!=null)
            tvemployeeemail.setText(employeeDetails.getEmailId());*/
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
