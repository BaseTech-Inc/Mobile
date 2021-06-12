package com.example.tupa_mobile.WeatherAPI;

public class WeatherLocation {

    private String name, region, country, tz_id, localtime;
    private double lat, lon;
    private long localtime_epoch;

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getTz_id() {
        return tz_id;
    }

    public String getLocaltime() {
        return localtime;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public long getLocaltime_epoch() {
        return localtime_epoch;
    }
}
