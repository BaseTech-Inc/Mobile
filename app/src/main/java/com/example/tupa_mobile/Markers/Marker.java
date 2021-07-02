package com.example.tupa_mobile.Markers;

public class Marker {

    private int type;
    private String name;

    public Marker(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
