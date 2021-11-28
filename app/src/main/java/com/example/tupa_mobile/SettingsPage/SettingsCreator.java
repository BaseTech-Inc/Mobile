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

        settingsList.add( new Settings("Conta", R.drawable.ic_person_focused, "Edson Koiti", new Intent(context, AccountActivity.class), 0));
        settingsList.add( new Settings("Privacidade", R.drawable.ic_lock_black_24dp, "", new Intent(context, PrivacyActivity.class), 0));
        settingsList.add( new Settings("Conexões", R.drawable.ic_link_black_24dp, "Outras intera...", new Intent(context, ConnectionsActivity.class), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);
    }

    public void createAppSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Notificações", R.drawable.ic_notifications, "Todas", new Intent(context, NotificationSettingsActivity.class), 0));
        settingsList.add( new Settings("Tema", R.drawable.ic_palette_black_24dp, "Tema Claro", new Intent(context, ThemeSettingsActivity.class), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createInfoSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sobre", R.drawable.ic_info_black_24dp, "", new Intent(context, AboutUsActivity.class), 0));
        settingsList.add( new Settings("Ajuda", R.drawable.ic_help_black_24dp, "", new Intent(context, HelpActivity.class), 0));

        adapter = new SettingsAdapter(context, settingsList);

        recyclerView.setAdapter(adapter);

    }

    public void createAccountSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Nome de usuário", R.drawable.day_sunny, "Edson Koiti", new Intent(context, AboutUsActivity.class), 2));
        settingsList.add( new Settings("Email", R.drawable.clock_ilustraion_white_theme, "edson@example.com", new Intent(context, HelpActivity.class), 2));
        settingsList.add( new Settings("Telefone", R.drawable.clock_ilustraion_white_theme, "9****-1234", new Intent(context, HelpActivity.class), 2));
        settingsList.add( new Settings("Mudar Senha", R.drawable.clock_ilustraion_white_theme, "", new Intent(context, HelpActivity.class), 2));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createAccountManagementSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Sair da sessão", R.drawable.day_sunny, "Edson Koiti", new Intent(context, AboutUsActivity.class), 3));
        settingsList.add( new Settings("Excluir conta", R.drawable.clock_ilustraion_white_theme, "edson@example.com", new Intent(context, HelpActivity.class), 3));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createPrivacySettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Exemplo", R.drawable.day_sunny, "...", new Intent(context, AboutUsActivity.class), 4));
        settingsList.add( new Settings("Exemplo", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createProtectionSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Atualizar dados da Conta", R.drawable.day_sunny, "...", new Intent(context, AboutUsActivity.class), 4));
        settingsList.add( new Settings("Autenticação em dois Fatores", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));
        settingsList.add( new Settings("Verificar Acessos", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createAdsSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Permitir Coleta de Dados", R.drawable.day_sunny, "...", new Intent(context, AboutUsActivity.class), 4));
        settingsList.add( new Settings("Gerenciar Preferência de Anúncios", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createPersonalInfoSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Exemplo", R.drawable.day_sunny, "...", new Intent(context, AboutUsActivity.class), 4));
        settingsList.add( new Settings("Exemplo", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }

    public void createLegalSettings(Context context, RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        settingsList = new ArrayList<>();

        settingsList.add( new Settings("Políticas de Privacidade", R.drawable.day_sunny, "...", new Intent(context, AboutUsActivity.class), 4));
        settingsList.add( new Settings("Termos de Uso", R.drawable.clock_ilustraion_white_theme, "...", new Intent(context, HelpActivity.class), 4));

        adapter = new SettingsAdapter(context, settingsList);
        recyclerView.setAdapter(adapter);
    }
}
