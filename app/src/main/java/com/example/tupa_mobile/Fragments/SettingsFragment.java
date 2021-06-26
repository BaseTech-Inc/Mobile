package com.example.tupa_mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tupa_mobile.OpenWeather.OpenDaily;
import com.example.tupa_mobile.OpenWeather.OpenDailyAdapter;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.PreferencesFragment;
import com.example.tupa_mobile.SettingsPage.Settings;
import com.example.tupa_mobile.SettingsPage.SettingsAdapter;
import com.example.tupa_mobile.SettingsPage.SettingsCreator;

import java.util.ArrayList;
public class SettingsFragment extends Fragment {

    private RecyclerView userSettingsRecycler, appSettingsRecycler, infoSettingsRecycler;
    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        userSettingsRecycler = view.findViewById(R.id.userSettingsRecycler);
        appSettingsRecycler = view.findViewById(R.id.appSettingsRecycler);
        infoSettingsRecycler = view.findViewById(R.id.infoSettingsRecycler);

        SettingsCreator creator = new SettingsCreator();
        creator.createUserSettings(view.getContext(), userSettingsRecycler);
        creator.createAppSettings(view.getContext(), appSettingsRecycler);
        creator.createInfoSettings(view.getContext(), infoSettingsRecycler);

        return view;
    }
}