package com.example.tupa_mobile.RiskPoints;

import java.util.ArrayList;

public class RiskPointResponse {

    private boolean succeeded;
    private String message;
    private ArrayList<String> errors;
    private ArrayList<RiskPointData> data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ArrayList<RiskPointData> getData() {
        return data;
    }
}
