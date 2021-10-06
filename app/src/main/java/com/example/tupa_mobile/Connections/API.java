package com.example.tupa_mobile.Connections;

import com.example.tupa_mobile.Alerts.GetAlerBairroResponse;
import com.example.tupa_mobile.Alerts.GetAlertResponse;
import com.example.tupa_mobile.GeoCoding.GeoCodingResponse;
import com.example.tupa_mobile.Login.LoginResponse;
import com.example.tupa_mobile.Markers.GetMarkersResponse;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.Rides.GetRidesResponse;
import com.example.tupa_mobile.Route.RouteResponse;
import com.example.tupa_mobile.User.UserResponse;
import com.example.tupa_mobile.WeatherAPI.Weather;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

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

    @POST("/api/Account/register")
    Call<UserResponse> postUser(
            @Query ("username") String username,
            @Query ("email") String email,
            @Query ("password") String password
    );

    @POST("/api/Account/login")
    Call<LoginResponse> postLogin(
            @Query ("email") String email,
            @Query ("password") String password
    );

    @GET("/api/v1/Marcadores")
    Call<GetMarkersResponse> getMarkers(
            @Header("Authorization") String access_token,
            @Query("UserId") String userId
    );

    @GET("/api/v1/Alertas")
    Call<GetAlertResponse> getAlerts(
            @Header("Authorization") String access_token,
            @Query("year") int year,
            @Query("month") int month,
            @Query("day") int day
    );

    @GET("/api/v1/Alertas/Bairro")
    Call <GetAlerBairroResponse> getAlertBairro(
            @Header("Authorization") String key,
            @Query("year") int year,
            @Query("month") int month,
            @Query("day") int day,
            @Query("districts") String districts
    );
    @GET("api/v1/HistoricoUsuario")
    Call <GetRidesResponse> getRides(
            @Header("Authorization") String key
    );


}
