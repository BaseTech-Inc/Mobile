package com.example.tupa_mobile.Markers;

import static com.example.tupa_mobile.Fragments.MapFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.util.List;

public class MarkerWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = MarkerWindowAdapter.class.getSimpleName();
    private Context context;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;

    public MarkerWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.marker_info, null);

        TextView markerName = (TextView) v.findViewById(R.id.markerName);
        TextView markerAddress = (TextView) v.findViewById(R.id.markerAddress);
        markerName.setText(marker.getTitle());

        geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            String street = addresses.get(0).getThoroughfare();
            String stNumber = addresses.get(0).getSubThoroughfare();
            markerAddress.setText(String.format("%s, %s", street, stNumber));
        } catch (Exception e) {
            markerAddress.setText("Unable to get address");
            Log.e(TAG, e.getMessage());
        }

        return v;
    }
}