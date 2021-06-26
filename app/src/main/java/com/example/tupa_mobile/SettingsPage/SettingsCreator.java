package com.example.tupa_mobile.SettingsPage;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class SettingsCreator {

    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;

    public void createUserSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Conta", R.drawable.day_sunny, "Edson Koiti"));
        settingsList.add( new Settings("Privacidade", R.drawable.clock_ilustraion_white_theme, ""));
        settingsList.add( new Settings("Conexões", R.drawable.night_clear, "Outras intera..."));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);
    }

    public void createAppSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Notificações", R.drawable.day_sunny, "Todas"));
        settingsList.add( new Settings("Tema", R.drawable.clock_ilustraion_white_theme, "Tema Claro"));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createInfoSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sobre", R.drawable.day_sunny, ""));
        settingsList.add( new Settings("Ajuda", R.drawable.clock_ilustraion_white_theme, ""));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }
}
