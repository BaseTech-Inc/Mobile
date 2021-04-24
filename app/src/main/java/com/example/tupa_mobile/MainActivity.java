package com.example.tupa_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tupa_mobile.Fragments.SettingsFragment;
import com.example.tupa_mobile.Fragments.ForecastFragment;
import com.example.tupa_mobile.Fragments.HistoryFragment;
import com.example.tupa_mobile.Fragments.MapFragment;
import com.example.tupa_mobile.Fragments.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new MapFragment()).commit();

        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tupã");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.secondary_menu, menu);

        return true;
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.item1:
                    selectedFragment = new MapFragment();
                break;

                case R.id.item2:
                    selectedFragment = new HistoryFragment();
                break;

                case R.id.item3:
                    selectedFragment = new ForecastFragment();
                break;

                case R.id.item4:
                    selectedFragment = new NotificationFragment();
                    break;

                case R.id.item5:
                    selectedFragment = new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, selectedFragment).commit();
            return true;

        }
    };
}