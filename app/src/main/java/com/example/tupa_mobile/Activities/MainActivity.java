package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupa_mobile.Fragments.SettingsFragment;
import com.example.tupa_mobile.Fragments.ForecastFragment;
import com.example.tupa_mobile.Fragments.HistoryFragment;
import com.example.tupa_mobile.Fragments.MapFragment;
import com.example.tupa_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private int ItemsList = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, new MapFragment()).commit();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper_menu, menu);

        markerItem = menu.findItem(R.id.markerItem);
        notificationItem = menu.findItem(R.id.notificationItem);
        addItem = menu.findItem(R.id.addItem);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);

        switch (ItemsList) {

            case 1:
                toolbar.setTitle(R.string.Mapa);
                markerItem.setVisible(true);
                notificationItem.setVisible(true);
                addItem.setVisible(false);
                return true;

            case 2:
                toolbar.setTitle(R.string.Historico);
                markerItem.setVisible(false);
                notificationItem.setVisible(true);
                addItem.setVisible(false);
                return true;

            case 3:
                toolbar.setTitle(R.string.Previsao);
                markerItem.setVisible(false);
                notificationItem.setVisible(true);
                addItem.setVisible(true);
                return true;

            case 4:
                toolbar.setTitle(R.string.Configuracoes);
                markerItem.setVisible(false);
                notificationItem.setVisible(false);
                addItem.setVisible(false);
                return true;

            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.notificationItem:
                startNotificationActivity();

                return true;

            case R.id.markerItem:

                return true;

            case R.id.addItem:
                startForecastPopup();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setMapToolbar(){

        ItemsList = 1;
        invalidateOptionsMenu();

    }

    public void setHistoryToolbar(){

        ItemsList = 2;
        invalidateOptionsMenu();

    }

    public void setForecastToolbar(){

        ItemsList = 3;
        invalidateOptionsMenu();
    }

    public void setSettingsToolbar(){

        ItemsList = 4;
        invalidateOptionsMenu();

    }

    private void startNotificationActivity() {
        int selectedFragment = bottomNav.getSelectedItemId();

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("currentFragment", selectedFragment);
        startActivityForResult(intent, 1);
    }

    private void startForecastPopup() {

        BlurView blur = findViewById(R.id.blur);
        View decorView = getWindow().getDecorView();
        Drawable windowBackground = decorView.getBackground();
        ViewGroup viewGroup = decorView.findViewById(R.id.blurred);

        blur.setupWith(viewGroup)
                .setFrameClearDrawable(windowBackground)
                .setBlurEnabled(true)
                .setBlurAutoUpdate(true)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(10);

        Intent intent = new Intent(this, ForecastPopupActivity.class);
        startActivity(intent);
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.item1:
                    selectedFragment = new MapFragment();
                    setMapToolbar();
                break;

                case R.id.item2:
                    selectedFragment = new HistoryFragment();
                    setHistoryToolbar();
                break;

                case R.id.item3:
                    selectedFragment = new ForecastFragment();
                    setForecastToolbar();
                break;

                case R.id.item4:
                    selectedFragment = new SettingsFragment();
                    setSettingsToolbar();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, selectedFragment).commit();
            return true;

        }
    };
}