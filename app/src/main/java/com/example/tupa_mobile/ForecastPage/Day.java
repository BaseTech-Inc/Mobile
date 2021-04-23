package com.example.tupa_mobile.ForecastPage;

import com.example.tupa_mobile.CurrentWeather.WeatherCondition;

public class Day {

    private double maxtemp_c, maxtemp_f, mintemp_c, mintemp_f, avgtemp_c, avgtemp_f, maxwind_mph, maxwind_kph, totalprecip_mm, totalprecip_in, avgvis_km, avgvis_miles, uv;
    private int avghumidity, daily_will_it_rain, daily_will_it_snow;
    private String daily_chance_of_rain, daily_chance_of_snow;
    private WeatherCondition weatherCondition;

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public double getMaxtemp_f() {
        return maxtemp_f;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public double getMintemp_f() {
        return mintemp_f;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public double getAvgtemp_f() {
        return avgtemp_f;
    }

    public double getMaxwind_mph() {
        return maxwind_mph;
    }

    public double getMaxwind_kph() {
        return maxwind_kph;
    }

    public double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public double getTotalprecip_in() {
        return totalprecip_in;
    }

    public double getAvgvis_km() {
        return avgvis_km;
    }

    public double getAvgvis_miles() {
        return avgvis_miles;
    }

    public double getUv() {
        return uv;
    }

    public int getAvghumidity() {
        return avghumidity;
    }

    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    public int getDaily_will_it_snow() {
        return daily_will_it_snow;
    }

    public String getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    public String getDaily_chance_of_snow() {
        return daily_chance_of_snow;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }
}
