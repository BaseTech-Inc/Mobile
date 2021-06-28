package com.example.tupa_mobile.SettingsPage;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.AboutUsActivity;
import com.example.tupa_mobile.Activities.AccountActivity;
import com.example.tupa_mobile.Activities.ConnectionsActivity;
import com.example.tupa_mobile.Activities.HelpActivity;
import com.example.tupa_mobile.Activities.NotificationSettingsActivity;
import com.example.tupa_mobile.Activities.PrivacyActivity;
import com.example.tupa_mobile.Activities.ThemeSettingsActivity;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class SettingsCreator {

    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;

    public void createUserSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Conta", R.drawable.day_sunny, "Edson Koiti", new Intent(context, AccountActivity.class), false, true, true));
        settingsList.add( new Settings("Privacidade", R.drawable.clock_ilustraion_white_theme, "", new Intent(context, PrivacyActivity.class), false, true, true));
        settingsList.add( new Settings("Conexões", R.drawable.night_clear, "Outras intera...", new Intent(context, ConnectionsActivity.class), false, true, true));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);
    }

    public void createAppSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Notificações", R.drawable.day_sunny, "Todas", new Intent(context, NotificationSettingsActivity.class), false, true, true));
        settingsList.add( new Settings("Tema", R.drawable.clock_ilustraion_white_theme, "Tema Claro", new Intent(context, ThemeSettingsActivity.class), false, true, true));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createInfoSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sobre", R.drawable.day_sunny, "", new Intent(context, AboutUsActivity.class), false, true, true));
        settingsList.add( new Settings("Ajuda", R.drawable.clock_ilustraion_white_theme, "", new Intent(context, HelpActivity.class), false, true, true));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createAccountSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Nome de usuário", R.drawable.day_sunny, "Edson Koiti", new Intent(context, AboutUsActivity.class), false, false, true));
        settingsList.add( new Settings("Email", R.drawable.clock_ilustraion_white_theme, "edson@example.com", new Intent(context, HelpActivity.class), false, false, true));
        settingsList.add( new Settings("Telefone", R.drawable.clock_ilustraion_white_theme, "9****-1234", new Intent(context, HelpActivity.class), false, false, true));
        settingsList.add( new Settings("Mudar Senha", R.drawable.clock_ilustraion_white_theme, "", new Intent(context, HelpActivity.class), false, false, true));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createAccountManagementSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sair da sessão", R.drawable.day_sunny, "Edson Koiti", new Intent(context, AboutUsActivity.class), false, true, false));
        settingsList.add( new Settings("Excluir conta", R.drawable.clock_ilustraion_white_theme, "edson@example.com", new Intent(context, HelpActivity.class), false, true, false));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }


}
