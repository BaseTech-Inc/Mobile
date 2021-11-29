package com.example.tupa_mobile.WeatherAPI;

import java.util.ArrayList;

public class ForecastHour {

    private int is_day, wind_degree, humidity, cloud, will_it_rain, chance_of_rain, will_it_snow, chance_of_snow;
    private long time_epoch;
    private String time, wind_dir;
    private double temp_c, temp_f, wind_mph, wind_kph, pressure_mb, pressure_in, precip_mm, precip_in, feelslike_c, feelslike_f, windchill_c, windchill_f, heatindex_c, heatindex_f, dewpoint_c, dewpoint_f, vis_km, vis_miles, gust_mph, gust_kph, uv;
    private WeatherCondition condition;

    public long getTime_epoch() {
        return time_epoch*1000;
    }

    public int getIs_day() {
        return is_day;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public int getWill_it_rain() {
        return will_it_rain;
    }

    public int getChance_of_rain() {
        return chance_of_rain;
    }

    public int getWill_it_snow() {
        return will_it_snow;
    }

    public int getChance_of_snow() {
        return chance_of_snow;
    }

    public String getTime() {
        return time;
    }

    public String getTimeFormatted(){
        String time = getTime().split(" ")[1];
        return time;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public double getTemp_f() {
        return temp_f;
    }

    public double getWind_mph() {
        return wind_mph;
    }

    public double getWind_kph() {
        return wind_kph;
    }

    public double getPressure_mb() {
        return pressure_mb;
    }

    public double getPressure_in() {
        return pressure_in;
    }

    public double getPrecip_mm() {
        return precip_mm;
    }

    public double getPrecip_in() {
        return precip_in;
    }

    public double getFeelslike_c() {
        return feelslike_c;
    }

    public double getFeelslike_f() {
        return feelslike_f;
    }

    public double getWindchill_c() {
        return windchill_c;
    }

    public double getWindchill_f() {
        return windchill_f;
    }

    public double getHeatindex_c() {
        return heatindex_c;
    }

    public double getHeatindex_f() {
        return heatindex_f;
    }

    public double getDewpoint_c() {
        return dewpoint_c;
    }

    public double getDewpoint_f() {
        return dewpoint_f;
    }

    public double getVis_km() {
        return vis_km;
    }

    public double getVis_miles() {
        return vis_miles;
    }

    public double getGust_mph() {
        return gust_mph;
    }

    public double getGust_kph() {
        return gust_kph;
    }

    public double getUv() {
        return uv;
    }

    public WeatherCondition getCondition() {
        return condition;
    }
}
