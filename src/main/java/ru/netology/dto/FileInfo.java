package ru.netology.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Информация о файле, передаваемая клиенту для получения списка файлов
 */
public class FileInfo {                 //todo переделать
    /** Имя файла, хранящийся в файловой системе */
    @JsonProperty("filename")
    private String fileName;
    /** Размер файла */
    private int size;


    public FileInfo() {
    }

    public FileInfo(String fileName, int size) {
        this.fileName = fileName;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
