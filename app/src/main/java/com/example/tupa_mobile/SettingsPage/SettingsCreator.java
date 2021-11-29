package com.example.tupa_mobile.SettingsPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Activities.AboutUsActivity;
import com.example.tupa_mobile.Activities.AccountActivity;
import com.example.tupa_mobile.Activities.ChangeUsernameActivity;
import com.example.tupa_mobile.Activities.ConnectionsActivity;
import com.example.tupa_mobile.Activities.HelpActivity;
import com.example.tupa_mobile.Activities.LoginOptionsActivity;
import com.example.tupa_mobile.Activities.NotificationSettingsActivity;
import com.example.tupa_mobile.Activities.ThemeSettingsActivity;
import com.example.tupa_mobile.Passwords.ChangePasswordActivity;
import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class SettingsCreator {

    private ArrayList<Settings> settingsList;
    private SettingsAdapter adapter;
    private SharedPreferences sp;


    public void createUserSettings(Context context, RecyclerView recyclerView){
        sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Conta", R.drawable.day_sunny, sp.getString("name", ""), new Intent(context, AccountActivity.class), 0));
        settingsList.add( new Settings("Conexões", R.drawable.night_clear, "Em breve", new Intent(context, ConnectionsActivity.class), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);
    }

    public void createAppSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Tema", R.drawable.clock_ilustraion_white_theme, "Tema Claro", new Intent(context, ThemeSettingsActivity.class), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createInfoSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        Uri uriAbout = Uri.parse("https://tupaweb.azurewebsites.net/About");
        Uri uriHelp = Uri.parse("https://tupaweb.azurewebsites.net/Settings/Help");
        Uri uriTerms = Uri.parse("https://tupaweb.azurewebsites.net/Terms");
        Uri uriPriv = Uri.parse("https://tupaweb.azurewebsites.net/Privacy");

        settingsList.add( new Settings("Sobre", R.drawable.day_sunny, "", new Intent(Intent.ACTION_VIEW, uriAbout), 0));
        settingsList.add( new Settings("Ajuda", R.drawable.clock_ilustraion_white_theme, "", new Intent(Intent.ACTION_VIEW, uriHelp), 0));
        settingsList.add( new Settings("Termos de Uso", R.drawable.day_sunny,"", new Intent(Intent.ACTION_VIEW, uriTerms), 0));
        settingsList.add( new Settings("Políticas de Privacidade", R.drawable.day_sunny, "", new Intent(Intent.ACTION_VIEW, uriPriv), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createAccountSettings(Context context, RecyclerView recyclerView){
        sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Nome", R.drawable.day_sunny, sp.getString("name", ""), new Intent(context, ChangeUsernameActivity.class), 2));
        settingsList.add( new Settings("Email", R.drawable.clock_ilustraion_white_theme, sp.getString("email", ""), new Intent(context, HelpActivity.class), 2));
        settingsList.add( new Settings("Mudar Senha", R.drawable.clock_ilustraion_white_theme, "******", new Intent(context, ChangePasswordActivity.class), 2));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createAccountManagementSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sair da sessão", R.drawable.day_sunny, "", new Intent(context, LoginOptionsActivity.class), 3));
        settingsList.add( new Settings("Excluir conta", R.drawable.clock_ilustraion_white_theme, "", new Intent(context, HelpActivity.class), 3));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }
}
