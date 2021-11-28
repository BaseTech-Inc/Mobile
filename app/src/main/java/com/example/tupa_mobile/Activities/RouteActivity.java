package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tupa_mobile.Address.AddressAdapter;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener{

    private static final String TAG = Localization.class.getSimpleName();
    public static final int ROUTE_TOP_ZOOM_PADDING = 1000;
    public static final int FASTEST_REQUEST_INTERVAL = 5;
    public static final int REQUEST_INTERVAL = 30;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 15;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int ROUTE_BOTTOM_ZOOM_PADDING = 300;

    private ArrayList<com.example.tupa_mobile.Address.Address> addresses;
    private AddressAdapter addressAdapter;
    private Location lastKnownLocation;
    private LocationRequest locationRequest;
    private CameraPosition cameraPosition;
    private Geocoder geocoder;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private Marker marker;
    private boolean locationPermissionGranted;

    private ImageButton confirmMarkerButton, btnBack;
    private Button confirmRouteButton;
    private ViewGroup searchLayout, resultsLayout;
    private EditText etFrom, etTo;
    private RecyclerView searchRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);
        confirmMarkerButton = findViewById(R.id.confirmMarkerButton);
        confirmRouteButton = findViewById(R.id.confirmRouteButton);
        searchLayout = findViewById(R.id.searchLayout);
        resultsLayout = findViewById(R.id.resultsLayout);
        searchRecycler = findViewById(R.id.searchRecycler);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        setEtFrom();
        setEtTo();
        setButtons();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setCompassEnabled(false);

        map.setOnCameraMoveListener(this);

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        createMarker();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener( this, new OnCompleteListener<Location>() {

                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                //map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                //map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onCameraMove() {
        centerMarker();
    }

    private void centerMarker() {
        LatLng midLatLng = map.getCameraPosition().target;
        if (marker!=null) marker.setPosition(midLatLng);
        else Log.d("TAG","UserMarker is null");

        if(marker.isVisible()){
            geocoder = new Geocoder(getApplicationContext());
            try{
                List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                String street = addresses.get(0).getThoroughfare();
                String stNumber = addresses.get(0).getSubThoroughfare();
                etFrom.setText(String.format("%s, %s", street, stNumber));
            }
            catch (Exception e){
                etFrom.setText("Unable to get address");
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void getCurrentAddress(EditText et){

        getLocationPermission();
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener( this, new OnCompleteListener<Location>() {

                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                geocoder = new Geocoder(getApplicationContext());
                                try{
                                    List<Address> addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                                    String street = addresses.get(0).getThoroughfare();
                                    String stNumber = addresses.get(0).getSubThoroughfare();
                                    etFrom.setText(String.format("%s, %s", street, stNumber));
                                }
                                catch (Exception e){
                                    etFrom.setText("Unable to get address");
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private Marker createMarker(){
        marker = map.addMarker(new MarkerOptions().position(defaultLocation));
        marker.setVisible(false);
        return marker;
    }

    private void setButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startMainActivity();
            }
        });
        confirmMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMarkerButton.setVisibility(View.GONE);
                marker.setVisible(false);
                etTo.requestFocus();
            }
        });
    }

    private void startMainActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void setEtTo() {
        etTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), etTo.getText(), Toast.LENGTH_SHORT).show();
                    confirmRouteButton.setVisibility(View.VISIBLE);
                    centerRoute();
                    return true;
                }
                return false;
            }
        });
        etTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                text.replaceAll("[|?*<\">+\\[\\]/']", "");
                if(text.length() != 0){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler();
                }
                else
                    resultsLayout.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setEtFrom() {
        getCurrentAddress(etFrom);
        etFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.setVisible(true);
                centerMarker();
                confirmMarkerButton.setVisibility(View.VISIBLE);
                etFrom.setFocusable(true);
                etFrom.setFocusableInTouchMode(true);
            }
        });

        etFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                text.replaceAll("[|?*<\">+ \\[\\]/']", "");
                if(marker.isVisible()){
                    resultsLayout.setVisibility(View.GONE);
                }
                else if(etFrom.isFocused() && text.length() != 0){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler();

                    //User query
                    String query = s.toString();

                    // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
                    // and once again when the user makes a selection (for example when calling fetchPlace()).
                    AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                    // Create a RectangularBounds object.
                    RectangularBounds bounds = RectangularBounds.newInstance(
                            new LatLng(-23.892792671007193, -46.97887828581721),
                            new LatLng(-22.52756065617645, -46.02750163888053));

                    // Use the builder to create a FindAutocompletePredictionsRequest.
                    FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                            // Call either setLocationBias() OR setLocationRestriction().
                            .setLocationBias(bounds)
                            //.setLocationRestriction(bounds)
                            .setOrigin(new LatLng(-23.5666595,-46.6084084))
                            .setCountries("BRA")
                            .setTypeFilter(TypeFilter.ADDRESS)
                            .setSessionToken(token)
                            .setQuery(query)
                            .build();

                    placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                        for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                            Log.i(TAG, prediction.getPlaceId());
                            Log.i(TAG, prediction.getPrimaryText(null).toString());
                        }
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Status: " + apiException.getStatusCode());
                            Log.e(TAG, "error: " + apiException.getMessage());
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFrom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), etFrom.getText(), Toast.LENGTH_SHORT).show();
                    marker.setVisible(false);
                    etTo.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void centerRoute(){
        if(etFrom.getText().length() > 0 && etTo.getText().length() > 0){

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(getCoordinates(etFrom.getText().toString()));
            builder.include(getCoordinates(etTo.getText().toString()));
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
            map.setPadding(0, ROUTE_TOP_ZOOM_PADDING, 0, ROUTE_BOTTOM_ZOOM_PADDING);
            map.animateCamera(cu);

        }
    }

    private LatLng getCoordinates(String address) {

        LatLng latLng = null;
        geocoder = new Geocoder(getApplicationContext());
        try{
            List<android.location.Address> addresses = geocoder.getFromLocationName(address, 1);
            if(addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                latLng = new LatLng(latitude, longitude);
            }
        }
        catch (Exception e){
            Log.e(TAG, "Unable to get address");
            Log.e(TAG, e.getMessage());
        }

        return latLng;
    }

    private void setSearchRecycler() {
        searchRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addresses = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addresses);
        searchRecycler.setAdapter(addressAdapter);
        fillAddresses();
    }

    private void fillAddresses(){
        addresses.add(new com.example.tupa_mobile.Address.Address("Alameda dos Guainumbis", "Planalto Paulista - SP", R.drawable.stormy));
        addresses.add(new com.example.tupa_mobile.Address.Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new com.example.tupa_mobile.Address.Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new com.example.tupa_mobile.Address.Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new com.example.tupa_mobile.Address.Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
    }
}