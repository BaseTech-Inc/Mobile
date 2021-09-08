package com.example.tupa_mobile.Rides;

public class Rides {
    private String distance, time, events, floods, imageSource;
    private int map;

    public Rides(String distance, String time, int map) {
        this.distance = distance;
        this.time = time;
        this.map = map;
    }
    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }
        public int getMap() {
            return map;
        }

}


