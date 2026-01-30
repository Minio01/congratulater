package com.project.congratulater.services;

import com.project.congratulater.entities.BirthdayPerson;
import com.project.congratulater.entities.BirthdayPersonRequest;
import com.project.congratulater.entities.BirthdayPersonResponse;
import com.project.congratulater.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<BirthdayPersonResponse> getAllPersons() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public BirthdayPersonResponse createPerson(BirthdayPersonRequest request) {
        validate(request);

        BirthdayPerson person = new BirthdayPerson();
        updateEntity(person, request);
        repository.save(person);

        return toResponse(person);
    }

    public BirthdayPersonResponse updatePerson(Long id, BirthdayPersonRequest request) {
        validate(request);

        BirthdayPerson person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Person with id " + id + " not found"));

        updateEntity(person, request);
        repository.save(person);

        return toResponse(person);
    }

    public void deletePerson(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Person with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    public List<BirthdayPersonResponse> getToday() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(p -> p.getBirthday().getMonth() == today.getMonth()
                        && p.getBirthday().getDayOfMonth() == today.getDayOfMonth())
                .map(this::toResponse)
                .toList();
    }

    public List<BirthdayPersonResponse> getUpcoming(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("Days must be >= 0");
        }

        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);

        return repository.findAll().stream()
                .filter(p -> {
                    LocalDate next = p.getBirthday().withYear(today.getYear());
                    if (next.isBefore(today)) next = next.plusYears(1);
                    return !next.isAfter(limit);
                })
                .sorted(Comparator.comparing(p -> {
                    LocalDate next = p.getBirthday().withYear(today.getYear());
                    if (next.isBefore(today)) next = next.plusYears(1);
                    return next;
                }))
                .map(this::toResponse)
                .toList();
    }

    private void validate(BirthdayPersonRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (request.getBirthday() == null) {
            throw new IllegalArgumentException("Birthday cannot be null");
        }
        if (request.getPhotoBase64() == null || request.getPhotoBase64().isBlank()) {
            throw new IllegalArgumentException("Photo cannot be empty");
        }
        if (request.getContentType() == null || request.getContentType().isBlank()) {
            throw new IllegalArgumentException("Content type cannot be empty");
        }
        if (!request.getBirthday().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday must be earlier than today");
        }
    }

    private void updateEntity(BirthdayPerson person, BirthdayPersonRequest request) {
        person.setName(request.getName());
        person.setBirthday(request.getBirthday());
        person.setPhotoBase64(request.getPhotoBase64());
        person.setContentType(request.getContentType());
    }

    private BirthdayPersonResponse toResponse(BirthdayPerson person) {
        return new BirthdayPersonResponse(
                person.getId(),
                person.getName(),
                person.getBirthday(),
                person.getPhotoBase64(),
                person.getContentType()
        );
    }
}