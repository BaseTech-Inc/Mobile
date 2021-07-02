package com.example.tupa_mobile.Notifications;

public class Notification {

    private int icon;
    private String title, date, description;
    private boolean active;

    public Notification(int icon, String title, String date, String description, boolean active) {
        this.icon = icon;
        this.title = title;
        this.date = date;
        this.description = description;
        this.active = active;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }
}
