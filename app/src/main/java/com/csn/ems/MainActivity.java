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
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.csn.ems.activity.LoginActivity;
import com.csn.ems.callback.MenuItemSelectedCallback;
import com.csn.ems.callback.NavigationDrawerCallback;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.csn.ems.R.id.nav_dashboard;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuItemSelectedCallback, NavigationDrawerCallback {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    private static final String TAG = "MainActivity";
    EmployeeDetails employeeDetails = new EmployeeDetails();
    Fragment fragment;
    Class fragmentClass = null;
    String tag = null;
    private TextView tvemployeename, tvemployeeemail;
    de.hdodenhof.circleimageview.CircleImageView image_employee;
    int selectedMenuItem;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int currentSelectedItem;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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

        image_employee = (de.hdodenhof.circleimageview.CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView_employee);
//        image_employee.setImageResource(R.drawable.coffee_cup);

        tvemployeename = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvemployeename);
        tvemployeeemail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvemployeeemail);

        if (SharedPreferenceUtils
                .getInstance(MainActivity.this)
                .getSplashCacheItem(
                        EmsConstants.rolename) != null && SharedPreferenceUtils
                .getInstance(MainActivity.this)
                .getSplashCacheItem(
                        EmsConstants.rolename).equals("Manager")) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_orgcalendar).setVisible(false);
            nav_Menu.findItem(R.id.nav_leave).setVisible(false);
            nav_Menu.findItem(R.id.nav_reports).setVisible(false);
            nav_Menu.findItem(R.id.nav_settings).setVisible(true);
        }



        if (SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.employeename) != null && !SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.employeename).toString().trim().isEmpty()) {
            tvemployeename.setText(SharedPreferenceUtils
                    .getInstance(getApplicationContext())
                    .getSplashCacheItem(
                            EmsConstants.employeename).toString().trim());
        }

        if (SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.emaailid) != null && !SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.emaailid).toString().trim().isEmpty()) {
            tvemployeeemail.setText(SharedPreferenceUtils
                    .getInstance(getApplicationContext())
                    .getSplashCacheItem(
                            EmsConstants.emaailid).toString().trim());
        }


        if (SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.photoPath) != null && SharedPreferenceUtils
                .getInstance(getApplicationContext())
                .getSplashCacheItem(
                        EmsConstants.photoPath).toString().trim().contains("www")) {
            Picasso.with(MainActivity.this)
                    .load("http://" + SharedPreferenceUtils
                            .getInstance(MainActivity.this)
                            .getSplashCacheItem(
                                    EmsConstants.photoPath).toString().trim()).into(image_employee);
        }


        if (savedInstanceState == null) {
//            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_employee));
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
//Test if this is getting called
        setupFirebase();
    }

    @Override
    public void updateImage(Bitmap bitmap) {
        Log.d(TAG, "updateImage() called with: bitmap sized [" + bitmap.getByteCount() + "] bytes.");
        if (image_employee != null) {
            image_employee.setImageBitmap(bitmap);
        }
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

    /**
     * Init. Firebase here.
     */
    private void setupFirebase() {
        Log.d(TAG, "setupFirebase() called");
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("killswitch", false);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: task.isSuccessful");
//                            Toast.makeText(HomeActivity.this, "Fetch Succeeded",
//                                    Toast.LENGTH_SHORT).show();

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                            applyRetrievedSwitch();
                        }
                        else {
                            Log.d(TAG, "onComplete: task.isNotSuccessful");
//                            Toast.makeText(HomeActivity.this, "Fetch Failed",
//                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void applyRetrievedSwitch() {
        final boolean killswitch = mFirebaseRemoteConfig.getBoolean("killswitch");
        Log.d(TAG, "ç: killswitch: "+killswitch);
        if (killswitch) {
            throw new NullPointerException();
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

            if (SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename) != null && SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename).equals("Manager")) {
                MenuItem item = menu.findItem(R.id.action_editdetails);
                item.setVisible(false);
            }
            MenuItem item1 = menu.findItem(R.id.action_changepassword);
            item1.setVisible(false);
        } else if (currentSelectedItem == R.id.nav_timeclock) {
            getMenuInflater().inflate(R.menu.timeclock, menu);
            if (SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename) != null && !SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename).equals("Manager")) {
                MenuItem item = menu.findItem(R.id.action_approvedtimesheet);
                item.setVisible(false);
            }else {
                MenuItem item = menu.findItem(R.id.action_checkin);
                item.setVisible(false);
            }

        }else if (currentSelectedItem == R.id.nav_orgcalendar) {
            getMenuInflater().inflate(R.menu.orgcal, menu);
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
          /*  if (SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename) != null && SharedPreferenceUtils
                    .getInstance(MainActivity.this)
                    .getSplashCacheItem(
                            EmsConstants.rolename).equals("Manager")) {
                MenuItem item = menu.findItem(R.id.action_editdetails);
                item.setVisible(false);
            }*/
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
                SharedPreferenceUtils
                        .getInstance(MainActivity.this)
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.employeeId,
                                "").commitSplash();
                SharedPreferenceUtils
                        .getInstance(MainActivity.this)
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.childEmployeeId,
                                "").commitSplash();
                SharedPreferenceUtils
                        .getInstance(MainActivity.this)
                        .editSplash()
                        .addSplashCacheItem(EmsConstants.breakinTime,
                                "").commitSplash();

                Intent intent_homescreen = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_homescreen);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void postInit() {
        Log.d(TAG, "postInit() called");
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

    @Override
    public void navigateToItem(int itemId) {
        Log.d(TAG, "navigateToItem() called with: itemId = [" + itemId + "]");
        if (itemId > 0) {
            onNavigationItemSelected(navigationView.getMenu().findItem(itemId));
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        currentSelectedItem = id;
        if (id == nav_dashboard) {
            fragmentClass = DashBoardFragment.class;
            //  onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_employee));
            tag = "Dashboard";
        } else if (id == R.id.nav_employee) {
            EmsConstants.isfromemployeedetails=true;
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
            tag = "Reports";
        } else if (id == R.id.nav_settings) {
            EmsConstants.isfromemployeedetails=false;
            fragmentClass = EmployeeDetailsFragment.class;
            tag = "Settings";
        }
        //
        //   onNavigationItemSelected(navigationView.getMenu().getItem(R.id.nav_employee));
        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();


                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment, fragment, tag)
                        .addToBackStack(tag)
//                        .addToBackStack(String.valueOf(previousItemChecked))
                        .commit();
                // Highlight the selected item has been done by NavigationView
                item.setChecked(true);


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
 /*   @Override
    public void selectedItemHide(MenuItem menuItem, int itemId) {
        if (menuItem != null) {
            selectedMenuItem = menuItem.getItemId();
           // selectedMenuItem.setVisible(false);
            //selectedMenuItem.setVisible(false);
        } else {
            selectedMenuItem = itemId;
        }
        invalidateOptionsMenu();
    }*/
    void displaydetails() {
        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please wait...", false, false);
        int empid = Integer.parseInt(SharedPreferenceUtils
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
            //  Bitmap bmp=null;
            //  image_employee = (ImageView)navigationView. findViewById(R.id.imageView_employee);
            ///   image_employee.setImageBitmap(getBitmapFromURL(employeeDetails.getPhotoPath()));
            //  Picasso.with(MainActivity.this)
            //        .load("http://"+employeeDetails.getPhotoPath()).into((ImageView) navigationView.findViewById(R.id.imageView_employee));
            //   (ImageView) navigationView.findViewById(R.id.imageView_employee).s(getBitmapFromURL("http://"+employeeDetails.getPhotoPath()));
        }
      /*  if (employeeDetails.getEmployeeName()!=null)
            tvemployeename.setText(employeeDetails.getEmployeeName());

        if (employeeDetails.getEmailId()!=null)
            tvemployeeemail.setText(employeeDetails.getEmailId());*/
    }

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        return super.navigateUpTo(upIntent);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
