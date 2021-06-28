package com.example.tupa_mobile.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Activities.GraphActivity;
import com.example.tupa_mobile.R;

public class ForecastFragment extends Fragment {

    private RecyclerView cardRecyclerView, hourRecyclerView;
    private TextView weatherPlace, weatherCondition, weatherTemp;
    private Button buttonGraph;

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

        weatherPlace = view.findViewById(R.id.weatherPlace);
        weatherCondition = view.findViewById(R.id.weatherCondition);
        weatherTemp = view.findViewById(R.id.weatherTemp);

        buttonGraph = view.findViewById(R.id.buttonGraph);
        buttonGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGraphActivity();
            }
        });

        hourRecyclerView = view.findViewById(R.id.hourRecycler);
        hourRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        cardRecyclerView = view.findViewById(R.id.forecastRecycler);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Connection con = new Connection();
        con.requestCurrentWeather(weatherPlace, weatherCondition, weatherTemp, view.getContext());
        con.requestHourForecast(hourRecyclerView, view.getContext());
        con.requestOpenForecast(cardRecyclerView, view.getContext());

        return view;
    }

    private void startGraphActivity(){
        Intent intent = new Intent(getContext(), GraphActivity.class);
        startActivity(intent);
    }
}