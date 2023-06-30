package com.zrisan.my_finance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewAccount {
    private String name;
    @JsonProperty("current_balance")
    private double currentBalance;
    @JsonProperty("user_id")
    private int userId;
    private int id;

    public NewAccount() {
    }

    // Constructor
    public NewAccount(String name, double currentBalance, int userId, int id) {
        this.name = name;
        this.currentBalance = currentBalance;
        this.userId = userId;
        this.id = id;
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
