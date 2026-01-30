package com.project.congratulater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

public interface PersonRepository extends JpaRepository<BirthdayPerson, Long> {

}
