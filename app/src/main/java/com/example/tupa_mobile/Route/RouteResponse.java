package com.example.tupa_mobile.Route;

import java.util.ArrayList;

public class RouteResponse {

    private ArrayList<RoutesInfo> routes;
    private ArrayList<Double> bbox;
    private Metadata metadata;

    public ArrayList<RoutesInfo> getRoutes() {
        return routes;
    }

    public ArrayList<Double> getBbox() {
        return bbox;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
