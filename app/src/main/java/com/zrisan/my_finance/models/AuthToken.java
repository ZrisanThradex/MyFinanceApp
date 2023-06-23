package com.zrisan.my_finance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {
    private TokenInfo token;

    public TokenInfo getToken() {
        return token;
    }

    public void setToken(TokenInfo token) {
        this.token = token;
    }

    public static class TokenInfo {
        private String type;
        private String token;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
