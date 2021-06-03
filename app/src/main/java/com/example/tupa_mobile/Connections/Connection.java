package com.example.tupa_mobile.Connections;

import android.content.Context;
import android.content.Entity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.OpenWeather.OpenWeather;
import com.example.tupa_mobile.Route.Coordinates;
import com.example.tupa_mobile.Route.Metadata;
import com.example.tupa_mobile.Route.Route;
import com.example.tupa_mobile.Route.RouteResponse;
import com.example.tupa_mobile.WeatherAPI.CurrentWeather;
import com.example.tupa_mobile.WeatherAPI.Day;
import com.example.tupa_mobile.WeatherAPI.Forecast;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;
import com.example.tupa_mobile.WeatherAPI.ForecastHour;
import com.example.tupa_mobile.WeatherAPI.ForecastHourAdapter;
import com.example.tupa_mobile.WeatherAPI.Weather;
import com.google.android.gms.common.api.Api;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

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
    private CurrentWeather currentWeather;
    private Weather weather;
    private Forecast forecast;
    private Day day;
    private OpenWeather openWeather;
    private ArrayList<OpenDaily> openDaily;
    private OpenDailyAdapter openAdapter;


    public void requestCurrentWeather(TextView txtResult, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Weather> call = jsonPlaceHolderApi.getCurrentWeather(weatherApiKey, position, "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    weather = response.body();
                    currentWeather = weather.getCurrentWeather();

                    String content = "";
                    content += "last update: " + currentWeather.getTemp_c();
                    content += "temperature: " + currentWeather.getFeelslike_c();
                    content += "humidity: " + currentWeather.getHumidity();

                    txtResult.append(content);
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

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Weather> call = jsonPlaceHolderApi.getForecast(weatherApiKey, position, 3, "no", "no");

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

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<OpenWeather> call = jsonPlaceHolderApi.getOpenForecastDaily(lat, lon, openApiKey, "metric");

        call.enqueue(new Callback<OpenWeather>() {

            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    openWeather = response.body();
                    openDaily = new ArrayList<>(openWeather.getDaily());
                    openAdapter = new OpenDailyAdapter(context, openDaily);
                    recyclerView.setAdapter(openAdapter);

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

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Weather> call = jsonPlaceHolderApi.getForecast(weatherApiKey, position, 1,"no", "no");

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

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

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

        Call<RouteResponse> call = jsonPlaceHolderApi.postRoute(body);

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

                Log.d("failure", String.valueOf(t));

            }
        });
    }
}
