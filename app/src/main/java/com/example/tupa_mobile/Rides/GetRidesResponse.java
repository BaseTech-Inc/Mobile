package com.example.tupa_mobile.Rides;

import java.util.ArrayList;

public class GetRidesResponse {
    private boolean succeded;
    private String message;
    private ArrayList<String> errors ;
    private ArrayList<Rides> data;

    public boolean getSucceded() {
        return succeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ArrayList<Rides> getData() {
        return data;
    }
}
