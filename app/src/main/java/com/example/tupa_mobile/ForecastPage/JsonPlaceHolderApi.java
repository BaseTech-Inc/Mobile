package com.example.tupa_mobile.ForecastPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("weather")
    Call<List<ForecastDay>> getForecasts();
}
