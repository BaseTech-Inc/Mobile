package com.example.tupa_mobile.Markers;

import android.view.View;

import com.google.android.gms.maps.model.Marker;

public interface CustomAdapterClickListener {
    boolean onMarkerClick(Marker marker);

    public void onItemClick(View v, MarkersData userMarker);
    public void onItemLongClick(View v, MarkersData userMarker, CustomAdapterClickListener clickListener, int position);
}