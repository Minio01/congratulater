package com.project.congratulater.entities;

import java.time.LocalDate;

public class BirthdayPersonResponse {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String photoBase64;
    private String contentType;

    public BirthdayPersonResponse(Long id, String name, LocalDate birthday, String photoBase64, String contentType) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.photoBase64 = photoBase64;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public String getContentType() {
        return contentType;
    }
}
