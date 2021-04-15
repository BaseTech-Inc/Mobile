package com.example.tupa_mobile.CurrentWeather;

public class CurrentWeather {
    private int last_updated_epoch, is_day;
    private String last_updated, wind_dir;
    private double temp_c, temp_f, wind_mph, wind_kph, wind_degree, pressure_mb, pressure_in, precip_mm, precip_in, humidity, cloud, feelslike_c, feelslike_f, vis_km, vis_miles, uv, gust_mph, gust_kph;
    private boolean day;
    private WeatherCondition condition;

    public int getLast_updated_epoch() {
        return last_updated_epoch;
    }

    public int getIs_day() {
        return is_day;
    }

    public String getLast_updated() {
        return last_updated;
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

    public double getWind_degree() {
        return wind_degree;
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

    public double getHumidity() {
        return humidity;
    }

    public double getCloud() {
        return cloud;
    }

    public double getFeelslike_c() {
        return feelslike_c;
    }

    public double getFeelslike_f() {
        return feelslike_f;
    }

    public double getVis_km() {
        return vis_km;
    }

    public double getVis_miles() {
        return vis_miles;
    }

    public double getUv() {
        return uv;
    }

    public double getGust_mph() {
        return gust_mph;
    }

    public double getGust_kph() {
        return gust_kph;
    }

    public boolean isDay() {
        return day;
    }

    public WeatherCondition getCondition() {
        return condition;
    }
}
