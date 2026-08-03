package ru.netology.exception;

/**
 * Выбрасываемое исключение при процесса авторизации
 */
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
