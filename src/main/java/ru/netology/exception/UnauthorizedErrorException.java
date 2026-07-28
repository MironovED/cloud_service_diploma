package ru.netology.exception;

/**
 * Выбрасываемое исключение при ошибке авторизации
 */
public class UnauthorizedErrorException extends RuntimeException{

    public UnauthorizedErrorException() {
    }

    public UnauthorizedErrorException(String message) {
        super(message);
    }
}
