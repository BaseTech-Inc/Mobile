package com.example.tupa_mobile.Connections;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.tupa_mobile.Markers.MarkerWindowAdapter;
import com.example.tupa_mobile.Markers.MarkersData;
import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.Rides.GetRidesResponse;
import com.example.tupa_mobile.Rides.RidesAdapter;
import com.example.tupa_mobile.RiskPoints.RiskPointData;
import com.example.tupa_mobile.RiskPoints.RiskPointResponse;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private SharedPreferences sp;
    private String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOlsiTWFuYWdlciIsIkF1dGhlbnRpY2F0ZWQiXSwic3ViIjoibWFuYWdlckBsb2NhbGhvc3QiLCJqdGkiOiI0NmUxZmRlMy03MTAwLTQ2ZDgtOTZlMS1iYmU4ZTk1MjQ4YWUiLCJlbWFpbCI6Im1hbmFnZXJAbG9jYWxob3N0IiwidWlkIjoiOTM2OTE0MzktMmRlOS00YWE3LThmOGMtNjVkMTU1NGZlZGEwIiwibmJmIjoxNjM4MTk0Mzk4LCJleHAiOjE2MzgyODA3OTgsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjUwMDEvIiwiYXVkIjoiVXNlciJ9.nbcGv0ljzHZwcub5JxZYIuMZa8wvi9Ptcg588qFZG2o";
    private boolean isRiskNotificationActive = false;
    private boolean isAlertNotificationActive = false;

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
                    sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

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

    public void getMarkers(Context context, RecyclerView bottomDrawerRecycler, ViewGroup emptyMarkersLayout, CustomAdapterClickListener clickListener){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetMarkersResponse> call = api.getMarkers("Bearer " + access_token);

        call.enqueue(new Callback<GetMarkersResponse>() {
            @Override
            public void onResponse(Call<GetMarkersResponse> call, Response<GetMarkersResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    emptyMarkersLayout.setVisibility(View.VISIBLE);
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

    public void getAlerts(Context context, GoogleMap map, BitmapDescriptor bitmapDescriptor, int year, int month, int day){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net/")
                .client(okHttpClient)
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
                boolean isNull = true;
                alertsData = getAlertResponse.getData();
                for(AlertData alertData : alertsData){
                    if (alertData.getPonto() != null){
                        builder.include(new LatLng(alertData.getPonto().getLatitude(), alertData.getPonto().getLongitude()));
                        map.addMarker(new MarkerOptions().position(new LatLng(alertData.getPonto().getLatitude(), alertData.getPonto().getLongitude())).icon(bitmapDescriptor).title(alertData.getDescricao()));
                        isNull = false;
                    }
                }
                if (isNull){
                    Toast.makeText(context, "Não há dados de alagamento em sua área", Toast.LENGTH_SHORT);
                }
                else{
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                    map.animateCamera(cu);
                }
            }

            @Override
            public void onFailure(Call<GetAlertResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getAlertsList(Context context, int year, int month, int day, double longitude, double latitude, int ALERT_ID, String ALERT_CHANNEL_ID, NotificationManager notificationManager){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net/")
                .client(okHttpClient)
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
                alertsData = getAlertResponse.getData();
                double radius = 0.01;

                for(AlertData alertData : alertsData){
                    if (alertData.getPonto() != null){
                        double centerLongitude = alertData.getPonto().getLongitude();
                        double centerLatitude = alertData.getPonto().getLatitude();

                        if(Math.pow((longitude - centerLongitude), 2) + Math.pow((latitude - centerLatitude), 2) < Math.pow(radius, 2)){
                            saveInsideFlood(context,"CONTAINS",true);
                            saveFloodName(context,"NAME", alertData.getDescricao());
                            return;
                        }
                    }
                }
                saveInsideFlood(context,"CONTAINS",false);
            }

            @Override
            public void onFailure(Call<GetAlertResponse> call, Throwable t) {

            }
        });
    }

    public void getRides(RecyclerView weekRecyclerView, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetRidesResponse> call = api.getRides("Bearer " + access_token);

        call.enqueue(new Callback<GetRidesResponse>() {
            @Override
            public void onResponse(Call<GetRidesResponse> call, Response<GetRidesResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetRidesResponse getRidesResponse = response.body();
                if (getRidesResponse == null || getRidesResponse.getData().size() < 1){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                Log.e(TAG, "Funciona carai");

                RidesAdapter adapter = new RidesAdapter( context,getRidesResponse.getData());
                weekRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetRidesResponse> call, Throwable t) {

            }
        });
    }
    public void getRidesMonth(RecyclerView monthRecyclerView, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetRidesResponse> call = api.getRides("Bearer " + access_token);

        call.enqueue(new Callback<GetRidesResponse>() {
            @Override
            public void onResponse(Call<GetRidesResponse> call, Response<GetRidesResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetRidesResponse getRidesResponse = response.body();
                if (getRidesResponse == null || getRidesResponse.getData().size() < 1){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                Log.e(TAG, "Funciona carai");

                RidesAdapter adapter = new RidesAdapter( context,getRidesResponse.getData());
                monthRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetRidesResponse> call, Throwable t) {

            }
        });
    }
    public void getRidesPast(RecyclerView pastRecyclerView, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetRidesResponse> call = api.getRides("Bearer " + access_token);

        call.enqueue(new Callback<GetRidesResponse>() {
            @Override
            public void onResponse(Call<GetRidesResponse> call, Response<GetRidesResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetRidesResponse getRidesResponse = response.body();
                if (getRidesResponse == null || getRidesResponse.getData().size() < 1){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }

                RidesAdapter adapter = new RidesAdapter( context,getRidesResponse.getData());
                pastRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetRidesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void saveInsideFlood(Context context, String key, boolean value){

        SharedPreferences sp = context.getSharedPreferences("INSIDE_FLOOD", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();

    }

    public void saveFloodName(Context context, String key, String value){

        SharedPreferences sp = context.getSharedPreferences("NAME_FLOOD", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();

    }

    public boolean getInsideFlood(Context context, String key){

        SharedPreferences sp = context.getSharedPreferences("INSIDE_FLOOD", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);

    }

    public void postMarker(Context context, double lat, double lng, String name){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetMarkersResponse> call = api.postMarker("Bearer " + access_token, lat, lng, name);

        call.enqueue(new Callback<GetMarkersResponse>() {
            @Override
            public void onResponse(Call<GetMarkersResponse> call, Response<GetMarkersResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetMarkersResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public void deleteMarker(Context context, String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetMarkersResponse> call = api.deleteMarker("Bearer " + access_token, id);

        call.enqueue(new Callback<GetMarkersResponse>() {
            @Override
            public void onResponse(Call<GetMarkersResponse> call, Response<GetMarkersResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                Log.d(TAG, response.body().getMessage());
            }

            @Override
            public void onFailure(Call<GetMarkersResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public void getRiskPoints(GoogleMap map, BitmapDescriptor bitmapDescriptor){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<RiskPointResponse> call = api.getRiskPoints("Bearer " + access_token);

        call.enqueue(new Callback<RiskPointResponse>() {
            @Override
            public void onResponse(Call<RiskPointResponse> call, Response<RiskPointResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                RiskPointResponse riskPointResponse = response.body();
                if (riskPointResponse == null){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                ArrayList<RiskPointData> dataArrayList = riskPointResponse.getData();
                for(RiskPointData data: dataArrayList){
                    if (data.getPonto() != null){
                        builder.include(new LatLng(data.getPonto().getLatitude(), data.getPonto().getLongitude()));
                        map.addMarker(new MarkerOptions().position(new LatLng(data.getPonto().getLatitude(), data.getPonto().getLongitude())).icon(bitmapDescriptor).title(data.getDescricao()));
                    }
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                map.animateCamera(cu);
            }
            @Override
            public void onFailure(Call<RiskPointResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getRiskPointsList(Context context, double longitude, double latitude){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<RiskPointResponse> call = api.getRiskPoints("Bearer " + access_token);

        call.enqueue(new Callback<RiskPointResponse>() {
            @Override
            public void onResponse(Call<RiskPointResponse> call, Response<RiskPointResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                RiskPointResponse riskPointResponse = response.body();
                if (riskPointResponse == null){
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                ArrayList<RiskPointData> dataArrayList = riskPointResponse.getData();
                double radius = 0.01;
                for(RiskPointData data: dataArrayList){
                    if (data.getPonto() != null){
                        double centerLongitude = data.getPonto().getLongitude();
                        double centerLatitude = data.getPonto().getLatitude();

                        if(Math.pow((longitude - centerLongitude), 2) + Math.pow((latitude - centerLatitude), 2) < Math.pow(radius, 2)){
                            //title.setText(data.getDistrito().getNome());
                            //desc.setText("Alagamento em " + data.getDescricao());
                            saveInsideRiskArea(context,"CONTAINS",true);
                            saveRiskAreaName(context, "NAME", data.getDescricao() + " está sujeita a alagamentos, baseado na previsão de hoje.");
                            return;
                        }
                    }
                    saveInsideRiskArea(context,"CONTAINS",false);
                }

            }

            @Override
            public void onFailure(Call<RiskPointResponse> call, Throwable t) {

            }
        });
    }

    public void saveInsideRiskArea(Context context, String key, boolean value){

        SharedPreferences sp = context.getSharedPreferences("INSIDE_RISK", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();

    }

    public void saveRiskAreaName(Context context, String key, String value){

        SharedPreferences sp = context.getSharedPreferences("INSIDE_RISK", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();

    }

}
