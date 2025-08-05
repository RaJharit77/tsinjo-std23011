package com.tsinjo.demo.repository;

import com.tsinjo.demo.domain.Donor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
  Optional<Donor> findByEmail(String email);
}
