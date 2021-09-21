package com.example.tupa_mobile.Connections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.LoginActivity;
import com.example.tupa_mobile.Activities.MainActivity;
import com.example.tupa_mobile.Alerts.AlertData;
import com.example.tupa_mobile.Alerts.GetAlertResponse;
import com.example.tupa_mobile.GeoCoding.GeoCodingFeatures;
import com.example.tupa_mobile.GeoCoding.GeoCodingProperties;
import com.example.tupa_mobile.GeoCoding.GeoCodingResponse;
import com.example.tupa_mobile.Login.LoginResponse;
import com.example.tupa_mobile.Markers.CustomAdapterClickListener;
import com.example.tupa_mobile.Markers.GetMarkersResponse;
import com.example.tupa_mobile.Markers.MarkerAdapter;
import com.example.tupa_mobile.Markers.MarkersData;
import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.Route.Metadata;
import com.example.tupa_mobile.Route.RouteResponse;
import com.example.tupa_mobile.User.UserResponse;
import com.example.tupa_mobile.WeatherAPI.CurrentWeather;
import com.example.tupa_mobile.WeatherAPI.Day;
import com.example.tupa_mobile.WeatherAPI.Forecast;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;
import com.example.tupa_mobile.WeatherAPI.ForecastHour;
import com.example.tupa_mobile.WeatherAPI.ForecastHourAdapter;
import com.example.tupa_mobile.WeatherAPI.Weather;
import com.example.tupa_mobile.WeatherAPI.WeatherLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

    public static final String TAG = "Conexao";
    private ForecastDayAdapter adapter;
    private String weatherApiKey = "0253a3a9322d4be2a5b174410211404";
    private String openApiKey = "601d15071f30c2f71f3e59a0df95e8a1";
    private String routeApiKey = "5b3ce3597851110001cf6248c6376f7864c04686a9863d79cd62f1a6";
    private String accept = "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8";
    private String contentType = "application/json; charset=utf-8";
    private String position = "-23.6182683,-46.639479";
    private String lat = "-23.6182683";
    private String lon = "-46.639479";
    private ArrayList<ForecastDay> forecastDays;
    private ForecastDay forecastDay;
    private ArrayList<ForecastHour> forecastHours;
    private ForecastHourAdapter hourAdapter;
    private WeatherLocation location;
    private CurrentWeather currentWeather;
    private Weather weather;
    private Forecast forecast;
    private Day day;
    private OpenWeather openWeather;
    private ArrayList<OpenDaily> openDaily;
    private OpenDailyAdapter openAdapter;
    private GeoCodingResponse geoCodingResponse;
    private ArrayList<GeoCodingFeatures> geoCodingFeaturesArrayList;
    private GeoCodingFeatures geoCodingFeatures;
    private GeoCodingProperties geoCodingProperties;
    private ArrayList<MarkersData> markersData;
    private ArrayList<AlertData> alertsData;
    private String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOlsiQXV0aGVudGljYXRlZCIsIk1hbmFnZXIiXSwic3ViIjoibWFuYWdlckBsb2NhbGhvc3QiLCJqdGkiOiIyNDQ5ZGRkZi1lMWZjLTRhMGMtYjFmNS1hYjIxMzQxZTUyZTUiLCJlbWFpbCI6Im1hbmFnZXJAbG9jYWxob3N0IiwidWlkIjoiOWIxMzk0MGEtMTI0NS00YWM4LTg0MjEtZGExMzFkODEzNTc4IiwibmJmIjoxNjMyMjU3NjU2LCJleHAiOjE2MzIyNjEyNTYsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjUwMDEvIiwiYXVkIjoiVXNlciJ9.auhLHj9MrB67mhBOxTU8YD_A_PU9mSP-2EdrLKWT1h0";

    public void requestCurrentWeather(TextView currentLocation, TextView condition, TextView temperature, TextView humidity, TextView pressure, TextView wind, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getCurrentWeather(weatherApiKey, position, "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    weather = response.body();
                    location = weather.getLocation();
                    currentWeather = weather.getCurrentWeather();
                    String temp = Math.round(currentWeather.getTemp_c()) + "°";

                    currentLocation.setText(location.getName());
                    condition.setText(currentWeather.getCondition().getText());
                    temperature.setText(temp);
                    humidity.setText(Math.round(currentWeather.getHumidity()) + "%");
                    pressure.setText(Math.round(currentWeather.getPressure_mb()) + " mBar");
                    wind.setText(Math.round(currentWeather.getWind_kph()) + "Km/h");

                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void requestForecast(RecyclerView recyclerView, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getForecast(weatherApiKey, position, 3, "no", "no");

        call.enqueue(new Callback<Weather>() {

            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    weather = response.body();
                    forecast = weather.getForecast();
                    forecastDays = new ArrayList<>(forecast.getForecastDays());
                    adapter = new ForecastDayAdapter(context, forecastDays);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void requestOpenForecast(RecyclerView recyclerView, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<OpenWeather> call = API.getOpenForecastDaily(lat, lon, openApiKey, "metric");

        call.enqueue(new Callback<OpenWeather>() {

            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    openWeather = response.body();
                    openDaily = new ArrayList<>(openWeather.getDaily());
                    openAdapter = new OpenDailyAdapter(context, openDaily);
                    recyclerView.setAdapter(openAdapter);

                    openAdapter.getFilter().filter(null);

                }
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void requestHourForecast(RecyclerView recyclerView, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getForecast(weatherApiKey, position, 1,"no", "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    weather = response.body();
                    forecast = weather.getForecast();
                    forecastDays = new ArrayList<>(forecast.getForecastDays());
                    forecastDay = forecastDays.get(0);
                    forecastHours = new ArrayList<>(forecastDay.getHours());

                    hourAdapter = new ForecastHourAdapter(context, forecastHours);
                    recyclerView.setAdapter(hourAdapter);

                    hourAdapter.getFilter().filter(null);
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void postRoute(TextView txtResponse, Context context){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new SpeechRecognitionInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.openrouteservice.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        /*

        ArrayList<Double> doubles1 = new ArrayList<>();
        doubles1.add(8.681495);
        doubles1.add(49.41461);

        ArrayList<Double> doubles2 = new ArrayList<>();
        doubles1.add(8.687872);
        doubles1.add(49.420318);

        ArrayList<ArrayList> coordinatesArrayList = new ArrayList<>();

        coordinatesArrayList.add(doubles1);
        coordinatesArrayList.add(doubles2);

        Route route = new Route(coordinatesArrayList);

         */

        String finalRoute = "{\"coordinates\":[[8.681495,49.41461],[8.686507,49.41943],[8.687872,49.420318]]}";

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), finalRoute);

        Call<RouteResponse> call = API.postRoute(body);

        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {

                Log.d("response", String.valueOf(response));
                Log.d("body", String.valueOf(response.body()));

                RouteResponse routeResponse = response.body();
                Metadata metadata = routeResponse.getMetadata();
                Log.d("res", metadata.getAttribution());

            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {

                Log.e(TAG, String.valueOf(t));

            }
        });
    }

    public void requestCurrentAddress(Context context, EditText editText, double latitude, double longitude){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<GeoCodingResponse> call = API.getCurrentAddress(routeApiKey, longitude, latitude, 1);

        call.enqueue(new Callback<GeoCodingResponse>() {
            @Override
            public void onResponse(Call<GeoCodingResponse> call, Response<GeoCodingResponse> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    geoCodingResponse = response.body();
                    geoCodingFeaturesArrayList = geoCodingResponse.getFeatures();
                    geoCodingFeatures = geoCodingFeaturesArrayList.get(0);
                    geoCodingProperties = geoCodingFeatures.getProperties();
                    editText.setText(geoCodingProperties.getStreet());
                }

            }

            @Override
            public void onFailure(Call<GeoCodingResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }
    public void registerUser(Context context, String username, String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<UserResponse> call = api.postUser(username, email, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d(TAG, response.message());
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.isSuccessful()));
                    return;
                }
                Intent it = new Intent(context, LoginActivity.class);
                ((Activity)context).startActivity(it);
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    public void loginUser(Context context, String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<LoginResponse> call = api.postLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Intent it = new Intent(context, MainActivity.class);
                    ((Activity)context).startActivity(it);
                    ((Activity)context).finish();
                }else{
                    Toast.makeText(((Activity)context), "Não bro, não é assim 😭", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    public void getMarkers(Context context, RecyclerView bottomDrawerRecycler, ViewGroup emptyMarkersLayout, CustomAdapterClickListener clickListener, String userId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetMarkersResponse> call = api.getMarkers("Bearer " + access_token, userId);

        call.enqueue(new Callback<GetMarkersResponse>() {
            @Override
            public void onResponse(Call<GetMarkersResponse> call, Response<GetMarkersResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetMarkersResponse markersResponse = response.body();
                if (markersResponse.getData() == null || markersResponse.getData().size() < 1){
                    emptyMarkersLayout.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Data attribute is null or empty");
                    return;
                }
                markersData = markersResponse.getData();
                MarkerAdapter markerAdapter = new MarkerAdapter(context, markersData, clickListener);
                bottomDrawerRecycler.setAdapter(markerAdapter);
            }

            @Override
            public void onFailure(Call<GetMarkersResponse> call, Throwable t) {
                Log.e(TAG, "Marker connection failed");
                emptyMarkersLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void getAlerts(GoogleMap map, int year, int month, int day){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetAlertResponse> call = api.getAlerts("Bearer " + access_token, year, month, day);

        call.enqueue(new Callback<GetAlertResponse>() {
            @Override
            public void onResponse(Call<GetAlertResponse> call, Response<GetAlertResponse> response) {

                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetAlertResponse getAlertResponse = response.body();
                if (getAlertResponse == null){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                alertsData = getAlertResponse.getData();
                for(AlertData alertData : alertsData){
                    builder.include(new LatLng(alertData.getPonto().getLatitude(), alertData.getPonto().getLongitude()));
                    map.addMarker(new MarkerOptions().position(new LatLng(alertData.getPonto().getLatitude(), alertData.getPonto().getLongitude())));
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                map.animateCamera(cu);
            }

            @Override
            public void onFailure(Call<GetAlertResponse> call, Throwable t) {

            }
        });
    }
}
