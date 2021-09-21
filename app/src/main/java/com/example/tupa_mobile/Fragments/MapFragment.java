package com.example.tupa_mobile.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.NotificationActivity;
import com.example.tupa_mobile.Address.Address;
import com.example.tupa_mobile.Address.AddressAdapter;
import com.example.tupa_mobile.Alerts.AlertData;
import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.Markers.CustomAdapterClickListener;
import com.example.tupa_mobile.Markers.MarkersData;
import com.example.tupa_mobile.Markers.UserMarker;
import com.example.tupa_mobile.Markers.MarkerAdapter;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, CustomAdapterClickListener {

    private static final String TAG = Localization.class.getSimpleName();
    public static final int ROUTE_TOP_ZOOM_PADDING = 1000;
    public static final int FASTEST_REQUEST_INTERVAL = 5;
    public static final int REQUEST_INTERVAL = 30;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int DEFAULT_ZOOM = 15;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int ROUTE_BOTTOM_ZOOM_PADDING = 300;
    private boolean BOTTOM_SHEET_DRAGGABLE = true;

    private LinearLayout bottomNavigationContainer;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText etFrom, etTo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Button confirmRouteButton;
    private ImageButton searchBack, confirmMarkerButton, btnSearch, btnNotification, btnRiskAreas, btnAlerts;
    private ViewGroup searchLayout, resultsLayout, mapToolbar;
    private Toolbar toolbar;
    private MenuItem searchItem, notificationItem;
    private ViewGroup mapFrame, emptyMarkersLayout;
    private ArrayList<UserMarker> userMarkers;
    private ArrayList<Address> addresses;
    private MarkerAdapter markerAdapter;
    private AddressAdapter addressAdapter;
    private RecyclerView bottomDrawerRecycler, searchRecycler;
    private GoogleMap map;
    private Marker marker, destinationMarker;
    private LatLng currentLocation;
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

        adjustBottomDrawer(view);
        setCloseSearchButton(view);
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
        /*

        LatLng lugar = new LatLng(-100, 100);
        LatLng lugar2 = new LatLng(-4, 4);

        marker = map.addMarker(new MarkerOptions()
                .position(lugar)
                .title("Marcador")
        );

        map.addMarker(new MarkerOptions()
                .position(lugar2)
                .visible(true)
                .zIndex(1)
                .rotation(0)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.googleg_disabled_color_18))
                .alpha(1)
                .draggable(true)
                .title("Título")
                .snippet("Subtítulo"));

         */

        map.setOnMapClickListener(this);
        map.setOnCameraIdleListener(this);
        map.setOnCameraMoveStartedListener(this);
        map.setOnCameraMoveListener(this);
        map.setOnCameraMoveCanceledListener(this);

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
                locationResult.addOnCompleteListener( getActivity(), new OnCompleteListener<Location>() {

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
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions((Activity)getContext(),
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

    private Marker createMarker(){
        marker = map.addMarker(new MarkerOptions().position(defaultLocation));
        marker.setVisible(false);
        return marker;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Criado agora"));
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
        LatLng midLatLng = map.getCameraPosition().target;
        if (marker!=null) marker.setPosition(midLatLng);
        else Log.d("TAG","UserMarker is null");

        Calendar currentCall = Calendar.getInstance();

        if(marker.isVisible()){

            if(getLastCall("LAST_API_CALL") == 0){
                geocoder = new Geocoder(getContext());
                try{
                    List<android.location.Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    String street = addresses.get(0).getThoroughfare();
                    String stNumber = addresses.get(0).getSubThoroughfare();
                    etFrom.setText(String.format("%s, %s", street, stNumber));
                }
                catch (Exception e){
                    etFrom.setText("Unable to get address");
                    Log.e(TAG, e.getMessage());
                }
                saveLastCall("LAST_API_CALL", currentCall.getTimeInMillis());
            }
            else if(getLastCall("LAST_API_CALL") + 4000 < currentCall.getTimeInMillis()){
                geocoder = new Geocoder(getContext());
                try{
                    List<android.location.Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    String street = addresses.get(0).getThoroughfare();
                    String stNumber = addresses.get(0).getSubThoroughfare();
                    etFrom.setText(String.format("%s, %s", street, stNumber));
                }
                catch (Exception e){
                    etFrom.setText("Unable to get address");
                    Log.e(TAG, e.getMessage());
                }
                saveLastCall("LAST_API_CALL", currentCall.getTimeInMillis());
            }

        }


    }

    @Override
    public void onCameraMoveCanceled() {

    }

    private void getCurrentAddress(EditText et){

        getLocationPermission();
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener( getActivity(), new OnCompleteListener<Location>() {

                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                geocoder = new Geocoder(getContext());
                                try{
                                    List<android.location.Address> addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
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

    private void setToolbar(View view){
        mapToolbar = view.findViewById(R.id.map_toolbar);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnNotification = view.findViewById(R.id.btnNotification);
        btnRiskAreas = view.findViewById(R.id.btnRiskAreas);
        btnAlerts = view.findViewById(R.id.btnAlerts);

        btnRiskAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinBottomSheet();
                addRiskMarkers();
            }
        });

        btnAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinBottomSheet();
                addAlertMarkers();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandSearchLayout();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotificationActivity();
            }
        });
    }

    private void setSearchETs(View view) {
        resultsLayout = view.findViewById(R.id.resultsLayout);
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
                if(marker.isVisible()){
                    resultsLayout.setVisibility(View.GONE);
                }
                else if(etFrom.isFocused() && text.length() != 0){
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
                if(text.length() != 0){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);
                }
                else
                    resultsLayout.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setSearchRecycler(View view) {
        searchRecycler = view.findViewById(R.id.searchRecycler);
        searchRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addresses = new ArrayList<>();
        addressAdapter = new AddressAdapter(getContext(), addresses);
        searchRecycler.setAdapter(addressAdapter);
        fillAddresses();
    }

    private void pinBottomSheet(){
        BOTTOM_SHEET_DRAGGABLE = false;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (!BOTTOM_SHEET_DRAGGABLE){
                    if(newState == BottomSheetBehavior.STATE_DRAGGING){
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void unpinBottomSheet(){
        BOTTOM_SHEET_DRAGGABLE = true;
    }

    private void addRiskMarkers(){
        map.addMarker(new MarkerOptions().position(new LatLng(-23.6182683, -46.639479)));
        map.addMarker(new MarkerOptions().position(new LatLng(-24.6182683, -47.639479)));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(-23.6182683, -46.639479));
        builder.include(new LatLng(-24.6182683, -47.639479));
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        map.animateCamera(cu);
    }

    private void addAlertMarkers() {
        Connection con = new Connection();
        con.getAlerts(map,2021,9,21);
    }

    private void fillAddresses(){
        addresses.add(new Address("Alameda dos Guainumbis", "Planalto Paulista - SP", R.drawable.stormy));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
        searchItem = menu.findItem(R.id.searchItem);
        notificationItem = menu.findItem(R.id.notificationItem);
    }

    private void adjustBottomDrawer(View view) {
        bottomNavigationContainer = view.findViewById(R.id.bottomNavigationContainer);
        emptyMarkersLayout = view.findViewById(R.id.emptyMarkersLayout);
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

        bottomDrawerRecycler = view.findViewById(R.id.savedLocationsRecycler);
        bottomDrawerRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        Connection con = new Connection();
        con.getMarkers(getContext(), bottomDrawerRecycler, emptyMarkersLayout, this, "1");
    }

    private void expandSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        mapToolbar.setVisibility(View.GONE);
    }

    private void closeSearchLayout(){
        searchLayout.setVisibility(View.GONE);
        etFrom.setFocusable(false);
        mapToolbar.setVisibility(View.VISIBLE);
        confirmMarkerButton.setVisibility(View.GONE);
        confirmRouteButton.setVisibility(View.GONE);
        map.setPadding(0,0,0,0);
        unpinBottomSheet();
    }

    private void setCloseSearchButton(View view){
        searchBack = view.findViewById(R.id.searchBack);
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearchLayout();
                getDeviceLocation();
                etTo.setText("");
                if(map != null){
                    map.clear();
                }
                createMarker();
            }
        });
    }

    private void setConfirmMarkerButton(View view){
        confirmMarkerButton = view.findViewById(R.id.confirmMarkerButton);
        confirmMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMarkerButton.setVisibility(View.GONE);
                unpinBottomSheet();
                marker.setVisible(false);
                etTo.requestFocus();
            }
        });
    }

    private void setConfirmRouteButton(View view){
        confirmRouteButton = view.findViewById(R.id.confirmRouteButton);
        confirmRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        geocoder = new Geocoder(getContext());
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

    private void startNotificationActivity() {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        startActivity(intent);
    }

    private void createViews(){
        userMarkers.add(new UserMarker(R.drawable.clock_ilustraion_white_theme, "Home", "Av. Afonso Mariano", map, -23.61601597825001, -46.64259490181567));
        userMarkers.add(new UserMarker(R.drawable.stormy, "Work", "Av. Tiradentes, 769 - São Paulo", map, -23.530234576782785, -46.63209304604776));
        userMarkers.add(new UserMarker(R.drawable.configuration_icon_gray_dark_theme_dimmed, "School", "Rua Alberto Albertão", map,-23.614954301071563, -46.643689243094215));
    }

    private void saveLastCall(String key, long value){
        SharedPreferences sp = getContext().getSharedPreferences("LAST_API_CALL", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value).apply();
    }

    public long getLastCall(String key){

        SharedPreferences sp = getContext().getSharedPreferences("LAST_API_CALL", Context.MODE_PRIVATE);
        return sp.getLong(key, 0);

    }

    @Override
    public void onItemClick(View v, MarkersData markerData) {
        destinationMarker = map.addMarker(new MarkerOptions().position(userMarker.getLatLng()).title(userMarker.getName()));
        this.markerData = markerData;
        markerData.setClicked(true);
        expandSearchLayout();
        etTo.setText(userMarker.getRegion());
        pinBottomSheet();
        resultsLayout.setVisibility(View.GONE);
        confirmRouteButton.setVisibility(View.VISIBLE);
        centerRoute();
    }
}