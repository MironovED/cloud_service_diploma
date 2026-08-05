package ru.netology.exception;

/**
 * Выбрасываемое исключение при ошибке удаления файла
 */
public class ErrorDeleteFileException extends RuntimeException {

    public ErrorDeleteFileException() {
    }

    public ErrorDeleteFileException(String message) {
        super(message);
    }
}
