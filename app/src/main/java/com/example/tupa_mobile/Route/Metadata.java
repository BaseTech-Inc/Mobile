package com.example.tupa_mobile.Route;

public class Metadata {

    private String attribution, service;
    private double timestamp;
    private RouteQuery query;
    private RouteEngine engine;

    public String getAttribution() {
        return attribution;
    }

    public String getService() {
        return service;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public RouteQuery getQuery() {
        return query;
    }

    public RouteEngine getEngine() {
        return engine;
    }
}
