package com.example.tupa_mobile.Connections;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tupa_mobile.CurrentWeather.*;
import com.example.tupa_mobile.CurrentWeather.Weather;
import com.example.tupa_mobile.ForecastPage.ForecastDay;
import com.example.tupa_mobile.ForecastPage.ForecastDayAdapter;
import com.example.tupa_mobile.Fragments.HistoryFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

    private ForecastDayAdapter adapter;
    private ArrayList<ForecastDay> forecastsList;

    public void requestCurrentWeather(TextView txtResult, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Weather> call = jsonPlaceHolderApi.getCurrentWeather("0253a3a9322d4be2a5b174410211404", "São Paulo", "no");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body()!=null) {

                    Weather weather = response.body();
                    CurrentWeather currentWeather = weather.getCurrentWeather();

                    String content = "";
                    content += "last update: " + currentWeather.getLast_updated() + "\n";
                    content += "temperature: " + currentWeather.getTemp_c() + "\n";
                    content += "humidity: " + currentWeather.getHumidity();

                    txtResult.append(content);

                    /*for(CurrentWeather currentWeather : listWeathers) {
                        String content = "";
                        content += currentWeather.getLast_updated();
                        content += currentWeather.getTemp_c();
                        content += currentWeather.getHumidity();

                        txtResult.append(content);
                    }*/
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                txtResult.setText(t.getMessage());
            }
        });
    }
}
