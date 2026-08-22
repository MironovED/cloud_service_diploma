package ru.netology.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Выбрасываемое исключение при процесса авторизации
 */
@Slf4j
public class BadCredentialsException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(BadCredentialsException.class);

    public BadCredentialsException() {
        log.error("Ошибка авторизации");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
