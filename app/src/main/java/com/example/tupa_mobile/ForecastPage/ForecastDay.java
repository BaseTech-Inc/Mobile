package com.example.tupa_mobile.ForecastPage;

public class ForecastDay {

    private String date;
    private int date_epoch;
    private Day day;
    private Astro astro;
    private ForecastHour forecastHour;

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

    public ForecastHour getForecastHour() {
        return forecastHour;
    }
}
