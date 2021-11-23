package com.example.tupa_mobile.Alerts;

import com.example.tupa_mobile.Rides.Rides;

import java.util.ArrayList;

public class GetAlerBairroResponse {
    private boolean succeded;
    private String message;
    private ArrayList<String> errors ;
    private ArrayList<AlerBairro> data;

    public boolean getSucceded() {
        return succeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ArrayList<AlerBairro> getData() {
        return data;
    }
}