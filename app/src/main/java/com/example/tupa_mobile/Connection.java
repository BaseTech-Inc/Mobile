package com.example.tupa_mobile;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.ForecastPage.ForecastDay;
import com.example.tupa_mobile.ForecastPage.ForecastDayAdapter;
import com.example.tupa_mobile.ForecastPage.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {

    private ForecastDayAdapter adapter;
    private ArrayList<ForecastDay> forecastsList;

    public void requestPosts(RecyclerView cardView, Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ForecastDay>> call = jsonPlaceHolderAPI.getForecasts();

        call.enqueue(new Callback<List<ForecastDay>>() {
            @Override
            public void onResponse(Call<List<ForecastDay>> call, Response<List<ForecastDay>> response) {

                if (response.isSuccessful() && response.body()!=null) {
                    forecastsList = new ArrayList<>(response.body());
                    adapter = new ForecastDayAdapter(context, forecastsList);
                    cardView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<ForecastDay>> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
