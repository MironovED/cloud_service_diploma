package ru.netology.dto;

import jakarta.validation.constraints.NotBlank;

public class Auth {

    /** Логин пользователя */
    @NotBlank
    String login;

    /** Пароль пользователя*/
    @NotBlank
    String password;

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
