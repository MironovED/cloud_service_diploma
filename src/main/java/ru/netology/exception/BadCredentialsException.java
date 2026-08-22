package ru.netology.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Выбрасываемое исключение при процесса авторизации
 */
public class BadCredentialsException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BadCredentialsException.class);

    public BadCredentialsException() {
        log.error("Ошибка авторизации");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
