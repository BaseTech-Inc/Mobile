package com.example.tupa_mobile.WeatherAPI;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {

    @SerializedName("forecastday")
    ArrayList<ForecastDay> forecastDays;

    public ArrayList<ForecastDay> getForecastDays() {
        return forecastDays;
    }
}
