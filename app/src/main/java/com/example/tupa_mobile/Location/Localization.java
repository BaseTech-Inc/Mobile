package com.example.tupa_mobile.Location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tupa_mobile.Activities.MainActivity;
import com.example.tupa_mobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.app.ActivityCompat.requestPermissions;

public class Localization {

    private int LOCATION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LatLng latLng;

    public void refreshLocation(Context context, GoogleMap map){

        locationManager = (LocationManager) ((Activity)context).getSystemService(LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(((Activity)context), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0.1f, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
            }
        });

    }
}
