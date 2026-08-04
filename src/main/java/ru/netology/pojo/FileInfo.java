package ru.netology.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Информация о файле, передаваемая клиенту
 */
public class FileInfo {
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
