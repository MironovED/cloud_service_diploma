package ru.netology.dto;

/**
 * ДТО файла для загрузки с сервера
 */
public class FileDto {
    /** Хэш файла */
    private String hash;
    /** Бинарник файла в виде строки */
    private String file;

    public FileDto() {
    }

    public FileDto(String hash, byte[] fileByte) {
        this.hash = hash;
        this.file = java.util.Base64.getEncoder().encodeToString(fileByte);;
    }

    public String getFile() {
        return file;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
