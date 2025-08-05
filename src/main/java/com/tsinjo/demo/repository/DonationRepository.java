package com.tsinjo.demo.repository;

import com.tsinjo.demo.domain.Donation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
  List<Donation> findAllByOrderByCreatedAtDesc();
}
