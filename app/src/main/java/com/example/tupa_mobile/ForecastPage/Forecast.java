package com.example.tupa_mobile.ForecastPage;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @SerializedName("forecastday")
    ArrayList<ForecastDay> forecastDays;

    public ArrayList<ForecastDay> getForecastDays() {
        return forecastDays;
    }
}
