package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class ForecastFragment extends Fragment {

    private RecyclerView cardRecyclerView;
    private ForecastDayAdapter adapter;
    private ArrayList<ForecastDay> forecasts;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        cardRecyclerView = view.findViewById(R.id.forecastRecycler);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Connection con = new Connection();
        con.requestOpenForecast(cardRecyclerView, view.getContext());

        return view;
    }
}