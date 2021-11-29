package com.example.tupa_mobile.Location;



public class Location {

    private String name, currentDesc;
    private Double temp;

    public Location(String name, String currentDesc, Double temp) {
        this.name = name;
        this.currentDesc = currentDesc;
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public String getCurrentDesc() {
        return currentDesc;
    }

    public Double getTemp() {
        return temp;
    }
}
