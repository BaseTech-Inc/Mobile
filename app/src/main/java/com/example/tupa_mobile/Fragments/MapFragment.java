package com.example.tupa_mobile.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.NotificationActivity;
import com.example.tupa_mobile.Address.Address;
import com.example.tupa_mobile.Address.AddressAdapter;
import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.Markers.Marker;
import com.example.tupa_mobile.Markers.MarkerAdapter;
import com.example.tupa_mobile.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Calendar;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener {

    private LinearLayout bottomNavigationContainer;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText etFrom, etTo;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageButton searchBack;
    private ViewGroup searchLayout, resultsLayout;
    private Toolbar toolbar;
    private MenuItem searchItem, notificationItem;
    private ViewGroup mapFrame;
    private ArrayList<Marker> markers;
    private ArrayList<Address> addresses;
    private MarkerAdapter markerAdapter;
    private AddressAdapter addressAdapter;
    private RecyclerView bottomDrawerRecycler, searchRecycler;
    private GoogleMap map;
    private com.google.android.gms.maps.model.Marker marker;
    private LatLng currentLocation;

    private static final String TAG = Localization.class.getSimpleName();

    private CameraPosition cameraPosition;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private double latitude, longitude;

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
        setHasOptionsMenu(true);
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        adjustToolbar(view);
        adjustBottomDrawer(view);
        setCloseSearchButton(view);
        setSearchETs(view);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setCompassEnabled(false);

        marker = map.addMarker(new MarkerOptions().position(defaultLocation));
        marker.setVisible(false);

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

    @Override
    public void onMapClick(LatLng latLng) {
        com.google.android.gms.maps.model.Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Criado agora"));
    }

    @Override
    public void onInfoWindowClick(@NonNull com.google.android.gms.maps.model.Marker marker) {
        Toast.makeText(getContext(), "Parabéns, você clickou em: " + String.format("Latitude",marker.getPosition().latitude) + String.format("Longitude",marker.getPosition().longitude), Toast.LENGTH_LONG).show();
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
        else Log.d("TAG","Marker is null");

        Calendar lastCall = Calendar.getInstance();
        lastCall.setTimeInMillis(lastCall.getTimeInMillis()-1000);
        Calendar currentCall = Calendar.getInstance();

        if(marker.isVisible()){
            if(currentCall.getTimeInMillis() > lastCall.getTimeInMillis() + 1000) {
                Connection con = new Connection();
                con.requestCurrentAddress(getContext(), etFrom, marker.getPosition().latitude, marker.getPosition().longitude);
                lastCall = currentCall;
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
                                Connection con = new Connection();
                                con.requestCurrentAddress(getContext(), et, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void setSearchETs(View view) {
        resultsLayout = view.findViewById(R.id.resultsLayout);
        etFrom = view.findViewById(R.id.etFrom);

        getCurrentAddress(etFrom);

        etFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.setVisible(true);
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
                /*
                String text = s.toString();
                text.replaceAll("[|?*<\">+ \\[\\]/']", "");
                if(text.length() != 0){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);
                }
                else
                    resultsLayout.setVisibility(View.GONE);

                 */
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
                    return true;
                }
                return false;
            }
        });

        etTo = view.findViewById(R.id.etTo);
        etTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getContext(), etTo.getText(), Toast.LENGTH_SHORT).show();
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

    private void fillAddresses(){
        addresses.add(new Address("Alameda dos Guainumbis", "Planalto Paulista - SP", R.drawable.stormy));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
        addresses.add(new Address("Avenida Tiradentes", "Bom Retiro - SP", R.drawable.day_sunny));
    }

    private void adjustBottomDrawer(View view) {
        bottomNavigationContainer = view.findViewById(R.id.bottomNavigationContainer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomNavigationContainer);
        bottomSheetBehavior.setPeekHeight(100);
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
        markers = new ArrayList<>();
        markerAdapter = new MarkerAdapter(view.getContext(), markers);
        bottomDrawerRecycler.setAdapter(markerAdapter);
        createViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);
        searchItem = menu.findItem(R.id.searchItem);
        notificationItem = menu.findItem(R.id.notificationItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()){
            case R.id.searchItem:
                expandSearchLayout();
                break;
            case R.id.notificationItem:
                startNotificationActivity();
                break;
        }
        return true;
    }

    private void adjustToolbar(View view){
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse);
        mCollapsingToolbarLayout.setTitleEnabled(false);
        toolbar = view.findViewById(R.id.mainToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mapa");
    }


    private void expandSearchLayout() {
        searchLayout.setVisibility(View.VISIBLE);
        toolbar.setContentInsetsAbsolute(0,0);
        searchItem.setVisible(false);
        notificationItem.setVisible(false);
    }

    private void closeSearchLayout(){
        searchLayout.setVisibility(View.GONE);
        toolbar.setContentInsetsAbsolute(44,44);
        searchItem.setVisible(true);
        notificationItem.setVisible(true);
        marker.setVisible(false);
    }

    public void setCloseSearchButton(View view){
        searchBack = view.findViewById(R.id.searchBack);
        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearchLayout();
            }
        });
    }

    private void startNotificationActivity() {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        startActivity(intent);
    }

    private void createViews(){
        markers.add(new Marker(R.drawable.clock_ilustraion_white_theme, "Home", "Av. Afonso Mariano"));
        markers.add(new Marker(R.drawable.stormy, "Work", "Av. Tiradentes"));
        markers.add(new Marker(R.drawable.configuration_icon_gray_dark_theme_dimmed, "School", "Rua Alberto Albertão"));
    }
}