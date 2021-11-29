package com.example.tupa_mobile.Fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.BingMapActivity;
import com.example.tupa_mobile.Activities.RouteActivity;
import com.example.tupa_mobile.Address.Address;
import com.example.tupa_mobile.Address.AddressAdapter;
import com.example.tupa_mobile.Alerts.AlertData;
import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.Markers.CustomAdapterClickListener;
import com.example.tupa_mobile.Markers.MarkerAdapter;
import com.example.tupa_mobile.Markers.MarkerWindowAdapter;
import com.example.tupa_mobile.Markers.MarkersData;
import com.example.tupa_mobile.Markers.UserMarker;
import com.example.tupa_mobile.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, CustomAdapterClickListener, LocationListener {

    private static final String TAG = Localization.class.getSimpleName();
    public static final int ROUTE_TOP_ZOOM_PADDING = 1000;
    public static final int FASTEST_REQUEST_INTERVAL = 5;
    public static final int REQUEST_INTERVAL = 30;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 15;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int ROUTE_BOTTOM_ZOOM_PADDING = 300;
    public static final long LOCATION_REFRESH_TIME = 1000;
    public static final float LOCATION_REFRESH_DISTANCE = 100;
    private boolean BOTTOM_SHEET_DRAGGABLE = true;

    private LinearLayout bottomNavigationContainer;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText etFrom, etTo, etAddress;
    private TextView txtAddMarkers;
    private FrameLayout topFrame;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Button confirmRouteButton;
    private ImageButton searchBack, confirmMarkerButton, btnSearch, btnRiskAreas, btnAlerts, btnMenu, btnMyLocation, btnBack;
    private ViewGroup searchLayout, resultsLayout, mapToolbar, relativeMenu, mapFrame, emptyMarkersLayout;
    private Toolbar toolbar;
    private MenuItem searchItem, notificationItem;
    private ArrayList<UserMarker> userMarkers;
    private ArrayList<Address> addresses;
    private MarkerAdapter markerAdapter;
    private AddressAdapter addressAdapter;
    private RecyclerView bottomDrawerRecycler, searchRecycler;
    private GoogleMap map;
    private Marker marker, destinationMarker;
    private Location currentLocation;
    private Calendar lastCall = Calendar.getInstance();
    private Geocoder geocoder;
    private UserMarker userMarker;
    private CameraPosition cameraPosition;
    private Location lastKnownLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private boolean locationPermissionGranted;

    private double latitude, longitude;
    private ArrayList<MarkersData> markersData;
    private MarkersData markerData;
    private ArrayList<AlertData> alertsData;
    private AlertData alertData;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFrame = view.findViewById(R.id.mapFrame);
        mapFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        searchLayout = view.findViewById(R.id.searchLayout);

        Places.initialize(getContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(getContext());

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * REQUEST_INTERVAL);
        locationRequest.setFastestInterval(1000 * FASTEST_REQUEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        adjustBottomDrawer(view, this);
        setSearchETs(view);
        setToolbar(view);
        setConfirmMarkerButton(view);
        setConfirmRouteButton(view);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
        map.getUiSettings().setTiltGesturesEnabled(false);

        map.setOnMapClickListener(this);
        map.setOnCameraIdleListener(this);
        map.setOnCameraMoveStartedListener(this);
        map.setOnCameraMoveListener(this);
        map.setOnCameraMoveCanceledListener(this);

        MarkerWindowAdapter markerInfoWindowAdapter = new MarkerWindowAdapter(getContext());
        map.setInfoWindowAdapter(markerInfoWindowAdapter);

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
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {

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
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions((Activity) getContext(),
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
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private Marker createMarker() {
        marker = map.addMarker(new MarkerOptions().position(defaultLocation));
        marker.setVisible(false);
        return marker;
    }

    private Marker createDestinationMarker(LatLng latLng) {
        if (destinationMarker == null) {
            destinationMarker = map.addMarker(new MarkerOptions().position(defaultLocation));
        } else {
            destinationMarker.setPosition(latLng);
        }
        centerMarkers();
        return destinationMarker;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //createDestinationMarker(latLng);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        //Toast.makeText(getContext(), "Parabéns, você clickou em: " + String.format("Latitude",marker.getPosition().latitude) + String.format("Longitude",marker.getPosition().longitude), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCameraIdle() {
    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onCameraMove() {

        getLocationPermission();
        try {
            if (locationPermissionGranted) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        if (map.getCameraPosition().target != myLocation) {
                            btnMyLocation.setVisibility(View.VISIBLE);
                        } else btnMyLocation.setVisibility(View.GONE);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }

        LatLng midLatLng = map.getCameraPosition().target;
        if (marker != null) marker.setPosition(midLatLng);
        else Log.d("TAG", "UserMarker is null");

        Calendar currentCall = Calendar.getInstance();

        if (marker.isVisible()) {

            if (getLastCall("LAST_API_CALL") == 0) {
                geocoder = new Geocoder(getContext());
                try {
                    List<android.location.Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    String street = addresses.get(0).getThoroughfare();
                    String stNumber = addresses.get(0).getSubThoroughfare();
                    etAddress.setText(String.format("%s, %s", street, stNumber));
                } catch (Exception e) {
                    etAddress.setText("Unable to get address");
                    Log.e(TAG, e.getMessage());
                }
                saveLastCall("LAST_API_CALL", currentCall.getTimeInMillis());
            } else if (getLastCall("LAST_API_CALL") + 4000 < currentCall.getTimeInMillis()) {
                geocoder = new Geocoder(getContext());
                try {
                    List<android.location.Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    String street = addresses.get(0).getThoroughfare();
                    String stNumber = addresses.get(0).getSubThoroughfare();
                    etAddress.setText(String.format("%s, %s", street, stNumber));
                } catch (Exception e) {
                    etAddress.setText("Unable to get address");
                    Log.e(TAG, e.getMessage());
                }
                saveLastCall("LAST_API_CALL", currentCall.getTimeInMillis());
            }

        }


    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    private void getCurrentAddress(EditText et) {

        getLocationPermission();
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {

                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                geocoder = new Geocoder(getContext());
                                try {
                                    List<android.location.Address> addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                                    String street = addresses.get(0).getThoroughfare();
                                    String stNumber = addresses.get(0).getSubThoroughfare();
                                    etFrom.setText(String.format("%s, %s", street, stNumber));
                                } catch (Exception e) {
                                    etFrom.setText("Unable to get address");
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void setToolbar(View view) {
        topFrame = view.findViewById(R.id.topFrame);
        mapToolbar = view.findViewById(R.id.map_toolbar);
        relativeMenu = view.findViewById(R.id.relativeMenu);
        btnMenu = view.findViewById(R.id.btnSandwich);
        btnBack = view.findViewById(R.id.btnBack);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnRiskAreas = view.findViewById(R.id.btnRiskAreas);
        btnAlerts = view.findViewById(R.id.btnAlerts);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenuVisible();
            }
        });

        btnRiskAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinBottomSheet();
                addRiskMarkers();
                switchMenuVisible();
                btnBack.setVisibility(View.VISIBLE);
            }
        });

        btnAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinBottomSheet();
                addAlertMarkers();
                switchMenuVisible();
                btnBack.setVisibility(View.VISIBLE);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRouteActivity();
                switchMenuVisible();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                LatLng myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                        DEFAULT_ZOOM));
                unpinBottomSheet();
                btnBack.setVisibility(View.GONE);
            }
        });
    }

    private void setSearchETs(View view) {

        resultsLayout = view.findViewById(R.id.resultsLayout);
        searchBack = view.findViewById(R.id.searchBack);
        etAddress = view.findViewById(R.id.etAddress);
        btnMyLocation = view.findViewById(R.id.btnMyLocation);

        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unpinBottomSheet();
                closeSearchLayout();
            }
        });

        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.setVisible(true);
                confirmMarkerButton.setVisibility(View.VISIBLE);
                etAddress.setFocusable(true);
                etAddress.setFocusableInTouchMode(true);
                pinBottomSheet();
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                text.replaceAll("[|?*<\">+ \\[\\]/']", "");
                if (marker.isVisible()) {
                    resultsLayout.setVisibility(View.GONE);
                } else if (etAddress.isFocused() && text.length() != 0) {
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);

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
                            .setOrigin(new LatLng(-23.5666595, -46.6084084))
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

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng myLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                        DEFAULT_ZOOM));
            }
        });

        /*
        etFrom = view.findViewById(R.id.etFrom);
        etTo = view.findViewById(R.id.etTo);

        getCurrentAddress(etFrom);

        etFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.setVisible(true);
                confirmMarkerButton.setVisibility(View.VISIBLE);
                etFrom.setFocusable(true);
                etFrom.setFocusableInTouchMode(true);
                pinBottomSheet();
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
                if (marker.isVisible()) {
                    resultsLayout.setVisibility(View.GONE);
                } else if (etFrom.isFocused() && text.length() != 0) {
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);

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
                            .setOrigin(new LatLng(-23.5666595, -46.6084084))
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
                    Toast.makeText(getContext(), etFrom.getText(), Toast.LENGTH_SHORT).show();
                    marker.setVisible(false);
                    etTo.requestFocus();
                    return true;
                }
                return false;
            }
        });



        etTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getContext(), etTo.getText(), Toast.LENGTH_SHORT).show();
                    confirmRouteButton.setVisibility(View.VISIBLE);
                    pinBottomSheet();
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
                if (text.length() != 0) {
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);
                } else
                    resultsLayout.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         */

    }

    private void setSearchRecycler(View view) {
        searchRecycler = view.findViewById(R.id.searchRecycler);
        searchRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addresses = new ArrayList<>();
        addressAdapter = new AddressAdapter(getContext(), addresses);
        searchRecycler.setAdapter(addressAdapter);
    }

    private void pinBottomSheet() {
        BOTTOM_SHEET_DRAGGABLE = false;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (!BOTTOM_SHEET_DRAGGABLE) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void unpinBottomSheet() {
        BOTTOM_SHEET_DRAGGABLE = true;
    }

    private void switchMenuVisible() {
        if (relativeMenu.getVisibility() == View.VISIBLE) {
            relativeMenu.setVisibility(View.GONE);
        } else relativeMenu.setVisibility(View.VISIBLE);
    }

    private void addRiskMarkers() {
        Connection con = new Connection();
        con.getRiskPoints(map, bitmapDescriptorFromVector(getContext(), R.drawable.ic_marker_alert));
    }

    private void addAlertMarkers() {
        Connection con = new Connection();
        con.getAlerts(getContext(), map, bitmapDescriptorFromVector(getContext(), R.drawable.ic_marker_alert), 2021, 01, 01);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0, vectorDrawable.getIntrinsicWidth() , vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void adjustBottomDrawer(View view, CustomAdapterClickListener clickListener) {
        bottomNavigationContainer = view.findViewById(R.id.bottomNavigationContainer);
        emptyMarkersLayout = view.findViewById(R.id.emptyMarkersLayout);
        txtAddMarkers = view.findViewById(R.id.txtAddMarkers);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomNavigationContainer);
        bottomSheetBehavior.setPeekHeight(120);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    // do stuff when the drawer is expanded
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    // do stuff when the drawer is collapsed
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // do stuff during the actual drag event for example
                // animating a background color change based on the offset

                Connection con = new Connection();
                con.getMarkers(getContext(), bottomDrawerRecycler, emptyMarkersLayout, clickListener);

                // or for example hidding or showing a fab

                /*
                if (slideOffset > 0.2) {
                    if (fab.isShown()) {
                        fab.hide();
                    }
                } else if (slideOffset < 0.15) {
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }

                 */
            }
        });

        txtAddMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinBottomSheet();
                expandSearchLayout();
            }
        });

        bottomDrawerRecycler = view.findViewById(R.id.savedLocationsRecycler);
        bottomDrawerRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        Connection con = new Connection();
        con.getMarkers(getContext(), bottomDrawerRecycler, emptyMarkersLayout, this);
    }

    private void closeTopFrame() {
        topFrame.setVisibility(View.GONE);
    }

    private void openTopFrame() {
        topFrame.setVisibility(View.VISIBLE);
    }

    private void expandSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        mapToolbar.setVisibility(View.GONE);
    }

    private void closeSearchLayout() {
        searchLayout.setVisibility(View.GONE);
        etAddress.setFocusable(false);
        mapToolbar.setVisibility(View.VISIBLE);
        confirmMarkerButton.setVisibility(View.GONE);
        confirmRouteButton.setVisibility(View.GONE);
        map.setPadding(0, 0, 0, 0);
        unpinBottomSheet();
    }

    private void setConfirmMarkerButton(View view) {
        confirmMarkerButton = view.findViewById(R.id.confirmMarkerButton);
        confirmMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMarkerButton.setVisibility(View.GONE);
                unpinBottomSheet();
                marker.setVisible(false);
                LatLng latLng = getCoordinatesByAddress(etAddress.getText().toString());
                Connection con = new Connection();
                con.postMarker(getContext(), latLng.latitude, latLng.longitude, etAddress.getText().toString());
                unpinBottomSheet();
                closeSearchLayout();
            }
        });
    }

    private void setConfirmRouteButton(View view) {
        confirmRouteButton = view.findViewById(R.id.confirmRouteButton);
        confirmRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void centerRoute() {
        if (etFrom.getText().length() > 0 && etTo.getText().length() > 0) {

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(getCoordinatesByAddress(etFrom.getText().toString()));
            builder.include(getCoordinatesByAddress(etTo.getText().toString()));
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
            map.setPadding(0, ROUTE_TOP_ZOOM_PADDING, 0, ROUTE_BOTTOM_ZOOM_PADDING);
            map.animateCamera(cu);

        }
    }

    private void centerMarkers() {
        if (destinationMarker != null) {
            getLocationPermission();
            try {
                if (locationPermissionGranted) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
                    builder.include(destinationMarker.getPosition());
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
                    map.setPadding(0, ROUTE_TOP_ZOOM_PADDING, 0, ROUTE_BOTTOM_ZOOM_PADDING);
                    map.animateCamera(cu);
                }
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage(), e);
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            currentLocation = location;
        }
    };

    private Location getCurrentLocation() {

        getLocationPermission();
        try {
            if (locationPermissionGranted) {

                while (currentLocation == null) {
                    fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
                        @Override
                        public boolean isCancellationRequested() {
                            return false;
                        }

                        @NonNull
                        @Override
                        public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                            return null;
                        }
                    })
                            .addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        currentLocation = location;
                                        Log.d(TAG, "current location is set");
                                    }
                                    Log.d(TAG, "current location is null");
                                }
                            })
                            .addOnFailureListener((Activity) getContext(), new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Connection Failure");
                                }
                            });
                }
            }
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }

        return currentLocation;
    }

    private LatLng getCoordinatesByAddress(String address) {

        LatLng latLng = null;
        geocoder = new Geocoder(getContext());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                latLng = new LatLng(latitude, longitude);
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to get address");
            Log.e(TAG, e.getMessage());
        }

        return latLng;
    }

    private void startRouteActivity() {
        Intent intent = new Intent(getContext(), BingMapActivity.class);
        startActivity(intent);
    }

    private void saveLastCall(String key, long value) {
        SharedPreferences sp = getContext().getSharedPreferences("LAST_API_CALL", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value).apply();
    }

    public long getLastCall(String key) {

        SharedPreferences sp = getContext().getSharedPreferences("LAST_API_CALL", Context.MODE_PRIVATE);
        return sp.getLong(key, 0);

    }

    @Override
    public void onItemClick(View v, MarkersData markerData) {



        /*

        destinationMarker = map.addMarker(new MarkerOptions().position(userMarker.getLatLng()).title(userMarker.getName()));
        this.markerData = markerData;
        markerData.setClicked(true);
        expandSearchLayout();
        etTo.setText(userMarker.getRegion());
        pinBottomSheet();
        resultsLayout.setVisibility(View.GONE);
        confirmRouteButton.setVisibility(View.VISIBLE);
        centerRoute();

         */
    }

    @Override
    public void onItemLongClick(View v, MarkersData markerData, CustomAdapterClickListener clickListener, int position) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.marker_menu);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                v.setVisibility(View.GONE);
                Connection con = new Connection();
                con.deleteMarker(v.getContext(), markerData.getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                con.getMarkers(getContext(), bottomDrawerRecycler, emptyMarkersLayout, clickListener);
                return true;
            }
        });
    }


}