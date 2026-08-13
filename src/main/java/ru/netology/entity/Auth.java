package ru.netology.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(schema = "cloudservice")
public class Auth {
    /** Логин пользователя */
    @NotBlank
    @Id
    String login;

    /** Пароль пользователя*/
    @NotBlank
    String password;

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Auth() {
    }

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
