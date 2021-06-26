package com.example.tupa_mobile.SettingsPage;

public class Settings {
    private String title, description;
    private int imgSource;

    public Settings(String title, int imgSource, String description) {
        this.title = title;
        this.imgSource = imgSource;
        this.description = description;
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
}
