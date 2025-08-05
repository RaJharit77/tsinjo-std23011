package com.tsinjo.demo.repository;

import com.tsinjo.demo.domain.Payment;
import com.tsinjo.demo.domain.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
  List<Payment> findByStatus(PaymentStatus status);
}
