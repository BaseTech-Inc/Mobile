package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.SettingsCreator;

public class AccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private RecyclerView userAccountRecycler, accountManagementRecycler;
    private TextView lblLogout, lblDeleteAccount;
    private SharedPreferences sp;

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
                Intent it = new Intent(getBaseContext(), LoginOptionsActivity.class);
                startActivity(it);
                finish();
            }
        });

        lblDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Excluiu a conta, kk", Toast.LENGTH_SHORT);
                /*Intent it = new Intent(getBaseContext(), LoginOptionsActivity.class);
                startActivity(it);
                finish();*/
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