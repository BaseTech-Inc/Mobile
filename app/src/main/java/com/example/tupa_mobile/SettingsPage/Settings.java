package com.example.tupa_mobile.SettingsPage;

public class Settings {
    private String title, imgSource;

    public Settings(String title, String imgSource) {
        this.title = title;
        this.imgSource = imgSource;
    }

    public String getTitle() {
        return title;
    }

    public String getImgSource() {
        return imgSource;
    }
}
