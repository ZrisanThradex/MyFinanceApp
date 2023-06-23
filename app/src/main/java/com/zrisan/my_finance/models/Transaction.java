package com.zrisan.my_finance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private int id;
    @JsonProperty("type_transaction")
    private String typeTransaction;
    private double amount;
    private int category;
    @JsonProperty("date_transaction")
    private String dateTransaction;
    private String description;
    @JsonProperty("account_id")
    private int accountId;
    private Account account;


    public Transaction() {
    }

    // Constructor
    public Transaction(int id, String typeTransaction, double amount, String dateTransaction, String description, int accountId, Account account) {
        this.id = id;
        this.typeTransaction = typeTransaction;
        this.amount = amount;
        this.dateTransaction = dateTransaction;
        this.description = description;
        this.accountId = accountId;
        this.account = account;
    }


    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
    public int getCategory() {
        return category;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateTransaction() {
        return formatDate(dateTransaction);
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
