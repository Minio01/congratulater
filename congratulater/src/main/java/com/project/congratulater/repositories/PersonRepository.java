package com.project.congratulater.repositories;

import com.project.congratulater.entities.BirthdayPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<BirthdayPerson, Long> {

}
