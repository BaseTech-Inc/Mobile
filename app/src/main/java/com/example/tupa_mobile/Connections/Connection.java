package com.example.tupa_mobile.Connections;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.AccountActivity;
import com.example.tupa_mobile.Activities.LoginActivity;
import com.example.tupa_mobile.Activities.LoginOptionsActivity;
import com.example.tupa_mobile.Activities.MainActivity;
import com.example.tupa_mobile.Alerts.AlertAdapter;
import com.example.tupa_mobile.Alerts.AlertData;
import com.example.tupa_mobile.Alerts.GetAlerBairroResponse;
import com.example.tupa_mobile.Alerts.GetAlertResponse;
import com.example.tupa_mobile.Graph.ForecastGraph;
import com.example.tupa_mobile.Passwords.ChangePasswordResponse;
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
import com.example.tupa_mobile.Passwords.ResetPasswordResponse;
import com.example.tupa_mobile.Profile.AccountResponse;
import com.example.tupa_mobile.Profile.ImageResponse;
import com.example.tupa_mobile.Profile.ProfileResponse;
import com.example.tupa_mobile.Profile.PutProfileResponse;
import com.example.tupa_mobile.Rides.GetRidesResponse;
import com.example.tupa_mobile.Rides.RidesAdapter;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private String token;
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
    private ArrayList<Entry> lineList, lineList2;
    private boolean isRiskNotificationActive = false;
    private boolean isAlertNotificationActive = false;

    public void requestCurrentWeather(TextView currentLocation, TextView condition, TextView temperature, TextView humidity, TextView pressure, TextView wind, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getCurrentWeather(weatherApiKey, position, "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body() != null) {

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

    public void requestForecast(RecyclerView recyclerView, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getForecast(weatherApiKey, position, 3, "no", "no");

        call.enqueue(new Callback<Weather>() {

            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body() != null) {

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

    public void requestOpenForecast(RecyclerView recyclerView, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<OpenWeather> call = API.getOpenForecastDaily(lat, lon, openApiKey, "metric");

        call.enqueue(new Callback<OpenWeather>() {

            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if (response.isSuccessful() && response.body() != null) {

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

    public void requestHourForecast(RecyclerView recyclerView, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<Weather> call = API.getForecast(weatherApiKey, position, 1, "no", "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body() != null) {

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

    public void requestGraphOpenForecast(LineChart forecastChart, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<OpenWeather> call = API.getOpenForecastDaily(lat, lon, openApiKey, "metric");

        call.enqueue(new Callback<OpenWeather>() {

            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if (response.isSuccessful() && response.body() != null) {

                    openWeather = response.body();
                    openDaily = new ArrayList<>(openWeather.getDaily());

                    ArrayList<Entry> MaxTemps = new ArrayList<>();
                    ArrayList<Entry> MinTemps = new ArrayList<>();
                    int i = 1;

                    for(OpenDaily daily: openDaily) {
                        MaxTemps.add(new Entry(i, (float) daily.getTemp().getMax()));
                        MinTemps.add(new Entry(i, (float) daily.getTemp().getMin()));

                        i++;
                    }

                    ForecastGraph forecastGraph = new ForecastGraph();
                    forecastGraph.createGraph(forecastChart, context, MaxTemps, MinTemps);

                    Log.d(TAG, MaxTemps.toString());
                }
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }


    public void postRoute(TextView txtResponse, Context context) {

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

    public void requestCurrentAddress(Context context, EditText editText, double latitude, double longitude) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API API = retrofit.create(API.class);

        Call<GeoCodingResponse> call = API.getCurrentAddress(routeApiKey, longitude, latitude, 1);

        call.enqueue(new Callback<GeoCodingResponse>() {
            @Override
            public void onResponse(Call<GeoCodingResponse> call, Response<GeoCodingResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

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

    public void registerUser(Context context, String username, String email, String password) {
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
                ((Activity) context).startActivity(it);
                ((Activity) context).finish();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    public void loginUser(Context context, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<LoginResponse> call = api.postLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();

                    sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", loginResponse.getData().getAccess_token());
                    editor.apply();

                    Intent it = new Intent(context, MainActivity.class);
                    ((Activity) context).startActivity(it);
                    ((Activity) context).finish();

                } else {
                    Toast.makeText(((Activity) context), "Não bro, não é assim 😭", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    public void getMarkers(Context context, RecyclerView bottomDrawerRecycler, ViewGroup emptyMarkersLayout, CustomAdapterClickListener clickListener, String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetMarkersResponse> call = api.getMarkers("Bearer " + getToken(context), userId);

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
                if (markersResponse.getData() == null || markersResponse.getData().size() < 1) {
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

    public void getAlerts(Context context, GoogleMap map, int year, int month, int day) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetAlertResponse> call = api.getAlerts("Bearer " + getToken(context), year, month, day);

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
                if (getAlertResponse == null) {
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                alertsData = getAlertResponse.getData();
                for (AlertData alertData : alertsData) {
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

    public void getAlertsList(Context context, int year, int month, int day, double longitude, double latitude, int ALERT_ID, String ALERT_CHANNEL_ID, NotificationManager notificationManager) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetAlertResponse> call = api.getAlerts("Bearer " + getToken(context), year, month, day);

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
                if (getAlertResponse == null) {
                    Log.d(TAG, "Data attribute is null");
                    return;
                }
                alertsData = getAlertResponse.getData();
                double radius = 0.01;

                for (AlertData alertData : alertsData) {
                    double centerLongitude = alertData.getPonto().getLongitude();
                    double centerLatitude = alertData.getPonto().getLatitude();

                    if (Math.pow((longitude - centerLongitude), 2) + Math.pow((latitude - centerLatitude), 2) < Math.pow(radius, 2)) {
                        saveInsideFlood(context, "CONTAINS", true);
                        return;
                    }
                }
                saveInsideFlood(context, "CONTAINS", false);
            }

            @Override
            public void onFailure(Call<GetAlertResponse> call, Throwable t) {

            }
        });
    }

    public void getRides(RecyclerView weekRecyclerView, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<GetRidesResponse> call = api.getRides("Bearer " + getToken(context));

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
                    Log.d(TAG, "Data attribute is null1");
                    return;
                }
                Log.e(TAG, "Funciona carai1");

                RidesAdapter adapter = new RidesAdapter(context, getRidesResponse.getData());
                weekRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetRidesResponse> call, Throwable t) {

            }
        });
    }





    public void saveInsideFlood(Context context, String key, boolean value) {

        SharedPreferences sp = context.getSharedPreferences("INSIDE_FLOOD", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();

    }

    public boolean getInsideFlood(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences("INSIDE_FLOOD", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);

    }

    public void getAlerBairro(RecyclerView weekRecyclerView, Context context){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);
          
           Call<GetAlerBairroResponse> call = api.getAlertBairro("Bearer " + getToken(context), 2021, 1, 1,"Casa Verde");

        call.enqueue(new Callback<GetAlerBairroResponse>() {
            @Override
            public void onResponse(Call<GetAlerBairroResponse> call, Response<GetAlerBairroResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                    return;
                }
                GetAlerBairroResponse getAlerBairroResponse = response.body();
                if (getAlerBairroResponse == null || getAlerBairroResponse.getData().size() < 1){
                    Log.e(TAG, "Data attribute is null4");
                    Log.d(TAG, response.body().getMessage());
                    return;
                }
                Log.e(TAG, "Funciona carai4");
                Log.d(TAG, String.valueOf(getAlerBairroResponse));
                AlertAdapter adapter = new AlertAdapter( context,getAlerBairroResponse.getData());
                weekRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetAlerBairroResponse> call, Throwable t) {
                Log.e(TAG, "fudeu");
              Log.e(TAG, t.getMessage());
                Log.e(TAG, t.toString());
            }
        });
    }


    public void ResetPassword(Context context, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);
          
        Call<ResetPasswordResponse> call = api.postPassword(email);

        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    Intent it = new Intent(context, LoginOptionsActivity.class);
                    ((Activity) context).startActivity(it);
                    ((Activity) context).finish();
                    Log.d(TAG, email);
                } else {
                    Log.d(TAG, "Error");
                    Log.d(TAG, response.message());
                    Log.d(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Log.e(TAG, "Master Error");
             }
        });
    }
    


    public void ChangePassword(Context context, String oldPass, String newPass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);
      
        Call<ChangePasswordResponse> call = api.postChangePassword("Bearer " + getToken(context), oldPass, newPass);

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();

                    Log.d(TAG, oldPass);
                    Log.d(TAG, newPass);

                    Intent it = new Intent(context, LoginActivity.class);
                    ((Activity) context).startActivity(it);
                    ((Activity) context).finish();

                } else {
                    Log.d(TAG, "Error");
                    Log.e(TAG, response.message());
                    Log.e(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Log.e(TAG, "Master Error");
                Log.e(TAG, t.getMessage());
                Log.e(TAG, t.toString());
            }
        });
    }

    public void LoadInfoProfile(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<ProfileResponse> call = api.getProfile("Bearer " + getToken(context));

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccessful()){
                    if(response != null){

                        sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", response.body().getData().getEmail());
                        editor.putString("name", response.body().getData().getName());
                        editor.putString("tipo", response.body().getData().getTipoUsuario());
                        editor.apply();

                    }else {
                        Log.d(TAG, "Is null");
                        Log.e(TAG, response.message());
                        Log.e(TAG, response.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e(TAG, "Deu algum erro");
                Log.e(TAG, t.getMessage());
                Log.e(TAG, t.toString());
            }
        });
    }

    public void SendInfoProfile(Context context, String name, String tipo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<PutProfileResponse> call = api.putProfile("Bearer " + getToken(context), name, tipo);

        call.enqueue(new Callback<PutProfileResponse>() {
            @Override
            public void onResponse(Call<PutProfileResponse> call, Response<PutProfileResponse> response) {
                if(response.isSuccessful()){
                    Intent it = new Intent(context, MainActivity.class);
                    ((Activity) context).startActivity(it);
                    ((Activity) context).finish();

                }else{
                    Log.d("Buda", "Is null");
                    Log.e("Buda", response.message());
                    Log.e("Buda", response.toString());
                }
            }

            @Override
            public void onFailure(Call<PutProfileResponse> call, Throwable t) {

            }
        });
    }

    public void LoadImageProfile(Context context, CircleImageView profile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);
  
        Call<ImageResponse> call = api.getImageProfile("Bearer " + getToken(context));

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {

                    Log.d("Morte", response.body().getData());

                    byte[] decodedString = Base64.decode(response.body().getData(), Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profile.setImageBitmap(image);


                }else {
                    Log.d("Ala", "Algo errado");
                    Log.e("Ala", response.message());
                    Log.e("Ala", response.toString());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.e("Errado", t.getMessage());
                Log.e("Erradíssimo", t.toString());
            }
        });
    }

    public void SendImageProfile(Context context, String encoded) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<ImageResponse> call = api.putImageProfile("Bearer " + getToken(context), encoded);

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if(response.isSuccessful())
                {
                    sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("image", encoded);
                    editor.apply();
                    Log.d("Ivo", "Deu certo");
                }else{

                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("Ivo", "Fudeu enorme");
                Log.e("Ivo", t.getMessage());
            }
        });
    }

    public void DeleteAccount(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tupaserver.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<AccountResponse> call = api.deleteAccount("Bearer " + getToken(context));

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                ((Activity) context).finishAffinity();
                System.exit(0);


            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {

            }
        });
    }

    public String getToken(Context context){

        sp = context.getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        token = sp.getString("token", null);

        return token;
    }
}
