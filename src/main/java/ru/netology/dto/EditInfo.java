package ru.netology.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditInfo {
    /** Новое имя файла */
    @JsonProperty("filename")
    private String name;

    public EditInfo() {
    }

    public EditInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
