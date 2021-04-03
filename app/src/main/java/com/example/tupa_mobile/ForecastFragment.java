package com.example.tupa_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tupa_mobile.ForecastPage.ForecastDay;
import com.example.tupa_mobile.ForecastPage.ForecastDayAdapter;

import java.util.ArrayList;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
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

        recyclerView = view.findViewById(R.id.normalRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        forecasts = new ArrayList<>();
        adapter = new ForecastDayAdapter(view.getContext(), forecasts);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),
                LinearLayoutManager.VERTICAL));

        for(int i=0; i<7; i++) {
            createListData(50, 20, 35, 70, "Terminal Santana", "dom., 14 de março 16:28");
        }
        return view;
    }

    public void createListData(int maxTemp, int minTemp, int humidity, int sensation, String location, String date) {
        // This method adds data to the recyclerView
        ForecastDay forecastDay = new ForecastDay(maxTemp, minTemp, humidity, sensation, location, date);
        forecasts.add(forecastDay);
    }
}