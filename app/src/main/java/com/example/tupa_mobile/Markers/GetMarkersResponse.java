package com.example.tupa_mobile.Markers;

import java.util.ArrayList;

public class GetMarkersResponse {

    private Boolean succeeded;
    private String message;
    private ArrayList<MarkersData> data;
    private ArrayList<String> errors;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MarkersData> getData() {
        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
