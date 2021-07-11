package com.example.tupa_mobile.Connections;

import com.example.tupa_mobile.GeoCoding.GeoCodingResponse;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.Route.Route;
import com.example.tupa_mobile.Route.RouteResponse;
import com.example.tupa_mobile.WeatherAPI.Weather;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

/*

    @POST("directions/driving-car/json")
    Call<RouteResponse> postRoute(
            @Body String route
    );

 */


    @POST("directions/driving-car/json")
    Call<RouteResponse> postRoute(
            @Body RequestBody route
    );

    @GET("reverse")
    Call<GeoCodingResponse> getCurrentAddress(
            @Query("api_key") String apiKey,
            @Query("point.lon") double longitude,
            @Query("point.lat") double latitude,
            @Query("boundary.circle.radius") double radius
    );
}
