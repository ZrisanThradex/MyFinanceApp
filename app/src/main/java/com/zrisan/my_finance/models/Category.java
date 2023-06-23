package com.zrisan.my_finance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    private int id;
    private String name;
    private String icon;
    private int userId;

    public Category(){}

    public Category(@JsonProperty("id") int id,
                    @JsonProperty("name") String name,
                    @JsonProperty("icon") String icon,
                    @JsonProperty("user_id") int user_id) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.userId = user_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
