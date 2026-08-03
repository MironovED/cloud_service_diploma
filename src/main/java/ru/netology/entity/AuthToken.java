package ru.netology.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "\"authtoken\"", schema = "cloudservice")
public class AuthToken {
    /** Токен для идентификации пользователся */
    @Id
    @JsonProperty("auth-token")
    private String token;

    public AuthToken() {
    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
