package com.example.tupa_mobile.Connections;

import com.example.tupa_mobile.CurrentWeather.Weather;
import com.example.tupa_mobile.ForecastPage.ForecastDay;

import java.util.List;

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
}
