package com.example.tupa_mobile.GeoCoding;

import java.util.ArrayList;

public class GeoCodingResponse {

    private GeoCoding geocoding;
    private String type;
    private ArrayList<GeoCodingFeatures> features;
    private ArrayList<Double> bbox;

    public GeoCoding getGeocoding() {
        return geocoding;
    }

    public String getType() {
        return type;
    }

    public ArrayList<GeoCodingFeatures> getFeatures() {
        return features;
    }

    public ArrayList<Double> getBbox() {
        return bbox;
    }
}
