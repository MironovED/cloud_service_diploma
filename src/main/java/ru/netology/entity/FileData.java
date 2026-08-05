package ru.netology.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"filesdata\"", schema = "cloudservice")
public class FileData {
    /** Хэш файла */
    @Id
    private String hash;
    /** Путь до файла в файловой системе */
    private String path;

    public FileData() {
    }

    public FileData(String hash, String file) {
        this.hash = hash;
        this.path = file;
    }

    public String getHash() {
        return hash;
    }

    public String getPath() {
        return path;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
