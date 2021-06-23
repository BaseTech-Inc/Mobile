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

import java.util.ArrayList;
public class SettingsFragment extends Fragment {

    private TextView txtResult;
    private RecyclerView userSettingsRecycler, appSettingsRecycler, infoSettingsRecycler;
    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;
    private ArrayList<OpenDaily> dailies;
    private OpenDailyAdapter openAdapter;

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
        userSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        appSettingsRecycler = view.findViewById(R.id.appSettingsRecycler);
        appSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        infoSettingsRecycler = view.findViewById(R.id.infoSettingsRecycler);
        infoSettingsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        settingsList = new ArrayList<>();
        adapter = new SettingsAdapter(view.getContext(), settingsList);

        userSettingsRecycler.setAdapter(adapter);
        appSettingsRecycler.setAdapter(adapter);
        infoSettingsRecycler.setAdapter(adapter);

        for(int i=0; i<4; i++) {
            createListData("Configuração exemplo", "");
        }


        return view;
    }

    public void createListData(String title, String imgSource) {
        // This method adds data to the recyclerView
        Settings settings = new Settings(title, imgSource);
        settingsList.add(settings);
    }
}