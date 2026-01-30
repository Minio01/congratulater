package com.project.congratulater;

import java.time.LocalDate;

public class BirthdayPersonResponse {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String photoBase64;
    private String contentType;

    public BirthdayPersonResponse(Long id, String name, LocalDate birthday, String photoBase64, String contentType) {
    }
}
