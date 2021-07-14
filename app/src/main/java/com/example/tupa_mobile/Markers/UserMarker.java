package com.example.tupa_mobile.Markers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class UserMarker {

    private int type;
    private String name, region;
    private LatLng latLng;
    private GoogleMap map;
    private boolean clicked = false;

    public UserMarker(int type, String name, String region, GoogleMap map, double latitude, double longitude) {
        this.type = type;
        this.name = name;
        this.region = region;
        this.map = map;
        this.latLng = new LatLng(latitude, longitude);
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public GoogleMap getMap() {
        return map;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }
}
