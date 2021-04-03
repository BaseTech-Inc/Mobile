package com.example.tupa_mobile.ForecastPage;

public class ForecastDay {

    private int maxTemp, minTemp, humidity, sensation;
    private String location, date;

    //Constructor

    public ForecastDay(int maxTemp, int minTemp, int humidity, int sensation, String location, String date) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.sensation = sensation;
        this.location = location;
        this.date = date;
    }

    //Getter

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getSensation() {
        return sensation;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
