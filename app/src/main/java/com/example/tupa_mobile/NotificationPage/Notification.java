package com.example.tupa_mobile.NotificationPage;

public class Notification {

    private String date, alertMessage;

    public Notification(String date, String alertMessage) {
        this.date = date;
        this.alertMessage = alertMessage;
    }

    public String getDate() {
        return date;
    }

    public String getAlertMessage() {
        return alertMessage;
    }
}
