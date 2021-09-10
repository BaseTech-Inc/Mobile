package com.example.tupa_mobile.Connections;

import java.util.ArrayList;

public class LoginResponse {
    private Boolean succeeded;
    private String message, email, password;
    private ArrayList<String> errors;
    private ResponseData data;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ResponseData getData() {
        return data;
    }
}

