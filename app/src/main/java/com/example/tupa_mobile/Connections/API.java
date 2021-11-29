package com.example.tupa_mobile.Connections;

import com.example.tupa_mobile.Alerts.GetAlerBairroResponse;
import com.example.tupa_mobile.Alerts.GetAlertResponse;
import com.example.tupa_mobile.Location.GetLocationResponse;
import com.example.tupa_mobile.Passwords.ChangePasswordResponse;
import com.example.tupa_mobile.GeoCoding.GeoCodingResponse;
import com.example.tupa_mobile.Login.LoginResponse;
import com.example.tupa_mobile.Markers.GetMarkersResponse;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.Passwords.ResetPasswordResponse;
import com.example.tupa_mobile.Profile.AccountResponse;
import com.example.tupa_mobile.Profile.ImageResponse;
import com.example.tupa_mobile.Profile.ProfileResponse;
import com.example.tupa_mobile.Profile.PutProfileResponse;
import com.example.tupa_mobile.Rides.GetRidesResponse;
import com.example.tupa_mobile.RiskPoints.RiskPointResponse;
import com.example.tupa_mobile.Route.RouteResponse;
import com.example.tupa_mobile.User.UserResponse;
import com.example.tupa_mobile.WeatherAPI.ForecastHourResponse;
import com.example.tupa_mobile.WeatherAPI.Weather;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
            @Header("Authorization") String access_token
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
    @GET("/api/v1/GooglePoints/decode-coordinates")
    Call<GetRidesResponse> getDecoCoor(
            @Header("Authorization") String key,
            @Query("encodedPoints") String encodedPoints
    );
    @GET("/api/v1/CurrentWeather/name")
    Call <GetLocationResponse> getLocation(
            @Header("Authorization") String key,
            @Query("street")    String street,
            @Query("district") String district,
            @Query("city") String city,
            @Query("state") String state
    );
    @GET("api/v1/HistoricoUsuario")
    Call <GetRidesResponse> getRides(
            @Header("Authorization") String key
    );

    @POST("/api/v1/Marcadores")
    Call<GetMarkersResponse> postMarker(
            @Header("Authorization") String key,
            @Query("Latitude") double lat,
            @Query("Longitude") double lng,
            @Query("Nome") String name
    );

    @DELETE("/api/v1/Marcadores")
    Call<GetMarkersResponse> deleteMarker(
            @Header("Authorization") String key,
            @Query("Id") String id
    );

    @GET("/api/v1/PontoRisco")
    Call<RiskPointResponse> getRiskPoints(
            @Header("Authorization") String key
    );
    @POST("/api/Account/generate-password-reset")
    Call<ResetPasswordResponse> postPassword(
            @Query ("email") String email
    );

    @POST("/api/Account/change-password/id")
    Call<ChangePasswordResponse> postChangePassword(
            @Header("Authorization") String access_token,
            @Query ("oldPassword") String oldPass,
            @Query ("newPassword") String newPass
    );

    @GET("/api/Account/basic-profile")
    Call<ProfileResponse> getProfile(
            @Header ("Authorization") String access_token
    );

    @PUT("/api/Account/basic-profile")
    Call<PutProfileResponse> putProfile(
            @Header ("Authorization") String access_token,
            @Query ("UserName") String UserName,
            @Query ("TipoUsuario") String TipoUsuario
    );

    @GET("/api/Account/image-profile")
    Call<ImageResponse> getImageProfile(
            @Header ("Authorization") String access_token
    );

    @PUT("/api/Account/image-profile")
    Call<ImageResponse> putImageProfile(
            @Header ("Authorization") String access_token,
            @Body String body
    );

    @DELETE("/api/Account")
    Call<AccountResponse> deleteAccount(
            @Header ("Authorization") String access_token
    );

    @GET("/data/2.5/onecall")
    Call<ForecastHourResponse> getHourForecast(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid,
            @Query("units") String units
    );
}
