package com.example.tupa_mobile.CurrentWeather;

import com.example.tupa_mobile.ForecastPage.Forecast;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("current")
    private CurrentWeather currentWeather;

    private WeatherLocation location;

    @SerializedName("forecast")
    private Forecast forecast;

    public Forecast getForecast() {return forecast;}

    public CurrentWeather getCurrentWeather() {return currentWeather;}

    public WeatherLocation getLocation() {return location;}
}
