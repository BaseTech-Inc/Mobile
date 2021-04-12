package com.example.tupa_mobile.Location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class Localization {

    public void getLocation(Context context, TextView txtLocation, FusedLocationProviderClient fusedLocationProviderClient, Activity activity, int requestCode){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(activity.getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                //get Location
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){

                                    Double lat = location.getLatitude();
                                    Double longitude = location.getLongitude();

                                    txtLocation.setText(lat + ", " + longitude);
                                }
                                else {
                                    txtLocation.setText("couldn't find your location");
                                }
                            }
                        });
            } else {
                requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            }
        }
    }
}
