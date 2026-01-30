package com.project.congratulater;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "persons")
@Entity
public class BirthdayPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Lob
    @Column(name ="photo")
    private String photoBase64;

    @Column(name = "content-type")
    private String contentType;

    public BirthdayPerson() {}

    public BirthdayPerson(Long id, String name, LocalDate birthday, String photoBase64, String contentType) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.photoBase64 = photoBase64;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}