package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.SelectorSettings;
import com.example.tupa_mobile.SettingsPage.SelectorSettingsAdapter;

import java.util.ArrayList;

public class ThemeSettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private RecyclerView themeRecycler;
    private ArrayList<SelectorSettings> settingsArray;
    private SelectorSettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_settings);

        toolbar = findViewById(R.id.notificationToolbar);
        toolbar.setTitle("Temas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_right_icon_white_black_theme_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createOptions();
    }

    private void createOptions(){

        themeRecycler = findViewById(R.id.themeRecycler);
        themeRecycler.setLayoutManager(new LinearLayoutManager(this));
        settingsArray = new ArrayList<>();

        settingsArray.add(new SelectorSettings("White", true));
        settingsArray.add(new SelectorSettings("Dark", false));
        settingsArray.add(new SelectorSettings("Dark Dimmed", false));

        adapter = new SelectorSettingsAdapter(this, settingsArray);
        themeRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}