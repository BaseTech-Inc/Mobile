package com.example.tupa_mobile.Passwords;

import java.util.ArrayList;

public class ChangePasswordResponse {
    private Boolean succeeded;
    private String message, id, data, oldPass, newPass;
    private ArrayList<String> errors;

    public Boolean getSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public String getData() {
        return data;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
