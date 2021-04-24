package com.example.tupa_mobile.WeatherAPI;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForecastDay {

    private String date;
    private int date_epoch;
    private Day day;
    private Astro astro;
    @SerializedName("hour")
    private ArrayList<ForecastHour> hours;

    public String getDate() {
        return date;
    }

    public int getDate_epoch() {
        return date_epoch;
    }

    public Day getDay() {
        return day;
    }

    public Astro getAstro() {
        return astro;
    }

    public ArrayList<ForecastHour> getHours() {
        return hours;
    }
}
