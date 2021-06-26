package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tupa_mobile.Alerts.Alert;
import com.example.tupa_mobile.Alerts.AlertAdapter;
import com.example.tupa_mobile.SettingsPage.Settings;
import com.example.tupa_mobile.WeatherAPI.ForecastDay;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class AlertFragment extends Fragment {

    private RecyclerView weekRecyclerView, monthRecyclerView, pastRecyclerView;
    private AlertAdapter adapter;
    private ArrayList<Alert> alerts;

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);

        weekRecyclerView = view.findViewById(R.id.weekAlertRecycler);
        monthRecyclerView = view.findViewById(R.id.monthAlertRecycler);
        pastRecyclerView = view.findViewById(R.id.previousAlertRecycler);

        alerts = new ArrayList<>();

        for(int i=0; i<3; i++) {
            createListData("Alerta de Alagamento", "Avenida Bla Bli Blop 333");
        }

        adapter = new AlertAdapter(view.getContext(), alerts);

        weekRecyclerView.setAdapter(adapter);
        weekRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        monthRecyclerView.setAdapter(adapter);
        monthRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pastRecyclerView.setAdapter(adapter);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public void createListData(String date, String desc) {
        // This method adds data to the recyclerView
        Alert alert = new Alert(date, desc);
        alerts.add(alert);
    }
}