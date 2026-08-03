package ru.netology.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {
    /** Токен для идентификации пользователся */
    @JsonProperty("auth-token")
    private String authToken;

    public AuthToken() {
    }

    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
