package ru.netology.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выбрасываемое исключение при ошибке запроса
 */
@Slf4j
public class ErrorInputDataException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(ErrorInputDataException.class);

    public ErrorInputDataException() {
        log.error("Ошибка запроса");
    }

    public ErrorInputDataException(String message) {
        super(message);
    }

}
