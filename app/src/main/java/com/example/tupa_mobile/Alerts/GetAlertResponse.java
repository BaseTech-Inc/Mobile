package com.example.tupa_mobile.Alerts;

import com.example.tupa_mobile.Markers.MarkersData;

import java.util.ArrayList;

public class GetAlertResponse {

    private Boolean succeeded;
    private String message;
    private ArrayList<AlertData> data;
    private ArrayList<String> errors;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<AlertData> getData() {
        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
