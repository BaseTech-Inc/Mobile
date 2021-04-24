package com.example.tupa_mobile.OpenWeather;

import java.util.ArrayList;

public class OpenDaily {

    private int dt, sunrise, sunset, moonrise, moonset, pressure, wind_deg;
    private double moon_phase, humidity, dew_point, wind_speed, clouds, pop, rain, uvi;
    private Temperature temp;
    private FeelsLike feels_like;
    private ArrayList<OpenWeatherCondition> weather;

    public int getDt() {
        return dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public int getMoonrise() {
        return moonrise;
    }

    public int getMoonset() {
        return moonset;
    }

    public int getPressure() {
        return pressure;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public double getMoon_phase() {
        return moon_phase;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getDew_point() {
        return dew_point;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public double getClouds() {
        return clouds;
    }

    public double getPop() {
        return pop;
    }

    public double getRain() {
        return rain;
    }

    public double getUvi() {
        return uvi;
    }

    public Temperature getTemp() {
        return temp;
    }

    public FeelsLike getFeels_like() {
        return feels_like;
    }

    public ArrayList<OpenWeatherCondition> getWeather() {
        return weather;
    }
}
