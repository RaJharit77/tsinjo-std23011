package com.tsinjo.demo.repository;

import com.tsinjo.demo.domain.Person;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, UUID> {
  Optional<Person> findByEmail(String email);
}
