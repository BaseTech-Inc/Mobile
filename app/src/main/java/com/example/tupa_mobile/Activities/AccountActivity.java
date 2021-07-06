package com.example.tupa_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.SettingsPage.SettingsCreator;

public class AccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private RecyclerView userAccountRecycler, accountManagementRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        toolbar = findViewById(R.id.notificationToolbar);
        toolbar.setTitle("Conta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_right_icon_white_black_theme_small);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userAccountRecycler = findViewById(R.id.userAccountRecycler);
        accountManagementRecycler = findViewById(R.id.accountManagementRecycler);

        SettingsCreator creator = new SettingsCreator();
        creator.createAccountSettings(getApplicationContext(), userAccountRecycler);
        creator.createAccountManagementSettings(getApplicationContext(), accountManagementRecycler);
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