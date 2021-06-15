package com.example.tupa_mobile.Graph;

import android.widget.ImageView;

public class GraphDayItem {

    private String weekDay, date, humidity, chanceOfRain;

    public GraphDayItem(String weekDay, String date, String humidity, String chanceOfRain) {
        this.weekDay = weekDay;
        this.date = date;
        this.humidity = humidity;
        this.chanceOfRain = chanceOfRain;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getDate() {
        return date;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getChanceOfRain() {
        return chanceOfRain;
    }
}
