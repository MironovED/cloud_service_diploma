package ru.netology.exception;

/**
 * Выбрасываемое исключение при ошибке получения файла с сервера
 */
public class ErrorUploadFileException extends RuntimeException{

    public ErrorUploadFileException() {
    }

    public ErrorUploadFileException(String message) {
        super(message);
    }
}
