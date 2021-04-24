package com.example.tupa_mobile.Connections;

import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.WeatherAPI.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("current.json")
    Call<Weather> getCurrentWeather(
            @Query("key") String key,
            @Query("q") String position,
            @Query("aqi") String aqi
    );

    @GET("forecast.json")
    Call<Weather> getForecast(
            @Query("key") String key,
            @Query("q") String position,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );

    @GET("data/2.5/onecall")
    Call<OpenWeather> getOpenForecastDaily(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String key,
            @Query("exclude") String exclude,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("data/2.5/onecall")
    Call<OpenWeather> getOpenForecastDaily(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String key,
            @Query("units") String units
    );
}
