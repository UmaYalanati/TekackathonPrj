package com.csn.ems.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csn.ems.R;
import com.csn.ems.callback.NavigationDrawerCallback;
import com.csn.ems.emsconstants.EmsConstants;
import com.csn.ems.emsconstants.SharedPreferenceUtils;
import com.csn.ems.model.EmployeeDetails;
import com.csn.ems.services.EMSService;
import com.csn.ems.services.ServiceGenerator;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

import static com.csn.ems.R.id.card_view_orgcalendar;
import static com.csn.ems.R.id.card_view_timeclcok;
import static com.csn.ems.R.id.imgprofilepic;


/**
 * Created by uyalanat on 20-10-2016.
 */

public class EditEmployeeDetailsFragment extends Fragment {
    private static final String TAG = "EditEmployeeDetailsFrag";

    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    EmployeeDetails employeeDetails = new EmployeeDetails();
    Button btnupdateemployee;
    TextInputEditText ed_gender;
    TextInputEditText ed_Name, ed_NickName, ed_email, ed_dob, ed_mobilenor, ed_homenor, ed_address, ed_address2, ed_city, ed_state, ed_zipcode;

    TextInputEditText ed_joiningDate, ed_positionName, ed_businessAreaName, ed_subBusinessAreaName, ed_hoursPerDay;

