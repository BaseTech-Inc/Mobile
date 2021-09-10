package com.example.tupa_mobile.Connections;

import java.util.ArrayList;

public class UserResponse {
    private Boolean succeeded;
    private String message, data, username, email, password;
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
    public UserResponse(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
