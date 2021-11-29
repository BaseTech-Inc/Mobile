package com.example.tupa_mobile.WeatherAPI;

import java.util.ArrayList;

public class HourForecast {

    private ArrayList<HourWeather> weather;
    private double temp;
    private long dt;

    public ArrayList<HourWeather> getWeather() {
        return weather;
    }

    public double getTemp() {
        return temp;
    }

    public long getDt() {
        return dt;
    }
}
