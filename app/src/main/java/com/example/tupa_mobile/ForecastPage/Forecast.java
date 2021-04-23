package com.example.tupa_mobile.ForecastPage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("forecastday")
    List<ForecastDay> forecastDays;

    public List<ForecastDay> getForecastDays() {
        return forecastDays;
    }
}
