package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.dto.Auth;
import ru.netology.dto.AuthToken;

import java.util.Optional;

@Repository
public class CloudServiceRepository {

    public Boolean checkUser(Auth auth) {
        return true;
    }

    public Boolean checkToken(AuthToken authToken) {
        return true;
    }

    public void addToken(String token) {

    }

    public void remoteToken(String token) {

    }
}
