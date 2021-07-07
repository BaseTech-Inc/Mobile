package com.example.tupa_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

import com.example.tupa_mobile.Activities.NotificationActivity;
import com.example.tupa_mobile.Address.Address;
import com.example.tupa_mobile.Address.AddressAdapter;
import com.example.tupa_mobile.Location.Localization;
import com.example.tupa_mobile.Markers.Marker;
import com.example.tupa_mobile.Markers.MarkerAdapter;
import com.example.tupa_mobile.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener{

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

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        searchBack = view.findViewById(R.id.searchBack);

        adjustToolbar(view);
        adjustBottomDrawer(view);
        setCloseSearchButton(view);
        setSearchETs(view);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

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

        map.setOnMapClickListener(this);

        map.moveCamera(CameraUpdateFactory.newLatLng(lugar));

        adjustMap();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        map.addMarker(new MarkerOptions().position(latLng).title("Criado agora"));
    }

    @Override
    public void onInfoWindowClick(@NonNull com.google.android.gms.maps.model.Marker marker) {
        Toast.makeText(getContext(), "Parabéns, você clickou em: " + String.format("Latitude",marker.getPosition().latitude) + String.format("Longitude",marker.getPosition().longitude), Toast.LENGTH_LONG).show();
    }

    private void adjustMap(){
        Localization localization = new Localization();
        localization.refreshLocation(getContext(), map);
    }

    private void setSearchETs(View view) {
        resultsLayout = view.findViewById(R.id.resultsLayout);
        etFrom = view.findViewById(R.id.etFrom);
        etFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);
                }
                else
                    resultsLayout.setVisibility(View.GONE);
            }
        });

        etTo = view.findViewById(R.id.etTo);
        etTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    resultsLayout.setVisibility(View.VISIBLE);
                    setSearchRecycler(view);
                }
                else
                    resultsLayout.setVisibility(View.GONE);
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
        // TODO Add your menu entries here
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