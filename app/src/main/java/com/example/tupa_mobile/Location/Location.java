package com.example.tupa_mobile.Location;


import com.example.tupa_mobile.Mainn.Mainn;
import com.example.tupa_mobile.Weather.Weather;
import com.google.android.gms.maps.model.LatLng;

public class Location {
    private LatLng coord;
    private Weather weather;
    private Mainn mainn;

    public Location(LatLng coord, Weather weather, Mainn mainn) {
        this.coord = coord;
        this.weather = weather;
        this.mainn = mainn;
    }

    public LatLng getCoord() {
        return coord;
    }

    public Weather getWeather() {
        return weather;
    }

    public Mainn getMainn() {
        return mainn;
    }






}
