package ru.netology.dto;

public class Auth {
    /** Логин пользователя */
    String login;
    /** Пароль пользователя*/
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
