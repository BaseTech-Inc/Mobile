package com.example.tupa_mobile.OpenWeather;

import android.text.format.DateUtils;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class OpenDaily {

    private int sunrise, sunset, moonrise, moonset, pressure, wind_deg;
    private long dt;
    private double moon_phase, humidity, dew_point, wind_speed, clouds, pop, rain, uvi;
    private Temperature temp;
    private FeelsLike feels_like;
    private ArrayList<OpenWeatherCondition> weather;
    private boolean expandable;

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public long getDt() {
        return dt;
    }

    public String getDtFormatted(){

        long dt = getDt()*1000;
        Timestamp timestamp = new Timestamp(dt);
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE");
        String strDate = dateFormat.format(date);

        if (DateUtils.isToday(dt)){
            return "Today";
        }

        return strDate;
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
