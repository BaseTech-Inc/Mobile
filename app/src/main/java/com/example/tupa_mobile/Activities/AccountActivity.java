package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.SettingsCreator;

public class AccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private RecyclerView userAccountRecycler, accountManagementRecycler;
    private TextView lblLogout, lblDeleteAccount;
    private SharedPreferences sp;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        lblLogout = findViewById(R.id.lblLogout);
        lblDeleteAccount = findViewById(R.id.lblDeleteAccount);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.notificationToolbar);
        toolbar.setTitle("Conta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_right_icon_white_black_theme_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userAccountRecycler = findViewById(R.id.userAccountRecycler);
        accountManagementRecycler = findViewById(R.id.accountManagementRecycler);

        SettingsCreator creator = new SettingsCreator();
        creator.createAccountSettings(this, userAccountRecycler);
        //creator.createAccountManagementSettings(getApplicationContext(), accountManagementRecycler);

        lblLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent it = new Intent(AccountActivity.this, LoginOptionsActivity.class);
                startActivity(it);
                finishAffinity();
            }
        });

        lblDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                name = sp.getString("name", "");

                AlertDialog.Builder confDelete = new AlertDialog.Builder(AccountActivity.this);
                confDelete.setTitle("Excluir conta");
                confDelete.setMessage("Ao excluir a conta, todos os seus dados e configurações serão apagadas. Você tem certeza que deseja fazer isso, " + name + "?");
                confDelete.setCancelable(false);
                confDelete.setNegativeButton("Não", null);
                confDelete.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Connection connection = new Connection();
                        connection.DeleteAccount(AccountActivity.this);
                    }
                });
                confDelete.create().show();
            }
        });


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