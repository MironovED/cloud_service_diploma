package ru.netology.exception;

/**
 * Выбрасываемое исключение при ошибке запроса
 */
public class ErrorInputDataException extends RuntimeException{

    public ErrorInputDataException() {
    }

    public ErrorInputDataException(String message) {
        super(message);
    }

}
