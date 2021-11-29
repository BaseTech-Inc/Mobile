package com.example.tupa_mobile.Profile;

import java.util.ArrayList;

public class AccountResponse {
    private Boolean succeeded;
    private String message, data;
    private ArrayList<String> errors;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
