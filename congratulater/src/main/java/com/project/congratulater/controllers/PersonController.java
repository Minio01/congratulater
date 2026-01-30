package com.project.congratulater.controllers;

import com.project.congratulater.services.PersonService;
import com.project.congratulater.entities.BirthdayPersonRequest;
import com.project.congratulater.entities.BirthdayPersonResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/birthdays")
public class PersonController {
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<BirthdayPersonResponse>> getAllPersons() {
        log.info("Called getBirthdays");
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @PostMapping
    public ResponseEntity<BirthdayPersonResponse> createPerson(
            @RequestBody @Valid BirthdayPersonRequest personRequest
    ) {
        log.info("Called createPerson");
        log.info("DTO name = {}", personRequest.getName());
        log.info("DTO photo = {}", personRequest.getPhotoBase64());

        return ResponseEntity.ok(personService.createPerson(personRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BirthdayPersonResponse> updatePerson(
            @PathVariable("id") Long id,
            @RequestBody @Valid BirthdayPersonRequest personRequest
    ) {
        log.info("Called updatePerson");
        return ResponseEntity.ok(personService.updatePerson(id, personRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BirthdayPersonResponse> deletePerson(
            @PathVariable("id") Long id
    ) {
        log.info("Called deletePerson");
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today")
    public List<BirthdayPersonResponse> getToday() {
        return personService.getToday();
    }

    @GetMapping("/upcoming")
    public List<BirthdayPersonResponse> getUpcoming(
            @RequestParam(defaultValue = "7") int days
    ) {
        return personService.getUpcoming(days);
    }

}
