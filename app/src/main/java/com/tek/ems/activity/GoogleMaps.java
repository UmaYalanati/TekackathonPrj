package com.tek.ems.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import com.tek.ems.R;
import com.tek.ems.emsconstants.EmsConstants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * @author Uma Yalanati
 */
public class GoogleMaps extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "GoogleMaps";

    String address;
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;
    String adminarea;
    String SubThoroughfare;
    Button add_loc;
    String str_lat, str_long, str_address;
    // Google Map
    private GoogleMap googleMap;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //CSNconstants.is_from_addevent=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);

        try {
            // Loading map
            initilizeMap();

         /*   googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            {
                add_loc.setVisibility(View.VISIBLE);
                googleMap.setOnMapClickListener(new OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng arg0) {

                        Log.d("arg0", arg0.latitude + "-" + arg0.longitude);

                        str_lat = String.valueOf(arg0.latitude);
                        str_long = String.valueOf(arg0.longitude);
                    }
                });
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            ((MapFragment) getFragmentManager().findFragmentById(R.id.
                    map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleap) {
                    googleMap = googleap;
                   // MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(address);

                    // Changing marker icon
                   // marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.redpin));
                    LatLng latLng = new LatLng(EmsConstants.latitude, EmsConstants.longitude);
                    // adding marker
                    //googleMap.addMarker(marker);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Employee Location"));
                   // googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

            });

        }
    }



    @Override
    protected void onResume() {
        super.onResume();

    }








}