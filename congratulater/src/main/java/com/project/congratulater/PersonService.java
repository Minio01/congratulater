package com.project.congratulater;

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

    public BirthdayPersonResponse createPerson(
            BirthdayPersonRequest request
            ) {
        BirthdayPerson person = new BirthdayPerson();
        updateEntity(person, request);
        repository.save(person);
        return toResponse(person);
    }

    public BirthdayPersonResponse updatePerson(
            Long id,
            BirthdayPersonRequest request
    ) {
        BirthdayPerson person = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        updateEntity(person, request);
        repository.save(person);
        return toResponse(person);
    }

    public void deletePerson(
            Long id
        ) {
        repository.deleteById(id);
    }

    public List<BirthdayPersonResponse> getToday() { //Заменить название
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(p -> p.getBirthday().getMonth() == today.getMonth()
                        && p.getBirthday().getDayOfMonth() == today.getDayOfMonth())
                .map(this::toResponse) .toList();
    }

    public List<BirthdayPersonResponse> getUpcoming(int days) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);
        return repository.findAll().stream()
            .filter(p -> {
                LocalDate next = p.getBirthday().withYear(today.getYear());
                if (next.isBefore(today)) {
                    next = next.plusYears(1);
                }
                return !next.isAfter(limit);})
            .sorted(Comparator.comparing(p ->
            { LocalDate next = p.getBirthday().withYear(today.getYear());
                if (next.isBefore(today)) next = next.plusYears(1);
                return next;
            }))
            .map(this::toResponse)
            .toList();
    }

    private void updateEntity(
            BirthdayPerson person, BirthdayPersonRequest request
    ) {
        person.setName(request.getName());
        person.setBirthday(request.getBirthday());
        person.setPhotoBase64(request.getPhotoBase64());
        person.setContentType(request.getContentType());
    }

    private BirthdayPersonResponse toResponse(
            BirthdayPerson person
    ) {
        BirthdayPersonResponse r = new BirthdayPersonResponse(
            person.getId(),
            person.getName(),
            person.getBirthday(),
            person.getPhotoBase64(),
            person.getContentType()
        );
        return r;
    }

}
