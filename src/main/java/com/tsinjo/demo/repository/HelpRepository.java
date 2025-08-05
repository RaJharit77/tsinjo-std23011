package com.tsinjo.demo.repository;

import com.tsinjo.demo.domain.Help;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRepository extends JpaRepository<Help, UUID> {
  List<Help> findAllByOrderByCreatedAtDesc();
}
