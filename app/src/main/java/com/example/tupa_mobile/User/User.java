package com.example.tupa_mobile.User;

public class User {
    private String id, username, email, password, contaBancaria, applicationUserID;
    private UserType tipoUsuario;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
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

    public UserType getTipoUsuario() {
        return tipoUsuario;
    }

    public String getContaBancaria() {
        return contaBancaria;
    }

    public String getApplicationUserID() {
        return applicationUserID;
    }
}
