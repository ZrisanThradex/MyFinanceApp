package com.zrisan.my_finance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int id;
    private String name;
    @JsonProperty("current_balance")
    private String currentBalance;
    @JsonProperty("user_id")
    private int userId;

    public Account() {
        // Constructor por defecto requerido por Jackson
    }

    @Override
    public String toString() {
        return String.format("%-20s %s", name, currentBalance);
    }

    // Constructor
    public Account(int id, String name, String current_balance, int user_id) {
        this.id = id;
        this.name = name;
        this.currentBalance = current_balance;
        this.userId = user_id;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String current_balance) {
        this.currentBalance = current_balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }
}
