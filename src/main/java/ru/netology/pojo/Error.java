package ru.netology.pojo;

/**
 * Объект ошибки, возвращаемый клиенту
 */
public class Error {
    /** Сообщение, что за ошибка */
    private String message;
    /** Идентификатор */
    private int id;

    public Error() {
    }

    public Error(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
