package ru.netology.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выбрасываемое исключение при ошибке авторизации
 */
public class UnauthorizedErrorException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(UnauthorizedErrorException.class);

    public UnauthorizedErrorException() {
        log.error("Ошибка авторизации - пользователь не авторизован");
    }

    public UnauthorizedErrorException(String message) {
        super(message);
    }
}
