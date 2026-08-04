package ru.netology.exception;

/**
 * Выбрасываемое исключение при ошибке получения списка файлов
 */
public class ErrorGetFilesException extends RuntimeException {

    public ErrorGetFilesException() {
    }

    public ErrorGetFilesException(String message) {
        super(message);
    }
}
