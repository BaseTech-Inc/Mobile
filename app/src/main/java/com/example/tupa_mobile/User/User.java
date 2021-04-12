package com.example.tupa_mobile.User;

public class User {

    private int userId;
    private String name, email, password, bankAccount;
    private boolean verified;

    public User(int userId, String name, String email, String password, String bankAccount, boolean verified) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bankAccount = bankAccount;
        this.verified = verified;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public boolean isVerified() {
        return verified;
    }
}
