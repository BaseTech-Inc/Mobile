package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;

import com.example.tupa_mobile.Location.Location;
import com.example.tupa_mobile.Location.LocationAdapter;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class ForecastPopupActivity extends AppCompatActivity {

    private EditText editText;
    private RecyclerView locationsRecycler;
    private LocationAdapter adapter;
    private ArrayList<Location> locations;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_popup);
        editText = findViewById(R.id.etFindCities);
        locationsRecycler = findViewById(R.id.findCitiesRecycler);
        btnAdd = findViewById(R.id.button);
        btnAdd.setOnClickListener(v -> {

                String content = editText.getText().toString();
                insertItem(content);
        });
        }


    public void insertItem(String content){
//        locations.add(content);
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


}