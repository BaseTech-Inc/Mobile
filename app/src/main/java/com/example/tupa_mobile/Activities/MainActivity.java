package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tupa_mobile.BackgroundService.LocationService;
import com.example.tupa_mobile.Fragments.SettingsFragment;
import com.example.tupa_mobile.Fragments.ForecastFragment;
import com.example.tupa_mobile.Fragments.HistoryFragment;
import com.example.tupa_mobile.Fragments.MapFragment;
import com.example.tupa_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private EditText etSearch;
    private String email, password;
    private SharedPreferences sp;
    private int ItemsList = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        email = sp.getString("email", null);
        password = sp.getString("password", null);

        if(email != null || email != null){
            Log.d("Deus", sp.getString("email", email));
            Log.d("Deus", sp.getString("password", password));
        }

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new MapFragment()).commit();
    }

    private void startNotificationActivity() {
        int selectedFragment = bottomNav.getSelectedItemId();

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("currentFragment", selectedFragment);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BlurView blur = findViewById(R.id.blur);
        blur.setBlurEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int currentFragment = data.getIntExtra("result", 0);
                bottomNav.setSelectedItemId(currentFragment);
            }
        }
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomNavigationContainer));

        if (count == 0) {
            super.onBackPressed();
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return;
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
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
                    selectedFragment = new SettingsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, selectedFragment).commit();
            return true;

        }
    };
}