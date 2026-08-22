package ru.netology.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выбрасываемое исключение при ошибке получения файла с сервера
 */
@Slf4j
public class ErrorUploadFileException extends RuntimeException{

    private static final Logger log = LoggerFactory.getLogger(ErrorUploadFileException.class);

    public ErrorUploadFileException() {
        log.error("Ошибка получения файла с сервера");
    }

    public ErrorUploadFileException(String message) {
        super(message);
    }
}
