package com.example.tupa_mobile.BackgroundService;

import android.app.Application;
import android.content.Intent;

public class BackgroundApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, LocationService.class));
    }
}
