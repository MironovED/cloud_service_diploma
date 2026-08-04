package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.entity.Auth;
import ru.netology.exception.BadCredentialsException;
import ru.netology.repository.CloudServiceRepository;

import java.security.SecureRandom;
import java.util.Optional;


@Service
public class CloudServiceService {
    private CloudServiceRepository cloudServiceRepository;

    public CloudServiceService(CloudServiceRepository cloudServiceRepository) {
        this.cloudServiceRepository = cloudServiceRepository;
    }

    /**
     * Метод авторизации и предоставления токена клиенту
     * @param   auth    логин и пароль пользователя
     * @return          токен в виде строки в 32 символа
     */
    public Optional<String> login(Auth auth) {
        if (cloudServiceRepository.checkUser(auth)) {
            var token = generateToken();
            try {
                cloudServiceRepository.addToken(token);
                return Optional.ofNullable(token);
            } catch (RuntimeException e) {
                throw new BadCredentialsException();
            }
        }
        return Optional.empty();
    }

    /**
     * Метод удаления сохраненного токена авторизации
     * @param token     действующий токен
     */
    public void logout(String token){
        cloudServiceRepository.remoteToken(getSplitToken(token));
    }

    /**
     * Метод генерации токена сессии
     * @return      сгенерированная строка длиной 32 символа
     */
    public String generateToken() {
        char[] charArray = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
        SecureRandom random = new SecureRandom();
        char[] token = new char[62];
        for(int i = 0; i < 62; i++) {
            int randomIndex = random.nextInt(32);
            token[i] = charArray[randomIndex];
        }
        return new String(token);
    }

    /**
     * Метод получения токена из значения хидера
     * @param rawToken      необработанный токен из хидера
     * @return              чистое значение токена
     */
    public String getSplitToken(String rawToken) {
        String[] splitToken = rawToken.split(" ");
        return splitToken[1];
    }

}
