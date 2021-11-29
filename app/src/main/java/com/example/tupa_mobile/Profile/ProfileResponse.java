package com.example.tupa_mobile.Profile;

import java.util.ArrayList;

public class ProfileResponse {
    private Boolean succeeded;
    private String message;
    private ArrayList<String> errors;
    private ResponseDataProfile data;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ResponseDataProfile getData() {
        return data;
    }
}

