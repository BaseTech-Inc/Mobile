package com.example.tupa_mobile.Location;

import com.example.tupa_mobile.Rides.Rides;

import java.util.ArrayList;

public class GetLocationResponse {
    private boolean succeded;
    private String message;
    private ArrayList<String> errors ;
    private ArrayList<Location> data;

    public boolean getSucceded() {
        return succeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ArrayList<Location> getData() {
        return data;
    }
}
