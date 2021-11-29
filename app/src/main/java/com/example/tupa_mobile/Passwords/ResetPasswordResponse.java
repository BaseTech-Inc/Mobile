package com.example.tupa_mobile.Passwords;

import java.util.ArrayList;

public class ResetPasswordResponse {
        private Boolean succeeded;
        private String message, email, data;
        private ArrayList<String> errors;

        public Boolean getSucceeded() {
            return succeeded;
        }

        public String getMessage() {
            return message;
        }

        public String getEmail() {
            return email;
        }

        public String getData() {
            return data;
        }

        public ArrayList<String> getErrors() {
            return errors;
        }



}
