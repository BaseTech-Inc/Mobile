package com.example.tupa_mobile.Markers;

public class Marker {

    private int type;
    private String name, region;

    public Marker(int type, String name, String region) {
        this.type = type;
        this.name = name;
        this.region = region;
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
