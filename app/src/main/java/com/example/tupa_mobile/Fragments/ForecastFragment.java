package com.example.tupa_mobile.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tupa_mobile.Activities.ForecastPopupActivity;
import com.example.tupa_mobile.Activities.NotificationActivity;
import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.Activities.GraphActivity;
import com.example.tupa_mobile.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ForecastFragment extends Fragment {

    private RecyclerView cardRecyclerView, hourRecyclerView;
    private TextView location, condition, temp, humidity, pressure, wind;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar toolbar;
    private MenuItem addItem, notificationItem;
    private Button buttonGraph;

    public ForecastFragment() {
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

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse);
        mCollapsingToolbarLayout.setTitleEnabled(false);

        toolbar = view.findViewById(R.id.mainToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Previsão");

        location = view.findViewById(R.id.weatherPlace);
        condition = view.findViewById(R.id.weatherCondition);
        temp = view.findViewById(R.id.weatherTemp);
        humidity = view.findViewById(R.id.txtHumidity);
        pressure = view.findViewById(R.id.txtPressure);
        wind = view.findViewById(R.id.txtWind);

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
        con.requestCurrentWeather(location, condition, temp, humidity, pressure, wind, view.getContext());
        con.requestHourForecast(hourRecyclerView, view.getContext());
        con.requestOpenForecast(cardRecyclerView, view.getContext());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast_menu, menu);
        addItem = menu.findItem(R.id.addItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()){
            case R.id.addItem:
                startForecastPopup();
                break;
        }
        return true;
    }

    private void startForecastPopup() {

        BlurView blur = ((Activity)getContext()).findViewById(R.id.blur);
        View decorView = ((AppCompatActivity)getActivity()).getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();
        ViewGroup viewGroup = decorView.findViewById(R.id.blurred);

        blur.setupWith(viewGroup)
                .setFrameClearDrawable(windowBackground)
                .setBlurEnabled(true)
                .setBlurAutoUpdate(true)
                .setBlurAlgorithm(new RenderScriptBlur(getContext()))
                .setBlurRadius(10);

        Intent intent = new Intent(getContext(), ForecastPopupActivity.class);
        startActivity(intent);
    }

    private void startGraphActivity(){
        Intent intent = new Intent(getContext(), GraphActivity.class);
        startActivity(intent);
    }
}