package com.example.tupa_mobile.SettingsPage;

import android.content.Intent;

public class Settings {
    private String title, description;
    private int imgSource;
    private Intent intent;
    private boolean hasSwitch, hasIcon, showArrow;

    public Settings(String title, int imgSource, String description, Intent intent, boolean hasSwitch, boolean hasIcon, boolean showArrow) {
        this.title = title;
        this.description = description;
        this.imgSource = imgSource;
        this.intent = intent;
        this.hasSwitch = hasSwitch;
        this.hasIcon = hasIcon;
        this.showArrow = showArrow;
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

    public boolean hasSwitch() {
        return hasSwitch;
    }

    public boolean showArrow() {
        return showArrow;
    }

    public boolean hasIcon() {
        return hasIcon;
    }
}
