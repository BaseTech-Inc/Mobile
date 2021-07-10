package com.example.tupa_mobile.Fragments;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tupa_mobile.Activities.ForecastPopupActivity;
import com.example.tupa_mobile.Activities.NotificationActivity;
import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.PreferencesFragment;
import com.example.tupa_mobile.SettingsPage.Settings;
import com.example.tupa_mobile.SettingsPage.SettingsAdapter;
import com.example.tupa_mobile.SettingsPage.SettingsCreator;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class SettingsFragment extends Fragment {

    private RecyclerView userSettingsRecycler, appSettingsRecycler, infoSettingsRecycler;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar toolbar;
    private MenuItem notificationItem;
    private ViewGroup searchLayout;
    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;

    public SettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        searchLayout = view.findViewById(R.id.searchLayout);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse);
        mCollapsingToolbarLayout.setTitleEnabled(false);

        toolbar = view.findViewById(R.id.mainToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Configurações");

        userSettingsRecycler = view.findViewById(R.id.userSettingsRecycler);
        appSettingsRecycler = view.findViewById(R.id.appSettingsRecycler);
        infoSettingsRecycler = view.findViewById(R.id.infoSettingsRecycler);

        SettingsCreator creator = new SettingsCreator();
        creator.createUserSettings(view.getContext(), userSettingsRecycler);
        creator.createAppSettings(view.getContext(), appSettingsRecycler);
        creator.createInfoSettings(view.getContext(), infoSettingsRecycler);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_menu, menu);
        notificationItem = menu.findItem(R.id.notificationItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        startNotificationActivity();
        return true;
    }

    private void startNotificationActivity() {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        startActivity(intent);
    }

}