package com.example.tupa_mobile.CurrentWeather;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("current")
    private CurrentWeather currentWeather;

    private WeatherLocation location;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public WeatherLocation getLocation() {return location;}
}
