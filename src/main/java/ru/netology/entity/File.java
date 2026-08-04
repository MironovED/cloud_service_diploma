package ru.netology.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"files\"", schema = "cloudservice")
public class File {
    /** Хэш файла */
    @Id
    private String hash;
    /** Путь до файла в файловой системе */
    private String file;

    public File() {
    }

    public File(String file, String hash) {
        this.file = file;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public String getFile() {
        return file;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
