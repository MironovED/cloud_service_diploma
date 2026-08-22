package ru.netology.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выбрасываемое исключение при ошибке удаления файла
 */
@Slf4j
public class ErrorDeleteFileException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(ErrorDeleteFileException.class);

    public ErrorDeleteFileException() {
        log.error("Ошибка удаления файла");
    }

    public ErrorDeleteFileException(String message) {
        super(message);
    }
}
