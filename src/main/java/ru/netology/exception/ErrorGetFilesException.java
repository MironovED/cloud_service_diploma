package ru.netology.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выбрасываемое исключение при ошибке получения списка файлов
 */
@Slf4j
public class ErrorGetFilesException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(ErrorGetFilesException.class);

    public ErrorGetFilesException() {
        log.error("Ошибка получения списка файлов");
    }

    public ErrorGetFilesException(String message) {
        super(message);
    }
}
