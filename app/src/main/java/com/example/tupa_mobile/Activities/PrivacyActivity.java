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

public class PrivacyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem markerItem, addItem, notificationItem;
    private RecyclerView privacyRec, securityRec, adsRec, personalDataRec, legalRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        toolbar = findViewById(R.id.notificationToolbar);
        toolbar.setTitle("Privacidade");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        privacyRec = findViewById(R.id.privacySettingsRecycler);
        securityRec = findViewById(R.id.protectionSettingsRecycler);
        adsRec = findViewById(R.id.adsSettingsRecycler);
        personalDataRec = findViewById(R.id.yourInfoSettingsRecycler);
        legalRec = findViewById(R.id.legalSettingsRecycler);

        SettingsCreator creator = new SettingsCreator();
        creator.createPrivacySettings(this, privacyRec);
        creator.createProtectionSettings(this, securityRec);
        creator.createAdsSettings(this, adsRec);
        creator.createPersonalInfoSettings(this, personalDataRec);
        creator.createLegalSettings(this,legalRec);
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