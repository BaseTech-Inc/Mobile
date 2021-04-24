package com.example.tupa_mobile.OpenWeather;

import java.util.ArrayList;

public class OpenWeather {

    private double lat, lon;
    private int timezone_offset;
    private String timezone;
    private OpenCurrent current;
    private ArrayList<OpenMinutes> minutely;
    private ArrayList<OpenHour> hourly;
    private ArrayList<OpenDaily> daily;
    private ArrayList<OpenAlerts> alerts;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public String getTimezone() {
        return timezone;
    }

    public OpenCurrent getCurrent() {
        return current;
    }

    public ArrayList<OpenMinutes> getMinutely() {
        return minutely;
    }

    public ArrayList<OpenHour> getHourly() {
        return hourly;
    }

    public ArrayList<OpenDaily> getDaily() {
        return daily;
    }

    public ArrayList<OpenAlerts> getAlerts() {
        return alerts;
    }
}
