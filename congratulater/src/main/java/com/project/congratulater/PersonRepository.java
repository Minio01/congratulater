package com.project.congratulater;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<BirthdayPerson, Long> {

}
