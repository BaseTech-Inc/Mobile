package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tupa_mobile.Location.Location;
import com.example.tupa_mobile.Location.LocationAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

import static com.example.tupa_mobile.R.id.blur;

public class ForecastPopupActivity extends AppCompatActivity {

    private EditText editText;
    private RecyclerView locationsRecycler;
    private LocationAdapter adapter;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_popup);

        editText = findViewById(R.id.etFindCities);

        locationsRecycler = findViewById(R.id.findCitiesRecycler);
        locationsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        locations = new ArrayList<>();
        adapter = new LocationAdapter(getApplicationContext(), locations);
        locationsRecycler.setAdapter(adapter);

        setWindowLayout();
        createLocations();
    }

    private void setWindowLayout(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.85), (int) (height*0.8));

        Drawable background = ContextCompat.getDrawable(this, R.drawable.cardview_borders);
        getWindow().setBackgroundDrawable(background);
    }

    private void createLocations(){
        locations.add(new Location("São Paulo", "Nublado", 20.0));
        locations.add(new Location("Rio de Janeiro", "Ensolarado", 32.0));
        locations.add(new Location("São josé do Rio Pardo", "Nevando", -2.0));
    }
}