    private NavigationDrawerCallback navigationDrawerCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_profile, container, false);

        if (getContext() instanceof NavigationDrawerCallback) {
            Log.d(TAG, "onCreateView: Context is instance of NavigationDrawerCallback");
            navigationDrawerCallback = (NavigationDrawerCallback) getContext();
        } else {
            Log.d(TAG, "onCreateView: Context is NOT instance of NavigationDrawerCallback");
        }

        circleImageView = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.circleImageView);
        ed_gender = (TextInputEditText) view.findViewById(R.id.ed_gender);
        btnupdateemployee = (Button) view.findViewById(R.id.btnupdateemployee);
        ed_Name = (TextInputEditText) view.findViewById(R.id.ed_Name);
        ed_NickName = (TextInputEditText) view.findViewById(R.id.ed_NickName);
        ed_address2 = (TextInputEditText) view.findViewById(R.id.ed_address2);
        ed_email = (TextInputEditText) view.findViewById(R.id.ed_email);
        ed_dob = (TextInputEditText) view.findViewById(R.id.ed_dob);
        ed_mobilenor = (TextInputEditText) view.findViewById(R.id.ed_mobilenor);
        ed_homenor = (TextInputEditText) view.findViewById(R.id.ed_homenor);
        ed_address = (TextInputEditText) view.findViewById(R.id.ed_address);
        ed_city = (TextInputEditText) view.findViewById(R.id.ed_city);
        ed_state = (TextInputEditText) view.findViewById(R.id.ed_state);
        ed_zipcode = (TextInputEditText) view.findViewById(R.id.ed_zipcode);

        ed_joiningDate = (TextInputEditText) view.findViewById(R.id.ed_joiningDate);
        ed_positionName = (TextInputEditText) view.findViewById(R.id.ed_positionName);
        ed_businessAreaName = (TextInputEditText) view.findViewById(R.id.ed_businessAreaName);
        ed_subBusinessAreaName = (TextInputEditText) view.findViewById(R.id.ed_subBusinessAreaName);
        ed_hoursPerDay = (TextInputEditText) view.findViewById(R.id.ed_hoursPerDay);

       /* Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_dashboard_profile_pic, getContext().getTheme());
        circleImageView.setImageDrawable(drawable);*/

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });

        loadConsolidatedData();

        btnupdateemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDetails();
            }
        });
        return view;
    }

    public void loadConsolidatedData() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please wait...", false, false);
        int empid = Integer.parseInt(SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.employeeId).toString().trim());

        if (SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.rolename) != null && SharedPreferenceUtils
                .getInstance(getActivity())
                .getSplashCacheItem(
                        EmsConstants.rolename).equals("Manager")) {
            empid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.childEmployeeId).toString().trim());
        }else {
            empid = Integer.parseInt(SharedPreferenceUtils
                    .getInstance(getActivity())
                    .getSplashCacheItem(
                            EmsConstants.employeeId).toString().trim());
        }
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
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
                    //DO SUCCESS HANDLING HERE
                    employeeDetails = response.body();
                    Log.i(TAG, "onResponse: Fetched " + employeeDetails + " PropertyTypes.");
                    setEmployeeDetails();
                }
            }

            @Override
            public void onFailure(Call<EmployeeDetails> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();
    }

    public void setEmployeeDetails() {
        // ed_Name,ed_NickName,ed_email,ed_username,ed_mobilenor,ed_homenor,ed_address,ed_city,ed_state,ed_zipcode
        ed_Name.setText(employeeDetails.getEmployeeName());
        ed_email.setText(employeeDetails.getEmailId());
        ed_mobilenor.setText(employeeDetails.getContactNumber());
        ed_address.setText(employeeDetails.getAddress1());
        ed_address2.setText(employeeDetails.getAddress2());
        ed_city.setText(employeeDetails.getCity());
        ed_state.setText(employeeDetails.getStateName());
        ed_zipcode.setText(String.valueOf(employeeDetails.getPostalCode()));
        ed_dob.setText(employeeDetails.getdOB());
        ed_gender.setText(employeeDetails.getGender());
        ed_joiningDate.setText(employeeDetails.getJoiningDate());
        ed_positionName.setText(employeeDetails.getPositionName());
        ed_businessAreaName.setText(employeeDetails.getBusinessAreaName());
        ed_subBusinessAreaName.setText(employeeDetails.getSubBusinessAreaName());
        ed_hoursPerDay.setText(employeeDetails.getHoursPerDay());
        if (employeeDetails.getPhotoPath()!=null&&employeeDetails.getPhotoPath().contains("www")){
        Picasso.with(getActivity())
                .load("http://" +employeeDetails.getPhotoPath()).into(circleImageView);
        //   (ImageView) navigationView.findViewById(R.id.imageView_employee).s(getBitmapFromURL("http://"+employeeDetails.getPhotoPath()));
    }/* else {
      circleImageView.setBackgroundResource(R.drawable.male_profilepic);
          *//*  Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_dashboard_profile_pic, getContext().getTheme());
            circleImageView.setImageDrawable(drawable);*//*
    }*/

    }

    public void updateemployeedetails() {
        // ed_Name,ed_NickName,ed_email,ed_username,ed_mobilenor,ed_homenor,ed_address,ed_city,ed_state,ed_zipcode
        employeeDetails.setEmployeeName(ed_Name.getText().toString().trim());
        employeeDetails.setEmailId(ed_email.getText().toString().trim());
        employeeDetails.setContactNumber(ed_mobilenor.getText().toString().trim());
        employeeDetails.setAddress1(ed_address.getText().toString().trim());
        employeeDetails.setAddress2(ed_address2.getText().toString().trim());
        employeeDetails.setdOB(ed_dob.getText().toString().trim());
        employeeDetails.setCity(ed_city.getText().toString().trim());
        employeeDetails.setStateName(ed_state.getText().toString().trim());
        employeeDetails.setGender(ed_gender.getText().toString().trim());
        if (!ed_zipcode.getText().toString().trim().isEmpty())
            employeeDetails.setPostalCode(Integer.parseInt(ed_zipcode.getText().toString().trim()));


        employeeDetails.setJoiningDate(ed_joiningDate.getText().toString().trim());
        employeeDetails.setPositionName(ed_positionName.getText().toString().trim());
        employeeDetails.setBusinessAreaName(ed_businessAreaName.getText().toString().trim());
        employeeDetails.setSubBusinessAreaName(ed_subBusinessAreaName.getText().toString().trim());
        employeeDetails.setHoursPerDay(ed_hoursPerDay.getText().toString().trim());


    }

    void uploadDetails() {
        updateemployeedetails();
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading Data", "Please wait...", false, false);

        EMSService service = ServiceGenerator.createService();
        Call<EmployeeDetails> employeeDetailsCall = service.updateEmployee(employeeDetails);
        employeeDetailsCall.enqueue(new Callback<EmployeeDetails>() {
            @Override
            public void onResponse(Call<EmployeeDetails> call, Response<EmployeeDetails> response) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }

                if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                    try {
                        String errorMessage = "ERROR - " + response.code() + " - " + response.errorBody().string();
                        Log.e(TAG, "onResponse: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: IOException while parsing response error", e);
                    }
                } else if (response != null && response.isSuccessful()) {
//DO SUCCESS HANDLING HERE

                    EmployeeDetails emp = response.body();


                    SharedPreferenceUtils
                            .getInstance(getActivity())
                            .editSplash()
                            .addSplashCacheItem(EmsConstants.employeename,
                                    String.valueOf(emp.getEmployeeName())).commitSplash();

                    SharedPreferenceUtils
                            .getInstance(getActivity())
                            .editSplash()
                            .addSplashCacheItem(EmsConstants.emaailid,
                                    String.valueOf(emp.getEmailId())).commitSplash();

                    SharedPreferenceUtils
                            .getInstance(getActivity())
                            .editSplash()
                            .addSplashCacheItem(EmsConstants.photoPath,
                                    String.valueOf(emp.getPhotoPath())).commitSplash();


                    if (emp != null) {
                        Log.i(TAG, "onResponse: Property Data Saved Successfully!, Response: " + emp);
                        new AlertDialog.Builder(getContext())
                                .setTitle("EmployeeDetails Uploaded Successfully!")
                                .setMessage("")
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //loadPage(1);
                                    }
                                })
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //  clear_Fields();
                                        // loadPage(1);
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("EmployeeDetails Creation Failed!")
                                .setMessage("We are unable to save your EmployeeDetailsin our database this time.\n\n" +
                                        "Please try validating your parameters once or Try again later.")
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeDetails> call, Throwable t) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
                Toast.makeText(getContext(), "Error connecting with Web Services...\n" +
                        "Please try again after some time.", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.e(TAG, "onFailure: Error parsing WS: " + t.getMessage(), t);
                } else {
                }
            }
        });
        loading.setCancelable(false);
        loading.setIndeterminate(true);
        loading.show();

    }

    private void updateNavigationDrawer(byte[] bytes) {
        if (navigationDrawerCallback != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            navigationDrawerCallback.updateImage(bitmap);
        }
    }

    void loadImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    RxImagePicker.with(getContext()).requestImage(Sources.CAMERA).subscribe(new Action1<Uri>() {
                        @Override
                        public void call(Uri uri) {
                            Log.d(TAG, "call() called with: " + "uri = [" + uri + "]");
                            Glide.with(EditEmployeeDetailsFragment.this)
                                    .load(uri)
                                    .dontAnimate()
                                    .error(R.drawable.ic_dashboard_profile_pic)
                                    .into(circleImageView);

                            try {
                                InputStream iStream = getActivity().getContentResolver().openInputStream(uri);
//                                byte[] bytes = ImageCompressor.compressImage(getBytes(iStream));
                                byte[] bytes = getBytes(iStream);
                                employeeDetails.setByteArrayPhoto(bytes);
                                updateNavigationDrawer(bytes);
                                //  getHoarding().setHoardingImage(bytes);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (items[item].equals("Choose from Library")) {
                    RxImagePicker.with(getContext()).requestImage(Sources.GALLERY).subscribe(new Action1<Uri>() {
                        @Override
                        public void call(Uri uri) {
                            Glide.with(EditEmployeeDetailsFragment.this)
                                    .load(uri)
                                    .dontAnimate()
                                    .error(R.drawable.ic_dashboard_profile_pic)
                                    .into(circleImageView);

                            InputStream iStream;
                            try {
                                iStream = getActivity().getContentResolver().openInputStream(uri);
//                                byte[] bytes = ImageCompressor.compressImage(getBytes(iStream));
                                byte[] bytes = getBytes(iStream);
                                employeeDetails.setByteArrayPhoto(bytes);
                                updateNavigationDrawer(bytes);
                                //  getHoarding().setHoardingImage(bytes);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
