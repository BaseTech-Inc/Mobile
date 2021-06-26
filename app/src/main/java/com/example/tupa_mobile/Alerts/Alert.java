package com.example.tupa_mobile.Alerts;

public class Alert {

    private String date, title, description, address, imageSource;

    public Alert(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getImageSource() {
        return imageSource;
    }
}
