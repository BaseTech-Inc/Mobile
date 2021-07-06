package com.example.tupa_mobile.SettingsPage;

import android.content.Intent;

public class Settings {
    private String title, description;
    private int imgSource, type;
    private Intent intent;

    public Settings(String title, int imgSource, String description, Intent intent, int type) {
        this.title = title;
        this.description = description;
        this.imgSource = imgSource;
        this.type = type;
        this.intent = intent;
    }

    public String getTitle() {
        return title;
    }

    public int getImgSource() {
        return imgSource;
    }

    public String getDescription() {
        return description;
    }

    public Intent getIntent() {
        return intent;
    }

    public int getType() {
        return type;
    }
}
